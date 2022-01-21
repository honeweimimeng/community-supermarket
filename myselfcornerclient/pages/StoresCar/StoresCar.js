// pages/StoresCar/StoresCar.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    storesarr:[],
    selectedarr:[],
    allmoney:0
  },
  req_allstores:function(type){
    this.setData({
      isloding:true
    })
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/GetStoresCar",
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
          this.req_allstores();
          return;
        }
        var arr=res.data.data;
        var resarr=arr;
        if(type==1){
          resarr=this.data.storesarr.concat(arr);
        }
        for(var index in resarr){
          resarr[index].itemizearr=JSON.parse(resarr[index].itemizearr)
          for(var index2 in resarr[index].itemizearr){
            if(resarr[index].content.indexOf(resarr[index].itemizearr[index].content[index2].name)!=-1){
              resarr[index].imgurl=resarr[index].itemizearr[index].content[index2].imgurl
            }
          }
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
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
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
  addtotal:function(){
    var jsonarr=this.data.storesarr;
    for(var index=0;index<jsonarr.length;index++){
      if(jsonarr[index].totalscore<=jsonarr[index].storesnum){
        wx.showToast({
          title: '不能大于最大库存了',
        })
        return;
      }
      jsonarr[index].storesnum=++jsonarr[index].storesnum
    }
    this.setData({
      storesarr:jsonarr
    })
  },
  selected:function(e){
    var arr=this.data.selectedarr;
    if (arr.indexOf(this.data.storesarr[e.currentTarget.dataset.idx])<0) {
      arr.push(this.data.storesarr[e.currentTarget.dataset.idx]);
      this.setData({
        allmoney:this.data.allmoney+parseFloat(this.data.storesarr[e.currentTarget.dataset.idx].price)
      })
    }else{
      arr.splice(e.currentTarget.dataset.idx,1); 
      this.setData({
        allmoney:this.data.allmoney-parseFloat(this.data.storesarr[e.currentTarget.dataset.idx].price)
      })
    }
    this.setData({
      selectedarr:arr
    })
  },
  subtotal:function(){
    var jsonarr=this.data.storesarr;
    for(var index=0;index<jsonarr.length;index++){
      if(jsonarr[index].storesnum==1){
        wx.showToast({
          title: '不能小于1了',
        })
        return;
      }
      jsonarr[index].storesnum=--jsonarr[index].storesnum
    }
    this.setData({
      storesarr:jsonarr
    })
  },
  topay:function(){
    if(this.data.selectedarr.length<=0){
      wx.showToast({
        title: '选择购买商品',
      })
    }
    var arr_sid=[];
    var ctnarr=[];
    var paynumarr=[];
    var bid=this.data.selectedarr[0].bid;
    for(var index in this.data.selectedarr){
      arr_sid.push(this.data.selectedarr[index].sid);
      ctnarr.push(this.data.selectedarr[index].content);
      paynumarr.push(this.data.selectedarr[index].price);
    }
    console.log(arr_sid);
    console.log(paynumarr);
    console.log(ctnarr);
    wx.navigateTo({
      url: '../PayMoney/PayMoney?sid='+JSON.stringify(arr_sid)+'&content='+JSON.stringify(ctnarr)+'&paynum='+JSON.stringify(paynumarr)+'&bid='+bid,
    })
  }
})