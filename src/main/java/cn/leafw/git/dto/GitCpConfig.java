package cn.leafw.git.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @description cherryPick需要的配置
 * @author CareyWYR
 */
public class GitCpConfig extends GitConfig implements Serializable{
    private static final long serialVerisionUID = 1l;
    /**当前分支**/
    private String  currentBranch;
    /**要合并过来的远程分支**/
    private String remoteBranch;
    /**过滤条件**/
    private List<String> filterCondition;

    public String getCurrentBranch() {
        return currentBranch;
    }

    public void setCurrentBranch(String currentBranch) {
        this.currentBranch = currentBranch;
    }

    public String getRemoteBranch() {
        return remoteBranch;
    }

    public void setRemoteBranch(String remoteBranch) {
        this.remoteBranch = remoteBranch;
    }

    public List<String> getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(List<String> filterCondition) {
        this.filterCondition = filterCondition;
    }
}
