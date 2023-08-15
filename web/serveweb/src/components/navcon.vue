<!--头部菜单-->
<template>
    <el-menu
             mode="horizontal"
             background-color="#24292f"
             text-color="#fff"
             active-text-color="#ffd04b">

        <el-submenu index="">
            <template slot="title">{{screenname}}</template>
            <el-menu-item index="/setting">设置</el-menu-item>
            <el-menu-item index="/" @click="signout">退出登录</el-menu-item>
        </el-submenu>

        <!--全屏展示-->
        <div class="fullscreen" @click="handleFull" content="全屏">
            <el-tooltip :content="fullscreen?`取消全屏`:`全屏`"
                        placement="bottom">
                <i class="el-icon-full-screen"></i>
            </el-tooltip>
        </div>

    </el-menu>
</template>

<script>
    export default {
        name: "navcon",
        data() {
            return {
                fullscreen: false,
            }
        },
        //监视
        computed:{
            screenname(){
                return  JSON.parse(localStorage.getItem('user')).screenname;
            }
        },
        methods: {
            //全屏事件
            handleFull() {
                if (this.fullscreen) {
                    //该语句主要是考虑不同浏览器兼容
                    if (document.exitFullscreen) {
                        document.exitFullscreen();
                    } else if (document.webkitCancelFullScreen) {//适用于谷歌，Safari，Chrome等浏览器
                        document.webkitCancelFullScreen();
                    }
                } else {
                    let element = document.documentElement;
                    element.requestFullscreen();
                }
                this.fullscreen = !this.fullscreen
            },
            //退出登录
            signout() {
                localStorage.removeItem('screenname');
                localStorage.removeItem('role');
                this.$router.push("/");
            }
        }
    }
</script>

<style scoped>

    .fullscreen{
        display: inline-block;
        margin: 16px;
    }

</style>