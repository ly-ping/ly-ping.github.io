<!--宠物列表-->
<template>
    <div>
        <el-row >
            <!--面包屑导航-->
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/dashboard' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/system/pet' }">系统管理</el-breadcrumb-item>
                <el-breadcrumb-item>宠物列表</el-breadcrumb-item>
            </el-breadcrumb>
        </el-row>


        <!--搜索筛选-->
        <div class="search">
            <el-input v-model="query.pname" style="width: 200px;margin-right: 20px" placeholder="输入宠物昵称">宠物昵称</el-input>
            <el-input v-model="query.breed" style="width: 200px;margin-right: 20px" placeholder="输入品种">宠物品种</el-input>
            <el-input v-model="query.owner" style="width: 200px;margin-right: 20px" placeholder="输入主人账号">宠物主人</el-input>
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
            <el-table-column prop="pid" label="宠物id" width="80" align="center">
            </el-table-column>
            <el-table-column prop="pname" label="宠物名" width="150" align="center">
            </el-table-column>
            <el-table-column prop="breed" label="品种" width=150 align="center">
            </el-table-column>
            <el-table-column prop="gender" label="宠物性别" width="100" align="center">
                <template slot-scope="scope">
                   {{scope.row.gender === '1' ? '雄性' : '雌性'}}
                </template>
            </el-table-column>
            <el-table-column prop="age" label="宠物年龄" width="200" align="center">
            </el-table-column>
            <el-table-column prop="owner" label="宠主" width="200" align="center">
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
        <el-dialog class="petcard" title="商品信息" :visible.sync="dialogFormVisible">
            <el-form :model="form" label-width="80px">
                <el-form-item label="宠物名">
                    <el-input v-model="form.pname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="品种">
                    <el-select v-model="form.breed" placeholder="请选择宠物品种">
                        <el-option v-for="breed in breedOptions" :key="breed" :label="breed" :value="breed"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="宠物性别">
                    <el-select v-model="form.gender" placeholder="请选择宠物性别">
                        <el-option label="雌性" value="0" />
                        <el-option label="雄性" value="1" />
                    </el-select>
                </el-form-item>
                <el-form-item label="宠物年龄">
                    <el-input v-model.number="form.age" autocomplete="off" type="number" :min="1" placeholder="单位：岁"></el-input>
                </el-form-item>
                <el-form-item label="宠主">
                    <el-input v-model="form.owner" autocomplete="off"></el-input>
                </el-form-item>
                <!--<el-form-item label="商品类型" :label-width="formLabelWidth">-->
                <!--<el-select v-model="form.region" placeholder="请选择商品类型">-->
                <!--<el-option label="区域一" value="shanghai"></el-option>-->
                <!--<el-option label="区域二" value="beijing"></el-option>-->
                <!--</el-select>-->
                <!--</el-form-item>-->
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
        name: "pet",
        data(){
            return {
                tableData:[],
                total:0,
                //请求数据参数
                query:{
                    //当前页
                    currentPage:1,
                    //每页数据条数
                    pageSize:5,
                    pname: '',
                    breed: '',
                    owner:''
                },
                dialogFormVisible: false,
                //卡片
                form: {
                    pname:'',
                    breed:'',
                    gender:'',
                    age:'',
                    owner:''
                },
                breedOptions:['猫咪','狗狗','水族','小宠']
            }
        },
        //初始化调用
        mounted(){
            this.getList()
        },
        methods: {
            getList() {
                this.axios.get('http://localhost:8902/pet/petlist', {
                    params:{
                        currentPage:this.query.currentPage,
                        pageSize:this.query.pageSize,
                        pname:this.query.pname,
                        breed:this.query.breed,
                        owner:this.query.owner
                    }
                }).then((resp) => {
                    let data = resp.data.content;
                    console.log(data, '返回结果')
                    this.tableData = data.list;
                    this.total = data.total;
                    console.log(resp)
                })
            },

            //删除
            handleDelete(scope) {
                let id = scope.row.pid;
                this.axios.delete("http://localhost:8902/pet/delete/"+id).then((resp)=>{
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


            //编辑修改【没用】
            handleEdit(row) {
                this.form = row;
                this.dialogFormVisible = true
            },

            //新增【没用】
            handleCreate(){
                this.form = {}
                this.dialogFormVisible = true
            },

            submitForm() {
                this.axios.post("http://localhost:8902/pet/save/", this.form).then((resp) => {
                    let data = resp.data;
                    debugger
                    if (data.success) {
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

            //分页【没用】
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
        }
    }
</script>

<style lang="scss" scoped>
    .el_breadbrum{
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
    }
    .search{
        margin: 10px;
    }
    .petcard{
        /*width: 25%;*/
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