package com.hone.serviceuser.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CreateUserInfoMapper {
    void CreateApplyBusiness(@Param("hostid") String hostid,@Param("phonenumber") String phonenumber,@Param("bname") String bname,@Param("btype") String btype,
                        @Param("blab") String blab,@Param("address") String address,@Param("longitude") String longitude,@Param("latitude") String latitude,
                        @Param("licenseurl") String licenseurl,@Param("idcardtopurl") String idcardtopurl,@Param("idcardbottomurl") String idcardbottomurl,
                        @Param("iconurl") String iconurl);
    void CreateBusiness(@Param("bid")String bid,@Param("hostid") String hostid,@Param("phonenumber") String phonenumber,@Param("bname") String bname,
                        @Param("btype") String btype,@Param("blab") String blab,@Param("address") String address,@Param("createdate")String createdate,
                        @Param("longitude") String longitude,@Param("latitude") String latitude,@Param("iconurl") String iconurl);
    void CreateBusinessDetails(@Param("bid")String bid);
    void CreateCustomerBaseInfo(@Param("uid")String uid,@Param("name")String name,@Param("icon")String icon);
    void UpdateCustomerBseInfo(@Param("uid")String uid,@Param("name")String name,@Param("icon")String icon);
    void CreateBrowse(String uid,String sid,String browresult,String browlongtime,String clicknum);
}