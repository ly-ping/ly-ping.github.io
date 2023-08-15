<!--搜索结果-->
<template>
    <div>
        <!--顶栏-->
        <topnav></topnav>

        <!--头部-->
        <headnav></headnav>

        <div class="main">
            <goods-card v-for="(item, index) in filteredItems" :key="index" :item="item" />
        </div>

        <!--底部-->
        <el-footer>
            <footernav></footernav>
        </el-footer>
    </div>
</template>

<script>
    import goodsCard from "../../fronts/components/goodsCard.vue";
    import topnav from '../../fronts/components/topnav.vue';
    import headnav from '../../fronts/components/headnav.vue';
    import footernav from '../../fronts/components/footernav.vue';

    export default {
        name: "SearchResult",
        components:{
            goodsCard,
            topnav,
            headnav,
            footernav
        },
        computed: {
            keyword() {
                return this.$route.params.keyword;
            },
            filteredItems() {
                return this.items.filter((item) =>
                    item.name.toLowerCase().includes(this.keyword.trim().toLowerCase())
                );
            },
        },
        data() {
            return {
                items: [], // 所有商品
            }
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
                        // .filter((item) =>
                        // item.name.toLowerCase().includes(this.keyword.trim().toLowerCase())
                    // );
                });
            },
        },
    }
</script>

<style lang="scss" scoped>


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


</style>