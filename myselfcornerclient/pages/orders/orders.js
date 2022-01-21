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
     orderarr:[],
     nowselectorder:'',
     talksshow:false,
     imgarr:[],
     bid:'',
     uploaded:false,
     goodtalks:'',
     talkscontent:'',
     talksstatus:'',
     licenseurl:'../images/uploadicon.png',
     licenseweburl:"http://"+getApp().globalData.reqip+":8796/img-server/ImgMapById?mid=",
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
        bid:'null',
        uid:getApp().globalData.sessionid,
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
        wx.showToast({
          title: '确认收货成功',
        })
        this.req_allorder();
      },
    });
  },
  totalks:function(e){
    this.setData({
      talksshow:true,
      nowselectorder:this.data.orderarr[e.currentTarget.dataset.idx]
    })
  },
  backup:function(){
    this.setData({
      talksshow:false
    })
  },
  chooseimg:function(e){
    var that=this;
    wx.chooseImage({
      count:5,
      sizeType:['originnal','compressed'],
      sourceType:['album','camera'],
      success:function(res){
        var tempFilePaths = res.tempFilePaths;
        for(var index=0;index<tempFilePaths.length;index++){
          that.uploadfile(tempFilePaths[index],(res)=>{
            var arr=that.data.imgarr
            arr.push(that.data.licenseweburl+JSON.parse(res.data).data);
            that.setData({
              imgarr:arr
            })
          });
        }
        that.setData({
          uploaded:true
        })
      }
    })
  },
  uploadfile:function(fileurl,resfunction){
    wx.uploadFile({
      url: "http://"+getApp().globalData.reqip+":8796/service-user/UserInfo/UploadIdcardImg",
      filePath: fileurl,
      name: 'IdCardFile',
      success:resfunction
    });
  },
  changestatus:function(e){
    this.setData({
      talksstatus:e.currentTarget.dataset.status
    })
  },
  contentinput:function(e){
    this.setData({
      talkscontent:e.detail.value
    })
  },
  sendtalks:function(){
    if(this.data.talksstatus==''||this.data.talkscontent==''){
      wx.showToast({
        title: '评论参数不为空',
      })
      return;
    }
    //发送请求
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-order/OrderInfo/AddTalksInfo",
      method: "post",
      data:{
        bid:this.data.nowselectorder.bid,
        sid:this.data.nowselectorder.sid,
        content:this.data.talkscontent,
        oid:this.data.nowselectorder.oid,
        type:this.data.talksstatus
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        console.log(res);
      }
    });
  }
})