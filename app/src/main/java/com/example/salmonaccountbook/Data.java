package com.example.salmonaccountbook;

import org.litepal.crud.DataSupport;

import java.util.UUID;

public class Data extends DataSupport{
    private UUID id;
    private long date;
    private String type;
    private String ie;
    private double money;
    private String remarks;
    private String username;

    public UUID getId() {
        return id;
    }

    public void setDate(long  date) {
        this.date = date;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getMoney() {
        return money;
    }

    public long getDate() {
        return date;
    }

    public String getIe() {
        return ie;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getType() {
        return type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
