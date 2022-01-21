package com.hone.serviceorder.model;

public class Adress {
    private String uid;
    private String name;
    private String adress;
    private String phonenumber;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getAdress() {
        return adress;
    }

    public String getName() {
        return name;
    }
}