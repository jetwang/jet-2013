package jetwang.framework.db;

public class CritieriaAlias {
    private String aliasPath;
    private String aliasName;
    private int joinType;


    public CritieriaAlias() {
    }

    public CritieriaAlias(String aliasPath, String aliasName, int joinType) {
        this.aliasPath = aliasPath;
        this.aliasName = aliasName;
        this.joinType = joinType;
    }

    public String getAliasPath() {
        return aliasPath;
    }

    public void setAliasPath(String aliasPath) {
        this.aliasPath = aliasPath;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public int getJoinType() {
        return joinType;
    }

    public void setJoinType(int joinType) {
        this.joinType = joinType;
    }

}
