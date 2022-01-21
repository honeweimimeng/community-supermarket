package com.hone.localcommons.configer;

import com.hone.localcommons.Utils.RedisUtils;
import com.hone.localcommons.annotation.AdminChecking;
import com.hone.localcommons.annotation.TokenChecking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Component
public class LocalTokenFilter implements HandlerInterceptor {
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String SessionId = httpServletRequest.getHeader("SessionId");
        String tuid=httpServletRequest.getHeader("tid");
        String token=httpServletRequest.getHeader("token");
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        if (method.isAnnotationPresent(TokenChecking.class)) {
            TokenChecking userLoginToken = method.getAnnotation(TokenChecking.class);
            if (userLoginToken.required()) {
                if (SessionId == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                if(redisUtils.getValue(SessionId)!=null){
                    redisUtils.setValue(SessionId,redisUtils.getValue(SessionId),redisUtils.TOKEN_EXPIRES_SECOND, TimeUnit.SECONDS);
                    return true;
                }
                throw new Exception("Token异常");
            }
        }
        if(method.isAnnotationPresent(AdminChecking.class)){
            //验证Admin权限
            if(token.equals(redisUtils.getValue(tuid))){
                if(redisUtils.getValue(tuid+"admincode")!=null&&((String)redisUtils.getValue(tuid+"admincode")).equals("1")){
                    redisUtils.setValue(tuid,token,redisUtils.TOKEN_EXPIRES_SECOND, TimeUnit.SECONDS);
                    redisUtils.setValue(tuid+"admincode","1",redisUtils.TOKEN_EXPIRES_SECOND, TimeUnit.SECONDS);
                    return true;
                }else{
                    throw new Exception("权限异常");
                }
            }
        }
        return true;
    }
}