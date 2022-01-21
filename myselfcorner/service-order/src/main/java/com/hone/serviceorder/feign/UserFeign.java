package com.hone.serviceorder.feign;

import com.hone.serviceorder.model.TalksInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-user")
public interface UserFeign {
    @RequestMapping(value = "/UserInfo/AlterBusinessTalks",method = RequestMethod.POST)
    String AlterBusinessTalks(@RequestBody TalksInfo talksInfo);
}