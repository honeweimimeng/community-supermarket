package com.hone.servicestores.model;

public class SearchInfo {
    private String searchcontent;
    private String searchtype;
    private String seltype;
    private String selsel;

    public void setSearchcontent(String searchcontent) {
        this.searchcontent = searchcontent;
    }

    public void setSearchtype(String searchtype) {
        this.searchtype = searchtype;
    }

    public void setSeltype(String seltype) {
        this.seltype = seltype;
    }

    public void setSelsel(String selsel) {
        this.selsel = selsel;
    }

    public String getSearchcontent() {
        return searchcontent;
    }

    public String getSearchtype() {
        return searchtype;
    }

    public String getSeltype() {
        return seltype;
    }

    public String getSelsel() {
        return selsel;
    }
}