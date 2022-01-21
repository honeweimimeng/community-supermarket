package com.hone.serviceorder.dao;

import com.hone.serviceorder.model.OrderInfo;
import com.hone.serviceorder.model.SeckillInfo;
import com.hone.serviceorder.model.StoresInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface GetOrderMapper {
    List<OrderInfo> GetOrderArr(@Param("uid") String uid,@Param("utype") String utype,@Param("status") String status);
    StoresInfo SingleStores(@Param("sid") String sid);
    SeckillInfo GetSingleSeckill(@Param("sid") String sid);
    List<OrderInfo> GetNotReadOrder(@Param("bid")String bid);
}