package com.hone.serviceorder.service;

import com.hone.serviceorder.dao.AlterOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlterOrderImpl {
    @Autowired
    private AlterOrderMapper alterOrderMapper;
    public String AlterOrderStatus(String oid){
        alterOrderMapper.AlterOrderStatus(oid);
        return "success";
    }
}