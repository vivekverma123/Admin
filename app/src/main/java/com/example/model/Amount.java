package com.example.model;

public class Amount {
    private String flatNo;
    private int amt;

    public Amount() {
    }

    public Amount(String flatNo, int amt) {
        this.flatNo = flatNo;
        this.amt = amt;
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
