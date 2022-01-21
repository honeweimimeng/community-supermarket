package com.hone.serviceuser.dao;

import com.hone.localcommons.Model.UserBrowse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AlterUserInfoMapper {
    void DeleteApplyBusiness(@Param("uid")String uid);
    void AlterBusinessStatus(@Param("bid")String bid);
    void BusinessGTalks(@Param("bid")String bid);
    void BusinessBTalks(@Param("bid")String bid);
    UserBrowse Adduserbrowse(@Param("uid")String uid,@Param("sid") String sid);
}