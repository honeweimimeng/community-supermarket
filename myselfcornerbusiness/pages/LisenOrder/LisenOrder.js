// pages/LisenOrder/LisenOrder.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    haveorder:false,
    video_ctrbtnstatus:true,
    orderarr:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-order/OrderInfo/GetNotReadOrder",
      method: "post",
      data:{
        bid:getApp().globalData.bid
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        console.log(res);
      },
    });
    var that=this;
    wx.connectSocket({
      url: "ws://"+getApp().globalData.reqip+":8084/Business/"+getApp().globalData.bid+'/0',
      success: function(res) {
        //console.log(res);
        wx.showToast({
          title: '已开始商家听单',
        })
        wx.onSocketMessage((result) => {
          var arr=that.data.orderarr;
          if(JSON.parse(result.data.replace(/\ufeff/g,"")).type=='order'){
            arr.push(JSON.parse(result.data.replace(/\ufeff/g)));
          that.setData({
            orderarr:arr,
            haveorder:true
          })
          wx.setTabBarBadge({
            index: 1,
            text: 'New'
          })
          }
          else{
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
          title: '登录商家错误',
        })
      }
    });
  },
  checkurl:function(e){
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
        var arr=this.data.orderarr;
        arr.splice(arr[e.currentTarget.dataset.idx],1);
        this.setData({
          orderarr:arr
        })
        if(this.data.orderarr.length==0){
          this.setData({
            haveorder:false
          })
        }
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
    wx.removeTabBarBadge({
      index: 1,
    })
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
  video_ctrbtn:function(){
    this.setData({
      video_ctrbtnstatus:!this.data.video_ctrbtnstatus
    })
    if(!this.data.video_ctrbtnstatus){
      wx.showToast({
        title: '关闭语音听单',
      })
    }else{
      wx.showToast({
        title: '开启语音听单',
      })
    }
  }
})