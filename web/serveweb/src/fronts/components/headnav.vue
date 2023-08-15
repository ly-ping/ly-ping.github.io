
<!--头部区域-->
<template>
    <div class="head">
        <div class="container">
            <!--logo-->
            <div class="top-logo">
                <i class="el-icon-collection" style="margin-right: 10px"></i>
                <span>宠物服务管理系统</span>
            </div>

            <!--搜索筛查-->
            <div class="header-search">
                <!--@keyup.enter:回车键触发事件，注意使用了element-ui的el-input,需要加.native,然不起作用-->
                <el-input v-model="gname" style="width: 400px" placeholder="输入商品名" @keyup.enter.native="search()"></el-input>

                <el-button @click="search()" type="primary" icon="el-icon-search">查询</el-button>
                <!--<router-link tag="el-button" :to="{ name: 'search', params: { keyword: gname } }" type="primary" icon="el-icon-search">查询</router-link>-->
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "headnav",
        data(){
            return{
                items: [], // 所有商品
                gname: "", // 搜索框中输入的商品名称
                router: this.$router,
            }
        },
        methods: {
            search() {
                this.$router.push({
                    name: "search",
                    params: {
                        keyword: this.gname
                    }
                });
            }
        }
    }
</script>

<style lang="scss" scoped>
    .container{
        /*弹性布局*/
        display: flex;
        /*左右贴边布局*/
        justify-content: space-between;
        /*水平居中*/
        align-items: center;
    }

    .top-logo{
        font-family: "Helvetica Neue", Helvetica, "PingFang SC",
        "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
        font-size: 28px;
        margin: 20px 0 20px 200px;
    }

    .header-search{
        margin:23px 400px 17px 0px;
    }


    .nav{
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        background: #f9e8d0;
        margin-top: 20px;
    }
    .nav-menu {
        border-bottom:0!important;
    }
    /deep/ .el-menu--horizontal>.el-menu-item {
        float: left;
        height: 60px;
        line-height: 60px;
        margin: 0;
        border-bottom: 2px solid transparent;
        width: 150px;
        font-size: 23px;
    }

</style>