package com.hone.servicestores.controller;

import com.hone.localcommons.Model.Response;
import com.hone.localcommons.Model.Result;
import com.hone.localcommons.Model.StoresCar;
import com.hone.localcommons.annotation.TokenChecking;
import com.hone.servicestores.model.*;
import com.hone.servicestores.service.GetStoresInfoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class GetStoresController {
    @Autowired
    private GetStoresInfoImpl getStoresInfo;
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/GetStoresAll",method = RequestMethod.POST)
    public Result<List<StoresInfo>> GteStoresAll(@RequestBody BusinessGeter businessGeter){
        List<StoresInfo> rescode=getStoresInfo.GetStoresAll(businessGeter.getBid(),businessGeter.getDown(),businessGeter.getUp(),
                businessGeter.getColumname(),businessGeter.getSearchcontent());
        return Response.CreateSuccessRsp(rescode);
    }
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/GetStoresAll_Single",method = RequestMethod.POST)
    public Result<List<StoresInfo>> GetStoresAll(@RequestBody BusinessGeter businessGeter){
        List<StoresInfo> rescode=getStoresInfo.GetSingleStoresAll(businessGeter.getBid(),businessGeter.getColumname(),businessGeter.getSearchcontent());
        return Response.CreateSuccessRsp(rescode);
    }
    @TokenChecking
    @RequestMapping(value = "/RecommendMap/GetRoMapForsingle",method = RequestMethod.POST)
    public Result<List<RecommendMap>> GetRoMapForsingle(@RequestBody RecommendMap recommendMap){
        return Response.CreateSuccessRsp(getStoresInfo.GetRoMapForsingle());
    }
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/GetRecommendStores",method = RequestMethod.POST)
    public Result<List<StoresInfo>> GetRecommendStores(@RequestBody UserInfo userInfo){
        return Response.CreateSuccessRsp(getStoresInfo.GetRecommendStores(userInfo.getUid(),userInfo.getCity()));
    }
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/GetSkillStores",method = RequestMethod.POST)
    public Result<List<SeckillInfo>> GetSkillStores(){
        return Response.CreateSuccessRsp(getStoresInfo.GetSkillStores(null));
    }
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/GetSingleSkillStores",method = RequestMethod.POST)
    public Result<SeckillInfo> GetSingleSkillStores(@RequestBody UserInfo userInfo){
        return Response.CreateSuccessRsp(getStoresInfo.GetSkillStores(userInfo.getSid()).get(0));
    }
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/SearchStores",method = RequestMethod.POST)
    public Result<List<StoresInfo>> SearchStores(@RequestBody SearchInfo searchInfo){
        return Response.CreateSuccessRsp(getStoresInfo.SearchStores(searchInfo.getSearchcontent(),searchInfo.getSearchtype(),searchInfo.getSeltype(),searchInfo.getSelsel()));
    }
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/GetStoresCar",method = RequestMethod.POST)
    public Result<List<StoresInfo>> GetStoresCar(@RequestBody UserInfo userInfo){
        return Response.CreateSuccessRsp(getStoresInfo.GetStoresCar(userInfo.getUid()));
    }
    @RequestMapping(value = "/StoresInfo/GetSingleStores",method = RequestMethod.POST)
    public StoresInfo GetSingleStores(@RequestBody String sid){
        return getStoresInfo.GetSingleStores(sid);
    }
}