<!--前台首页-->
<template>
    <div class="home">
      <!--背景-->
        <div class="carousel-background">
            <!--轮播图组件-->
            <!--type="card":卡片模式-->
            <el-carousel indicator-position="outside" >
                <el-carousel-item v-for="item in list" :key="item.id">
                    <img :src="item.src" alt="">
                </el-carousel-item>
            </el-carousel>
        </div>

        <homeProduct :products="products" :catelist = "catelist"></homeProduct>

    </div>
</template>

<script>
    import homeProduct from './home-product'

    export default {
        name: "dash",
        data(){
            return{
                list:[
                    {
                        src: require("@/assets/banner/两只兔子.jpg"),
                    },{
                        src: require("@/assets/banner/乌龟.jpg"),
                    }, {
                        src: require("@/assets/banner/仓鼠2.jpg"),
                    },{
                        src: require("@/assets/banner/兔子.jpg"),
                    },{
                        src: require("@/assets/banner/合影.jpg"),
                    },{
                        src: require("@/assets/banner/小老鼠.jpg"),
                    },{
                        src: require("@/assets/banner/狗.jpg"),
                    },{
                        src: require("@/assets/banner/猫.jpg"),
                    },{
                        src: require("@/assets/banner/猫狗.jpg"),
                    },{
                        src: require("@/assets/banner/睡觉猫.jpg"),
                    },
                ],
                products:[],//产品数据
                catelist:[],//分类数据
            }
        },
        components:{
            homeProduct
        },
        methods:{
        }
    }
</script>

<style lang="less" scoped>
    .home {
        display: flex;
        flex-direction: column;
        justify-content: center; /* 水平居中 */
        align-items: center; /* 垂直居中 */

    }
    .el-carousel {
        width: 1280px;
        position: relative;
        left: 0;
        top: 0;
        height: 450px;
        border-radius: 5px;
        img{
            height: 100%;
            width: 100%;
            object-fit: cover;
            /*object-fit: scale-down;*/

        }
    }

    .el-carousel__item {
        height: 450px;/* 将容器高度设置为 500px */
        overflow: hidden; /* 隐藏内部滚动条 */
    }

</style>