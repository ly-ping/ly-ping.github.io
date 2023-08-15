<!--产品区块组件:猫咪 商品-->
<template>
    <div class="home-product">
        <panel title="猫咪商品" v-for="i in 1" :key="i">
            <!--右侧插槽-->
            <template  #right>
                <div class="sub">
                    <routerLink to="/">食品</routerLink>
                    <RouterLink to="/">药品</RouterLink>
                    <RouterLink to="/">用品</RouterLink>
                    <RouterLink to="/">洗护</RouterLink>
                    <RouterLink to="/">日常</RouterLink>
                </div>
                <see-more />
            </template>

            <div class="mainBox">
                <!--左侧-->
                <routerLink class="cover" to="/">
                    <img src="http://img.boqiicdn.com/Data/Shop/0/0/0/shoppicpath1536131645.jpg" alt="">
                    <!--<img :src="item.picture">-->
                    <strong class="label">
                        <span>宠物馆</span>
                        <span>全场3件7折</span>
                    </strong>
                </routerLink>
                <!--右侧-->
                <ul class="goods-list">
                    <li v-for="(item,index) in items" :key="index" >
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

    export default {
        name: "dog",
        data(){
            return{
                //card组件数据
                items:[],
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
        },
        methods:{
            getAll(){
                this.axios.get('http://localhost:8902/goods/getall').then((resp)=>{
                    let data = resp.data;
                    // 过滤商品类型为 "猫咪" 的商品
                    data = data.filter((item) => {
                        return item.category1 === '猫咪';
                    });
                    // 映射数据到组件数据格式
                    this.items = data.map((item) => {
                        return {
                            id: item.gid,
                            name: item.gname,
                            category1: item.category1,
                            category2: item.category2,
                            price: item.gprice,//用price代替gprice 从而映射到item.price
                            image: item.image,
                            // 其它属性...
                        };
                    });

                    console.log(data)

                }).catch((error) => {
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
        /*下方*/
        .mainBox {
            display: flex;
            margin: 20px 0 40px 0;
            /*左侧*/
            .cover {
                width: 240px;
                height: 610px;
                margin-right: 10px;
                position: relative;
                img {
                    width: 100%;
                    height: 100%;
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
                             width: 80px;
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
    }
</style>