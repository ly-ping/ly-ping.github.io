<!--列表展示-->
<template>
    <div>
        <!--面包屑导航-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/dashboard' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/system/member' }">系统管理</el-breadcrumb-item>
            <el-breadcrumb-item>用户列表</el-breadcrumb-item>
        </el-breadcrumb>


        <!--搜索筛选-->
        <div class="search">
            <el-input v-model="query.account"  placeholder="按账号搜索"></el-input>
            <el-input v-model="query.phone"  placeholder="按电话搜索"></el-input>
            <el-input v-model="query.screenname"  placeholder="按用户名搜索"></el-input>
            <!--查询-->
            <el-button @click="getList()" type="primary" icon="el-icon-search">查询</el-button>
            <!--新增-->
            <el-button @click="handleCreate()" type="primary" icon="el-icon-search">新增</el-button>
        </div>

        <!--列表-->
        <el-table :data="tableData"
                  :header-cell-style="{background:'#eaeaea',color:'#555',height:'80px'}"
                  header-align="center"
                  stripe>
            <el-table-column prop="uid" label="id" width="100" align="center">
            </el-table-column>
            <el-table-column prop="role" label="角色" width="80" align="center">
                <template slot-scope="scope">
                    <el-tag
                            :type="scope.row.role === 'admin' ? 'danger' : 'primary'"
                            disable-transitions>{{scope.row.role === 'admin' ? 'admin' : 'member'}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="account" label="账号" width="100" align="center">
            </el-table-column>
            <el-table-column prop="password" label="密码" width="150" align="center">
            </el-table-column>
            <el-table-column prop="screenname" label="昵称" width="140" align="center">
            </el-table-column>
            <el-table-column prop="sex" label="性别" width="80" align="center">
            </el-table-column>
            <el-table-column prop="phone" label="电话" width="140" align="center">
            </el-table-column>
            <el-table-column prop="address" label="地址" width="200" align="center">
            </el-table-column>
            <el-table-column prop="action" label="操作" align="center">
                <template slot-scope="scope">
                    <el-button
                            size="small"
                            @click="handleEdit(scope.row)">编辑</el-button>
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

        <!--新增卡片-->
        <el-dialog class="usercard" title="用户信息" :visible.sync="dialogFormVisible">
            <el-form :model="form">
                <el-form-item label="账号">
                    <el-input v-model="form.account" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="form.screenname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="身份">
                    <el-select v-model="form.role" placeholder="请选择用户身份">
                        <el-option label="普通用户" value="member"></el-option>
                        <el-option label="管理员" value="admin"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="地址">
                    <el-input v-model="form.address" autocomplete="off"></el-input>
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
        //注意使用大写开头，小写生效
        name: "member",
        data() {
            return {
                //用户数据
                tableData:[],
                total:0,
                //请求数据参数(分页和搜索)
                query:{
                    currentPage:1,
                    pageSize: 5,
                    account: '',
                    phone: '',
                    screenname: '',
                    // role: ''
                },
                dialogFormVisible: false,
                //卡片
                form:{
                    account:'',
                    screenname:'',
                    phone:'',
                    role:'',
                    address:'',
                }

            }
        },
        //初始化调用
        mounted(){
            this.getList()
        },
        methods: {
            //获取用户列表
            getList() {
                this.axios.get('http://localhost:8902/user/getlist', {
                    params:{
                        currentPage:this.query.currentPage,
                        pageSize:this.query.pageSize,
                        Account:this.query.account,
                        Phone:this.query.phone,
                        Screenname:this.query.screenname
                    }
                }).then((resp) => {
                    console.log(resp)
                    this.tableData = resp.data.content.list;
                    this.total = resp.data.content.total;
                })
            },

            //编辑修改
            handleEdit(row) {
                this.form = row;
                this.dialogFormVisible = true
            },
            //删除
            handleDelete(scope) {
                let id = scope.row.uid;
                this.axios.delete("http://localhost:8902/user/delete/"+id).then((resp)=>{
                    let data = resp.data;
                    console.log(data);
                    if(data.success){
                        this.getList();
                        this.$message({
                            message: '删除成功',
                            type: 'success',
                            center: true
                        });
                    }
                })
            },

            //新增
            handleCreate(){
                this.form = {};
                this.dialogFormVisible = true
            },

            //分页
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.query.pageSize = val;
                this.getList()
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.query.currentPage = val;
                this.getList()
            },


            submitForm(){
                this.axios.post("http://localhost:8902/user/save/",this.form).then((resp)=>{
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
        },

    }
</script>

<style lang="scss" scoped>
    .user-search {
        margin-top: 20px;
    }
    .search{
        margin: 10px;
        .el-input{
            width: 200px;
            margin-right: 20px
        }
    }

    .usercard{
        display: inline-block;
        /*margin-right: 5%;*/
        justify-content: center;
        .el-form-item {
            display: flex;
            justify-content:center;
            /* 以下为可选的调整样式 */
            margin-bottom: 20px;
            /*width: 400px;*/
            .el-input,.el-select{
                width: 250px;
            }
        }
    }
</style>