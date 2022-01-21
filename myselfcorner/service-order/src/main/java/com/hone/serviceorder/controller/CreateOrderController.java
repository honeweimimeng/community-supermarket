package com.hone.serviceorder.controller;

import com.hone.localcommons.Model.Response;
import com.hone.localcommons.Model.Result;
import com.hone.localcommons.annotation.TokenChecking;
import com.hone.serviceorder.model.Adress;
import com.hone.serviceorder.model.OrderInfo;
import com.hone.serviceorder.service.AdressImpl;
import com.hone.serviceorder.service.CreateOrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CreateOrderController {
    @Autowired
    private CreateOrderImpl createOrder;
    @Autowired
    private AdressImpl adressimpl;
    @TokenChecking
    @RequestMapping(value = "/OrderInfo/CreateOrder",method = RequestMethod.POST)
    public Result<String> CreateOrder(@RequestBody List<OrderInfo> orderInfos){
        return Response.CreateSuccessRsp(createOrder.CreateOrder(orderInfos));
    }
    @TokenChecking
    @RequestMapping(value = "/AdressInfo/CreateAdress",method = RequestMethod.POST)
    public Result<String> CreateAdress(@RequestBody Adress adress){
        return Response.CreateSuccessRsp(adressimpl.CreateAdress(adress.getUid(),adress.getName(),adress.getAdress(),
                adress.getPhonenumber()));
    }
}