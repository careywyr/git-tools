package cn.leafw.git.common;

import cn.leafw.git.dto.GitConfig;

import java.io.*;

/**
 * @description git配置工具
 * @author CareyWYR
 */
public class ReadGitConfigTool {

    /**
     * @return
     * @description 读取Git配置
     */
    public GitConfig readGitConfig(File configFile) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));
        String str= null;
        while((str = bufferedReader.readLine()) != null){
        }
        return null;
    }
}
