package cn.leafw.git.common;

import cn.leafw.git.dto.GitConfig;
import cn.leafw.git.dto.GitCpConfig;

import java.io.*;

/**
 * @description git配置工具
 * @author CareyWYR
 */
public class ReadGitConfigTool {

    /**
     * @return
     * @description 读取Git配置
     * 配置文件格式：账号，密码
     */
    public GitConfig readGitConfig(String configFilePath) throws Exception {
        File configFile = new File("configFilePath");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));
        String str= null;
        String userAccount = null;
        String password = null;
        GitConfig gitConfig = new GitConfig();
        while((str = bufferedReader.readLine()) != null){
            String[] config = str.split(",");
            if(null == config || config.length < 2){
                throw new RuntimeException("配置文件错误！");
            }else{
                userAccount = config[0];
                password = config[1];
            }
            gitConfig.setUserAccount(userAccount);
            gitConfig.setPassword(password);
        }
        return gitConfig;
    }

    /**
     * @return
     * @description 读取cp的配置
     * 配置文件格式：过滤条件用,隔开
     */
    public GitCpConfig readCpConfig(String cpConfigFilePath) throws Exception{
        File configFile = new File("configFilePath");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));

        return null;
    }
}
