package com.hone.servicesocket.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hone.localcommons.Utils.HttpClientUtils;
import com.hone.localcommons.Utils.RedisUtils;
import com.hone.localcommons.constant.FontConstant;
import com.hone.servicesocket.dao.MsgMapper;
import com.hone.servicesocket.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageImpl {
    @Autowired
    private MsgMapper msgMapper;
    @Autowired
    private RedisUtils redisUtils;
    public List<ChatMessage> GetChatList(String uid,String utype){
        List<ChatMessage> list=new ArrayList<>();
        if(utype.equals("0")){
            list=msgMapper.GetChatList_Business(uid);
        }else if(utype.equals("1")){
            uid=redisUtils.getValue(uid)+"";
            list=msgMapper.GetChatList_Customer(uid);
        }
        return list;
    }
    public List<ChatMessage> GetChatListForUser(String uid,String aimuid,String utype){
        if(utype.equals("1")){
            uid=redisUtils.getValue(uid)+"";
        }
        List<ChatMessage> list=msgMapper.GetChatListForUser(uid,aimuid,utype);
        if(list.size()==0){
            //添加联系人
            Date date=new Date();
            String createdate=date.getYear()+1900+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
            msgMapper.CreateNewChat(uid,"欢迎光临","0",createdate,aimuid,"0");
        }
        return list;
    }
    public void SendOutLineMsg() throws IOException {
        //推送离线消息
    }
    public void StoresMsg(String uid,String aimuid,String utype,String msg){
        //储存聊天信息
        Date date=new Date();
        String createdate=date.getYear()+1900+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
        msgMapper.CreateNewChat(uid,msg,"0",createdate,aimuid,utype);
    }
}