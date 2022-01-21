package com.hone.serviceorder.feign;

import com.hone.serviceorder.model.StoresInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-stores")
public interface StoresFeign {
    @RequestMapping(value = "/StoresInfo/AlterSalesNum",method = RequestMethod.POST)
    void AlterSalesNum(@RequestBody StoresInfo storesInfo);
    @RequestMapping(value = "/StoresInfo/GetSingleStores",method = RequestMethod.POST)
    StoresInfo GetSingleStores(@RequestBody String sid);
}