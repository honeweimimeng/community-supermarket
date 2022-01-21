// pages/PayGoods/PayGoods.js
const adress_utils=require('../../utils/qqmap-wx-jssdk.js')
let qqmaper=new adress_utils({
  key:'DG5BZ-G77CW-YOBRL-RLJ3I-ZSWH2-KSFWC'
})
Page({

  /**
   * 页面的初始数据
   */
  data: {
    nowaddress:'',
    isloding:true,
    imgUrls: [],
    swiperIdx: 0,
    storesarr:[],
    starttime:10,
    limittime:'10:50:00',
    skillstores:[],
    skillstores_lit:[],
    searchcontent:'',
    searchtype:''
  },
  bindchange(e) {
    this.setData({
      swiperIdx: e.detail.current
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var timer=setInterval(()=>{
      var date=new Date();
      var dates=date.getHours()+1+"点场"+(60-date.getMinutes())+":"+(60-date.getSeconds())+":"+((1000-date.getMilliseconds())+"").substring(0,2)
      this.setData({
        limittime:dates
      })
    },100);
    var that=this;
    qqmaper.reverseGeocoder({
      location:'',
      success: function(res) {
        var rough=res.result.formatted_addresses.rough.substring(res.result.formatted_addresses.rough.indexOf('区')+1,res.result.formatted_addresses.rough.length);
        getApp().globalData.lat=res.result.ad_info.location.lat;
        getApp().globalData.lng=res.result.ad_info.location.lng;
        that.setData({
          nowaddress:res.result.address_component.street+rough,
        })
        wx.login({
          success:(res)=>{
            wx.request({
              url: "http://"+getApp().globalData.reqip+":8796/service-user/UserInfo/VUserInfos",
              method: "post",
              data:{
                id: res.code,
              },
              success: (res) => {
                if(res.rescode==400){
                  //认证错误
                  wx.showModal({
                    title: '提示' ,
                    cancelText: '重试一次',
                    content: '认证失败，重新启动授权',
                    complete: function (res) {
                      wx.navigateTo({
                        url: '../index/index',
                      })
                    }
                  });
                  return;
                }else{
                  getApp().globalData.sessionid=res.data.data;
                  that.req_romap();
                  that.req_quickskill();
                  that.req_recommendstores();
                  that.req_recstores();
                  that.req_connwebsocket();
                }
              },
            }); 
          }
        });
        getApp().globalData.address=res.result.address_component.city+res.result.address_component.district+res.result.address_component.street+rough;
      },
      fail: function(error) {
        console.log(error);
        wx.showToast({
          title: '位置服务错误',
        })
      }
    })
  },
  req_romap:function(){
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/RecommendMap/GetRoMapForsingle",
      method: "post",
      data:{
        uid:getApp().globalData.sessionid
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        if(res.data.status==404){
          //轮寻
          this.req_romap();
          return;
        }
        this.setData({
          imgUrls:res.data.data
        })
      },
    });
  },
  req_connwebsocket:function(){
    wx.connectSocket({
      url: "ws://"+getApp().globalData.reqip+":8084/Business/"+getApp().globalData.sessionid.replace(/[/]/g,"")+"/1",
      success: function(res) {
        wx.showToast({
          title: '连接通信服务成功',
        })
        wx.onSocketMessage((result) => {
          var arr=that.data.orderarr;
          if(JSON.parse(result.data.replace(/\ufeff/g,"")).type!='order'){
            console.log('=========')
            var arr=getApp().globalData.messagearr;
            arr.push(JSON.parse(result.data.replace(/\ufeff/g,"")));
            getApp().globalData.messagearr=arr;
            wx.setTabBarBadge({
              index: 2,
              text: 'New'
            })
          }
        })
      },
      fail: function(res) {
        wx.showToast({
          title: '连接通信服务器错误',
        })
      }
    });
  },
  req_quickskill:function(){
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/GetSkillStores",
      method: "post",
      data:{
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        if(res.data.status==404){
          //轮寻
          this.req_quickskill();
          return;
        }
        var json=res.data.data;
        for(var index in json){
          json[index].detailsarr=JSON.parse(json[index].detailsarr);
          json[index].itemizearr=JSON.parse(json[index].itemizearr);
          json[index].price=(json[index].price*1-(json[index].price*(0.1*(10-(json[index].discount*1)))))
        }
        var lit_json=json;
        if(json.length>20){
          lit_json=lit_json.slice(0,20);
        }
        this.setData({
          skillstores:json,
          skillstores_lit:lit_json
        })
      },
    });
  },
  req_recommendstores:function(){

  },
  jum_stores:function(e){
    wx.navigateTo({
      url: '../StoresDetail/StoresDetails?sid='+e.currentTarget.dataset.idx
    })
  },
  jum_business:function(e){
    wx.navigateTo({
      url: '../BusinessPage/BusinessPage?bid='+e.currentTarget.dataset.bid,
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },
  searchcontent:function(e){
    this.setData({
      searchcontent:e.detail.value
    })
  },
  searchtypeclick:function(e){
    this.setData({
      searchtype:e.currentTarget.dataset.type
    })
    this.jum_search();
  },
  jum_search:function(){
    wx.navigateTo({
      url: '../SearchResult/SearchResult?searchcontent='+this.data.searchcontent+'&searchtype='+this.data.searchtype,
    })
  },
  req_recstores:function(){
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/GetRecommendStores",
      method: "post",
      data:{
        uid:getApp().globalData.sessionid,
        city:getApp().globalData.address.substring(0,getApp().globalData.address.indexOf('市')+1)
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        if(res.data.status==404){
          //轮寻
          this.req_recstores();
          return;
        }
        var json=res.data.data;
        for(var index in json){
          json[index].content=JSON.parse(json[index].content);
          json[index].detailsarr=JSON.parse(json[index].detailsarr);
          json[index].itemizearr=JSON.parse(json[index].itemizearr);
        }
        this.setData({
          storesarr:json,
          isloding:false,
        })
      },
    });
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.req_romap();
    this.req_quickskill();
    this.req_recommendstores();
    this.req_recstores();
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

  }
})