package com.example.model;

class Maintenance
{
    private String FlatNumber;
    private int status;

    public Maintenance(String flatNumber) {
        FlatNumber = flatNumber;
        status = 0;
    }

    public Maintenance(String flatNumber, int status) {
        FlatNumber = flatNumber;
        this.status = status;
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
}
