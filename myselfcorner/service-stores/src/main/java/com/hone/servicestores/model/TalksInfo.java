package com.hone.servicestores.model;

public class TalksInfo {
    private String bid;
    private String content;
    private String sid;
    private String date;

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBid() {
        return bid;
    }

    public String getSid() {
        return sid;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}