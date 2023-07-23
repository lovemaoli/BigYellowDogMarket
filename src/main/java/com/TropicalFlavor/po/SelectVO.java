package com.TropicalFlavor.po;

public class SelectVO {
    private String pid;
    private String buyerId;
    private String gid;
    private String  gName;

    public SelectVO() {
    }

    public SelectVO(String pid, String buyerId, String gid, String gName) {
        this.pid = pid;
        this.buyerId = buyerId;
        this.gid = gid;
        this.gName = gName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    @Override
    public String toString() {
        return "SelectVO{" +
                "pid='" + pid + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", gid='" + gid + '\'' +
                ", gName='" + gName + '\'' +
                '}';
    }
}
