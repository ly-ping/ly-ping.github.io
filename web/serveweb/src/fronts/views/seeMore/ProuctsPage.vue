<template>
    <div class="products-page">
        <div class="back">
            <el-page-header @back="goBack"></el-page-header>
        </div>


        <h1>{{ title }}</h1>
        <div class="row">
            <goods-card v-for="(item, index) in matchedList" :key="index" :item="item" />
        </div>
    </div>
</template>

<script>
    import goodsCard from "../../components/goodsCard";

    export default {
        name: "ProductsPage",
        components: {
            goodsCard,
        },
        computed: {
            matchedList() {
                return this.items.filter(item => item.category1 === this.catename);
            },
            title() {
                return `${this.catename}馆`;
            }
        },
        created() {
            // 通过路由参数获取 catename
            this.catename = this.$route.params.catename;
        },
        data() {
            return {
                catename: "",
                items: [], // 所有商品
            };
        },
        mounted() {
            // 获取商品数据，根据搜索关键词过滤
            this.getAll();
        },
        methods: {
            getAll() {
                this.axios.get("http://localhost:8902/goods/getall", {}).then((resp) => {
                    this.items = resp.data.map((item) => {
                        return {
                            id: item.gid,
                            name: item.gname,
                            category1: item.category1,
                            category2: item.category2,
                            price: item.gprice,
                            image: item.image,
                        };
                    })
                });
            },
            goBack() {
                if (window.history.length <= 1) {
                    this.$router.push({path: "/dash"});
                    return false;
                } else {
                    this.$router.go(-1);
                }
            }
        },
    };
</script>

<style lang="scss" scoped>
    /* 定义页面样式 */
    .products-page {
        background: #c6d7e7;
        h1 {
            margin-top: 30px;
            padding-bottom: 20px;
            font-size: 50px;
            font-weight: bold;
            text-align: center;
            color: #0b3977;
            border-bottom: 5px solid #ed7d19;
        }
    }
    /*返回上一级*/
    .back{
        width: 100%;
        .el-page-header {
            line-height: 50px;
        }
    }
    /*卡片区块*/
    .row {
        display: flex;
        flex-wrap: wrap;
        gap: 20px; /* 卡片之间的水平间距 */
        margin: 20px 200px; /* 上下间距并剧中 */
    }
    /*卡片*/
    .goods-card {
        max-width: calc(25% - 10px); /* 父容器宽度除以4再减去水平间距 */
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.3); /* 卡片阴影效果 */
        background: #fff;
    }
</style>