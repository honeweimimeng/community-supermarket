package com.hone.localcommons.Model;

public class UserBrowse {
    private String uid;
    private String sid;
    private String browseresult;
    private String browslongtime;
    private String clicknum;
    private String btype;
    private String city;
    private String keyword;
    private String se_keyword;
    private String simuserarr;

    public void setClicknum(String clicknum) {
        this.clicknum = clicknum;
    }

    public void setBrowslongtime(String browslongtime) {
        this.browslongtime = browslongtime;
    }

    public void setBrowseresult(String browseresult) {
        this.browseresult = browseresult;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSimuserarr(String simuserarr) {
        this.simuserarr = simuserarr;
    }

    public void setSe_keyword(String se_keyword) {
        this.se_keyword = se_keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getClicknum() {
        return clicknum;
    }

    public String getBrowslongtime() {
        return browslongtime;
    }

    public String getBrowseresult() {
        return browseresult;
    }

    public String getSid() {
        return sid;
    }

    public String getUid() {
        return uid;
    }

    public String getBtype() {
        return btype;
    }

    public String getCity() {
        return city;
    }

    public String getSimuserarr() {
        return simuserarr;
    }

    public String getSe_keyword() {
        return se_keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}