package com.hone.servicestores.model;

public class RecommendMap extends BusinessGeter{
    private String bname;
    private String bid;
    private String mapurl;
    private String lab;
    private String startdate;
    private String enddate;
    private String status;

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setMapurl(String mapurl) {
        this.mapurl = mapurl;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBid() {
        return bid;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getMapurl() {
        return mapurl;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getLab() {
        return lab;
    }

    public String getBname() {
        return bname;
    }

    public String getStatus() {
        return status;
    }
}