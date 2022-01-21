package com.hone.serviceuser.model;

public class TokensInfo {
    private String tid;
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getToken() {
        return token;
    }

    public String getTid() {
        return tid;
    }
}