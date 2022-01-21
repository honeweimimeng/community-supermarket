package com.hone.servicecalculation.App;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hone.localcommons.Model.StoresCar;
import com.hone.localcommons.Model.UserBrowse;
import com.hone.localcommons.Utils.RedisUtils;
import com.hone.localcommons.constant.FontConstant;
import com.hone.localcommons.Client.MQUtils;
import com.hone.servicecalculation.dao.AddUserInsMapper;
import com.hone.servicecalculation.dao.GetUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class MQApplication {
    @Autowired
    private MQUtils mqUtils;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private GetUserMapper getUserMapper;
    @Autowired
    private AddUserInsMapper addUserInsMapper;
    @PostConstruct
    public void initMQ(){
        mqUtils.PopForMQQue(FontConstant.CALMQNAME,(value)->{
            JSONObject jsonObject=JSONObject.parseObject(value);
            String uid=jsonObject.getString("Uid");
            String city=jsonObject.getString("City");
            if(redisUtils.getValue(uid+FontConstant.CALRESCODE)!=null){
                return;
            }
            //运算CAL定制用户兴趣->1.用户特征(购物车)1->2.用户特征(浏览)2->3.用户相似用户列表
            redisUtils.setValue(uid+FontConstant.CALRESCODE,"END",RedisUtils.TOKEN_EXPIRES_SECOND, TimeUnit.SECONDS);
            List<UserBrowse> browseList= (List<UserBrowse>) JSONArray.parseArray(jsonObject.getString("Browses"),UserBrowse.class);
            List<StoresCar> storesCarList=(List<StoresCar>) JSONArray.parseArray(jsonObject.getString("StoresCar"),StoresCar.class);
            List<UserBrowse> userlist=getUserMapper.GetUserStores(city);
            String maxdate="1900年2月2日20:00";
            String maxtype="";
            for(int index=0;index<storesCarList.size();index++){
                if(storesCarList.get(index).getDate().compareTo(maxdate)>0){
                    maxtype=storesCarList.get(index).getBtype();
                }
            }
            Integer maxclik=0;
            String maxtype_se="";
            for(int index=0;index<browseList.size();index++){
                if(Integer.parseInt(browseList.get(index).getClicknum())>maxclik){
                    maxtype_se=browseList.get(index).getBtype();
                }
            }
            //Sid对应浏览次数
            if(browseList.size()==0||userlist.size()==0){
                return;
            }
            List<Map<String,Long[]>> storesbrowmaparr=new ArrayList<>();
            Map<String,Long[]> mymap=new HashMap<>();
            Long[] my_longarr=new Long[2];
            my_longarr[0]=Long.parseLong(browseList.get(0).getSid());
            my_longarr[1]=Long.parseLong(browseList.get(0).getClicknum());
            mymap.put(uid,my_longarr);
            for(UserBrowse userBrowse:userlist){
                Map<String,Long[]> stringIntegerMap=new HashMap<>();
                Long[] longarr=new Long[2];
                longarr[0]=Long.parseLong(userBrowse.getSid());
                longarr[1]=Long.parseLong(userBrowse.getClicknum());
                stringIntegerMap.put(userBrowse.getUid(),longarr);
                storesbrowmaparr.add(stringIntegerMap);
            }
            //余弦相似度算法找出最相似
            Map<String,Double> map=new HashMap<>();
            double[] simarr=new double[storesbrowmaparr.size()];
            for(int index=0;index<storesbrowmaparr.size();index++){
                double result = 0;
                result = pointMulti(storesbrowmaparr.get(index)) / sqrtMulti(mymap);
                simarr[index]=result;
                map.put(storesbrowmaparr.get(index).keySet().toArray()[0]+"",result);
            }
            //相似度用快排排序
            quickSort(simarr,0,simarr.length-1);
            //取前十个相似用户
            List<String> simuidarr=new ArrayList<>();
            for(int index=0;index<10;index++){
                simuidarr.add(getKeySet(map,simarr[index]));
            }
            //插入数据库
            String arrjson=JSON.toJSONString(simuidarr);
            if(getUserMapper.GetSingleUserIns(uid)==null){
                addUserInsMapper.CreateUserIns(uid,maxtype,maxtype_se,arrjson);
            }else{
                addUserInsMapper.AlterUserIns(uid,maxtype,maxtype_se,arrjson);
            }
        });
    }
    private double squares(Map<String,Long[]> paramMap) {
        double result1 = 0;
        double result2 = 0;
        Set<String> keySet = paramMap.keySet();
        for (String character : keySet) {
            Long temp[]= paramMap.get(character);
            result1 += (temp[0] * temp[0]);
            result2 += (temp[1] * temp[1]);
        }
        return result1 * result2;
    }
    private double pointMulti(Map<String,Long[]> paramMap) {
        double result = 0;
        Set<String> keySet = paramMap.keySet();
        for (String character : keySet) {
            Long[] temp= paramMap.get(character);
            result += (temp[0] * temp[1]);
        }
        return result;
    }
    private double sqrtMulti(Map<String,Long[]> paramMap) {
        double result = 0;
        result = squares(paramMap);
        result = Math.sqrt(result);
        return result;
    }
    public void quickSort(double[] arr,int low,int high){
        int i,j;
        double temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = arr[low];
        while (i<j) {
            while (temp<=arr[j]&&i<j) {
                j--;
            }
            while (temp>=arr[i]&&i<j) {
                i++;
            }
            if (i<j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }
        }
        arr[low] = arr[i];
        arr[i] = temp;
        quickSort(arr, low, j-1);
        quickSort(arr, j+1, high);
    }
    public static String getKeySet(Map<String,Double> map,Double value) {
        String key = null;
        for(String getKey: map.keySet()){
            if(map.get(getKey).equals(value)){
                key = getKey;
            }
        }
        return key;
    }
}