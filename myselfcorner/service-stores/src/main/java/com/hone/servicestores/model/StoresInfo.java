package com.hone.servicestores.model;

import java.util.List;

public class StoresInfo {
    private String bid;
    private String sid;
    private String name;
    private String type;
    private String price;
    private List<StoresTypeArr> storesTypeArr;
    private List<StoresDetailInfo> storesDetailInfo;
    private String content;
    private String detailsarr;
    private String btype;
    private String totalscore;
    private String salesnum;
    private String praisetalks;
    private String badtalks;
    private String mothnum;
    private String itemizearr;
    private String altertype;
    private String storesnum;
    private String date;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStoresTypeArr(List<StoresTypeArr> storesTypeArr) {
        this.storesTypeArr = storesTypeArr;
    }

    public void setStoresDetailInfo(List<StoresDetailInfo> storesDetailInfo) {
        this.storesDetailInfo = storesDetailInfo;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setDetailsarr(String detailsarr) {
        this.detailsarr = detailsarr;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public void setSalesnum(String salesnum) {
        this.salesnum = salesnum;
    }

    public void setBadtalks(String badtalks) {
        this.badtalks = badtalks;
    }

    public void setMothnum(String mothnum) {
        this.mothnum = mothnum;
    }

    public void setPraisetalks(String praisetalks) {
        this.praisetalks = praisetalks;
    }

    public void setTotalscore(String totalscore) {
        this.totalscore = totalscore;
    }

    public void setItemizearr(String itemizearr) {
        this.itemizearr = itemizearr;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setStoresnum(String storesnum) {
        this.storesnum = storesnum;
    }

    public void setAltertype(String altertype) {
        this.altertype = altertype;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPraisetalks() {
        return praisetalks;
    }

    public String getSalesnum() {
        return salesnum;
    }

    public String getBadtalks() {
        return badtalks;
    }

    public String getMothnum() {
        return mothnum;
    }

    public String getTotalscore() {
        return totalscore;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public List<StoresDetailInfo> getStoresDetailInfo() {
        return storesDetailInfo;
    }

    public List<StoresTypeArr> getStoresTypeArr() {
        return storesTypeArr;
    }

    public String getBid() {
        return bid;
    }

    public String getDetailsarr() {
        return detailsarr;
    }

    public String getContent() {
        return content;
    }

    public String getBtype() {
        return btype;
    }

    public String getItemizearr() {
        return itemizearr;
    }

    public String getSid() {
        return sid;
    }

    public String getAltertype() {
        return altertype;
    }

    public String getStoresnum() {
        return storesnum;
    }

    public String getDate() {
        return date;
    }
}