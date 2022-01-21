package com.hone.servicesocket.dao;

import com.hone.servicesocket.model.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface MsgMapper {
    List<ChatMessage> GetChatList_Customer(@Param("uid")String uid);
    List<ChatMessage> GetChatList_Business(@Param("uid")String uid);
    List<ChatMessage> GetChatListForUser(@Param("uid")String uid,@Param("aimuid")String aimuid,@Param("utype")String utype);
    void CreateNewChat(@Param("uid")String uid,@Param("content")String content,@Param("type")String type,@Param("date")String date,@Param("aimuid")String aimuid,@Param("utype")String utype);
}