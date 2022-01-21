// pages/joinseckill/joinseckill.js
var util = require('../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    storestypenum:0,
    storesarr:[],
    up:0,
    down:1,
    columname:'date',
    columnameshow:'日期',
    page:0,
    optionarr:[],
    orderarr:["升序","降序"],
    typearr:["日期","库余量","销量"],
    optiontype:null,
    optionshow:false,
    changetotal:null,
    altertotalinput:null,
    sid:null,
    altertype:null,
    contentarr:[],
    nowselectstoresarrindex:null,
    checkbox:false,
    nowtypecontenttotal:0,
    searchcontent:'',
    input_searchcontent:'',
    isloding:true,
    isoption:false,
    joinstoresarr:[],
    nowoptionstores:'',
    nowtime:0,
    aftertime:0,
    discount:'',
    starttime:'',
    overprice:0
  },
  searchcontent:function(e){
    this.setData({
      input_searchcontent:e.detail.value
    })
  },
  tosaearch:function(){
    this.setData({
      searchcontent:this.data.input_searchcontent
    })
    this.req_allstores();
  },
  nextpage:function(){
    this.setData({
      page:++this.data.page
    })
    this.req_allstores(1);
  },
  req_allstores:function(type){
    this.setData({
      isloding:true
    })
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/GetStoresAll",
      method: "post",
      data:{
        bid:getApp().globalData.bid,
        up:this.data.up,
        down:this.data.down,
        columname:this.data.columname,
        page:this.data.page,
        searchcontent:this.data.searchcontent
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        if(res.data.status==404){
          //轮寻
          this.req_allstores();
          return;
        }
        var arr=res.data.data;
        var resarr=arr;
        if(type==1){
          resarr=this.data.storesarr.concat(arr);
        }
        for(var item in res.data.data){
          arr[item].content=JSON.parse(arr[item].content);
          arr[item].detailsarr=JSON.parse(arr[item].detailsarr);
          arr[item].itemizearr=JSON.parse(arr[item].itemizearr);
        }
        this.setData({
          storesarr:resarr,
          isloding:false
        })
        this.req_joinstr();
      },fail:(res)=>{
        wx.showToast({
          title: '服务错误',
        })
      }
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var date=new Date();
    var aftertime=date.getHours()+12;
    if(aftertime>23){
      aftertime=(aftertime-23)+":00M";
    }
    this.setData({
      nowtime:date.getHours()+':00',
      aftertime:aftertime
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
    this.req_allstores();
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
  closeoption:function(){
    this.setData({
      isoption:false
    })
  },
  selectstores:function(e){
    this.setData({
      isoption:true,
      sid:e.currentTarget.dataset.sid
    })
    for(var index in this.data.storesarr){
      if(this.data.storesarr[index].sid==this.data.sid){
        this.setData({
          nowoptionstores:this.data.storesarr[index],
          overprice:this.data.storesarr[index].price
        })
      }
    }
  },
  discount_input:function(e){
    if(isNaN((e.detail.value*1))||((e.detail.value*1)-10>=0)){
      wx.showToast({
        title: '请输入指定格式',
      })
      return;
    }
    this.setData({
      discount:e.detail.value,
      overprice:(this.data.nowoptionstores.price*1)-((this.data.nowoptionstores.price*1)*(1-(e.detail.value*0.1)))
    })
  },
  starttime_input:function(e){
    if(isNaN((e.detail.value*1))){
      wx.showToast({
        title: '请输入指定格式',
      })
      return;
    }
    this.setData({
      starttime:e.detail.value
    })
  },
  req_joinstr:function(){
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/GetJoinSeckill",
      method: "post",
      data:{
        bid:getApp().globalData.bid
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        if(res.data.status==404){
          //轮寻
          this.req_joinstr();
          return;
        }
        this.setData({
          joinstoresarr:res.data.data
        })
      },
    });
  },
  optioncheck:function(){
    var that=this;
    this.setData({
      isoption:false,
      isloding:true,
    })
    console.log(this.data);
    var joinarr=this.data.joinstoresarr;
    var json={"bid":getApp().globalData.bid,"sid":that.data.sid,"discount":that.data.discount,
              "starttime":that.data.starttime}
    joinarr.push(json);
    that.setData({
      joinstoresarr:joinarr
    });
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/JoinSeckill",
      method: "post",
      data:that.data.joinstoresarr,
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        if(res.data.status==404){
          //轮寻
          this.optioncheck();
          return;
        }
        this.setData({
          isloding:false
        })
        wx.showToast({
          title: '商品添加秒杀成功',
        })
      },
    });
  }
})