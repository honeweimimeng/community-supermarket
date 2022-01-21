<template>
    <div class="mainbox">
        <div style="width: 100%;height: 50px;" v-if="isloding==false">
            <div class="showitem title phonenumber">电话号码</div>
            <div class="showitem title">店铺名称</div>
            <div class="showitem title">店铺主打类型</div>
            <div class="showitem title">店铺说明</div>
            <div class="showitem title">营业地址</div>
            <div class="showitem title">营业执照</div>
            <div class="showitem title">店主证件(正)</div>
            <div class="showitem title">店主证件(反)</div>
            <div class="showitem title">审核结果</div>
        </div>
        <div v-if="isloding==false" style="width: 100%;height:auto">
            <div v-for="(item,keys) in applybusinessarr" :key="keys" :class="keys%2==0 ? 'businessitem_si' : 'businessitem_re'">
            <div class="showitem" :data-content="item.phonenumber" @click="showdetail(item.phonenumber)">{{item.phonenumber}}</div>
            <div class="showitem" :data-content="item.bname" @click="showdetail(item.name)">{{item.bname}}</div>
            <div class="showitem" :data-content="item.btype" @click="showdetail(item.btype)">{{item.btype}}</div>
            <div class="showitem" :data-content="item.blab" @click="showdetail(item.blab)">{{item.blab}}</div>
            <div class="showitem" :data-content="item.adress" @click="showdetail(item.adress)">{{item.adress}}</div>
            <div class="showitem"><button @click="showdetailimg(item.licenseurl)">查看详情</button></div>
            <div class="showitem"><button @click="showdetailimg(item.idcardtopurl)">查看详情</button></div>
            <div class="showitem"><button @click="showdetailimg(item.idcardbottomurl)">查看详情</button></div>
            <div class="showitem"><button style="width:40px;margin-right:10px" @click="checked(keys)">确认</button><button style="width:40px" @click="closed(keys)">驳回</button></div>
            </div>
        </div>
        <Loding v-if="isloding==true"></Loding>
        <div class="detailimgbox" v-if="isshowdetailimg" @click="hidedetailimg">
            <img :src="nowshowdetailimg"/>
        </div>
    </div>
</template>
<script>
import {getCookie} from '../assets/utils.js';
import {setCookie} from '../assets/utils.js';
import Loding from '@/components/common/Loding.vue'
export default({
    data() {
        return{
            page:0,
            allpage:0,
            applybusinessarr:[],
            isloding:true,
            nowshowdetailimg:null,
            isshowdetailimg:false
        }
    },created(){
        this.reqapplydata();
    },components:{
        'Loding':Loding
    },methods: {
        showdetail(e){
            alert(e);
        },
        showdetailimg(e){
            this.isshowdetailimg=true;
            this.nowshowdetailimg=e;
        },
        hidedetailimg(){
            this.isshowdetailimg=false
        },
        checked(e){
            this.isloding=true;
            var json=this.applybusinessarr[e];
            this.req_axios('http://localhost:8796/service-user/Admin/CheckedApply',json,(response)=>{
                this.isloding=false;
                if(response.data.msg==undefined){
                    alert('接口错误，请重试');
                    return;
                }
                this.reqapplydata();
            },(response)=>{
                alert("请求错误");
            });
        },closed(e){
            this.isloding=true;
            var json=this.applybusinessarr[e];
            this.req_axios('http://localhost:8796/service-user/Admin/ClosedApply',json,(response)=>{
                this.isloding=false;
                if(response.data.msg==undefined){
                    alert('接口错误，请重试');
                    return;
                }
                this.reqapplydata();
            },(response)=>{
                alert("请求错误");
            });
        },
        reqapplydata(){
            var json={
                page:this.page
            }
            this.req_axios('http://localhost:8796/service-user/Admin/GetApplyBusiness',json,(response)=>{
                this.isloding=false;
                if(response.data.msg==undefined){
                    alert('接口错误，请重试');
                    return;
                }
                this.allpage=response.data.data.data
                this.applybusinessarr=response.data.data.list
            },(response)=>{
                alert("请求错误");
            });
        },
        req_axios(url,json,success,catchs){
            this.$axios.post(url,json,{
                headers:{
                    "content-type": "application/json",
                    tid:getCookie('tuid'),
                    token:getCookie('token')
                }
            }).then(success).catch(catchs);
        }
    },
})
</script>
<style scoped>
    .mainbox{
        width: 100%;
        height: 100%;
        overflow-y: scroll;
        text-align: left;
        position: relative;
    }
    .mainbox .businessitem_si{
        width: 100%;
        height: 50px;
        background: rgb(255, 245, 214);
    }
    .mainbox .businessitem_re{
        width: 100%;
        height: 50px;
        background: rgb(225, 255, 199);
    }
    .mainbox .showitem{
        width: 9%;
        height: 100%;
        display: inline-block;
        line-height: 50px;
        padding: 0 10px;
        float: left;
        overflow: hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
        color: rgb(121, 121, 121);
        cursor: pointer;
        position: relative;
    }
    .mainbox .showitem button{
        width: 80px;
        height: 60%;
        background: tomato;
        text-align: center;
        line-height: 30px;
        border: 0;
        color: white;
        border-radius: 10px;
        cursor: pointer;
    }
    .mainbox .title{
        font-weight: 800;
        color: rgb(121, 121, 121);
        font-size: 15px;
    }
    .detailimgbox{
        position: fixed;
        width: 100%;
        height: 100%;
        z-index: 1003;
        top: 0;
        background: rgba(0, 0, 0, 0.6);
        left: 0;
    }
    .detailimgbox img{
        margin: 0 auto;
        width: 50%;
        height: auto;
        display: block;
        position: relative;
        top: 20%;
    }
</style>