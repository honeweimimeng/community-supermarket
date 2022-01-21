// pages/StoresDetail/StoresDetails.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    sid:'',
    skill:false,
    storesjson:'',
    juli:'',
    reloopimgarr:[],
    isloding:true,
    checkcontent:'',
    checkimgurl:'',
    checkcount:0,
    bid:'',
    businessjson:'',
    talksnum:0,
    contentarr:[],
    optionshow:false,
    //1购买
    //0购物车
    jumtype:null,
    storesnum:0,
    limittime:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      sid:options.sid
    })
    var timer=setInterval(()=>{
      var date=new Date();
      var dates=(60-date.getMinutes())+":"+(60-date.getSeconds())+":"+((1000-date.getMilliseconds())+"").substring(0,2)
      this.setData({
        limittime:dates
      })
    },100);
  },
  ctr_add:function(){
    if(this.data.checkcount<=this.data.storesnum){
      wx.showToast({
        title: '达到购买上限了哦~',
      })
      return;
    }
    this.setData({
      storesnum:++this.data.storesnum
    })
  },
  ctr_sub:function(){
    if(this.data.storesnum<=0){
      wx.showToast({
        title: '不能再小于0了哦~',
      })
      return;
    }
    this.setData({
      storesnum:--this.data.storesnum
    })
  },
  tostorescar:function(){
    this.setData({
      jumtype:0,
      optionshow:true
    })
  },
  tobuystores:function(){
    this.setData({
      jumtype:1,
      optionshow:true
    })
  },
  closeoption:function(){
    this.setData({
      optionshow:false
    })
  },
  req_singlestore:function(){
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/GetSingleSkillStores",
      method: "post",
      data:{
        sid:this.data.sid,
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        if(res.data.status==404){
          //轮寻
          this.req_singlestore();
          return;
        }
        var json=res.data.data;
        json.content=JSON.parse(json.content);
        json.detailsarr=JSON.parse(json.detailsarr);
        json.itemizearr=JSON.parse(json.itemizearr);
        var myDate = new Date();
        var nowtime=myDate.getFullYear()+"年"+(myDate.getMonth()+1)+"月"+myDate.getDate()+"日"+myDate.getHours()+":00";
        if(json.discount!=null&&json.starttime>nowtime){
          json.price=(json.price*1-(json.price*(0.1*(10-(json.discount*1)))))
          this.setData({
            skill:true,
            storesjson:json,
            isloding:false
          })
        }
        var testdistance=this.distance(getApp().globalData.lat,getApp().globalData.lng,json.latitude,json.longitude);
        for(var index in json.itemizearr){
          for(var index_1 in json.itemizearr[index].content){
            var arr=this.data.reloopimgarr;
            arr.push(json.itemizearr[index].content[index_1].imgurl);
            this.setData({
              reloopimgarr:arr,
              isloding:false,
              storesjson:json,
            })
          }
        }
        this.setData({
          storesjson:json,
          juli:testdistance,
          checkcontent:this.data.storesjson.content[0].content,
          checkimgurl:this.data.storesjson.itemizearr[0].content[0].imgurl,
          checkcount:this.data.storesjson.content[0].total,
        })
        this.req_business();
        this.req_talks();
      },
    });
  },
  distance: function (la1, lo1, la2, lo2) {
    var La1 = la1 * Math.PI / 180.0;
    var La2 = la2 * Math.PI / 180.0;
    var La3 = La1 - La2;
    var Lb3 = lo1 * Math.PI / 180.0 - lo2 * Math.PI / 180.0;
    var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(La3 / 2), 2) + Math.cos(La1) * Math.cos(La2) * Math.pow(Math.sin(Lb3 / 2), 2)));
    s = s * 6378.137;
    s = Math.round(s * 10000) / 10000;
    s = s.toFixed(2);
    return s;
  },
  alter_contentselect:function(e){
    var arr=this.data.contentarr;
    var index=e.currentTarget.dataset.idx;
    arr[index]=e.currentTarget.dataset.content;
    this.setData({
      contentarr:arr
    });
    var content="";
    var imgurl="";
    for(var index in this.data.contentarr){
      content+=this.data.contentarr[index];
    }
    for(var index=0;index<this.data.storesjson.itemizearr[0].content.length;index++){
      if(this.data.storesjson.itemizearr[0].content[index].name==this.data.contentarr[0]){
        imgurl=this.data.storesjson.itemizearr[0].content[index].imgurl;
      }
    }
    var total=0;
    for(var index in this.data.storesjson.content){
      if(this.data.storesjson.content[index].content==content){
        total=this.data.storesjson.content[index].total;
      }
    }
    this.setData({
      checkcontent:content,
      checkcount:total,
      checkimgurl:imgurl
    });
  },
  option_jum:function(){
    if(this.data.storesjson.itemizearr.length>this.data.contentarr.length){
      wx.showToast({
        title: '选择全品类',
      })
      return;
    }
    if(this.data.storesnum==0){
      wx.showToast({
        title: '增减数不为0',
      })
      return;
    }
    if(this.data.jumtype==0){
      wx.request({
        url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/AddStoresCar",
        method: "post",
        data:{
          bid:getApp().globalData.sessionid,
          sid:this.data.sid,
          storesnum:this.data.storesnum,
          altertype:'0',
          content:this.data.checkcontent
        },
        header:{
          "content-type": "application/json",
          SessionId:getApp().globalData.sessionid,
        },
        success: (res) => {
          if(res.data.status==404){
            //轮寻
            this.option_jum();
            return;
          }
          wx.showToast({
            title: '加入购物车成功',
          })
        },
      });
    }else{
      var arr_sid=[];
      var ctnarr=[];
      var paynumarr=[];
      arr_sid.push(this.data.storesjson.sid);
      ctnarr.push(this.data.checkcontent);
      paynumarr.push(this.data.storesnum);
      wx.navigateTo({
        url: '../PayMoney/PayMoney?sid='+JSON.stringify(arr_sid)+'&content='+JSON.stringify(ctnarr)+'&paynum='+JSON.stringify(paynumarr)+'&bid='+this.data.storesjson.bid,
      })
    }
  },
  jum_business:function(){
    wx.navigateTo({
      url: '../BusinessPage/BusinessPage?bid='+this.data.storesjson.bid,
    })
  },
  jum_business:function(){
    wx.navigateTo({
      url: '../BusinessPage/BusinessPage?bid='+this.data.storesjson.bid,
    })
  },
  jum_chat:function(){
    console.log(this.data.businessjson.iconurl);
    wx.navigateTo({
      url: '../messagepage/messagepage?uid='+getApp().globalData.sessionid+'&bid='+this.data.storesjson.bid+'&bname='+this.data.businessjson.bname+'&iconurl='+(this.data.businessjson.iconurl).replace('?','@').replace('=','!'),
    })
  },
  req_business:function(){
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-user/UserInfo/GetBusinessInfo",
      method: "post",
      data:{
        bid:this.data.storesjson.bid,
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
  req_talks:function(){

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
    this.req_singlestore();
    //启动监听器
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