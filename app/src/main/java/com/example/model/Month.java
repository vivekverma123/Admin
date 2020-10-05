package com.example.model;

import java.util.ArrayList;



public class Month
{
    private String Id;
    private String Title;
    private int M,Y;
    private int Security,Sweeper,Gardener,Electricity,GarbageCollection,Other,Contr,AmtOb;
    private String Description;

    public int getContr() {
        return Contr;
    }

    public void setContr(int contr) {
        Contr = contr;
    }

    public int getAmtOb() {
        return AmtOb;
    }

    public void setAmtOb(int amtOb) {
        AmtOb = amtOb;
    }

    public Month(String id, String title, int m, int y, int security, int sweeper, int gardener, int electricity, int garbageCollection, int other, int contr, int amtOb, String description) {
        Id = id;
        Title = title;
        M = m;
        Y = y;
        Security = security;
        Sweeper = sweeper;
        Gardener = gardener;
        Electricity = electricity;
        GarbageCollection = garbageCollection;
        Other = other;
        Contr = contr;
        AmtOb = amtOb;
        Description = description;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public Month() {
    }

    public void setBills()
    {
        Title = "";
        Security = 0;
        Sweeper = 0;
        Gardener = 0;
        Electricity = 0;
        GarbageCollection = 0;
        Other = 0;
        Contr = 0;
        AmtOb = 0;
        this.Description = "";
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public int getM() {
        return M;
    }

    public void setM(int m) {
        this.M = m;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public int getSecurity() {
        return Security;
    }

    public void setSecurity(int security) {
        Security = security;
    }

    public int getSweeper() {
        return Sweeper;
    }

    public void setSweeper(int sweepper) {
        Sweeper = sweepper;
    }

    public int getGardener() {
        return Gardener;
    }

    public void setGardener(int gardener) {
        Gardener = gardener;
    }

    public int getElectricity() {
        return Electricity;
    }

    public void setElectricity(int electricity) {
        Electricity = electricity;
    }

    public int getGarbageCollection() {
        return GarbageCollection;
    }

    public void setGarbageCollection(int garbageCollection) {
        GarbageCollection = garbageCollection;
    }

    public int getOther() {
        return Other;
    }

    public void setOther(int other) {
        Other = other;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }
}
