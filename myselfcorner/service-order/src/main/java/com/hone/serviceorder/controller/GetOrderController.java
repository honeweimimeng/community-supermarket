package com.hone.serviceorder.controller;

import com.hone.localcommons.Model.Response;
import com.hone.localcommons.Model.Result;
import com.hone.localcommons.annotation.TokenChecking;
import com.hone.serviceorder.model.Adress;
import com.hone.serviceorder.model.OrderInfo;
import com.hone.serviceorder.service.AdressImpl;
import com.hone.serviceorder.service.GetOrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class GetOrderController {
    @Autowired
    private GetOrderImpl getOrder;
    @Autowired
    private AdressImpl adressimpl;
    @TokenChecking
    @RequestMapping(value = "/OrderInfo/GetOrderArr",method = RequestMethod.POST)
    public Result<List<OrderInfo>> GetOrderArr(@RequestBody OrderInfo orderInfo){
        return Response.CreateSuccessRsp(getOrder.GetOrderArr(orderInfo.getUid(),
                orderInfo.getBid(),orderInfo.getOrdertype()));
    }
    @TokenChecking
    @RequestMapping(value = "/AdressInfo/GetAdress",method = RequestMethod.POST)
    public Result<List<Adress>> GetAdress(@RequestBody OrderInfo orderInfo){
        return Response.CreateSuccessRsp(adressimpl.GetAdress(orderInfo.getUid()));
    }
    @TokenChecking
    @RequestMapping(value = "/AdressInfo/DeleteAdress",method = RequestMethod.POST)
    public Result<String> DeleteAdress(@RequestBody Adress adress){
        return Response.CreateSuccessRsp(adressimpl.DeleteAdress(adress.getUid(),adress.getName(),adress.getAdress()));
    }
    @TokenChecking
    @RequestMapping(value = "/OrderInfo/GetNotReadOrder",method = RequestMethod.POST)
    public Result<List<OrderInfo>> GetNotReadOrder(@RequestBody OrderInfo orderInfo){
        return Response.CreateSuccessRsp(getOrder.GetNotReadOrder(orderInfo.getBid()));
    }
}