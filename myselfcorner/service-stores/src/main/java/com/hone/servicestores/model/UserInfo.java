package com.hone.servicestores.model;

public class UserInfo {
    private String uid;
    private String keyword;
    private String se_keyword;
    private String sid;
    private String browseresult;
    private String browslongtime;
    private String clicknum;
    private String simuserarr;
    private String city;
    private String longitude;
    private String latitude;

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setBrowseresult(String browseresult) {
        this.browseresult = browseresult;
    }

    public void setBrowslongtime(String browslongtime) {
        this.browslongtime = browslongtime;
    }

    public void setClicknum(String clicknum) {
        this.clicknum = clicknum;
    }

    public void setSimuserarr(String simuserarr) {
        this.simuserarr = simuserarr;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSid() {
        return sid;
    }

    public String getBrowseresult() {
        return browseresult;
    }

    public String getBrowslongtime() {
        return browslongtime;
    }

    public String getClicknum() {
        return clicknum;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setSe_keyword(String se_keyword) {
        this.se_keyword = se_keyword;
    }

    public String getUid() {
        return uid;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getSe_keyword() {
        return se_keyword;
    }

    public String getSimuserarr() {
        return simuserarr;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getCity() {
        return city;
    }
}