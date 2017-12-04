package cn.leafw;

import cn.leafw.git.operation.BaseOperation;
import cn.leafw.git.dto.GitCpConfig;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;

import java.io.File;

public class CpTest {

    private static BaseOperation baseOperation = null;

    static {
        GitCpConfig gitCpConfig = new GitCpConfig();
        gitCpConfig.setCurrentBranch("master");
        gitCpConfig.setRemoteBranch("origin/dev");
        gitCpConfig.setProjectFileDir(new File("C:\\Document\\GitRepository\\testforgittool\\.git"));
        baseOperation = new BaseOperation(gitCpConfig);
    }

    @Test
     public void testLog(){
         try {
//             baseOperation.showLog();
             RevWalk revWalk = baseOperation.showLogSafe("master");
             revWalk.forEach(e -> System.out.println(e.getShortMessage()));
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     @Test
     public void testPull(){
         try {
             baseOperation.pullRebase();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

    @Test
    public void testCp(){
        try {
            baseOperation.codeCheeryPick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
