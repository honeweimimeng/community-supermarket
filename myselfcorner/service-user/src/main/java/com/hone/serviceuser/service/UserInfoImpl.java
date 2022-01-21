package com.hone.serviceuser.service;

import com.hone.localcommons.Utils.RedisUtils;
import com.hone.serviceuser.dao.AlterUserInfoMapper;
import com.hone.serviceuser.dao.CreateUserInfoMapper;
import com.hone.serviceuser.dao.GetUserInfoMapper;
import com.hone.serviceuser.model.UserBaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoImpl {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private GetUserInfoMapper getUserInfoMapper;
    @Autowired
    private AlterUserInfoMapper alterUserInfoMapper;
    @Autowired
    private CreateUserInfoMapper createUserInfoMapper;
    public String CreateUserInfo(String uid,String name,String icon){
        uid=redisUtils.getValue(uid)+"";
        if(getUserInfoMapper.GetCustomerBaseInfo(uid)==null){
            //更新用户
            createUserInfoMapper.UpdateCustomerBseInfo(uid, name, icon);
            return "success";
        }
        createUserInfoMapper.CreateCustomerBaseInfo(uid, name, icon);
        return "success";
    }
    public UserBaseInfo GetUserBaseInfo(String uid){
        uid=redisUtils.getValue(uid)+"";
        return getUserInfoMapper.GetCustomerBaseInfo(uid);
    }
    public void AlterBusinessTalks(String bid,String type){
        if(type.equals("true")){
            alterUserInfoMapper.BusinessGTalks(bid);
        }else{
            alterUserInfoMapper.BusinessBTalks(bid);
        }
    }
}