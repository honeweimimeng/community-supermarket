// pages/storesdetails/storesdetails.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    name:'',
    price:'',
    typearr:[],
    typeimgweburl:'',
    weburl:"http://"+getApp().globalData.reqip+":8796/img-server/ImgMapById?mid=",
    storedetailinfoarr:[],
    typeselect:'选择商品类型',
    optionselect:false,
    storestypearr:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const areajson=require('../../utils/area.js');
    this.setData({
      storestypearr:areajson.types
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
  nameinput:function(e){
    this.setData({
      name:e.detail.value
    })
  },
  priceinput:function(e){
    this.setData({
      price:e.detail.value
    })
  },
  addtypearr:function(e){
    var json={name:"",content:[]}
    if(this.data.typearr.length>4){
      wx.showToast({
        title: '最大子分类数5',
      })
      return;
    }
    var arr=this.data.typearr;
    arr.push(json);
    this.setData({
      typearr:arr
    })
  },
  addsontypearr:function(e){
    if(this.data.typearr[(e.currentTarget.dataset.index*1)].content.length>20){
      wx.showToast({
        title: '最大子分类数20',
      })
    }
    var son_arr=this.data.typearr[(e.currentTarget.dataset.index*1)].content;
    var arr=this.data.typearr;
    for(var index=0;index<arr.length;index++){
      if(index==(e.currentTarget.dataset.index*1)){
        if(arr[(e.currentTarget.dataset.index*1)].content.length==0){
          son_arr.push({id:0,name:"",imgurl:"../images/uploadicon.png"});
          break;
        }
        son_arr.push({id:arr[(e.currentTarget.dataset.index*1)].content.length,name:"",imgurl:"../images/uploadicon.png"});
        break;
      }
    }
    this.setData({
      typearr:arr
    })
  },
  typearrname:function(e){
    var arr=this.data.typearr;
    arr[(e.currentTarget.dataset.index*1)].name=e.detail.value
    this.setData({
      typearr:arr
    })
  },
  sontypearrname:function(e){
    var arr=this.data.typearr;
    arr[(e.currentTarget.dataset.index*1)].content[e.currentTarget.dataset.sonindex].name=e.detail.value;
    this.setData({
      typearr:arr
    })
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
          typeimgurl:tempFilePaths
        })
        that.uploadfile(tempFilePaths,(res)=>{
          that.setData({
            typeimgweburl:that.data.weburl+JSON.parse(res.data).data
          })
          var arr=that.data.typearr;
          arr[(e.currentTarget.dataset.index*1)].content[e.currentTarget.dataset.sonindex].imgurl=that.data.typeimgweburl;
          that.setData({
            typearr:arr
          });
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
  adddetailarr:function(){
    if(this.data.storedetailinfoarr.length>50){
      wx.showToast({
        title: '最大详情数50',
      })
    }
    var json={key:"",value:""}
    var arr=this.data.storedetailinfoarr;
    arr.push(json);
    this.setData({
      storedetailinfoarr:arr
    })
  },
  storedetailsinfoarrkey:function(e){
    var arr=this.data.storedetailinfoarr;
    arr[e.currentTarget.dataset.index].key=e.detail.value;
    this.setData({
      storedetailinfoarr:arr
    })
  },
  storedetailinfoarrvalue:function(e){
    var arr=this.data.storedetailinfoarr;
    arr[e.currentTarget.dataset.index].value=e.detail.value;
    this.setData({
      storedetailinfoarr:arr
    })
  },
  sendcreatestore:function(){
    if(this.data.typearr.length!=0){
      for(var index in this.data.typearr){
        if(this.data.typearr[index].name==''){
          wx.showToast({
            title: '子类族名不为空',
          })
          return;
        }
        for(var index_sn in this.data.typearr[index].content){
          if(this.data.typearr[index].content[index_sn].name==''||this.data.typearr[index].content[index_sn].imgurl=='../images/uploadicon.png'){
            wx.showToast({
              title: '子类名和图片不为空',
            })
            return;
          }
        }
      }
    }
    if(this.data.storedetailinfoarr.length!=0){
      for(var index in this.data.storedetailinfoarr){
        if(this.data.storedetailinfoarr[index].key==''||this.data.storedetailinfoarr[index].value==''){
          wx.showToast({
            title: '商品详情不为空',
          })
          return;
        }
      }
    }
    if(this.data.name==''||this.data.price==''||this.data.typeselect=='选择商品类型'){
      wx.showToast({
        title: '商品信息不为空',
      })
      return;
    }
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-stores/StoresInfo/CreateStores",
      method: "post",
      data:{
        bid:getApp().globalData.bid,
        name:this.data.name,
        type:this.data.typeselect,
        price:this.data.price,
        storesTypeArr:this.data.typearr,
        storesDetailInfo:this.data.storedetailinfoarr
      },
      header:{
        "content-type": "application/json",
        SessionId:getApp().globalData.sessionid,
      },
      success: (res) => {
        if(res.data.status==404){
          //轮寻
          this.sendcreatestore();
          return;
        }
        if(res.data.status==500){
          wx.showToast({
            title: '请重新登录',
          })
          return;
        }
        if(res.data.rescode==400){
          wx.showToast({
            title: '商品已添加',
          })
          return;
        }
        wx.showToast({
          title: '添加成功',
        })
        wx.navigateBack({
          delta: 0,
        })
      },
    });
  },
  selecttype:function(){
    this.setData({
      optionselect:true
    })
  },
  quiteoptionsel:function(){
    this.setData({
      optionselect:false
    })
  },
  itemclick:function(e){
    this.setData({
      typeselect:this.data.storestypearr[e.currentTarget.dataset.idx],
      optionselect:false,
    })
  },
})