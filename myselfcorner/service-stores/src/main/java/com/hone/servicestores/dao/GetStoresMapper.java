package com.hone.servicestores.dao;

import com.hone.localcommons.Model.StoresCar;
import com.hone.localcommons.Model.UserBrowse;
import com.hone.servicestores.model.RecommendMap;
import com.hone.servicestores.model.SeckillInfo;
import com.hone.servicestores.model.StoresInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface GetStoresMapper {
    StoresInfo GetSingleStoresForName(@Param("name") String name,@Param("price") String price);
    List<StoresInfo> GetStoresSortFieldArr_Limit(@Param("bid") String bid,@Param("field")String field,String Sort,@Param("searchcontent") String searchcontent);
    List<StoresInfo> GetSingleStoresAll(@Param("bid") String bid,@Param("field")String field,@Param("searchcontent") String searchcontent);
    StoresInfo GetSingleItemize(@Param("sid") String sid);
    List<SeckillInfo> GetJoinSeckill(@Param("bid")String bid,@Param("date")String date);
    List<RecommendMap> GetRowMap(@Param("bid")String bid);
    List<RecommendMap> GetRoMapForsingle();
    List<UserBrowse> GetUserBrowse(@Param("uid")String uid);
    List<StoresCar> GetUserStoresCar(@Param("uid")String uid);
    List<SeckillInfo> GetSkillStoresLimitTime(@Param("start") String start);
    SeckillInfo GetSkillStores(@Param("sid") String sid);
    StoresInfo GetStoresCar(@Param("uid")String uid,@Param("sid")String sid);
    List<StoresInfo> GetSearchStores_Up(@Param("content")String content,@Param("type")String type,@Param("seltype")String seltype);
    List<StoresInfo> GetSearchStores_Down(@Param("content")String content,@Param("type")String type,@Param("seltype")String seltype);
    StoresInfo GetStoresSIngleByType(@Param("type")String type);
    List<StoresInfo> GetStoresNewLimitArr(@Param("end")String end);
    StoresInfo SingleStores(@Param("sid") String sid);
}