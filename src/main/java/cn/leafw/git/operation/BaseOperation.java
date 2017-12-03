package cn.leafw.git.operation;

import cn.leafw.git.dto.GitCpConfig;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
     * 更新代码
     * @throws Exception
     */
    public void pullRebase() throws Exception{
        //获取当前仓库
        Repository repository = new FileRepository(gitCpConfig.getProjectFileDir());
        //初始化git命令
        Git git = new Git(repository);
    }
}
