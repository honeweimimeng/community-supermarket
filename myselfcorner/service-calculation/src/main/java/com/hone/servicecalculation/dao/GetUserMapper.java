package com.hone.servicecalculation.dao;

import com.hone.localcommons.Model.UserBrowse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface GetUserMapper {
    List<UserBrowse> GetUserStores(@Param("city")String city);
    UserBrowse GetSingleUserIns(@Param("uid") String uid);
}