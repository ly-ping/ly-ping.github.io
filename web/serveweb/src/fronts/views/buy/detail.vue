<!--商品详情-->
<template>
    <div class="container">
        <!--返回上一级按钮-->
        <div class="back">
            <el-page-header @back="returnPage" content="详情页面">
            </el-page-header>
        </div>
        <el-col class="detail-box">
           <!--左侧商品图：动态绑定-->
            <div class="goods-img">
                <!--<img src="../../../assets/logo.png" alt="">-->
                <img v-if="item.image" :src="item.image" alt="">
                <p v-else>暂无商品图片</p>
            </div>

            <!--右侧商品信息-->
            <div class="goods-info">
                <!--商品名-->
                <p class="name">{{item.gname}}</p>
                <!--商品详情-->
                <p class="details">{{item.gdetail}}</p>
                <!--商品价格-->
                <p class="price">&yen;{{item.gprice}}</p>
                <!--加购数量-->
                <div class="num">
                    <el-input-number v-model="num" :min="1"></el-input-number>
                </div>
                <!--加入购物车，立即购买等按钮 （加入购物车则不转跳页面提示加入购物车成功，立即购买则转跳到购物车全部一起结算）-->
                <div class="btns">
                    <el-button type="primary" @click="addCart">加入购物车</el-button>
                    <el-button type="success" @click="goToPay">去购物车结算</el-button>
                </div>
            </div>
        </el-col>
    </div>
</template>

<script>
    import Decimal from 'decimal.js';

    export default{
        name:'detail',

        data(){
            return{
                item:{},
                num:1
            }
        },
        created() {
            // 从 route.params 中获取商品ID
            const id = this.$route.params.id;
            // 调用接口获取商品详情
            this.axios.get(`http://localhost:8902/goods/detail/`+id).then(resp => {
                console.log(resp.data);
                this.item = resp.data.content
            })
        },
        methods: {
            // 返回上一级
            returnPage() {
                if (window.history.length <= 1) {
                    this.$router.push({path: "/dash"});
                    return false;
                } else {
                    this.$router.go(-1);
                }
            },
            handleChange(value) {
                console.log(value);
            },

            //跳转购物车页面
            goToPay() {
                this.$router.push({
                    path: '/cart'
                })
            },

            //添加购物车
            addCart() {
                // 创建购物车对象，如果购物车已存在（如localStorage里），则从里面获取购物车数据
                let shopcart = JSON.parse(localStorage.getItem('shopcart') || '[]');
                // 查找购物车中是否已经存在该商品，如果存在，则只更新数量；否则，将该商品加入购物车
                let flag = false;
                for (let cart of shopcart) {
                    console.log(shopcart,'xxx');
                    if (cart.id === this.item.gid) {
                        cart.num += this.num;
                        cart.total = new Decimal(cart.num).times(this.item.gprice).toFixed(2);
                        console.log(shopcart.total,'总价');
                        flag = true;
                        break;
                    }
                }
                // 如果购物车中不存在该商品，则将该商品加入购物车
                if (!flag) {
                    shopcart.push({
                        id: this.item.gid,
                        name: this.item.gname,
                        price: this.item.gprice,
                        num: this.num,
                        total: new Decimal(this.num).times(this.item.gprice).toFixed(2)
                    });
                }
                // 将购物车对象存储到本地存储中
                localStorage.setItem('shopcart', JSON.stringify(shopcart));
                this.$message.success("已添加到购物车")
                // console.log("加购成功");
            },
        }

    }
</script>

<style lang="less" scoped>

    @import "../../../style/variables";

    .container{
        display: flex;
        flex-direction: column;
        justify-content: center; /* 水平居中 */
        align-items: center; /* 垂直居中 */
    }
    .back{
        width: 100%;
        .el-page-header {
            line-height: 50px;
        }
    }
    .detail-box{
        width: 1240px;
        display: flex;;
        justify-content: center;
        margin: 50px auto;
        /*鼠标悬停阴影*/
        &:hover {
            transform: translate3d(0, -3px, 0);
            box-shadow: 0 3px 8px rgba(0, 0, 0, 0.2);
        }
        .goods-img{
            width: 360px;
            height: 360px;
            border: 1px solid #e6e6e6;
            padding: 19px;
            position: relative;
            img{
                width: 100%;
                height: 100%;
            }
        }
        .goods-info{
            width: 520px;
            margin-left: 20px;
            div{
                margin-top: 20px;
            }
            p {
                margin: 15px 30px 0;
                text-align: left;
                &.name {
                    font-size: 23px;
                    height: 60px;
                }
                &.details {
                    font-size: 18px;
                    color: #666;
                }
                &.price {
                    margin-top: 10px;
                    font-size: 30px;
                    color: @priceColor;
                }
            }

        }
    }

    /*测试*/
    div{
        /*border: 1px solid #1DC779;*/
    }

</style>

