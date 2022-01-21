package com.hone.serviceuser.controller;

import com.alibaba.fastjson.JSONObject;
import com.hone.localcommons.Model.Response;
import com.hone.localcommons.Model.Result;
import com.hone.localcommons.Model.UserBrowse;
import com.hone.localcommons.annotation.TokenChecking;
import com.hone.localcommons.constant.FileConstant;
import com.hone.serviceuser.model.ApplyBusinessInfo;
import com.hone.serviceuser.model.BusinessInfo;
import com.hone.serviceuser.model.TalksInfo;
import com.hone.serviceuser.model.UserBaseInfo;
import com.hone.serviceuser.service.UserInfoImpl;
import com.hone.serviceuser.service.UserOutherInfoImpl;
import com.hone.serviceuser.service.VUserInfoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
public class UserInfoController {
    @Autowired
    private VUserInfoImpl vUserInfo;
    @Autowired
    private UserOutherInfoImpl userOutherInfo;
    @Autowired
    private UserInfoImpl userInfoimpl;
    @RequestMapping(value = "/UserInfo/VUserInfos",method = RequestMethod.POST)
    public Result<String> UserLogin(@RequestBody UserBaseInfo userBaseInfo) throws IOException {
        String code=vUserInfo.VUserInfos(userBaseInfo.getId());
        if(code==null||code.equals("")){
            return Response.CreateErrorRsp("error01认证错误，重新登入",null);
        }
        return Response.CreateSuccessRsp(code);
    }
    @RequestMapping(value = "/UserInfo/BusinessLogin",method = RequestMethod.POST)
    public Result<JSONObject> BusinessLogin(@RequestBody UserBaseInfo userBaseInfo) throws IOException {
        JSONObject code=vUserInfo.BusinessLogin(userBaseInfo.getId());
        if(code.getString("result").equals("error01")){
            return Response.CreateErrorRsp("error01认证错误，重新登入",code);
        }
        if(code.getString("result").equals("error02")){
            return Response.CreateErrorRsp("warning01WaiteCheck",code);
        }
        return Response.CreateSuccessRsp(code);
    }
    @TokenChecking
    @RequestMapping(value = "/UserInfo/GetBusinessInfo",method = RequestMethod.POST)
    public Result<BusinessInfo> GetBusinessInfo(@RequestBody BusinessInfo businessInfo){
        return Response.CreateSuccessRsp(userOutherInfo.GetBusinessInfo(businessInfo.getBid()));
    }
    @RequestMapping(value = "/UserInfo/ApplyBusiness",method = RequestMethod.POST)
    public Result<String> ApplyBusiness(@RequestBody ApplyBusinessInfo applyBusinessInfo) throws UnsupportedEncodingException {
        String code=vUserInfo.CreateApplyBusinessInfo(applyBusinessInfo);
        if(code==null){
            return Response.CreateErrorRsp("error01是用户已存在",null);
        }
        return Response.CreateSuccessRsp(code);
    }
    @RequestMapping(value = "/UserInfo/UploadIdcardImg",method = RequestMethod.POST)
    public Result<String> UploadIdcardImg(HttpServletRequest request){
        return Response.CreateSuccessRsp(userOutherInfo.UpLoadFile(request,0));
    }
    @RequestMapping(value = "/UserInfo/AlterBusinessTalks",method = RequestMethod.POST)
    public String AlterBusinessTalks(@RequestBody TalksInfo talksInfo){
        userInfoimpl.AlterBusinessTalks(talksInfo.getBid(),talksInfo.getType());
        return "success";
    }
    @TokenChecking
    @RequestMapping(value = "/UserInfo/CreateBrow",method = RequestMethod.POST)
    public void CreateBrow(@RequestBody UserBrowse userBrowse){
        userOutherInfo.CreateBrowse(userBrowse.getUid(),userBrowse.getSid(),userBrowse.getBrowseresult(),userBrowse.getBrowslongtime(),userBrowse.getClicknum());
    }
}