package com.hone.serviceorder.dao;

import com.hone.serviceorder.model.Adress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface AdressMapper {
    List<Adress> GetAdress(@Param("uid")String uid,@Param("name")String name,@Param("adress")String adress);
    void CreateAdress(@Param("uid")String uid,@Param("name")String name,@Param("adress")String adress,@Param("phonenumber")String phonenumber);
    void DeleteAdress(@Param("uid")String uid,@Param("name")String name,@Param("adress")String adress);
}