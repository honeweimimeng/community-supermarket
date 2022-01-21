package com.hone.localcommons.Model;

public enum RestCodes{
    SUCCESS(200),
    FAIL(400);
    public int rescode;
    RestCodes(int code){
        this.rescode=code;
    }
}