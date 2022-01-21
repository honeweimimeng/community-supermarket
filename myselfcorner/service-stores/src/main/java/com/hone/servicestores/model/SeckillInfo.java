package com.hone.servicestores.model;

public class SeckillInfo extends StoresInfo{
    private String sid;
    private String discount;
    private String starttime;
    private String bid;
    private String longitude;
    private String latitude;

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSid() {
        return sid;
    }

    public String getDiscount() {
        return discount;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getBid() {
        return bid;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}