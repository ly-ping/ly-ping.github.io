<!--底部区域-->
<template>
    <div class="footer">
        <div>
            <el-row>
                <el-col :lg="8">
                    <h4>关于我们</h4>
                    <ul>
                        <li><a href="#">公司简介</a></li>
                        <li><a href="#">联系方式</a></li>
                        <li><a href="#">合作伙伴</a></li>
                    </ul>
                </el-col>
                <el-col :lg="8">
                    <h4>商品中心</h4>
                    <ul>
                        <li><a href="#">食品饮料</a></li>
                        <li><a href="#">日用品</a></li>
                        <li><a href="#">家电数码</a></li>
                    </ul>
                </el-col>
                <el-col :lg="8">
                    <h4>服务支持</h4>
                    <ul>
                        <li><a href="#">帮助中心</a></li>
                        <li><a href="#">售后服务</a></li>
                        <li><a href="#">留言</a></li>
                    </ul>
                </el-col>
            </el-row>
        </div>
        <div class="bottom-bar">
            <p>&copy; 2023 公司名称 版权所有</p>
        </div>
    </div>
</template>

<script>
    export default {
        name: "footernav",
    };
</script>

<style lang="less" scoped>
    .footer {
        margin-top: 50px;
        background-color: #f5f5f5;
        padding-top: 50px;
        padding-bottom: 50px;
    }

    .footer h4 {
        font-size: 18px;
        margin-bottom: 20px;
    }

    .footer ul {
        list-style: none;
        padding: 0;
    }

    .footer ul li {
        margin-bottom: 10px;
    }

    .bottom-bar {
        background-color: #eaeaea;
        text-align: center;
        padding: 10px;
        margin-top: 50px;
    }
</style>