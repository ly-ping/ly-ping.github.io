<!--查看更多-->
<template>
    <router-link :to="`/category/${catename}`" class="more">
        <span>查看更多</span>
        <i class="el-icon-d-arrow-right"></i>
    </router-link>
</template>

<script>
    export default {
        name: "seeMore",
        props: {
            catename: String,
        },
    }
</script>

<style lang="less" scoped>
    /* 定义页面样式 */
    .main {
        display: flex;
        flex-wrap: wrap;
        gap: 20px; /* 卡片之间的水平间距 */
        margin: 20px 200px; /* 上下间距并剧中 */
    }

    .goods-card {
        max-width: calc(25% - 10px); /* 父容器宽度除以4再减去水平间距 */
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.3); /* 卡片阴影效果 */
    }


    .more{
        text-decoration: none;
        font-size: 17px;
        span{

            color: #999;
            margin-right: 5px;

        }
        i{
            font-size: 14px;
            color: #ccc;
        }
    }
</style>