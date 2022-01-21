package com.hone.servicesocket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hone.localcommons.Utils.RedisUtils;
import com.hone.localcommons.constant.FontConstant;
import com.hone.servicesocket.model.SingleMsg;
import com.hone.servicesocket.service.MessageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;

@ServerEndpoint("/Business/{bid}/{utype}")
@Component
public class BusinessController {
    private static RedisUtils redisUtils;
    private static MessageImpl messageimpl;
    public static HashMap<String,BusinessController> businesschannlmap=new HashMap();
    private Session session;
    private String bid;
    @Autowired
    public void setRedisUtils(RedisUtils redisUtils){
        BusinessController.redisUtils=redisUtils;
    }
    @Autowired
    public void setMessageImpl(MessageImpl messageImpl){
        BusinessController.messageimpl=messageImpl;
    }
    @OnOpen
    public void onOpen(Session session,@PathParam("bid")String bid,@PathParam("utype")String utype) {
        if(utype.equals("1")){
            this.bid=redisUtils.getValue(bid)+"";
            System.out.println(bid);
        }else{
            this.bid=bid;
        }
        businesschannlmap.put(bid,this);
        this.session=session;
    }
    @OnClose
    public void onClose(Session session) {
        businesschannlmap.remove(this.bid);
    }
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        businesschannlmap.remove(this.bid);
    }
    @OnMessage
    public void onMessage(String message) throws IOException, EncodeException {
        JSONObject jsonObject= JSON.parseObject(message);
        String bid=jsonObject.getString("Bid");
        String Msg=jsonObject.getString("Msg");
        String uid=jsonObject.getString("Uid");
        String Utype=jsonObject.getString("UType");
        if(jsonObject.getString("Type").equals("Order")){
            //推送订单
            if(businesschannlmap.get(bid)==null) {
                //推送小程序通知
                messageimpl.SendOutLineMsg();
                return;
            }
            businesschannlmap.get(bid).session.getBasicRemote().sendText(Msg);
        }else if(jsonObject.getString("Type").equals("Chat")){
            SingleMsg singleMsg=new SingleMsg("message",message);
            JSONObject jsonObject1=JSON.parseObject(singleMsg.getMsg());
            if(Utype.equals("1")){
                //商家接收
                if(businesschannlmap.get(bid)==null){
                    //推送离线信息
                    messageimpl.SendOutLineMsg();
                    messageimpl.StoresMsg(redisUtils.getValue(uid)+"",bid,Utype,jsonObject1.getString("Msg"));
                    return;
                }
                messageimpl.StoresMsg(redisUtils.getValue(uid)+"",bid,Utype,jsonObject1.getString("Msg"));
                businesschannlmap.get(bid).session.getBasicRemote().sendText(JSON.toJSONString(singleMsg));
            }else if(Utype.equals("0")){
                //用户接收
                System.out.println(uid);
                if(businesschannlmap.get(uid)==null){
                    //推送离线信息
                    messageimpl.SendOutLineMsg();
                    messageimpl.StoresMsg(uid,bid,Utype,jsonObject1.getString("Msg"));
                    return;
                }
                messageimpl.StoresMsg(uid,bid,Utype,jsonObject1.getString("Msg"));
                businesschannlmap.get(uid).session.getBasicRemote().sendText(jsonObject1.getString("Msg"));
            }
        }
    }
}