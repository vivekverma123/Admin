package com.example.model;

public class FlatOwner
{
    private String flatNo,Name,mobNo;

    public FlatOwner() {
    }

    public FlatOwner(String flatNo, String name, String mobNo) {
        this.flatNo = flatNo;
        Name = name;
        this.mobNo = mobNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }
}
