package com.hone.serviceorder.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AlterOrderMapper {
    void AlterOrderStatus(@Param("oid")String oid);
}