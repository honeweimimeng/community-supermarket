// pages/JoinRoMap/JoinRoMap.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    name:'',
    lab:'',
    isapply:false,
    uploadurl:'../images/uploadicon.png',
    uploadweburl:"http://"+getApp().globalData.reqip+":8796/img-server/ImgMapById?mid=",
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
    this.sendreq(1);
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
  name_input:function(e){
    this.setData({
      name:e.detail.value
    })
  },
  blab_input:function(e){
    this.setData({
      lab:e.detail.value
    })
  },
  sendreq:function(e){
    if(this.data.name==''||this.data.lab==''||this.data.uploadurl=='../images/uploadicon.png'){
      if(e==1){
        var arr=this.req_applyromap();
        return;
      }
      wx.showToast({
        title: '选项不为空',
      })
      wx.navigateBack({
        delta: 0,
      })
      return;
    }
    this.req_applyromap();
  },
  req_applyromap:function(){
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/RecommendMap/CreateRoMap",
      method: "post",
      data:{
        bid:getApp().globalData.bid,
        mapurl:this.data.uploadweburl,
        lab:this.data.lab
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        if(res.data.rescode==400){
          wx.showToast({
            title: '已经申请啦！',
          })
          this.setData({
            isapply:true
          })
          return res.data.data;
        }
      },
    });
  },
  chooseimg:function(e){
    var that=this;
    wx.chooseImage({
      count:1,
      sizeType:['originnal','compressed'],
      sourceType:['album','camera'],
      success:function(res){
        var tempFilePaths = res.tempFilePaths;
        that.setData({
          uploadurl:tempFilePaths
        })
        that.uploadfile(tempFilePaths,(res)=>{
          that.setData({
            uploadweburl:that.data.uploadweburl+JSON.parse(res.data).data
          })
        });
      }
    })
  },
  uploadfile:function(fileurl,resfunction){
    wx.uploadFile({
      url: "http://"+getApp().globalData.reqip+":8796/service-user/UserInfo/UploadIdcardImg",
      filePath: fileurl[0],
      name: 'IdCardFile',
      success:resfunction
    });
  },
})