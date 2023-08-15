<!--侧边栏菜单-->
<template>
    <el-menu
            mode="vertical"
            @open="handleOpen"
            @close="handleClose"
            default-active="/dashboard"
            background-color="#323940"
            text-color="#fff"
            active-text-color="#ffd04b"
            router
            :default-active="$route.path"
            :collapse="isCollapse"
            :collapse-transition="false"
            unique-opened>

        <el-menu-item index="/dashboard">
            <i class="el-icon-menu"></i>
            <span slot="title">首页</span>
        </el-menu-item>

        <el-submenu index="/system">
            <template slot="title">
                <i class="el-icon-user"></i>
                <span>系统管理</span>
            </template>
            <el-menu-item-group title="用户信息">
                <i class="el-icon-user" ></i>
                <el-menu-item index="/system/member">用户列表</el-menu-item>
            </el-menu-item-group>

            <el-menu-item-group title="宠物信息">
                <el-menu-item index="/system/pet">宠物列表</el-menu-item>
            </el-menu-item-group>
        </el-submenu>

        <el-submenu index="/goods">
            <template slot="title">
                <i class="el-icon-goods"></i>
                <span>商品管理</span>
            </template>
            <el-menu-item-group>
                <template slot="title">1（主食零食）</template>
                <el-menu-item index="/goods/goods">宠物商品</el-menu-item>
            </el-menu-item-group>

            <el-menu-item-group>
                <template slot="title">2（分类）</template>
                <el-menu-item index="/goods/category">商品分类</el-menu-item>
            </el-menu-item-group>
        </el-submenu>

        <el-submenu index="/order">
            <template slot="title">
                <i class="el-icon-box"></i>
                <span>订单管理</span>
            </template>
            <el-menu-item-group>
                <template slot="title">线上交易</template>
                <el-menu-item index="/order/trade">交易订单</el-menu-item>
            </el-menu-item-group>
        </el-submenu>

        <el-submenu index="/comments">
            <template slot="title">
                <i class="el-icon-message"></i>
                <span>系统评论</span>
            </template>

            <el-menu-item-group>
                <template slot="title">留言管理</template>
                <el-menu-item index="/comments/comments">留言列表</el-menu-item>
            </el-menu-item-group>
        </el-submenu>

        <!--折叠按钮-->
        <div class="toggle-button" @click="toggleCollapse">|||</div>

    </el-menu>
</template>


<script>
    export default {
        name: "leftnav",
        props:{
            isCollapse:Boolean
        },
        methods:{
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            },
            // 点击按钮，提交到父组件
            toggleCollapse () {
                this.$emit('toggleCollapse')
            }
        }
    }
</script>

<style lang="scss" scoped>
    /*去掉垂直导航栏右侧的竖线，该竖线使得导航菜单总是比侧边栏窄0.8px，导致视觉上无法对齐*/
    .el-menu{
       border-right:0!important;
    }
    .toggle-button{
        font-size: 10px;
        color: #fff;
        text-align: center;
        letter-spacing: 0.2em;
        cursor: pointer;
    }
</style>