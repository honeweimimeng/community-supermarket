package com.hone.serviceorder.model;

public class TalksInfo {
    private String oid;
    private String content;
    private String type;
    private String bid;
    private String sid;

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getSid() {
        return sid;
    }

    public String getBid() {
        return bid;
    }

    public String getOid() {
        return oid;
    }
}