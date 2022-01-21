// pages/messagepage/messagepage.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    chatlist:[],
    msgarr:[],
    showchat:false,
    bid:'',
    iconurl:'',
    bname:'',
    inputmsg:'',
    uid:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if(options.iconurl=='null'){
      this.setData({
        showchat:true,
        uid:options.uid,
        bid:options.bid
      })
      this.req_msglistforuser();
      return;
    }
    this.req_msglist();
  },
  inputmsg:function(e){
    this.setData({
      inputmsg:e.detail.value
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
  jum_chatpage:function(e){
    wx.navigateTo({
      url: '../chatpage/chatpage?uid='+e.currentTarget.dataset.uid,
    })
  },
  req_msglistforuser:function(){
    console.log()
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8084/Message/ChatListForUser",
      method: "post",
      data:{
        uid:this.data.bid,
        aimuid:this.data.uid,
        utype:'0'
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        var arr=this.data.chatlist;
        console.log(res.data.data);
        for(var index in res.data.data){
          arr.push(res.data.data[index]);
        }
        this.setData({
          chatlist:arr
        })
      },
    });
  },
  sendmsg:function(){
    console.log(this.data.uid+this.data.bid);
    var json={
      Uid:this.data.uid,
      Bid:this.data.bid,
      Msg:this.data.inputmsg,
      Type:'Chat',
      UType:'0'
    }
    wx.sendSocketMessage({
      data:JSON.stringify(json)
    })
    var arr=this.data.chatlist;
    var json={"content":this.data.inputmsg,"utype":"0"}
    arr.push(json);
    this.setData({
      chatlist:arr
    })
  },
  req_msglist:function(){
    console.log(getApp().globalData.bid)
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8084/Message/GetChatList",
      method: "post",
      data:{
        uid:getApp().globalData.bid,
        utype:'0'
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        console.log(res.data.data);
        var arr=this.data.msgarr;
        for(var index in res.data.data){
          arr.push(res.data.data[index]);
        }
        this.setData({
          msgarr:arr,
          isloding:false
        })
      },
    });
  },
  jum_chat:function(e){
    wx.navigateTo({
      url: '../messagepage/messagepage?uid='+e.currentTarget.dataset.uid+'&bid='+e.currentTarget.dataset.aimuid+'&bname='+e.currentTarget.dataset.name+'&iconurl=null',
    })
  },
})