package com.hone.localcommons.constant;

public class FontConstant {
    public static final String APPID="wx927adcd364fe8944";
    public static final String APPSECRER="115f01f94faa2812fd1a0066d619196f";
    public static final String APPID2="wxe12c1b699ab985d1";
    public static final String APPSECRER2="339599be11e49d64cfcb896fe3dc6926";
    public static final String WXUrl="https://api.weixin.qq.com/sns/jscode2session?appid="+APPID+"&secret="+APPSECRER+"&js_code=";
    public static final String WXUrl2="https://api.weixin.qq.com/sns/jscode2session?appid="+APPID2+"&secret="+APPSECRER2+"&js_code=";
    public static final String tokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRER;
    public static final String WXUrl_over="&grant_type=authorization_code";
    public static final String WXSEName="session_key";
    public static final String WXOPIDName="openid";
    public static final Integer OnePageNums=20;
    public static final String CALMQNAME="cal_MQName";
    public static final String CALRESCODE="CALRES";
    public static final String REDISORDER="REDISORDER";
}