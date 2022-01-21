// pages/PayMoney/PayMoney.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isloding:false,
    paystatus:'跳转微信支付中.....',
    selectadress:true,
    additem:false,
    name:'',
    adress:'',
    phonenumber:'',
    adressarr:[],
    nowseleadress:'',
    sid:'',
    paynum:'',
    content:'',
    price:0,
    bid:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      sid:options.sid,
      paynum:options.paynum,
      content:options.content,
      bid:options.bid
    })
    this.req_adress();
  },
  additem:function(){
    this.setData({
      additem:true
    })
  },
  name_input:function(e){
    console.log(e);
    this.setData({
      name:e.detail.value
    })
  },
  phone_input:function(e){
    this.setData({
      phonenumber:e.detail.value
    })
  },
  adress_input:function(e){
    this.setData({
      adress:e.detail.value
    })
  },
  req_newadress:function(){
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-order/AdressInfo/CreateAdress",
      method: "post",
      data:{
        uid:getApp().globalData.sessionid,
        name:this.data.name,
        adress:this.data.adress,
        phonenumber:this.data.phonenumber
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        console.log(res);
        wx.showToast({
          title: '添加成功',
        })
        this.setData({
          name:'',
          adress:'',
          phonenumber:'',
          additem:false
        })
        this.req_adress();
      },
    });
  },
  topayover:function(){
    setTimeout(()=>{
      this.setData({
        paystatus:'支付成功',
        isloding:false
      })
      this.req_createorder();
    },1000)
  },
  req_adress:function(){
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-order/AdressInfo/GetAdress",
      method: "post",
      data:{
        uid:getApp().globalData.sessionid
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        this.setData({
          adressarr:res.data.data
        })
      },
    });
  },
  deleteadress:function(e){
    console.log(e.currentTarget.dataset.json);
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-order/AdressInfo/DeleteAdress",
      method: "post",
      data:{
        uid:getApp().globalData.sessionid,
        name:this.data.nowseleadress.name,
        adress:this.data.nowseleadress.adress
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        wx.showToast({
          title: '删除成功',
        })
      },
    });
  },
  selectadress:function(e){
    this.setData({
      nowseleadress:e.currentTarget.dataset.json
    })
  },
  req_createorder:function(){
    if(this.data.nowseleadress==''){
      wx.showToast({
        title: '选择收件地址',
      })
      return;
    }
    console.log(this.data.nowseleadress);
    var json={
      bid:this.data.bid,
      sid:this.data.sid,
      storesnum:this.data.storesnum,
      altertype:'0',
      adress:this.data.nowseleadress,
      storesnum:this.data.paynum,
      content:this.data.content
    };
    var reqarr=[];
    reqarr.push(json);
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-order/OrderInfo/CreateOrder",
      method: "post",
      data:reqarr,
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        wx.redirectTo({
          url: '../orders/orders?type=sending',
        })
      },
    });
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