package com.hone.servicesocket.model;

public class SingleMsg {
    private String type;
    private String msg;
    private String token;
    public SingleMsg(String type,String msg,String token){
        this.type=type;
        this.msg=msg;
        this.token=token;
    }
    public SingleMsg(String type,String msg){
        this.type=type;
        this.msg=msg;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setToken(String token) {
        this.token = token;
    }
}