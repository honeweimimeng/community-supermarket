package com.hone.serviceuser.controller;

import com.alibaba.fastjson.JSONObject;
import com.hone.localcommons.Model.Response;
import com.hone.localcommons.Model.Result;
import com.hone.localcommons.annotation.AdminChecking;
import com.hone.serviceuser.model.ApplyBusinessInfo;
import com.hone.serviceuser.model.SingleArray;
import com.hone.serviceuser.model.TokensInfo;
import com.hone.serviceuser.model.UserBaseInfo;
import com.hone.serviceuser.service.UserOutherInfoImpl;
import com.hone.serviceuser.service.VUserInfoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private VUserInfoImpl vUserInfo;
    @Autowired
    private UserOutherInfoImpl userOutherInfo;
    @RequestMapping(value = "/Admin/Login",method = RequestMethod.POST)
    public Result<TokensInfo> Login(@RequestBody UserBaseInfo userBaseInfo){
        TokensInfo tokensInfo=vUserInfo.AdminLogin(userBaseInfo);
        if(tokensInfo==null){
            return Response.CreateErrorRsp("密码错误");
        }
        return Response.CreateSuccessRsp(tokensInfo);
    }
    @AdminChecking
    @RequestMapping(value = "/Admin/GetApplyBusiness",method = RequestMethod.POST)
    public Result<SingleArray<ApplyBusinessInfo>>GetApplyBusiness(@RequestBody JSONObject jsonObject){
        String page=jsonObject.getString("page");
        return Response.CreateSuccessRsp(userOutherInfo.GetApplyBusinessArr(page));
    }
    @AdminChecking
    @RequestMapping(value = "/Admin/CheckedApply",method = RequestMethod.POST)
    public Result<String> CheckedApply(@RequestBody ApplyBusinessInfo applyBusinessInfo){
        String rescode=userOutherInfo.AlterApplyBusiness(applyBusinessInfo,1);
        if(rescode.indexOf("error")!=-1){
            return Response.CreateErrorRsp(rescode);
        }
        return Response.CreateSuccessRsp("success");
    }
    @AdminChecking
    @RequestMapping(value = "/Admin/ClosedApply",method = RequestMethod.POST)
    public Result<String> ClosedApply(@RequestBody ApplyBusinessInfo applyBusinessInfo){
        String rescode=userOutherInfo.AlterApplyBusiness(applyBusinessInfo,0);
        if(rescode.indexOf("error")!=-1){
            return Response.CreateErrorRsp(rescode);
        }
        return Response.CreateSuccessRsp("success");
    }
}