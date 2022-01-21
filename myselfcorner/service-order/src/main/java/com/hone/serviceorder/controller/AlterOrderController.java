package com.hone.serviceorder.controller;

import com.hone.localcommons.Model.Response;
import com.hone.localcommons.Model.Result;
import com.hone.localcommons.annotation.TokenChecking;
import com.hone.serviceorder.model.OrderInfo;
import com.hone.serviceorder.model.TalksInfo;
import com.hone.serviceorder.service.AlterOrderImpl;
import com.hone.serviceorder.service.CreateOrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlterOrderController {
    @Autowired
    private AlterOrderImpl alterOrder;
    @Autowired
    private CreateOrderImpl createOrder;
    @TokenChecking
    @RequestMapping(value = "/OrderInfo/AlterOrderStatus",method = RequestMethod.POST)
    public Result<String> AlterOrderStatus(@RequestBody OrderInfo orderInfo){
        alterOrder.AlterOrderStatus(orderInfo.getOid());
        return Response.CreateSuccessRsp("success");
    }
    @TokenChecking
    @RequestMapping(value = "/OrderInfo/AddTalksInfo",method = RequestMethod.POST)
    public Result<String> AddTalksInfo(@RequestBody TalksInfo talksInfo){
        createOrder.CreateTalksInfo(talksInfo.getBid(),talksInfo.getSid(),talksInfo.getContent(),talksInfo.getOid(),talksInfo.getType());
        return Response.CreateSuccessRsp("success");
    }
}