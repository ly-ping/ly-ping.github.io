<!--分页组件-->
<template>
    <div>
        <!--分页组件-->
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="query.currentPage"
                :page-sizes="[5, 10, 20]"
                :page-size="this.query.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="this.total">
        </el-pagination>
    </div>
</template>
<script>


    export default {
        name: 'Page',
        data() {
            return {
                total:0,
                query:{
                    //当前页
                    currentPage:1,
                    //每页数据条数
                    pageSize:5,
                }
            }
        },
        methods:{
            //分页
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.query.pageSize = val
                this.getList()
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.query.currentPage = val
                this.getList()
            },
        },

    }


</script>

<style>
    .page-box {
        margin: 10px auto;
    }
</style>
