<!--留言-->
<template>
    <div>
        <!--面包屑导航-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/dashboard' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>留言</el-breadcrumb-item>
        </el-breadcrumb>

        <!--搜索-->
        <el-input v-model="query.uid" style="width: 200px" placeholder="按用户名查询"></el-input>
        <el-button @click="getList()" type="primary" icon="el-icon-search">查询</el-button>


        <!--列表-->
        <el-table :data="tableData"
                  :header-cell-style="{background:'#eaeaea',color:'#555',height:'80px'}"
                  header-align="center"
                  stripe>
            <el-table-column prop="id" label="留言编号" width="80" align="center">
            </el-table-column>
            <el-table-column prop="content" label="留言内容" width="250" align="center">
            </el-table-column>
            <el-table-column prop="addtime" label="留言时间" width=150 align="center">
            </el-table-column>
            <el-table-column prop="uid" label="留言用户" width="100" align="center">
            </el-table-column>
            <el-table-column prop="replytime" label="回复时间" width="150" align="center">
            </el-table-column>
            <el-table-column prop="replycontent" label="回复内容" width="250" align="center">
            </el-table-column>
            <el-table-column prop="action" label="操作" align="center">
                <template slot-scope="scope">
                    <el-button
                            size="small"
                            @click="handleReply(scope.row)">回复</el-button>
                    <el-button
                            size="small"
                            type="danger"
                            @click="handleDelete(scope)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>


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


        <!--回复卡片-->
        <el-dialog title="回复" :visible.sync="dialogFormVisible">
            <el-form :model="form"
                     :rules="{ required: true, message: '回复内容不能为空', trigger: 'blur'}">
                <el-form-item label="回复内容">
                    <el-input type="textarea" v-model="form.replycontent"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitForm()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "Comments",
        data() {
            return {
                tableData: [],
                total: 0,
                //来自分页和搜索的信息
                query: {
                    //当前页
                    currentPage: 1,
                    //每页数据条数
                    pageSize: 5,
                    //搜索
                    uid: '',
                },
                dialogFormVisible: false,
                form:{
                    replycontent:'',
                    replytime:''
                }
            }
        },
        //初始化调用
        mounted() {
            this.getList()
        },
        methods: {
            //初始化
            getList() {
                this.axios.get('http://localhost:8902/message/getlist', {
                    params: {
                        //将本页面的数据（this.query.xxx）传到后端messageReq和PageReq
                        currentPage: this.query.currentPage,
                        pageSize: this.query.pageSize,
                        Uid: this.query.uid,
                    }
                }).then((resp) => {
                    console.log(resp);
                    this.tableData = resp.data.content.list;
                    this.total = resp.data.content.total;
                })
            },

            //打开回复框
            handleReply(row){
                this.form = row;
                this.dialogFormVisible = true
            },

            //提交留言到数据库
            submitForm(){
                this.form.replytime =  new Date().toJSON() // 添加replytime属性并设置为当前时间
                // this.form.replytime =  new Date().getTime() // 获取当前时间的时间戳
                this.axios.post("http://localhost:8902/message/save/",this.form).then((resp)=>{
                    let data = resp.data;
                    if(data.success){
                        this.dialogFormVisible = false;
                        //清空表单
                        this.form = {};
                        //查询提交的数据
                        this.getList();
                        this.$message({
                            message: '恭喜您，操作成功',
                            type: 'success',
                            center: true
                        })
                    }
                })
            },


            //删除
            handleDelete(scope) {
                let id = scope.row.id;
                this.axios.delete("http://localhost:8902/message/delete/" + id).then((resp) => {
                    let data = resp.data;
                    console.log(data)
                    if (data.success) {
                        this.getList();
                        this.$message({
                            message: '删除成功',
                            type: 'success',
                            center: true
                        });
                    }
                })
            },

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

        }
    }
</script>

<style lang="scss" scoped>

</style>