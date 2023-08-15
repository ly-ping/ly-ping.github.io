<!--顶部通栏-->
<template>
    <div id="topnav">

        <div class="top-links">
            <!--顶层文字链接-->
            <el-link href="/#/dash" target="_blank" :underline="false">首页</el-link>
            <el-link href="https://www.lovepet.cn/" target="_blank" :underline="false">养宠百科</el-link>
            <el-link href="/#/cart" target="_blank" :underline="false">购物车</el-link>
            <el-link href="/#/boards" target="_blank"  :underline="false">问题反馈</el-link>
        </div>

        <!--用户信息-->
        <el-menu class="login"
                 mode="horizontal"
                 background-color="#242424"
                 style="float: right"
                 :default-active="$route.path"
                 router>
            <el-submenu trigger="hover" placement="bottom" index="">
                <!--头像或者用户名-->
                <template slot="title">{{screenname}}</template>
                <!--<img :src="`${userAvatarUrl}?param=30y30`" alt="">-->
                <el-menu-item index="/person">个人中心</el-menu-item>
                <el-menu-item index="/" @click="signout">退出登录</el-menu-item>
            </el-submenu>
        </el-menu>


    </div>
</template>

<script>
    export default {
        name: "topnav",
        data() {
            return {
                cartCount:0,
            }
        },
        //监视
        computed:{
            screenname(){
                return JSON.parse(localStorage.getItem('user')).screenname;
            }
        },
        created() {

        },
        methods: {
            //退出登录
            signout(){
                console.log(screenname,'name')
                localStorage.removeItem('screenname');
                localStorage.removeItem('role');
                this.$router.replace("/");
            },
        }
    }
</script>

<style lang="scss" scoped>
    /*去掉垂直导航栏底部横线线*/
    .el-menu{
        border-bottom:0!important;
    }
    #topnav {
        /*弹性布局*/
        display: flex;
        /*左右贴边布局*/
        justify-content: space-between;
        /*水平居中*/
        align-items: center;
        background: #242424;
        height: 40px;
    }
    .top-links {
        display: flex;
        justify-content: flex-start;
        margin-left: 200px;
    }

    /deep/ .el-link.el-link--default {
        color: antiquewhite;
        width: 80px;
        position: relative;

    }

    .login{
        margin-right: 200px;
    }
    /deep/ .el-menu--horizontal>.el-submenu .el-submenu__title {
        height: 40px;
        line-height: 40px;
        color: antiquewhite;
    }

</style>
