<!--预约订单-->
<template>
    <div>
        <!--面包屑导航-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/dashboard' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/' }">订单管理</el-breadcrumb-item>
            <el-breadcrumb-item>预约订单</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
</template>

<script>
    export default {
        name: "booking"
    }
</script>

<style scoped>

</style>