package cn.leafw.git.dto;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * @description Git配置
 * @author CareyWYR
 */
public class GitConfig implements Serializable{

    private static final long serialVersionUID = 1l;

    /**项目名**/
    private String projectName;
    /**用户账号**/
    private String userAccount;
    /**密码**/
    private String password;
    /**项目地址**/
    private File projectFileDir;
    /**子项目（模块）列表**/
    private List<String> moduleList;

    public File getProjectFileDir() {
        return projectFileDir;
    }

    public void setProjectFileDir(File projectFileDir) {
        this.projectFileDir = projectFileDir;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<String> moduleList) {
        this.moduleList = moduleList;
    }
}
