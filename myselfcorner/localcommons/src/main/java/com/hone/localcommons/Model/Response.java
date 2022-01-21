package com.hone.localcommons.Model;

import com.hone.localcommons.Model.RestCodes;

public class Response {
    private final static String SUCCESS = "success";
    public static <T> Result<T> CreateSuccessRsp() {
        return new Result<T>().setCode(RestCodes.SUCCESS).setMsg(SUCCESS);
    }

    public static <T> Result<T> CreateSuccessRsp(T data) {
        return new Result<T>().setCode(RestCodes.SUCCESS).setMsg(SUCCESS).setData(data);
    }

    public static <T> Result<T> CreateErrorRsp(String message,T data) {
        return new Result<T>().setCode(RestCodes.FAIL).setMsg(message).setData(data);
    }
    public static <T> Result<T> CreateErrorRsp(String message) {
        return new Result<T>().setCode(RestCodes.FAIL).setMsg(message);
    }
    public static <T> Result<T> CreateRsp(int code, String msg) {
        return new Result<T>().setCode(code).setMsg(msg);
    }

    public static <T> Result<T> CreateRsp(int code, String msg, T data) {
        return new Result<T>().setCode(code).setMsg(msg).setData(data);
    }
}