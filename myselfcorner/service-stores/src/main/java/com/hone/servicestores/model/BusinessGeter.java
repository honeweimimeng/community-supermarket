package com.hone.servicestores.model;

public class BusinessGeter {
    private String bid;
    private String btype;
    private Integer up;
    private Integer down;
    private String columname;
    private String page;
    private String searchcontent;

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setColumname(String columname) {
        this.columname = columname;
    }

    public void setDown(Integer dowm) {
        this.down = dowm;
    }

    public void setUp(Integer up) {
        this.up = up;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setSearchcontent(String searchcontent) {
        this.searchcontent = searchcontent;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public String getBid() {
        return bid;
    }

    public String getColumname() {
        return columname;
    }

    public Integer getDown() {
        return down;
    }

    public Integer getUp() {
        return up;
    }

    public String getPage() {
        return page;
    }

    public String getSearchcontent() {
        return searchcontent;
    }

    public String getBtype() {
        return btype;
    }
}