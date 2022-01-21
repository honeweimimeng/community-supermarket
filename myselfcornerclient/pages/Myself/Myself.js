// pages/MyInfos/MyInfos.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    businessinfojson:null,
    isloding:true,
    notreadmsgnum:0,
    name:'获取信息',
    lab:'',
    adress:'',
    iconurl:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      adress:getApp().globalData.address,
      iconurl:getApp().globalData.userInfo.avatarUrl
    }),
    this.getuserinfo();
  },
  getuserinfo:function(){
    wx.getUserInfo({
      success: function(res) {
        console.log(res);
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.reqbusinessinfo();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  jum_messagepage:function(){
    wx.navigateTo({
      url: '../messagepage/messagepage',
    })
  },
  jum_joinromap:function(){
    wx.navigateTo({
      url: '../JoinRoMap/JoinRoMap',
    })
  },
  reqbusinessinfo:function(){
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-user/UserInfo/GetBusinessInfo",
      method: "post",
      data:{
        bid:getApp().globalData.bid,
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        this.setData({
          businessinfojson:res.data.data,
          isloding:false
        })
      },
    });
  },
  jum_torder:function(e){
    wx.navigateTo({
      url: '../orders/orders?type='+e.currentTarget.dataset.content,
    })
  },
  alter_bstatus:function(){
    this.setData({
      isloding:true
    });
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-user/UserInfo/AlterBStatus",
      method: "post",
      data:{
        bid:getApp().globalData.bid,
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        this.setData({
          businessinfojson:res.data.data,
          isloding:false
        })
      },
    });
  },
  jum_joinseckill:function(){
    wx.navigateTo({
      url: '../joinseckill/joinseckill',
    })
  }
})