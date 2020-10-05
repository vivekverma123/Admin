package com.example.model;

public class Request {
    private String flatNo;
    private int amt;
    private String remarkClient;
    private String remarkAdmin;
    boolean status;

    public String getRemarkClient() {
        return remarkClient;
    }

    public void setRemarkClient(String remarkClient) {
        this.remarkClient = remarkClient;
    }

    public String getRemarkAdmin() {
        return remarkAdmin;
    }

    public void setRemarkAdmin(String remarkAdmin) {
        this.remarkAdmin = remarkAdmin;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Request(String flatNo, int amt, String remarkClient, String remarkAdmin, boolean status) {
        this.flatNo = flatNo;
        this.amt = amt;
        this.remarkClient = remarkClient;
        this.remarkAdmin = remarkAdmin;
        this.status = status;
    }

    public Request() {
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }
}
