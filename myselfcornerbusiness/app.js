// app.js
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  },
  globalData: {
    userInfo: null,
    reqip:'100.96.145.140',
    sessionid:'',
    address:'',
    bid:'',
    messagearr:[]
  }
})
