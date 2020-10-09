package com.example.model;

public class Maintenance
{
    private String FlatNumber;
    private int status;
    private int amt_paid;
    private String id;
    private int Contr;


    public Maintenance(String flatNumber, int status, int amt_paid, String id, int contr) {
        FlatNumber = flatNumber;
        this.status = status;
        this.amt_paid = amt_paid;
        this.id = id;
        Contr = contr;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getContr() {
        return Contr;
    }

    public void setContr(int contr) {
        Contr = contr;
    }
}
