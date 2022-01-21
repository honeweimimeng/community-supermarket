package com.hone.servicecalculation.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AddUserInsMapper {
    void CreateUserIns(@Param("uid") String uid,@Param("keyword") String keyword,@Param("se_keyword") String se_keyword,@Param("simuserarr") String simuserarr);
    void AlterUserIns(@Param("uid") String uid,@Param("keyword") String keyword,@Param("se_keyword") String se_keyword,@Param("simuserarr") String simuserarr);
}