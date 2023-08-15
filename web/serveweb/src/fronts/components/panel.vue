<!--通用产品区块面板的头部-->
<template>
    <div class="panel">
        <!--头部-->
        <div class="header">
            <h3>{{title}}</h3>
            <!--右侧插槽-->
            <slot name="right"></slot>
        </div>
        <!--默认插槽 (有时间考虑做tabs标签页用于展示不同类型的商品)-->
        <slot/>
    </div>
</template>

<script>
    export default {
        name: "panel",
        props:{
            title:{
                type:String,
                default:"主标题"
            },
        }
    }
</script>

<style lang="less" scoped>
    .panel {
        background-color: #fff;
        .header {
            padding: 0;
            display: flex;
            align-items: baseline;
            width: 1240px;
            margin: 20px 0 5px 0;
            h3 {
                flex: 1;
                font-size: 32px;
                font-weight: normal;
                text-align: initial;
                margin-left: 100px;
                height: 35px;
                line-height: 35px;
                color: #ED7D19;
                small {
                    font-size: 16px;
                    color: #999;
                    margin-left: 20px;
                }
            }
        }
    }

</style>