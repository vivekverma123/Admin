package com.example.model;

public class Maintenance
{
    private String FlatNumber;
    private int status;
    private int amt_paid;

    public Maintenance(String flatNumber, int amt_paid) {
        FlatNumber = flatNumber;
        status = 0;
        this.amt_paid = amt_paid;
    }

    public Maintenance() {
    }

    public String getFlatNumber() {
        return FlatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        FlatNumber = flatNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAmt_paid() {
        return amt_paid;
    }

    public void setAmt_paid(int amt_paid) {
        this.amt_paid = amt_paid;
    }
}
