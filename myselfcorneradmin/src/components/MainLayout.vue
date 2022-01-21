<template>
    <div class="content">
        <div class="navbar" v-if="islogin==0">
            <div class="navbaritem" :class="isshowindex==0 ? 'navbaritemselect':''"  @click="juminnerpage(0)">用户管理▷</div>
            <div class="navbaritem" :class="isshowindex==1 ? 'navbaritemselect':''"  @click="juminnerpage(1)">开店申请▷</div>
            <div class="navbaritem" :class="isshowindex==2 ? 'navbaritemselect':''" @click="juminnerpage(2)">商品管理▷</div>
            <div class="navbaritem" :class="isshowindex==3 ? 'navbaritemselect':''" @click="juminnerpage(3)">订单管理▷</div>
            <div class="navbaritem" :class="isshowindex==4 ? 'navbaritemselect':''" @click="juminnerpage(4)">轮播图管理▷</div>
        </div>
        <div class="innercontent" v-if="islogin==0">
            <UserInfoController v-if="isshowindex==0"></UserInfoController>
            <ApplyBusiness v-if="isshowindex==1"></ApplyBusiness>
            <RoMap v-if="isshowindex==4"></RoMap>
        </div>
        <div class="loginbox" v-if="islogin==1">
            <div class="navtip">登录您的账户</div>
            <div class="loginitem">
                <input placeholder="工管号码" v-model="uid"/>
            </div>
            <div class="loginitem">
                <input placeholder="个人密码" type="password" v-model="password"/>
            </div>
            <div class="loginitem">
                <button @click="login">登录此账户</button>
            </div>
        </div>
        <Loding v-if="isloding==true"></Loding>
    </div>
</template>
<script>
import UserInfoController from '@/components/UserInfoController.vue'
import ApplyBusiness from '@/components/ApplyBusiness.vue'
import Loding from '@/components/common/Loding.vue'
import RoMap from '@/components/RoMap.vue'
import {getCookie} from '../assets/utils.js';
import {setCookie} from '../assets/utils.js';
export default({
    data() {
        return{
            isshowindex:0,
            islogin:1,
            uid:'',
            password:'',
            isloding:false
        }
    },created(){
        if(getCookie('token')!=null&&getCookie('tuid')!=null){
            this.islogin=0;
        }
    },components:{
        'UserInfoController':UserInfoController,
        'ApplyBusiness':ApplyBusiness,
        'Loding':Loding,
        'RoMap':RoMap
    },methods: {
        juminnerpage(e){
            this.isshowindex=e;
        },
        login(){
            if(this.uid==''||this.password==''){
                alert('必选项不为空');
                return;
            }
            this.isloding=true;
            var json={
                id:this.uid,
                password:this.password
            }
            this.req_axios('http://localhost:8796/service-user/Admin/Login',json,(response)=>{
                this.isloding=false;
                if(response.data.rescode==400||response.data.status!=undefined){
                    if(response.data.msg==undefined){
                        alert('接口错误，请重试');
                        return;
                    }
                    alert(response.data.msg);
                    return;
                }
                //登录成功
                setCookie('tuid',response.data.data.tid);
                setCookie('token',response.data.data.token);
                this.islogin=0;
            });
        },req_axios(url,json,success){
            this.$axios.post(url,json,{headers:{
                "content-type": "application/json",
                "token":getCookie('token'),
                "tid":getCookie('tuid')
            }}).then(success).catch((error)=>{
                alert('接口错误，请重试');
            });
        }
    },
})
</script>
<style scoped>
    .content{
        width: 100%;
        height: 100%;
        position: relative;
    }
    .loginbox{
        width: 400px;
        height: 200px;
        background: white;
        margin: 0 auto;
        border: 1px solid rgb(221, 221, 221);
        position: relative;
        top: 25%;
        display: block;
        z-index: 1002;
        border-radius: 10px;
        overflow: hidden;
    }
    .loginbox .navtip{
        width: 100%;
        height: 50px;
        text-align: center;
        line-height: 50px;
        color: white;
        background: #6495ED;
        font-size: 20px;
    }
    .loginbox .loginitem{
        width: 90%;
        margin: 10px auto;
        height: 40px;
    }
    .loginbox .loginitem input{
        width: 100%;
        height: 30px;
        border-radius: 30px;
        border: 1px solid rgb(184, 184, 184);
        text-align: center;
    }
    .loginbox .loginitem button{
        width: 50%;
        height: 30px;
        border: 0;
        border-radius: 30px;
        background: #6495ED;
        color: white;
        text-align: center;
        line-height: 30px;
        cursor: pointer;
    }
    .navbar{
        position: fixed;
        z-index: 1001;
        width: 200px;
        height: 100%;
        background: rgb(85, 85, 85);
    }
    .navbar .navbaritem{
        position: relative;
        width: 100%;
        height: 80px;
        line-height: 80px;
        color: white;
        cursor: pointer;
        transition: 1s;
    }
    .navbar .navbaritem:hover{
        background: #6495ED;
        transition: 1s;
    }
    .navbaritemselect{
        transition: 1s;
        background: #6495ED;
        position: relative;
        left: -10px;
    }
    .innercontent{
        position: fixed;
        height: auto;
        position: absolute;
        right: 0;
        top: 0;
        min-width: 1100px;
        left: 200px;
        height: 100%;
    }
</style>