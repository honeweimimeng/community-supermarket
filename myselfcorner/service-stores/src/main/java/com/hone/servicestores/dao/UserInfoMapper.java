package com.hone.servicestores.dao;

import com.hone.localcommons.Model.UserBrowse;
import com.hone.servicestores.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserInfoMapper {
    UserInfo GetInterestInfo(@Param("uid") String uid);
    UserBrowse GetSingleUserIns(@Param("uid") String uid);
}