package com.hone.serviceuser.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hone.localcommons.Utils.HttpClientUtils;
import com.hone.localcommons.Utils.RedisUtils;
import com.hone.localcommons.Utils.SingleIdGenerator;
import com.hone.localcommons.constant.FontConstant;
import com.hone.serviceuser.constant.ComConstant;
import com.hone.serviceuser.dao.CreateUserInfoMapper;
import com.hone.serviceuser.dao.GetUserInfoMapper;
import com.hone.serviceuser.model.ApplyBusinessInfo;
import com.hone.serviceuser.model.BusinessInfo;
import com.hone.serviceuser.model.TokensInfo;
import com.hone.serviceuser.model.UserBaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class VUserInfoImpl {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private GetUserInfoMapper getUserInfoMapper;
    @Autowired
    private HttpClientUtils httpClientUtils;
    @Autowired
    private CreateUserInfoMapper createUserInfoMapper;
    @Autowired
    private SingleIdGenerator singleIdGenerator;
    public String VUserInfos(String id) throws IOException {
        String url=FontConstant.WXUrl+id+FontConstant.WXUrl_over;
        JSONObject jsonObject=(JSONObject) JSONObject.parse(httpClientUtils.URLClient(url));
        System.out.println(JSON.toJSONString(jsonObject));
        redisUtils.setValue(jsonObject.getString(FontConstant.WXSEName),jsonObject.getString(FontConstant.WXOPIDName),
                redisUtils.TOKEN_EXPIRES_SECOND, TimeUnit.SECONDS);
        String rescode=jsonObject.getString(FontConstant.WXSEName);
        return rescode;
    }
    public JSONObject BusinessLogin(String id) throws IOException {
        String url=FontConstant.WXUrl2+id+FontConstant.WXUrl_over;
        JSONObject jsonObject=(JSONObject) JSONObject.parse(httpClientUtils.URLClient(url));
        String rescode=jsonObject.getString(FontConstant.WXSEName);
        JSONObject resjsonObject=new JSONObject();
        //验证是否存在用户信息,存在信息为空的号码字段
        BusinessInfo businessInfo=getUserInfoMapper.GetSingleBusinessInfo(jsonObject.getString(FontConstant.WXOPIDName));
        redisUtils.setValue(jsonObject.getString(FontConstant.WXSEName),jsonObject.getString(FontConstant.WXOPIDName),
                redisUtils.TOKEN_EXPIRES_SECOND, TimeUnit.SECONDS);
        resjsonObject.put("SessionId",rescode);
        resjsonObject.put("result","error01");
        if(businessInfo!=null){
            resjsonObject.put("bid",businessInfo.getBid());
            resjsonObject.put("result","success");
        }
        //判断是否申请中
        businessInfo=getUserInfoMapper.GetWaiteCheckBusinessInfo(jsonObject.getString(FontConstant.WXOPIDName));
        if(businessInfo!=null){
            resjsonObject.put("result","error02");
        }
        return resjsonObject;
    }
    public TokensInfo AdminLogin(UserBaseInfo userBaseInfo){
        UserBaseInfo dao_userinfo=getUserInfoMapper.GetAdminUserInfo(userBaseInfo.getId());
        if(dao_userinfo!=null&&userBaseInfo.getPassword().equals(dao_userinfo.getPassword())){
            //登录成功
            singleIdGenerator.initUidGenerator(ComConstant.serveridcard,1);
            String tuid=singleIdGenerator.nextId()+"";
            String token= JWT.create().withAudience(tuid).sign(Algorithm.HMAC256(userBaseInfo.getPassword()));
            redisUtils.setValue(tuid,token,redisUtils.TOKEN_EXPIRES_SECOND, TimeUnit.SECONDS);
            redisUtils.setValue(tuid+"admincode","1",redisUtils.TOKEN_EXPIRES_SECOND, TimeUnit.SECONDS);
            TokensInfo tokensInfo=new TokensInfo();
            tokensInfo.setTid(tuid);
            tokensInfo.setToken(token);
            return tokensInfo;
        }
        //登陆失败
        return null;
    }
    @Transactional
    public String CreateApplyBusinessInfo(ApplyBusinessInfo applyBusinessInfo){
        String hostid=(String)redisUtils.getValue(applyBusinessInfo.getUid());
        if(getUserInfoMapper.GetWaiteCheckBusinessInfo(hostid)!=null||
                getUserInfoMapper.GetSingleBusinessInfo(hostid)!=null){
            return null;
        }
        Date date=new Date();
        String createdate=1900+date.getYear()+"年"+(1+date.getMonth())+"月"+date.getDate()+"日";
        applyBusinessInfo.setCreatedate(createdate);
        createUserInfoMapper.CreateApplyBusiness(hostid,applyBusinessInfo.getPhonenumber(),applyBusinessInfo.getBname(),
                applyBusinessInfo.getBtype(),applyBusinessInfo.getBlab(),applyBusinessInfo.getAdress(),
                applyBusinessInfo.getLongitude(),applyBusinessInfo.getLatitude(),applyBusinessInfo.getLicenseurl(),
                applyBusinessInfo.getIdcardtopurl(),applyBusinessInfo.getIdcardbottomurl(),applyBusinessInfo.getIconurl());
        return "success";
    }
}