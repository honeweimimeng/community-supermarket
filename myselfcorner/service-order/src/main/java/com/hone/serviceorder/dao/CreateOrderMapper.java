package com.hone.serviceorder.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CreateOrderMapper {
    void CreateOrderBase(@Param("oid")String oid,@Param("status")String status,@Param("finalpaymoney")String finalpaymoney,
                         @Param("showpaymoney")String showpaymoney,@Param("uid")String uid,@Param("bid")String bid,@Param("aimadress")String aimadress);
    void CreateOrderDate(@Param("oid")String oid,@Param("createdate")String createdate,@Param("paydate")String paydate);
    void CreateOrderDetail(@Param("oid")String oid,@Param("sid")String sid,@Param("content") String content,@Param("storesnum")String storesnum);
    void CreateTalksInfo(@Param("bid")String bid,@Param("content")String content,@Param("sid")String sid,@Param("date")String date);
}