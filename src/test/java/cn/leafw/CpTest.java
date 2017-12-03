package cn.leafw;

import cn.leafw.git.operation.BaseOperation;
import cn.leafw.git.dto.GitCpConfig;
import org.junit.Test;

import java.io.File;

public class CpTest {

    private static BaseOperation baseOperation = null;

    static {
        GitCpConfig gitCpConfig = new GitCpConfig();
        gitCpConfig.setCurrentBranch("master");
        gitCpConfig.setRemoteBranch("master");
        gitCpConfig.setProjectFileDir(new File("C:\\Document\\GitRepository\\imooc.mmall\\.git"));
        baseOperation = new BaseOperation(gitCpConfig);
    }

    @Test
     public void testLog(){
         try {
             baseOperation.showLog();
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
