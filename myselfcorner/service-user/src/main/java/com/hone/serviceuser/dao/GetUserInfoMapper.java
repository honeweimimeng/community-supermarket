package com.hone.serviceuser.dao;

import com.hone.localcommons.Model.UserBrowse;
import com.hone.serviceuser.model.ApplyBusinessInfo;
import com.hone.serviceuser.model.BusinessInfo;
import com.hone.serviceuser.model.UserBaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GetUserInfoMapper {
    BusinessInfo GetSingleBusinessInfo(@Param("hostid")String hostid);
    BusinessInfo GetWaiteCheckBusinessInfo(@Param("hostid")String hostid);
    UserBaseInfo GetAdminUserInfo(@Param("uid")String uid);
    List<ApplyBusinessInfo> GetApplyBusinessArr(@Param("start")String start,@Param("end")String end);
    String GetApplyBusinessCount();
    BusinessInfo GetBusinessInfo(@Param("bid") String bid);
    UserBaseInfo GetCustomerBaseInfo(@Param("uid") String uid);
    UserBrowse Getuserbrowse(@Param("uid")String uid,@Param("sid") String sid);
}