package com.hone.serviceorder.App;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hone.localcommons.Client.MQUtils;
import com.hone.localcommons.Utils.RedisUtils;
import com.hone.localcommons.Utils.SingleIdGenerator;
import com.hone.localcommons.constant.FontConstant;
import com.hone.serviceorder.dao.CreateOrderMapper;
import com.hone.serviceorder.dao.GetOrderMapper;
import com.hone.serviceorder.feign.StoresFeign;
import com.hone.serviceorder.model.Adress;
import com.hone.serviceorder.model.OrderInfo;
import com.hone.serviceorder.model.StoresInfo;
import com.hone.serviceorder.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class MQApp {
    @Autowired
    private MQUtils mqUtils;
    @Autowired
    private CreateOrderMapper createOrderMapper;
    @Autowired
    private GetOrderMapper getOrderMapper;
    @Autowired
    private SingleIdGenerator singleIdGenerator;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SpeenMsgClient speenMsgClient;
    @Autowired
    private StoresFeign storesFeign;
    @PostConstruct
    @Transactional
    public void init(){
        mqUtils.PopForMQQue(FontConstant.REDISORDER,(value)->{
            List<OrderInfo> orderInfoList= JSONArray.parseArray(value,OrderInfo.class);
            int index=0;
            singleIdGenerator.initUidGenerator(3,2);
            for(OrderInfo orderInfo:orderInfoList){
                //判断是否是秒杀中的商品
                List<String> sidarr=JSONArray.parseArray(orderInfo.getSid(),String.class);
                List<String> contentarr=JSONArray.parseArray(orderInfo.getContent(),String.class);
                List<String> storesnumarr=JSONArray.parseArray(orderInfo.getStoresnum(),String.class);
                Adress adress=orderInfo.getAdress();
                StoresInfo storesInfo=getOrderMapper.SingleStores(sidarr.get(index));
                String parice=storesInfo.getPrice();
                String finalprice=storesInfo.getPrice();
                Date date=new Date();
                String nowdate=date.getYear()+1900+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日"+(date.getHours()+1)+":00";
                if(getOrderMapper.GetSingleSeckill(sidarr.get(index))!=null){
                    if(getOrderMapper.GetSingleSeckill(sidarr.get(index)).getStarttime().equals(nowdate)){
                        //秒杀商品
                        Float discount=Float.parseFloat(getOrderMapper.GetSingleSeckill(sidarr.get(index)).getDiscount());
                        Float parice_int=Float.parseFloat(parice);
                        parice=(discount*0.1)*parice_int+"";
                    }
                }
                //扣减库存
                if(!AlterSalesnum(sidarr.get(index),contentarr.get(index),storesnumarr.get(index))){
                    //创建订单失败
                    return;
                }
                Long oid=singleIdGenerator.nextId();
                createOrderMapper.CreateOrderBase(oid+"",1+"",finalprice,parice,adress.getUid(),
                        orderInfo.getBid(),adress.getName()+adress.getPhonenumber()+adress.getAdress());
                createOrderMapper.CreateOrderDate(oid+"",nowdate,nowdate);
                createOrderMapper.CreateOrderDetail(oid+"",sidarr.get(index),contentarr.get(index),storesnumarr.get(index));
                //通知商家
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("Bid",orderInfo.getBid());
                jsonObject.put("Type","Order");
                jsonObject.put("Oid",oid+"");
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("Uid",adress.getUid());
                jsonObject1.put("Name",storesInfo.getName());
                jsonObject1.put("Content",contentarr.get(index));
                jsonObject1.put("Price",storesInfo.getPrice());
                jsonObject1.put("Oid",oid+"");
                jsonObject1.put("Adress",adress.getName()+adress.getPhonenumber()+adress.getAdress());
                jsonObject1.put("StoresNum",orderInfo.getStoresnum());
                JSONArray jsonArray=JSONArray.parseArray(storesInfo.getItemizearr());
                JSONObject jsonObject2=(JSONObject)jsonArray.get(0);
                JSONArray jsonArray1=JSONArray.parseArray(jsonObject2.getString("content"));
                jsonObject1.put("ImgUrl",((JSONObject)jsonArray1.get(0)).getString("imgurl"));
                jsonObject.put("Msg",jsonObject1.toJSONString());
                speenMsgClient.appointSending(JSON.toJSONString(jsonObject));
                index++;
            }
            return;
        });
    }
    private synchronized boolean AlterSalesnum(String sid,String content,String totalscore){
        String total="";
        JSONArray jsonObject=JSONArray.parseArray(storesFeign.GetSingleStores(sid).getContent());
        for(Object o:jsonObject){
            JSONObject jsonObject1=(JSONObject)o;
            if(jsonObject1.getString("content").equals(content)){
                total=jsonObject1.getString("total");
                break;
            }
        }
        System.out.println(total);
        if(Integer.parseInt(total)<=0){
            //订单创建失败
            return false;
        }
        StoresInfo storesInfo=new StoresInfo();
        storesInfo.setSid(sid);
        storesInfo.setTotalscore(totalscore);
        storesInfo.setContent(content);
        storesFeign.AlterSalesNum(storesInfo);
        return true;
    }
}