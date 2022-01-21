// pages/SearchResult/SearchResult.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    searchcontent:'',
    searchtype:'',
    nowsel:'saledown',
    seltype:'sale',
    storesarr:[],
    selsel:0//0升序1降序
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var searchtype='';
    if(options.searchtype==""||options.searchtype==null){
      searchtype='全部分类';
    }else{
      searchtype=options.searchtype
    }
    this.setData({
      searchcontent:options.searchcontent,
      searchtype:searchtype
    });
  },
  jum_stores:function(e){
    wx.navigateTo({
      url: '../StoresDetail/StoresDetails?sid='+e.currentTarget.dataset.idx
    })
  },
  changesel:function(e){
    switch(e){
      case 'saleup':
        this.setData({
          seltype:'sale',
          selsel:0
        })
        break;
      case 'saledown':
        this.setData({
          seltype:'sale',
          selsel:1
        })
        break;
      case 'priceup':
        this.setData({
          seltype:'price',
          selsel:0
        })
        break;
      case 'pricedown':
        this.setData({
          seltype:'price',
          selsel:1
        })
        break;
    }
    this.setData({
      nowsel:e.currentTarget.dataset.ctn
    })
    this.req_searchresult();
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  req_searchresult:function(){
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/SearchStores",
      method: "post",
      data:{
        searchcontent:this.data.searchcontent,
        searchtype:this.data.searchtype,
        seltype:this.data.seltype,
        selsel:this.data.selsel,
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        if(res.data.status==404){
          //轮寻
          this.req_searchresult();
          return;
        }
        this.setData({
          storesarr:res.data.data
        })
      },
    });
  },
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.req_searchresult();
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