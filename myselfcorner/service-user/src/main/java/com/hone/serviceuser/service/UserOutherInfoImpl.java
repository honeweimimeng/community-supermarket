package com.hone.serviceuser.service;

import com.hone.localcommons.Model.UserBrowse;
import com.hone.localcommons.Utils.SingleIdGenerator;
import com.hone.localcommons.constant.FileConstant;
import com.hone.localcommons.constant.FontConstant;
import com.hone.serviceuser.constant.ComConstant;
import com.hone.serviceuser.dao.AlterUserInfoMapper;
import com.hone.serviceuser.dao.CreateUserInfoMapper;
import com.hone.serviceuser.dao.GetUserInfoMapper;
import com.hone.serviceuser.model.ApplyBusinessInfo;
import com.hone.serviceuser.model.BusinessInfo;
import com.hone.serviceuser.model.SingleArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class UserOutherInfoImpl {
    @Autowired
    private SingleIdGenerator singleIdGenerator;
    @Autowired
    private GetUserInfoMapper getUserInfoMapper;
    @Autowired
    private AlterUserInfoMapper alterUserInfoMapper;
    @Autowired
    private CreateUserInfoMapper createUserInfoMapper;
    public String UpLoadFile(HttpServletRequest httpServletRequest,Integer runcode){
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) httpServletRequest;
        MultipartFile multipartFile = req.getFile("IdCardFile");
        if(runcode==0){
            singleIdGenerator.initUidGenerator(ComConstant.serveridcard,2);
        }else if(runcode==1){
            singleIdGenerator.initUidGenerator(ComConstant.serveridcard,3);
        }else {
            return "error";
        }
        Long bid=singleIdGenerator.nextId();
        String picName=bid+"IdCardImg"+".jpg";
        try {
            File dir = new File(FileConstant.IDCARDDIR);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(FileConstant.IDCARDDIR, picName);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return picName;
    }
    public SingleArray<ApplyBusinessInfo> GetApplyBusinessArr(String page){
        Integer start=Integer.parseInt(page)* FontConstant.OnePageNums;
        Integer end=start+FontConstant.OnePageNums;
        SingleArray<ApplyBusinessInfo> singleArray=new SingleArray<>();
        Integer pagenum=Integer.parseInt(getUserInfoMapper.GetApplyBusinessCount())/FontConstant.OnePageNums;
        if(Integer.parseInt(getUserInfoMapper.GetApplyBusinessCount())%FontConstant.OnePageNums>0){
            pagenum++;
        }
        singleArray.setData(pagenum+"");
        List<ApplyBusinessInfo> reslist=getUserInfoMapper.GetApplyBusinessArr(start+"",end+"");
        singleArray.setList(reslist);
        return singleArray;
    }
    @Transactional
    public String AlterApplyBusiness(ApplyBusinessInfo applyBusinessInfo,Integer Resultcode){
        if(Resultcode==1){
            singleIdGenerator.initUidGenerator(ComConstant.serveridcard,3);
            alterUserInfoMapper.DeleteApplyBusiness(applyBusinessInfo.getUid());
            Long bid=singleIdGenerator.nextId();
            Date date=new Date();
            String createdate=1900+date.getYear()+"年"+(1+date.getMonth())+"月"+date.getDate()+"日";
            applyBusinessInfo.setCreatedate(createdate);
            createUserInfoMapper.CreateBusiness(bid+"",applyBusinessInfo.getUid(),applyBusinessInfo.getPhonenumber(),
                    applyBusinessInfo.getBname(),applyBusinessInfo.getBtype(),applyBusinessInfo.getBlab(),applyBusinessInfo.getAdress(),
                    applyBusinessInfo.getCreatedate(),applyBusinessInfo.getLongitude(),applyBusinessInfo.getLatitude(),
                    applyBusinessInfo.getIconurl());
            createUserInfoMapper.CreateBusinessDetails(bid+"");
        }else if(Resultcode==0){
            alterUserInfoMapper.DeleteApplyBusiness(applyBusinessInfo.getUid());
        }else{
            return "error";
        }
        return "success";
    }
    public BusinessInfo GetBusinessInfo(String bid){
        BusinessInfo businessInfo=getUserInfoMapper.GetBusinessInfo(bid);
        businessInfo.setHostid(null);
        return businessInfo;
    }
    public BusinessInfo AlterBStatus(String bid){
        alterUserInfoMapper.AlterBusinessStatus(bid);
        return getUserInfoMapper.GetBusinessInfo(bid);
    }
    public void CreateBrowse(String uid,String sid,String browresult,String browlongtime,String clicknum){
        if(getUserInfoMapper.Getuserbrowse(uid,sid)!=null){
            alterUserInfoMapper.Adduserbrowse(uid,sid);
            return;
        }
        createUserInfoMapper.CreateBrowse(uid,sid,browresult,browlongtime,clicknum);
    }
}