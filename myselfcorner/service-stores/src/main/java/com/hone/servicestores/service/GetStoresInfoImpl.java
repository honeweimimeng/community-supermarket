package com.hone.servicestores.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hone.localcommons.Client.MQUtils;
import com.hone.localcommons.Model.StoresCar;
import com.hone.localcommons.Model.UserBrowse;
import com.hone.localcommons.Utils.RedisUtils;
import com.hone.localcommons.constant.FontConstant;
import com.hone.servicestores.constant.ComConstant;
import com.hone.servicestores.dao.GetStoresMapper;
import com.hone.servicestores.dao.UserInfoMapper;
import com.hone.servicestores.model.RecommendMap;
import com.hone.servicestores.model.SeckillInfo;
import com.hone.servicestores.model.StoresInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class GetStoresInfoImpl {
    @Autowired
    private GetStoresMapper getStoresMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private MQUtils mqUtils;
    public List<StoresInfo> GetStoresAll(String bid, Integer up, Integer down, String colum,String searchcontent){
        String sort;
        if(up==1){
            sort="up";
        }else if(down==1){
            sort="down";
        }else{
            return null;
        }
        List<StoresInfo> res_storesInfos=getStoresMapper.GetStoresSortFieldArr_Limit(bid,colum,sort,searchcontent);
        return res_storesInfos;
    }
    public List<StoresInfo> GetSingleStoresAll(String bid,String colum,String searchcontent){
        List<StoresInfo> res_storesInfos=getStoresMapper.GetSingleStoresAll(bid,colum,searchcontent);
        return res_storesInfos;
    }
    public List<RecommendMap> GetRoMapForsingle(){
        List<RecommendMap> list;
        if(redisUtils.getValue("RoMapArr")==null){
            redisUtils.setValue("RoMapArr",JSON.toJSONString(getStoresMapper.GetRoMapForsingle()),RedisUtils.TOKEN_EXPIRES_SECOND, TimeUnit.SECONDS);
            list=getStoresMapper.GetRoMapForsingle();
        }else{
            String json_str=(String)redisUtils.getValue("RoMapArr");
            JSONArray jsonArray=JSONArray.parseArray(json_str);
            list=JSONArray.toJavaObject(jsonArray,List.class);
        }
        Date date=new Date();
        String nowdate=date.getYear()+1900+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日"+(date.getHours()+1)+"时";
        int index_=0;
        for(RecommendMap recommendMap:list){
            if(nowdate.compareTo(recommendMap.getStartdate())>0){
                list.remove(index_);
            }
            index_++;
        }
        for(int index=0;index<(8-list.size());index++){
            RecommendMap recommendMap=new RecommendMap();
            recommendMap.setMapurl(ComConstant.DefualtImg);
            list.add(recommendMap);
        }
        return list;
    }
    public List<SeckillInfo> GetSkillStores(String sid){
        if(sid!=null){
            List<SeckillInfo> list=new ArrayList<>();
            list.add(getStoresMapper.GetSkillStores(sid));
            return list;
        }
        Date date=new Date();
        String now_date=date.getYear()+1900+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日"+(date.getHours()+1)+":00";
        return getStoresMapper.GetSkillStoresLimitTime(now_date);
    }
    @Transactional
    public List<StoresInfo> GetRecommendStores(String uid,String city){
        uid=redisUtils.getValue(uid)+"";
        if(redisUtils.getValue(uid+"RecommendStores")!=null){
            //取缓存数据
            List<StoresInfo> reslist=JSONArray.parseArray(redisUtils.getValue(uid+"RecommendStores")+"",StoresInfo.class);
            return reslist;
        }
        List<UserBrowse> userBrowses=getStoresMapper.GetUserBrowse(uid);
        List<StoresCar> userstorescar=getStoresMapper.GetUserStoresCar(uid);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Browses",JSON.toJSONString(userBrowses));
        jsonObject.put("StoresCar",JSON.toJSONString(userstorescar));
        jsonObject.put("City",city);
        jsonObject.put("Uid",uid);
        UserBrowse my_userBrowse_Ins=userInfoMapper.GetSingleUserIns(uid);
        List<StoresInfo> storesInfoList=new ArrayList<>();
        if(my_userBrowse_Ins!=null){
            //获取返回列表
            String type1=my_userBrowse_Ins.getKeyword();
            String type2=my_userBrowse_Ins.getSe_keyword();
            List<UserBrowse> simuserlist=JSONArray.parseArray(my_userBrowse_Ins.getSimuserarr(),UserBrowse.class);
            storesInfoList.add(getStoresMapper.GetStoresSIngleByType(type1));
            storesInfoList.add(getStoresMapper.GetStoresSIngleByType(type2));
            for(UserBrowse userBrowse_now:simuserlist){
                String keycode=userBrowse_now.getKeyword();
                storesInfoList.add(getStoresMapper.GetStoresSIngleByType(keycode));
            }
            redisUtils.setValue(uid+"RecommendStores",JSON.toJSONString(storesInfoList),RedisUtils.TOKEN_EXPIRES_SECOND,TimeUnit.SECONDS);
            return storesInfoList;
        }
        //加入计算队列
        mqUtils.PushForMQQue(FontConstant.CALMQNAME,JSON.toJSONString(jsonObject));
        //补齐加入缓存
        storesInfoList=getStoresMapper.GetStoresNewLimitArr(ComConstant.ALLSTORESNUM+"");
        redisUtils.setValue(uid+"RecommendStores",JSON.toJSONString(storesInfoList),RedisUtils.TOKEN_EXPIRES_SECOND,TimeUnit.SECONDS);
        return storesInfoList;
    }
    public List<StoresInfo> SearchStores(String content,String type,String seltype,String selsel){
        if(selsel.equals("0")) {
            //升序
            if(seltype.equals("sale")){
                seltype="sd.salesnum";
            }else if(seltype.equals("price")){
                seltype="sb.price";
            }else{
                return new ArrayList<>();
            }
            if(type.equals("全部分类")){
                type="null";
            }
            return getStoresMapper.GetSearchStores_Up(content,type,seltype);
        }else if(selsel.equals("1")){
            if(seltype.equals("sale")){
                seltype="sd.salesnum";
            }else if(seltype.equals("price")){
                seltype="sb.price";
            }else{
                return new ArrayList<>();
            }
            if(type.equals("全部分类")){
                type="null";
            }
            return getStoresMapper.GetSearchStores_Down(content,type,seltype);
        }else{
            return new ArrayList<>();
        }
    }
    public List<StoresInfo> GetStoresCar(String uid){
        uid=redisUtils.getValue(uid)+"";
        List<StoresInfo> reslist=new ArrayList<>();
        for(StoresCar storesCar:getStoresMapper.GetUserStoresCar(uid)){
            StoresInfo storesInfo=getStoresMapper.SingleStores(storesCar.getSid());
            storesInfo.setStoresnum(storesCar.getStoresnum());
            storesInfo.setContent(storesCar.getContent());
            storesInfo.setDetailsarr(null);
            reslist.add(storesInfo);
        }
        return reslist;
    }
    public StoresInfo GetSingleStores(String sid){
        return getStoresMapper.SingleStores(sid);
    }
}