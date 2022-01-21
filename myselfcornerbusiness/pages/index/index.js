// index.js
// 获取应用实例
const app = getApp()

Page({
  data: {
    userInfo: {},
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    now_canIUse:false
  },
  onLoad() {
    var that=this;
    wx.getSetting({
      success (res){
        if (res.authSetting['scope.userInfo']) {
          that.setData({
            now_canIUse:true
          })
          wx.getUserInfo({
            success: function(res) {
              getApp().globalData.userInfo=res.userInfo;
              that.req_login();
            }
          })
        }
      }
    })
  },
  getUserProfile(e) {
    wx.getUserProfile({
      success: (res) => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      },
    })
  },
  getUserInfo(e) {
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  req_login:function(){
    wx.login({
      success:(res)=>{
        wx.request({
          url: "http://"+getApp().globalData.reqip+":8796/service-user/UserInfo/BusinessLogin",
          method: "post",
          data:{
            id: res.code,
          },
          success: (res) => {
            if(res.data.rescode==400){
              //认证错误
              wx.redirectTo({
                url: '../NewBusiness/NewBusiness?msg='+res.data.msg,
              })
            }else{
              setInterval(()=>{
                this.re_req_login();
              },1000*60*10);
              wx.switchTab({
                url: '../LisenOrder/LisenOrder',
              })
            }
            getApp().globalData.bid=res.data.data.bid;
            getApp().globalData.sessionid=res.data.data.SessionId;
          },
        });
      }
    });
  },
  re_req_login:function(){
    wx.login({
      success:(res)=>{
        wx.request({
          url: "http://"+getApp().globalData.reqip+":8796/service-user/UserInfo/BusinessLogin",
          method: "post",
          data:{
            id: res.code,
          },
          success: (res) => {
            if(res.data.rescode==400){
              //认证错误
              wx.redirectTo({
                url: '../NewBusiness/NewBusiness?msg='+res.data.msg,
              })
            }else{
              wx.switchTab({
                url: '../LisenOrder/LisenOrder',
              })
            }
            getApp().globalData.bid=res.data.data.bid;
            getApp().globalData.sessionid=res.data.data.SessionId;
          },
        });
      }
    });
  },
  bindGetUserInfo (e) {
    this.setData({
      now_canIUse:true
    })
    getApp().globalData.userInfo=e.detail.userInfo;
    this.req_login();
  }
})
