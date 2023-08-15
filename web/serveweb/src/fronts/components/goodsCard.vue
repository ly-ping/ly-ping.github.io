<!--商品盒子组件，展示单个商品-->
<template>
    <div class="goods-card">

        <router-link :to="`/detail/${item.id}`" class="image">
            <!--商品图:动态绑定数据库的图片-->
            <img :src="item.image" alt="" />
        </router-link>
        <!--商品名-->
        <p class="name ellipsis-2">{{item.name}}</p>
        <!--一级分类-->
        <!--<p class="category1">{{item.category1}}</p>-->
        <!--二级分类-->
        <p class="category2">{{item.category2}}</p>
        <!--价格  &yen;价格符号-->
        <p class="price">&yen;{{item.price}}</p>

    </div>
</template>

<script>
    export default {
        name: "goodsCard",

        props:['item'],
    }
</script>

<style lang="less" scoped>

    @import "../../style/base.css";
    @import "../../style/variables";

    .goods-card {
        width: 240px;
        height: 300px;
        position: relative;
        overflow: hidden;
        border-radius: 5px;
        border: 1px solid transparent;
        transition: all .5s;
        margin-bottom: 5px;
        .image {
            display: block;
            width: 160px;
            height: 160px;
            margin: 0 auto;
            img {
                width: 100%;
                height: 100%;
            }
        }
        p {
            margin: 6px 10px 0;
            font-size: 16px;
            text-align: left;

            &.name {
                height: 44px;
            }
            &.category1,&.category2 {
                color: #666;
                height: 22px;
            }
            &.price {
                margin-top: 10px;
                font-size: 20px;
                color: @priceColor;
            }
        }
        &:hover {
            border-color: @xtxColor;
        }
    }

</style>