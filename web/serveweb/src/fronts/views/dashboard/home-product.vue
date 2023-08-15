<!--产品区块组件-->
<template>
    <div class="home-product">
        <panel :title="category.catename" v-for="(category,index) in catelist" :key="index">
            <!--右侧插槽-->
            <template  #right>
                <!--<div class="sub">-->
                    <!--<routerLink v-for="(sub,i) in category.children" :key="i" to="/">-->
                        <!--{{sub.catename}}-->
                    <!--</routerLink>-->
                <!--</div>-->
                <see-more :catename="category.catename"/>
            </template>
            <!--商品列表-->
            <div class="mainBox">
                <!--左侧-->
                <routerLink class="cover" :to="`/category/${category.catename}`">
                    <img :src="require(`@/assets/${category.catename}.jpg`)" alt="">
                    <strong class="label">
                        <span>{{category.catename}}馆</span>
                        <span>全场3件7折</span>
                    </strong>
                </routerLink>
                <!--右侧-->
                <ul class="goods-list">
                    <li v-for="(item,index) in getMatchedItems(category.catename).slice(0, 8)" :key="index">
                        <goods-card :item="item" />
                    </li>
                </ul>
            </div>
        </panel>
    </div>
</template>

<script>
    import SeeMore from "../seeMore/seeMore";
    import panel from '../../components/panel'
    import GoodsCard from "../../components/goodsCard";

    // 直接树
    function getChildrenCategories(categories, categoryId = 0) {
        const result = [];
        for (let i = 0; i < categories.length; i++) {
            const category = categories[i];
            if (category.catepid == categoryId) {
                const children = getChildrenCategories(categories, category.id);
                category.children = children;
                result.push(category);
            }
        }
        return result;
    }

    export default {
        name: "home-product",
        data(){
            return{
                //card组件数据
                items:[],
                catelist: {
                    type:Array,
                    required:true
                },
            }
        },
        components: {
            GoodsCard,
            SeeMore,
            panel
        },
        //初始化调用
        mounted(){
            this.getAll();
            this.getCategories();
        },

        methods: {
            //获取商品列表
            getAll(){
                this.axios.get("http://localhost:8902/goods/getall",{}).then((resp)=>{
                    this.items = resp.data.map((item) => {
                        return {
                            id: item.gid,
                            name: item.gname,
                            category1: item.category1,
                            category2: item.category2,
                            price: item.gprice,
                            image: item.image,
                        };
                    });
                    console.log("items",this.items)
                }).catch((error)=>{
                    console.log(error);
                })
            },
            //过滤符合条件的商品列表，并在页面上进行渲染
            getMatchedItems(title) {
                return this.items.filter((item) => item.category1 === title);
            },

            //获取商品分类列表
            getCategories(){
                this.axios.get("http://localhost:8902/cate/getall").then((resp)=>{
                    console.log('resp.data',JSON.parse(JSON.stringify(resp.data)));
                    //扁平化的数据转变为多层数据
                    let data = resp.data;
                    this.catelist = getChildrenCategories(data);
                    console.log('this.catelist', this.catelist);
                }).catch((error)=>{
                    console.log(error);
                });
            },

        },

    }
</script>

<style lang="less" scoped>
    @import "../../../style/mixins"; /* 导入 mixins */
    @import "../../../style/variables";


    .home-product {
        background: #fff;
        /*右侧插槽*/
        .sub {
            margin-bottom: 2px;
            a {
                padding: 2px 17px;
                font-size: 20px;
                border-radius: 4px;
                text-decoration: none;
                &:hover {
                    background: @xtxColor;
                    color: #fff;
                }
                &:last-child {
                    margin-right: 80px;
                }
            }
        }

    }
    /*下方*/
    .mainBox {
        display: flex;
        margin-bottom: 80px;
        border-top: 4px solid #ED7D19;
        border-bottom: 1px solid #ED7D19;
        /*左侧*/
        .cover {
            width: 240px;
            height: 610px;
            margin-right: 10px;
            position: relative;
            img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }
            .label {
                width: 240px;
                height: 66px;
                display: flex;
                font-size: 18px;
                color: #fff;
                line-height: 66px;
                font-weight: normal;
                position: absolute;
                left: 0;
                top: 50%;
                opacity: 0;/*隐藏*/
                transform: translate3d(0,-110%,0);
                transition: all .5s;/*动画效果*/
                span {
                    text-align: center;
                    &:first-child {
                        width: 100px;
                        background: rgba(0,0,0,.9);
                    }
                    &:last-child {
                        flex: 1;
                        background: rgba(0,0,0,.7);
                    }
                }
            }
            &:hover{
                .label{
                    opacity: 1;
                }
            }
        }
        /*右侧*/
        .goods-list {
            width: 990px;
            display: flex;
            flex-wrap: wrap;
            margin: 0 20px;
            padding: 0;
            li {
                list-style:none;
                width: 240px;
                height: 300px;
                margin-right: 10px;
                margin-bottom: 10px;
                &:nth-last-child(-n+4) {
                    margin-bottom: 0;
                }
                &:nth-child(4n) {
                    margin-right: 0;
                }

            }
        }
    }

</style>