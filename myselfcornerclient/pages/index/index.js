// index.js
// 获取应用实例
const app = getApp()

Page({
  data: {
    userInfo: {},
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    now_canIUse:false,
    name:'',
    iconurl:''
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
              console.log(res);
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
  req_login:function(){
    wx.login({
      success:(res)=>{
        wx.switchTab({
          url: '../PayGoods/PayGoods',
        })
      }
    });
  },
  getUserInfo(e) {
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  bindGetUserInfo (e) {
    this.setData({
      now_canIUse:true
    })
    getApp().globalData.userInfo=e.detail.userInfo;
    this.req_login();
  }
})
