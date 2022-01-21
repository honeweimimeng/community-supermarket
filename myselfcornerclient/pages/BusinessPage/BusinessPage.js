var singlestoreynreqed=false;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    bid:'',
    businessjson:'',
    reqtype:0,
    storesarr:[],
    searchcontent:''
  },
  jum_type:function(e){
    this.setData({
      reqtype:e.currentTarget.dataset.idx
    })
    this.req_allstores();
  },
  req_business:function(){
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-user/UserInfo/GetBusinessInfo",
      method: "post",
      data:{
        bid:this.data.bid,
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        this.setData({
          businessjson:res.data.data
        })
      },
    });
  },
  req_allstores:function(type){
    this.setData({
      isloding:true
    })
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/GetStoresAll_Single",
      method: "post",
      data:{
        bid:this.data.bid,
        columname:this.data.reqtype,
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
      },fail:(res)=>{
        wx.showToast({
          title: '服务错误',
        })
      }
    });
  },
  jum_stores:function(e){
    wx.navigateTo({
      url: '../StoresDetail/StoresDetails?sid='+e.currentTarget.dataset.idx
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      bid:options.bid
    })
    this.req_business();
    this.req_allstores();
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

  }, 
})