package com.hone.serviceorder.service;

import com.hone.localcommons.Utils.RedisUtils;
import com.hone.serviceorder.dao.AdressMapper;
import com.hone.serviceorder.model.Adress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdressImpl {
    @Autowired
    private AdressMapper adressMapper;
    @Autowired
    private RedisUtils redisUtils;
    public List<Adress> GetAdress(String uid){
        uid=redisUtils.getValue(uid)+"";
        return adressMapper.GetAdress(uid,null,null);
    }
    public String DeleteAdress(String uid, String name,String adress){
        uid=redisUtils.getValue(uid)+"";
        adressMapper.DeleteAdress(uid,name,adress);
        return "success";
    }
    public String CreateAdress(String uid,String name,String adress,String phonenumber){
        uid=redisUtils.getValue(uid)+"";
        if(adressMapper.GetAdress(uid,name,adress).size()==0){
            adressMapper.CreateAdress(uid, name, adress, phonenumber);
        }
        return "success";
    }
}