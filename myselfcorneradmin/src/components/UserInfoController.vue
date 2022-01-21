<template>
    <div class="content">
        <div class="topnav">
            <div :class="selectindex==0 ? 'navitemshow navitem':'navitem'">顾客用户</div>
            <div :class="selectindex==1 ? 'navitemshow navitem':'navitem'">商铺用户</div>
        </div>
    </div>
</template>
<script>
export default({
    data() {
        return{
            selectindex:0
        }
    },methods: {
        
    },
})
</script>
<style scoped>
    .content{
        width: 100%;
        height: 100%;
        position: relative;
    }
    .topnav{
        width: 100%;
        height: 50px;
        text-align: left;
    }
    .topnav .navitem{
        width: 120px;
        height: 100%;
        display: inline-block;
        text-align: center;
        line-height: 50px;
        color: rgb(87, 87, 87);
        cursor: pointer;
    }
    .navitemshow{
        background: rgb(238, 238, 238);
    }
</style>