package com.hone.servicesocket.model;

public class ChatMessage {
    private String uid;
    private String content;
    private String type;
    private String date;
    private String aimuid;
    private String utype;
    private String bname;
    private String iconurl;
    private String blab;

    public void setContent(String content) {
        this.content = content;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAimuid(String aimuid) {
        this.aimuid = aimuid;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public void setBlab(String blab) {
        this.blab = blab;
    }

    public String getContent() {
        return content;
    }

    public String getUid() {
        return uid;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getAimuid() {
        return aimuid;
    }

    public String getUtype() {
        return utype;
    }

    public String getBname() {
        return bname;
    }

    public String getIconurl() {
        return iconurl;
    }

    public String getBlab() {
        return blab;
    }
}