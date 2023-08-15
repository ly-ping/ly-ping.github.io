<!--主体框架-->
<template>
    <div class="container">
        <el-container class="homepage">

            <!--顶栏-->
            <topnav></topnav>

            <!--头部-->
            <headnav></headnav>

            <!--主体-->
            <el-main>
                <!--二级路由-->
                <router-view/>
            </el-main>

            <!--底部-->
            <el-footer>
                <footernav></footernav>
            </el-footer>


        </el-container>
    </div>
</template>

<script>
    import topnav from './components/topnav.vue';
    import headnav from './components/headnav.vue';
    import footernav from './components/footernav.vue';

    export default {
        name: "homePage",
        data() {
            return {

            }
        },
        //注册组件
        components:{
            topnav,
            headnav,
            footernav,
        },

        methods:{

        },

    }
</script>

<style lang="scss" scoped>
    .container{
        min-width: 1020px;
    }

    .topnav{
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

    .el-main{
        overflow-y: scroll;
    }


</style>