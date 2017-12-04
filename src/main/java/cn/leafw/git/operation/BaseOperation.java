package cn.leafw.git.operation;

import cn.leafw.git.dto.GitCpConfig;
import org.eclipse.jgit.api.CherryPickCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.errors.IllegalTodoFileModification;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @description 合并代码工具
 * @author CareyWYR
 */
public class BaseOperation {

    private static final Logger LOGGER  = LoggerFactory.getLogger(BaseOperation.class);

    private GitCpConfig gitCpConfig;

    public BaseOperation(GitCpConfig gitCpConfig) {
        this.gitCpConfig = gitCpConfig;
    }

    public BaseOperation() {
    }

    /**
     * @description 合并代码
     */
    public void codeCheeryPick() throws Exception{
        LOGGER.info("****合并代码开始,当前分支:{},远程分支:{}****",gitCpConfig.getCurrentBranch(),gitCpConfig.getRemoteBranch());
        Repository repository = new FileRepository(gitCpConfig.getProjectFileDir());
        Git git = new Git(repository);
        RevWalk currentRevWalk = showLogSafe(gitCpConfig.getCurrentBranch());
        RevWalk remoteRevWalk = showLogSafe(gitCpConfig.getRemoteBranch());
        List<ObjectId> currentList = new ArrayList<>();
        currentRevWalk.forEach(e -> {
            System.out.println(e.getId());
            currentList.add(e.getId());
        });
        List<ObjectId> remoteList = new ArrayList<>();
        remoteRevWalk.forEach(e -> {
            System.out.println(e.getId());
            remoteList.add(e.getId());
        });
        List<ObjectId> diffCommit = remoteList.stream().filter(e -> !currentList.contains(e)).collect(Collectors.toList());
        CherryPickCommand cherryPickCommand = git.cherryPick();
        for (ObjectId objectId : diffCommit) {
            LOGGER.info("commit: {}",objectId.getName());
            cherryPickCommand.include(objectId).call().;
            git.add().addFilepattern(".").call();
            git.commit().setMessage(objectId.getName());
        }

        CredentialsProvider cp = new UsernamePasswordCredentialsProvider("","");
        git.push().setCredentialsProvider(cp).call();


    }

    /**
     * 查看日志
     * @throws Exception
     */
    public void showLog()throws Exception{
        //获取当前仓库
        Repository repository = new FileRepository(gitCpConfig.getProjectFileDir());
        //初始化git命令
        Git git = new Git(repository);
        //show log
        Iterable<RevCommit> logs =  git.log().call();
        for (RevCommit log : logs) {
            LOGGER.info("commit " + log.getName());
            LOGGER.info("Author:{},{}", log.getAuthorIdent().getName(), log.getAuthorIdent().getEmailAddress());
            LOGGER.info("Date: " + LocalDateTime.ofEpochSecond(log.getCommitTime(), 0, ZoneOffset.UTC));
            LOGGER.info("\t" + log.getShortMessage() + "\n");
        }
    }

    /**
     * 显示日志 Log 命令内部使用的 RevWalk 是永远不会关闭，此方法相对安全
     * @throws Exception
     */
     public RevWalk showLogSafe(String branch) throws Exception{
         Repository repository = new FileRepository(gitCpConfig.getProjectFileDir());
         RevWalk revWalk = new RevWalk(repository);
         ObjectId commitId = repository.resolve(branch);
         revWalk.markStart(revWalk.parseCommit(commitId));
         return revWalk;
     }

    /**
     * 更新代码
     * @throws Exception
     */
    public void pullRebase() throws Exception{
        //获取当前仓库
        Repository repository = new FileRepository(gitCpConfig.getProjectFileDir());
        //初始化git命令
        Git git = new Git(repository);
        //stash
        git.stashCreate().call();
        //fetch
        git.fetch().setCheckFetchedObjects(true).call();
        //设置rebase规则
        RebaseCommand.InteractiveHandler handler = new RebaseCommand.InteractiveHandler() {
            @Override
            public void prepareSteps(List<RebaseTodoLine> steps) {
                // the handler receives the list of commits that are rebased, i.e. the ones on the local branch
                for(RebaseTodoLine step : steps) {
                    // for each step, you can decide which action should be taken
                    // default is PICK
                    try {
                        // by selecting "EDIT", the rebase will stop and ask you to edit the commit-contents
                        step.setAction(RebaseTodoLine.Action.EDIT);
                    } catch (IllegalTodoFileModification e) {
                        throw new IllegalStateException(e);
                    }
                }
            }

            @Override
            public String modifyCommitMessage(String oldMessage) {
                return oldMessage;
            }
        };
        //rebase
        git.rebase().setUpstream("origin/master").runInteractively(handler).call();
        Collection<RevCommit> stashList = git.stashList().call();
        if(null != stashList && stashList.size() > 0){
            git.stashApply().call();
        }
        //将stash清除
        git.stashDrop().setStashRef(0).call();
    }
}
