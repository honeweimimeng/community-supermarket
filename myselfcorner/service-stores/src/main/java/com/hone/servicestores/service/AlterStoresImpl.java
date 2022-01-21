package com.hone.servicestores.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hone.servicestores.dao.AlterStoresMapper;
import com.hone.servicestores.dao.CreateStoresMapper;
import com.hone.servicestores.dao.GetStoresMapper;
import com.hone.servicestores.model.SeckillInfo;
import com.hone.servicestores.model.StoresInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AlterStoresImpl {
    @Autowired
    private AlterStoresMapper alterStoresMapper;
    @Autowired
    private GetStoresMapper getStoresMapper;
    @Autowired
    private CreateStoresMapper createStoresMapper;
    public String AlterStoresTotal(String altertype,String sid,String total,String content){
        StoresInfo list=getStoresMapper.GetSingleItemize(sid);
        JSONArray jsonArray = JSONArray.parseArray(list.getContent());
        JSONArray jsonArray1=new JSONArray();
        Integer scores=0;
        for(int index=0;index<jsonArray.size();index++){
            JSONObject jsonObject=(JSONObject) jsonArray.get(index);
            if(jsonObject.getString("content").equals(content)){
                int totals=Integer.parseInt(jsonObject.getString("total"));
                if(altertype.equals("add")){
                    totals+=Integer.parseInt(total);
                }else if(altertype.equals("sub")){
                    if(!(Integer.parseInt(total)<=0)){
                        totals-=Integer.parseInt(total);
                    }
                    if(totals<0){
                        totals=0;
                    }
                }
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("total",totals);
                jsonObject1.put("content",content);
                jsonArray1.add(jsonObject1);
            }else{
                jsonArray1.add(jsonObject);
            }
        }
        for(int index=0;index<jsonArray1.size();index++){
            scores+=Integer.parseInt(((JSONObject)jsonArray1.get(index)).getString("total"));
        }
        alterStoresMapper.AlterStoresDetailsTotal(sid,scores+"");
        alterStoresMapper.AlterStoresTotal(sid,JSON.toJSONString(jsonArray1));
        return "success";
    }
    public String DeleteStores(String sid){
        alterStoresMapper.DeleteStores_baseinfo(sid);
        alterStoresMapper.DeleteStores_details(sid);
        alterStoresMapper.DeleteStores_itemze(sid);
        return "success";
    }
    public synchronized String JoinSeckill(List<SeckillInfo> list){
        for(SeckillInfo seckillInfo:list){
            Date date=new Date();
            seckillInfo.setStarttime((date.getYear()+1900)+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日"+seckillInfo.getStarttime()+":00");
            createStoresMapper.JoinSeckill(seckillInfo.getSid(),seckillInfo.getStarttime(),
                    seckillInfo.getDiscount(),seckillInfo.getBid());
        }
        return "scuuess";
    }
    public List<SeckillInfo> GetJoinSeckill(String bid){
        Date date=new Date();
        String nowdate=date.getYear()+1900+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日"+(date.getHours()+1)+":00";
        System.out.println(getStoresMapper.GetJoinSeckill(bid,nowdate));
        return getStoresMapper.GetJoinSeckill(bid,nowdate);
    }
    @Transactional
    public String AlterRoMap(String bid,String createtype){
        Date date=new Date();
        Integer endhour=date.getHours();
        String day=date.getDate()+"";
        if((endhour+12)>=24){
            day=day+"M";
            endhour=(endhour+12)-24;
        }
        if (createtype.equals("1")){
            String enddate=date.getYear()+1900+"年"+(date.getMonth()+1)+"月"+day+"日"+endhour+"时";
            alterStoresMapper.AlterRoMapStatus(bid,enddate);
        }else{
            alterStoresMapper.DeleteRoMap(bid);
        }
        return "success";
    }
    public void RemoveStoresCar(String uid,String sid){
        alterStoresMapper.RemoveStoresCar(uid,sid);
    }
}