// pages/NewBusiness/NewBusiness.js
const adress_utils=require('../../utils/qqmap-wx-jssdk.js')
let qqmaper=new adress_utils({
  key:'DG5BZ-G77CW-YOBRL-RLJ3I-ZSWH2-KSFWC'
})
Page({
  data: {
    is_authentication:false,
    name:'',
    phonenumber:'',
    blab:'',
    liteadress:'',
    nowcity:'',
    optionselect:false,
    optionarr:[],
    nowseloption:'',
    preitemindex:0,
    last_preitemindex:0,
    areaselect:'选择营业地址',
    typeselect:'选择主营业务品类',
    typearr:[],
    licenseurl:'../images/uploadicon.png',
    idcardtopurl:'../images/uploadicon.png',
    idcardbottomurl:'../images/uploadicon.png',
    licenseweburl:"http://"+getApp().globalData.reqip+":8796/img-server/ImgMapById?mid=",
    idcardtopweburl:"http://"+getApp().globalData.reqip+":8796/img-server/ImgMapById?mid=",
    idcardbottomweburl:"http://"+getApp().globalData.reqip+":8796/img-server/ImgMapById?mid=",
    loadtips:'您还不是认证商家哦',
    sendedcreatereq:false,
    longitude:'',
    latitude:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if(options.msg=='warning01WaiteCheck'){
      this.setData({
        loadtips:'您的申请已经提交了哦',
        sendedcreatereq:true
      })
      return;
    }
    const areajson=require('../../utils/area.js');
    this.setData({
      typearr:areajson.types
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
  authentication:function(){
    this.setData({
      is_authentication:true
    })
  },
  quiteoptionsel:function(){
    this.setData({
      optionselect:false,
      optionarr:[],
    })
  },
  itemclick:function(e){
    switch(this.data.nowseloption){
      case 3:
        this.setData({
          typeselect:this.data.typearr[e.currentTarget.dataset.idx],
          optionselect:false,
        })
        break;
    }
  },
  selectarea:function(){
    var that=this;
    qqmaper.reverseGeocoder({
      location:'',
      success: function(res) {
        var rough=res.result.formatted_addresses.rough.substring(res.result.formatted_addresses.rough.indexOf('区')+1,res.result.formatted_addresses.rough.length);
        that.setData({
          areaselect:res.result.address_component.city+res.result.address_component.district+res.result.address_component.street+rough,
          longitude:res.result.location.lng,
          latitude:res.result.location.lat,
          liteadress:res.result.address_component.city+res.result.address_component.district
        });
        getApp().globalData.address=res.result.address_component.city+res.result.address_component.district+res.result.address_component.street+rough;
      },
      fail: function(error) {
        console.log(error);
        wx.showToast({
          title: '位置服务错误',
        })
      }
    })
  },
  selecttype:function(){
    this.setData({
      optionselect:true,
      nowseloption:3
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
        switch(e.currentTarget.dataset.idx){
          case '0':
            that.setData({
              licenseurl:tempFilePaths
            })
            that.uploadfile(tempFilePaths,(res)=>{
              that.setData({
                licenseweburl:that.data.licenseweburl+JSON.parse(res.data).data
              })
            });
            break;
          case '1':
            that.setData({
              idcardtopurl:tempFilePaths
            })
            that.uploadfile(tempFilePaths,(res)=>{
              that.setData({
                idcardtopweburl:that.data.idcardtopweburl+JSON.parse(res.data).data
              })
            });
            break;
          case '2':
            that.setData({
              idcardbottomurl:tempFilePaths
            })
            that.uploadfile(tempFilePaths,(res)=>{
              that.setData({
                idcardbottomweburl:that.data.idcardbottomweburl+JSON.parse(res.data).data
              })
            });
            break;
        }
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
  nameinput:function(e){
    this.setData({
      name:e.detail.value
    })
  },
  phonenumberinput:function(e){
    this.setData({
      phonenumber:e.detail.value
    })
  },
  blabinput:function(e){
    this.setData({
      blab:e.detail.value
    })
  },
  sendtoserver:function(){
    if(this.data.name==''||this.data.phonenumber==''||this.data.blab==''||
    this.data.typeselect=='选择主营业务品类'||
    this.data.licenseurl=='../images/uploadicon.png'||this.data.idcardtopurl=='../images/uploadicon.png'||
    this.data.idcardbottomurl=='../images/uploadicon.png'||this.data.latitude==''||
    this.data.longitude==''||this.data.liteadress==''){
      wx.showToast({
        title:'不可有空项'
      })
      return;
    }
    if(this.data.name.length>=10){
      wx.showToast({
        title:'名称最多10个字符'
      })
      return;
    }
    if(isNaN(this.data.phonenumber)){
      wx.showToast({
        title:'电话格式不合法'
      })
    }
    //发送创建请求
    this.setData({
      
    })
    wx.request({
      url: "http://"+getApp().globalData.reqip+":8796/service-user/UserInfo/ApplyBusiness",
      method: "post",
      data:{
        uid:getApp().globalData.sessionid,
        phonenumber:this.data.phonenumber,
        bname:this.data.name,
        btype:this.data.typeselect,
        blab:this.data.blab,
        adress:this.data.liteadress,
        longitude:this.data.longitude,
        latitude:this.data.latitude,
        licenseurl:this.data.licenseweburl,
        idcardtopurl:this.data.idcardtopweburl,
        idcardbottomurl:this.data.idcardbottomweburl,
      },
      success: (res) => {
        if(res.data.rescode==400){
          wx.showToast({
            title:'请勿重复发送'
          })
          return;
        }else{
          wx.showToast({
            title:'申请发送成功'
          })
          wx.redirectTo({
            url: '../NewBusiness/NewBusiness',
          })
        }
      },
    });
  }
})