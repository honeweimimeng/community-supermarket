package com.hone.serviceorder.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hone.localcommons.Utils.RedisUtils;
import com.hone.serviceorder.dao.GetOrderMapper;
import com.hone.serviceorder.model.OrderInfo;
import com.hone.serviceorder.model.StoresInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetOrderImpl {
    @Autowired
    private GetOrderMapper getOrderMapper;
    @Autowired
    private RedisUtils redisUtils;
    public List<OrderInfo> GetOrderArr(String uid,String bid,String ordertype){
        String utype="0";
        String searchid=bid;
        if(bid.equals("null")||bid==null){
            utype="1";
            searchid=redisUtils.getValue(uid)+"";
        }
        List<OrderInfo> reslist=getOrderMapper.GetOrderArr(searchid,utype,ordertype);
        List<OrderInfo> res=new ArrayList<>();
        for(OrderInfo orderInfo:reslist){
            StoresInfo storesInfo=getOrderMapper.SingleStores(orderInfo.getSid());
            OrderInfo orderInfo1=new OrderInfo();
            JSONArray jsonArray1=JSONArray.parseArray(storesInfo.getItemizearr());
            orderInfo1.setPage(storesInfo.getName());
            JSONObject jsonObject=(JSONObject) jsonArray1.get(0);
            JSONArray jsonArray=JSONArray.parseArray(jsonObject.getString("content"));
            JSONObject jsonObject1=(JSONObject) jsonArray.get(0);
            orderInfo1.setImgurl(jsonObject1.getString("imgurl"));
            orderInfo1.setOid(orderInfo.getOid());
            orderInfo1.setContent(orderInfo.getContent());
            orderInfo1.setAimadress(orderInfo.getAimadress());
            orderInfo1.setFinalpaymoney(orderInfo.getFinalpaymoney());
            orderInfo1.setShowpaymoney(orderInfo.getShowpaymoney());
            orderInfo1.setStoresnum(orderInfo.getStoresnum());
            orderInfo1.setStatus(orderInfo.getStatus());
            orderInfo1.setSid(orderInfo.getSid());
            orderInfo1.setBid(orderInfo.getBid());
            res.add(orderInfo1);
        }
        return res;
    }
    public List<OrderInfo> GetNotReadOrder(String bid){
        List<OrderInfo> orderInfoList=getOrderMapper.GetNotReadOrder(bid);
        return orderInfoList;
    }
}