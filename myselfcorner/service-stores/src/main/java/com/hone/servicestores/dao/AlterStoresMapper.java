package com.hone.servicestores.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AlterStoresMapper {
    void AlterStoresTotal(@Param("sid") String sid,@Param("content") String content);
    void AlterStoresDetailsTotal(@Param("sid") String sid,@Param("total")String total);
    void DeleteStores_baseinfo(@Param("sid")String sid);
    void DeleteStores_details(@Param("sid")String sid);
    void DeleteStores_itemze(@Param("sid")String sid);
    void DeleteRoMap(@Param("bid") String bid);
    void AlterRoMapStatus(@Param("bid") String bid,@Param("endtime")String endtime);
    void AlterStoresCarNum(@Param("uid")String uid,@Param("sid")String sid,@Param("storesnum")String storesnum,
                           @Param("altertype")String altertype);
    void RemoveStoresCar(@Param("uid")String uid,@Param("sid")String sid);
}