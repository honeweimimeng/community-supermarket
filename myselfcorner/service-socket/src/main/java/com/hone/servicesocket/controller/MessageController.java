package com.hone.servicesocket.controller;

import com.hone.localcommons.Model.Response;
import com.hone.localcommons.Model.Result;
import com.hone.localcommons.annotation.TokenChecking;
import com.hone.servicesocket.model.ChatMessage;
import com.hone.servicesocket.service.MessageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageImpl messageimpl;
    @TokenChecking
    @RequestMapping(value = "/Message/GetChatList",method = RequestMethod.POST)
    public Result<List<ChatMessage>> GetChatList(@RequestBody ChatMessage chatMessage){
        return Response.CreateSuccessRsp(messageimpl.GetChatList(chatMessage.getUid(),chatMessage.getUtype()));
    }
    @TokenChecking
    @RequestMapping(value = "/Message/ChatListForUser",method = RequestMethod.POST)
    public Result<List<ChatMessage>> ChatListForUser(@RequestBody ChatMessage chatMessage){
        return Response.CreateSuccessRsp(messageimpl.GetChatListForUser(chatMessage.getUid(),chatMessage.getAimuid(),chatMessage.getUtype()));
    }
}