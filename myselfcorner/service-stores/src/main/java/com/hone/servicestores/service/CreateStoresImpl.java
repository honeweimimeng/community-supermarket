package com.hone.servicestores.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hone.localcommons.Utils.RedisUtils;
import com.hone.localcommons.Utils.SingleIdGenerator;
import com.hone.servicestores.dao.AlterStoresMapper;
import com.hone.servicestores.dao.CreateStoresMapper;
import com.hone.servicestores.dao.GetStoresMapper;
import com.hone.servicestores.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CreateStoresImpl {
    @Autowired
    private CreateStoresMapper createStoresMapper;
    @Autowired
    private GetStoresMapper getStoresMapper;
    @Autowired
    private SingleIdGenerator singleIdGenerator;
    @Autowired
    private AlterStoresMapper alterStoresMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Transactional
    public String CreateNewStores(String bid, String name, String type, String price, List<StoresDetailInfo> detailsinfo,List<StoresTypeArr> typearr){
        String detailsinfo_strjson= JSON.toJSONString(detailsinfo);
        String typearr_strjson=JSON.toJSONString(typearr);
        if(getStoresMapper.GetSingleStoresForName(name,price)!=null){
            return "error";
        }
        singleIdGenerator.initUidGenerator(2,1);
        Long sid=singleIdGenerator.nextId();
        Date date=new Date();
        String createdate=1900+date.getYear()+"年"+(1+date.getMonth())+"月"+date.getDate()+"日";
        createStoresMapper.CreateStoresBaseInfo(sid+"",bid,name,type,price,detailsinfo_strjson,createdate);
        createStoresMapper.CreateStoresDetails(sid+"",typearr_strjson);
        List<List<String>> list_list_sontype=new ArrayList<>();
        for(StoresTypeArr storesTypeArr:typearr){
            List<String> contentarr=new ArrayList<>();
            for(StoresSonType storesSonType:storesTypeArr.getContent()){
                contentarr.add(storesSonType.getName());
            }
            list_list_sontype.add(contentarr);
        }
        List<String> contentarr=list_list_sontype.get(0);
        for(int index=0;index<list_list_sontype.size()-1;index++){
            contentarr=doListLine(contentarr,list_list_sontype.get(index+1));
        }
        List<JSONObject> jsonObject=new ArrayList<>();
        for(String content:contentarr){
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("content",content);
            jsonObject1.put("total",0);
            jsonObject.add(jsonObject1);
        }
        createStoresMapper.CreateStoresItemize(sid+"",JSON.toJSONString(jsonObject));
        return "success";
    }
    public List doListLine(List<String> arrayLists1,List<String> arrayLists2){
        List<String> reslist=new ArrayList<>();
        for(String content_now:arrayLists1){
            for(String content_last:arrayLists2){
                reslist.add(content_now+content_last);
            }
        }
        return reslist;
    }
    public synchronized String CreateRoMap(String bid,String lab,String mapurl){
        if(GetRowMap(bid).size()!=0){
            return "error01";
        }
        Date date=new Date();
        String startdate=date.getYear()+1900+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日"+date.getHours()+"时";
        Integer endhour=date.getHours();
        String day=date.getDate()+"";
        if((endhour+12)>=24){
            day=day+"M";
            endhour=(endhour+12)-24;
        }
        String enddate=date.getYear()+1900+"年"+(date.getMonth()+1)+"月"+day+"日"+endhour+"时";
        createStoresMapper.CreateRoMap(bid,mapurl,lab,startdate,enddate);
        return "success";
    }
    public List<RecommendMap> GetRowMap(String bid){
        return getStoresMapper.GetRowMap(bid);
    }
    public String AddStoresCar(StoresInfo storesInfo){
        String uid=redisUtils.getValue(storesInfo.getBid())+"";
        Date date=new Date();
        String startdate=date.getYear()+1900+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日"+date.getHours()+"时";
        if(getStoresMapper.GetStoresCar(uid,storesInfo.getSid())==null){
            createStoresMapper.CreateStoresCar(uid,storesInfo.getSid(),startdate,
                    storesInfo.getStoresnum(),storesInfo.getContent());
            return "success";
        }
        alterStoresMapper.AlterStoresCarNum(uid,storesInfo.getSid(),storesInfo.getStoresnum(),storesInfo.getAltertype());
        return "success";
    }
}