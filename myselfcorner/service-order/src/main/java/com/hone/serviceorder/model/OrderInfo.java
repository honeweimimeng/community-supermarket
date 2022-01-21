package com.hone.serviceorder.model;

public class OrderInfo {
    private String oid;
    private String sid;
    private String storesnum;
    private String altertype;
    private String status;
    private String finalpaymoney;
    private String showpaymoney;
    private String uid;
    private String bid;
    private String aimadress;
    private String createdate;
    private String paydate;
    private String bcheckdate;
    private String ucheckdate;
    private String overdate;
    private String storescontent;
    private String page;
    private String ordertype;
    private Adress adress;
    private String content;
    private String skill;
    private String imgurl;

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAimadress(String aimadress) {
        this.aimadress = aimadress;
    }

    public void setBcheckdate(String bcheckdate) {
        this.bcheckdate = bcheckdate;
    }

    public void setFinalpaymoney(String finalpaymoney) {
        this.finalpaymoney = finalpaymoney;
    }

    public void setOverdate(String overdate) {
        this.overdate = overdate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public void setShowpaymoney(String showpaymoney) {
        this.showpaymoney = showpaymoney;
    }

    public void setStorescontent(String storescontent) {
        this.storescontent = storescontent;
    }

    public void setUcheckdate(String ucheckdate) {
        this.ucheckdate = ucheckdate;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
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

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBid() {
        return bid;
    }

    public String getUid() {
        return uid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public String getOid() {
        return oid;
    }

    public String getStatus() {
        return status;
    }

    public String getAimadress() {
        return aimadress;
    }

    public String getBcheckdate() {
        return bcheckdate;
    }

    public String getFinalpaymoney() {
        return finalpaymoney;
    }

    public String getOverdate() {
        return overdate;
    }

    public String getPaydate() {
        return paydate;
    }

    public String getShowpaymoney() {
        return showpaymoney;
    }

    public String getStorescontent() {
        return storescontent;
    }

    public String getUcheckdate() {
        return ucheckdate;
    }

    public String getPage() {
        return page;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public String getSid() {
        return sid;
    }

    public String getStoresnum() {
        return storesnum;
    }

    public String getAltertype() {
        return altertype;
    }

    public Adress getAdress() {
        return adress;
    }

    public String getContent() {
        return content;
    }

    public String getImgurl() {
        return imgurl;
    }
}