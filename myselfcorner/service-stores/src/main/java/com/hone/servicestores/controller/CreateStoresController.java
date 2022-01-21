package com.hone.servicestores.controller;

import com.alibaba.fastjson.JSON;
import com.hone.localcommons.Model.Response;
import com.hone.localcommons.Model.Result;
import com.hone.localcommons.annotation.TokenChecking;
import com.hone.servicestores.model.RecommendMap;
import com.hone.servicestores.model.StoresInfo;
import com.hone.servicestores.service.CreateStoresImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateStoresController {
    @Autowired
    private CreateStoresImpl createStores;
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/CreateStores",method = RequestMethod.POST)
    public Result<String> CreateStores(@RequestBody StoresInfo storesInfo){
        String rescode=createStores.CreateNewStores(storesInfo.getBid(),storesInfo.getName(),storesInfo.getType(),storesInfo.getPrice(),
                storesInfo.getStoresDetailInfo(),storesInfo.getStoresTypeArr());
        if(rescode.indexOf("error")!=-1){
            return Response.CreateErrorRsp("error");
        }
        return Response.CreateSuccessRsp("success");
    }
    @TokenChecking
    @RequestMapping(value = "/RecommendMap/CreateRoMap",method = RequestMethod.POST)
    public Result<String> CreateRoMap(@RequestBody RecommendMap recommendMap){
        String rescode=createStores.CreateRoMap(recommendMap.getBid(),recommendMap.getLab(),recommendMap.getMapurl());
        if(rescode.indexOf("error")!=-1){
            return Response.CreateErrorRsp("已提交申请");
        }
        return Response.CreateSuccessRsp("success");
    }
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/AddStoresCar",method = RequestMethod.POST)
    public Result<String> AddStoresCar(@RequestBody StoresInfo storesInfo){
        return Response.CreateSuccessRsp(createStores.AddStoresCar(storesInfo));
    }
}