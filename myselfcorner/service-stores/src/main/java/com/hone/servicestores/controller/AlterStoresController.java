package com.hone.servicestores.controller;

import com.alibaba.fastjson.JSON;
import com.hone.localcommons.Model.Response;
import com.hone.localcommons.Model.Result;
import com.hone.localcommons.Model.StoresCar;
import com.hone.localcommons.annotation.AdminChecking;
import com.hone.localcommons.annotation.TokenChecking;
import com.hone.servicestores.dao.GetStoresMapper;
import com.hone.servicestores.model.BusinessGeter;
import com.hone.servicestores.model.RecommendMap;
import com.hone.servicestores.model.SeckillInfo;
import com.hone.servicestores.model.StoresInfo;
import com.hone.servicestores.service.AlterStoresImpl;
import com.hone.servicestores.service.CreateStoresImpl;
import com.hone.servicestores.service.GetStoresInfoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlterStoresController {
    @Autowired
    private AlterStoresImpl alterStores;
    @Autowired
    private CreateStoresImpl createStores;
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/AlterStoresTotal",method = RequestMethod.POST)
    public Result<String> AlterStoresTotal(@RequestBody StoresInfo storesInfo){
        String rescode=alterStores.AlterStoresTotal(storesInfo.getAltertype(),storesInfo.getSid(),
                storesInfo.getTotalscore(),storesInfo.getContent());
        if(rescode.equals("error")){
            return Response.CreateErrorRsp("库存错误");
        }
        return Response.CreateSuccessRsp();
    }
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/DeleteStores",method = RequestMethod.POST)
    public Result<String> DeleteStores(@RequestBody StoresInfo storesInfo){
        return Response.CreateSuccessRsp(alterStores.DeleteStores(storesInfo.getSid()));
    }
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/JoinSeckill",method = RequestMethod.POST)
    public Result<String> JoinSeckill(@RequestBody List<SeckillInfo> list){
        String rescode=alterStores.JoinSeckill(list);
        return Response.CreateSuccessRsp(rescode);
    }
    @TokenChecking
    @RequestMapping(value = "/StoresInfo/GetJoinSeckill",method = RequestMethod.POST)
    public Result<List<SeckillInfo>> GetJoinSeckill(@RequestBody BusinessGeter businessGeter){
        return Response.CreateSuccessRsp(alterStores.GetJoinSeckill(businessGeter.getBid()));
    }
    @AdminChecking
    @RequestMapping(value = "/StoresInfo/GetRoMapArr",method = RequestMethod.POST)
    public Result<List<RecommendMap>> GetRoMapArr(){
        return Response.CreateSuccessRsp(createStores.GetRowMap(null));
    }
    @AdminChecking
    @RequestMapping(value = "/RecommendMap/AlterRoMap",method = RequestMethod.POST)
    public Result<String> AlterRoMap(@RequestBody RecommendMap recommendMap){
        return Response.CreateSuccessRsp(alterStores.AlterRoMap(recommendMap.getBid(),recommendMap.getBname()));
    }
    @RequestMapping(value = "/StoresInfo/AlterSalesNum",method = RequestMethod.POST)
    public void AlterSalesNum(@RequestBody StoresInfo storesInfo){
        alterStores.AlterStoresTotal("sub",storesInfo.getSid(),storesInfo.getTotalscore(),storesInfo.getContent());
    }
    @RequestMapping(value = "/StoresInfo/RemoveStoresCar",method = RequestMethod.POST)
    public void RemoveStoresCar(@RequestBody StoresCar storesCar){
        alterStores.RemoveStoresCar(storesCar.getUid(),storesCar.getSid());
    }
}