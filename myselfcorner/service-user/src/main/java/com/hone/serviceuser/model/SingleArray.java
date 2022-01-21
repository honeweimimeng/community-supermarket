package com.hone.serviceuser.model;

import java.util.List;

public class SingleArray<T> {
    private String data;
    private List<T> list;

    public void setData(String data) {
        this.data = data;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getData() {
        return data;
    }

    public List<T> getList() {
        return list;
    }
}