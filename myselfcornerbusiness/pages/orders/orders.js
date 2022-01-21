// pages/orders/orders.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    looparr: [0,1,2,3,4],
     contentindex:0,
     duration: 1000,
     current:0,
     nowindexcontent:'',
     page:0,
     orderarr:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var index=0;
    switch(options.type){
      case 'paying':
        index=0;
        break;
      case 'sending':
        index=1;
        break;
      case 'sended':
        index=2;
        break;
      case 'checking':
        index=3;
        break;
      case 'allorder':
        index=4;
        break;
    }
    this.setData({
      contentindex:index,
      nowindexcontent:options.type
    })
  },
  checkurl:function(e){
    console.log(e.currentTarget.dataset.oid);
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-order/OrderInfo/AlterOrderStatus",
      method: "post",
      data:{
        oid:e.currentTarget.dataset.oid
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        wx.showToast({
          title: '发货成功',
        })
        this.req_allorder();
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
    this.req_allorder();
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
  nextpage:function(){
    this.setData({
      page:++this.data.page
    })
    this.req_allorder();
  },
  swiperChange:function(e){
    this.setData({
      contentindex:e.detail.current,
      page:0
    })
    //请求订单信息
    this.req_allorder();
  },
  ClickHandle:function(e){
    this.setData({
      contentindex:e.target.dataset.index
    });
  },
  req_allorder:function(){
    var status=this.data.contentindex;
    if(this.data.contentindex==4){
      status='';
    }
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-order/OrderInfo/GetOrderArr",
      method: "post",
      data:{
        bid:getApp().globalData.bid,
        uid:'null',
        ordertype:status
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        this.setData({
          orderarr:res.data.data
        })
      },
    });
  }
})