package com.hone.servicestores.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CreateStoresMapper {
    void CreateStoresBaseInfo(@Param("sid") String sid,@Param("bid") String bid,@Param("name") String name,@Param("btype") String btype,
                              @Param("price") String price,@Param("details")String details,@Param("createdate") String createdate);
    void CreateStoresDetails(@Param("sid") String sid,@Param("itemizearr")String itemizearr);
    void CreateStoresItemize(@Param("sid") String sid,@Param("content")String content);
    void JoinSeckill(@Param("sid")String sid,@Param("starttime")String starttime,@Param("discount")String discount,@Param("bid")String bid);
    void CreateRoMap(@Param("bid")String bid,@Param("mapurl")String mapurl,@Param("lab") String lab,@Param("startdate")String startdate,@Param("enddate")String enddate);
    void CreateStoresCar(@Param("uid")String uid,@Param("sid")String sid,@Param("date")String date,@Param("storesnum")String storesnum,@Param("content")String content);
}