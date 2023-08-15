<template>
    <!--后台主页-->
    <div>
        <el-container class="manage">
            <!--头部菜单-->
            <el-header>
                <div>
                    <span>宠物服务管理系统</span>
                </div>
               <navcon></navcon>
            </el-header>

            <el-container class="main-container">
                <!--问题一：展开缩小大小变动问题-->
                <!--左侧边栏-->
                <el-aside :style="{flexBasis:isCollapse?'80px':'190px'}">
                    <div class="logobox">
                        <img class="logoimg" src="../assets/daijin.png" alt="">
                    </div>
                    <!--传递数据，前一个是子传父，后一个是父传子-->
                    <leftnav @toggleCollapse="toggleCollapse"
                            :isCollapse="isCollapse"
                             ></leftnav>
                </el-aside>

                <!--右侧-->
                <el-main style="height: 100%">
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    import navcon from '../components/navcon.vue';
    import leftnav from '../components/leftnav.vue';


    export default {
        name:"manage",

        data(){
            return{
                isCollapse:false,
            }
        },

        //注册组件
        components:{
            navcon,
            leftnav
        },

        comments:{

        },

        methods:{
            // 点击按钮，父组件改变子组件
            toggleCollapse () {
                this.isCollapse = !this.isCollapse
            }
        },
    }
</script>

<style lang="scss" scoped>
    /*manage容器*/
    .manage{
        /*铺满全屏*/
        width: 100%;
        height: 100vh;
    }
    .main-container{
        position: relative;
        height: calc(100vh - 60px);
    }


    .el-aside{
        height: 100%;
        padding: 20px;
        background: #323940;
    }
    .el-main{
        overflow-y: auto;
        height: 100%;

    }
    .el-header{
        background: #24292f;
        /*弹性布局*/
        display: flex;
        /*左右贴边布局*/
        justify-content: space-between;
        /*水平居中*/
        align-items: center;
        color: beige;
        font-size: 25px;
    }
    .toggle-button{
        font-size: 10px;
        color: #fff;
        text-align: center;
        letter-spacing: 0.2em;
        cursor: pointer;
    }

    .logobox {
        height: 50px;
        line-height: 40px;
        font-size: 20px;
        text-align: center;
        padding: 20px 0px;
    }
    .logoimg {
        height: 80px;
    }
</style>