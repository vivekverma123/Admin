package com.example.model;

public class DueRecord
{
    private String id;
    private boolean type;
    private int amt;
    private String remark;

    public DueRecord(String id, boolean type, int amt, String remark) {
        this.id = id;
        this.type = type;
        this.amt = amt;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
