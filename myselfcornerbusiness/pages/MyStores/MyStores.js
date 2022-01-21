// pages/MyStores/MyStores.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    storestypenum:0,
    storesarr:[],
    up:0,
    down:1,
    columname:'date',
    columnameshow:'日期',
    page:0,
    optionarr:[],
    orderarr:["升序","降序"],
    typearr:["日期","库余量","销量"],
    optiontype:null,
    optionshow:false,
    changetotal:null,
    altertotalinput:null,
    sid:null,
    altertype:null,
    contentarr:[],
    nowselectstoresarrindex:null,
    checkbox:false,
    nowtypecontenttotal:0,
    searchcontent:'',
    input_searchcontent:'',
    isloding:true
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
    //请求商品库存信息
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
  searchcontent:function(e){
    this.setData({
      input_searchcontent:e.detail.value
    })
  },
  tosaearch:function(){
    this.setData({
      searchcontent:this.data.input_searchcontent
    })
    this.req_allstores();
  },
  alter_contentselect:function(e){
    var arr=this.data.contentarr;
    var index=e.currentTarget.dataset.idx;
    arr[index]=e.currentTarget.dataset.content;
    this.setData({
      contentarr:arr
    });
    if(this.data.contentarr.length==this.data.storesarr[this.data.nowselectstoresarrindex].itemizearr.length){
      var total=0;var reqcontent='';
      for(var index in this.data.contentarr){
        reqcontent+=this.data.contentarr[index];
      }
      for(var index=0;index<this.data.storesarr[this.data.nowselectstoresarrindex].content.length;index++){
        if(this.data.storesarr[this.data.nowselectstoresarrindex].content[index].content==reqcontent){
          total=this.data.storesarr[this.data.nowselectstoresarrindex].content[index].total;
        }
      }
      this.setData({
        nowtypecontenttotal:total
      })
    }
  },
  orderbyoption:function(){
    this.setData({
      optionshow:true,
      optionarr:this.data.orderarr,
      optiontype:0,
      checkbox:false,
    })
  },
  filedoption:function(){
    this.setData({
      optionshow:true,
      optionarr:this.data.typearr,
      optiontype:0,
      checkbox:false,
    })
  },
  addtotal:function(e){
    this.setData({
      optiontype:1,
      optionshow:true,
      sid:e.currentTarget.dataset.sid,
      altertype:'add',
      nowselectstoresarrindex:e.currentTarget.dataset.idx,
      checkbox:false,
      contentarr:[],
      nowtypecontenttotal:0
    })
  },
  closeoption:function(){
    this.setData({
      optionshow:false
    })
  },
  subtotal:function(e){
    this.setData({
      optiontype:1,
      optionshow:true,
      sid:e.currentTarget.dataset.sid,
      altertype:'sub',
      nowselectstoresarrindex:e.currentTarget.dataset.idx,
      checkbox:false,
      contentarr:[],
      nowtypecontenttotal:0
    })
  },
  selectopitem:function(e){
    switch(e.currentTarget.dataset.inner){
      case '升序':
        this.setData({
          up:1,
          down:0
        })
        break;
      case '降序':
        this.setData({
          up:0,
          down:1
        })
        break;
      case '日期':
        this.setData({
          columname:'date',
          columnameshow:'日期'
        })
        break;
      case '库余量':
        this.setData({
          columname:'total',
          columnameshow:'库余量'
        })
        break;
      case '销量':
        this.setData({
          columname:'sales',
          columnameshow:'销量'
        })
        break;
    }
    this.setData({
      optionshow:false
    })
    this.req_allstores();
  },
  createstores:function(){
    wx.navigateTo({
      url: '../storesdetails/storesdetails',
    })
  },
  sendaltertotal(){
    if(isNaN((this.data.altertotalinput*1))||this.data.altertotalinput==''||this.data.altertotalinput==null){
      wx.showToast({
        title: '输入纯数字',
      })
      return;
    }
    //发送更改请求
    var reqcontent='';
    for(var index in this.data.contentarr){
      reqcontent+=this.data.contentarr[index];
    }
    if(reqcontent==null||reqcontent==''||this.data.contentarr.length!=this.data.storesarr[this.data.nowselectstoresarrindex].itemizearr.length){
      wx.showToast({
        title: '请选全商品分类',
      })
      return;
    }
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/AlterStoresTotal",
      method: "post",
      data:{
        sid:this.data.sid,
        altertype:this.data.altertype,
        totalscore:this.data.altertotalinput,
        content:reqcontent
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        if(res.data.status==404){
          //轮寻
          this.sendaltertotal();
          return;
        }
        this.req_allstores();
        this.setData({
          optionshow:false
        })
      },fail:(res)=>{
        wx.showToast({
          title: '服务错误',
        })
      }
    });
  },
  changetotal:function(e){
    this.setData({
      altertotalinput:e.detail.value
    })
  },
  nextpage:function(){
    this.setData({
      page:++this.data.page
    })
    this.req_allstores(1);
  },
  req_allstores:function(type){
    this.setData({
      isloding:true
    })
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/GetStoresAll",
      method: "post",
      data:{
        bid:getApp().globalData.bid,
        up:this.data.up,
        down:this.data.down,
        columname:this.data.columname,
        page:this.data.page,
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
  deletestores:function(e){
    this.setData({
      checkbox:true,
      optionshow:true,
      sid:e.currentTarget.dataset.sid,
    })
  },
  cansole:function(){
    this.setData({
      checkbox:true,
      optionshow:false
    })
  },
  check:function(){
    //发送删除请求
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/DeleteStores",
      method: "post",
      data:{
        sid:this.data.sid
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        wx.showToast({
          title: '删除成功',
        })
        this.setData({
          optionshow:false
        })
        this.req_allstores();
      },fail:(res)=>{
        wx.showToast({
          title: '服务错误',
        })
      }
    });
  }
})