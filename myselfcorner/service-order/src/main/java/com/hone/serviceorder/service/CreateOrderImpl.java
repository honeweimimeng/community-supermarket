package com.hone.serviceorder.service;

import com.alibaba.fastjson.JSON;
import com.hone.localcommons.Client.MQUtils;
import com.hone.localcommons.constant.FontConstant;
import com.hone.serviceorder.dao.AlterOrderMapper;
import com.hone.serviceorder.dao.CreateOrderMapper;
import com.hone.serviceorder.feign.UserFeign;
import com.hone.serviceorder.model.OrderInfo;
import com.hone.serviceorder.model.TalksInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CreateOrderImpl {
    @Autowired
    private MQUtils mqUtils;
    @Autowired
    private CreateOrderMapper createOrderMapper;
    @Autowired
    private AlterOrderMapper alterOrderMapper;
    @Autowired
    private UserFeign userFeign;
    public String CreateOrder(List<OrderInfo> orderInfos){
        mqUtils.PushForMQQue(FontConstant.REDISORDER,JSON.toJSONString(orderInfos));
        return "success";
    }
    @Transactional
    public void CreateTalksInfo(String bid,String sid,String content,String oid,String type){
        //添加评论，更新商品，店铺好评数
        TalksInfo talksInfo=new TalksInfo();
        talksInfo.setBid(bid);
        talksInfo.setContent(content);
        talksInfo.setOid(oid);
        talksInfo.setSid(sid);
        talksInfo.setType(type);
        userFeign.AlterBusinessTalks(talksInfo);
        Date date=new Date();
        String nowdate=date.getYear()+1900+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日"+(date.getHours()+1)+":00";
        createOrderMapper.CreateTalksInfo(bid,content,sid,nowdate);
        alterOrderMapper.AlterOrderStatus(oid);
    }
}