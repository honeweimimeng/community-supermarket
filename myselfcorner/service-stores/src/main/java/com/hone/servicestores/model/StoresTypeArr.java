package com.hone.servicestores.model;

import java.util.List;

public class StoresTypeArr {
    private String name;
    private List<StoresSonType> content;

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(List<StoresSonType> content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public List<StoresSonType> getContent() {
        return content;
    }
}