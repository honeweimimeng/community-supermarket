package com.hone.localcommons.Model;

import com.hone.localcommons.Model.UserBrowse;

public class StoresCar extends UserBrowse {
    private String date;
    private String storesnum;
    private String content;
    private String imgurl;

    public void setDate(String date) {
        this.date = date;
    }

    public void setStoresnum(String storesnum) {
        this.storesnum = storesnum;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDate() {
        return date;
    }

    public String getStoresnum() {
        return storesnum;
    }

    public String getContent() {
        return content;
    }

    public String getImgurl() {
        return imgurl;
    }
}