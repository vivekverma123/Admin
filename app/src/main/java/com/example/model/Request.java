package com.example.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Request {
    private String flatNo;
    private int amt;
    private String remarkClient;
    private String remarkAdmin;
    private boolean status;
    private String id;
    private String id_month;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Request(String flatNo, int amt, String remarkClient, String remarkAdmin, boolean status, String id, String id_month) {
        this.flatNo = flatNo;
        this.amt = amt;
        this.remarkClient = remarkClient;
        this.remarkAdmin = remarkAdmin;
        this.status = status;
        this.id = id;
        this.id_month = id_month;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
        Date date1 = new Date();
        date = simpleDateFormat.format(date1);
    }

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

    public String getId_month() {
        return id_month;
    }

    public void setId_month(String id_month) {
        this.id_month = id_month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
