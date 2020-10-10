package com.example.model;

public class Transaction {
    private int amount;
    private int type;
    private String date;
    private String id;
    private String flatNo;

    /*
    Value of type
        0 : Maintenance Payment
        1 : Advance Payment
        2 : Previous Due Payment
     */

    public Transaction(int amount, int type, String date, String id, String flatNo) {
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.id = id;
        this.flatNo = flatNo;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public Transaction() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
