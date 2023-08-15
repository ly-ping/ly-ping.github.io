<!--交易订单-->
<template>
    <div>
        <!--面包屑导航-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/dashboard' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/order/trade' }">订单管理</el-breadcrumb-item>
            <el-breadcrumb-item>交易订单</el-breadcrumb-item>
        </el-breadcrumb>

        <!--搜索筛选-->
        <div class="search">
            <el-input v-model="query.oid" style="width: 200px" placeholder="输入订单编号"></el-input>
            <el-input v-model="query.uid" style="width: 200px" placeholder="输入用户id"></el-input>
            <el-input v-model="query.status" style="width: 200px" placeholder="输入订单状态"></el-input>
            <el-button @click="getList()" type="primary" icon="el-icon-search">查询</el-button>
            <!--新增-->
            <el-button @click="handleCreate()" type="primary" icon="el-icon-search">新增</el-button>
        </div>

        <!--列表-->
        <el-table :data="tableData"
                  :header-cell-style="{background:'#eaeaea',color:'#555',height:'80px'}"
                  header-align="center"
                  stripe>
            <el-table-column prop="oid" label="订单编号" width="200" align="center">
            </el-table-column>
            <el-table-column prop="uid" label="下单用户" width="150" align="center">
            </el-table-column>
            <el-table-column prop="ordertime" label="下单时间" width=200 align="center">
            </el-table-column>
            <el-table-column prop="status" label="订单状态" align="center" width="200">
            </el-table-column>
            <el-table-column prop="totalprice" label="总价" width="150" align="center">
            </el-table-column>
            <el-table-column prop="action" label="操作" align="center">
                <template slot-scope="scope">
                    <el-button
                            size="small"
                            @click="showOrder(tableData[scope.$index].id,tableData[scope.$index].oid,tableData[scope.$index].status)">查看详情</el-button>
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



        <!--订单详情卡片-->
        <el-dialog title="订单详情" :visible.sync="orderDetailVisible" >
            <el-form>
                <el-form-item label="编辑订单状态">
                    <el-select v-model="status" placeholder="请选择订单状态">
                        <el-option value="已付款" label="已付款"></el-option>
                        <el-option value="已发货" label="已发货"></el-option>
                        <el-option value="已收货" label="已收货"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>

            <el-table :data="selectedOrder" class="order-details" label-width="80px">
                <el-table-column prop="gid" label="商品编号"></el-table-column>
                <el-table-column prop="gname" label="商品名"></el-table-column>
                <el-table-column prop="price" label="商品单价"></el-table-column>
                <el-table-column prop="quantity" label="商品数量"></el-table-column>
            </el-table>

            <el-button-group>
                <el-button @click="handleStatusChange()">确定</el-button>
                <el-button @click="close">关闭</el-button>
            </el-button-group>

        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "trade",
        data(){
            return{
                //订单信息
                tableData:{},
                total:0,
                query:{
                    //当前页
                    currentPage:1,
                    //每页数据条数
                    pageSize:5,
                    oid:'',
                    uid:'',
                    status:'',
                },
                dialogFormVisible: false,
                form: {
                    uid:'',
                    ordertime:'',
                    sum:'',
                    status:'',
                    totalprice:''
                },

                //日期选择器
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    },
                    shortcuts: [{
                        text: '今天',
                        onClick(picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: '昨天',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一周前',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', date);
                        }
                    }]
                },


                orderDetailVisible:false,// 是否展示订单详细信息对话框
                //被查看的订单详情
                selectedOrder:{
                    gid:'',
                    gname:'',
                    price:'',
                    quantity:'',
                },
                selectedId:'',
                //下拉菜单栏
                status:'',

            }
        },
        //初始化调用
        mounted(){
            this.getList()
        },
        methods:{
            //初始化
            getList() {
                this.axios.get('http://localhost:8902/order/getlist',{
                    params:{
                        currentPage:this.query.currentPage,
                        pageSize:this.query.pageSize,
                        Uid:this.query.uid,
                        Oid:this.query.oid,
                        Status:this.query.status
                    }
                }).then((resp) => {
                    console.log(resp);
                    this.tableData = resp.data.content.list;
                    this.total = resp.data.content.total;
                })
            },


            //删除
            handleDelete(scope) {
                let id = BigInt(scope.row.oid);
                this.axios.delete("http://localhost:8902/order/delete/"+id).then((resp)=>{
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
                }),
                this.axios.delete("http://localhost:8902/orderdetail/delete/"+id).then((resp)=>{
                    console.log('订单详情',resp.data);
                })
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


            //查看订单详情
            showOrder(id,oid,status){
                this.orderDetailVisible= true;
                this.status = status; // 把传递过来的 status 值赋值给组件中的 status 变量,以便显示当前状态
                this.selectedId = id;//获取被选择订单的id并且传给更新状态的方法
                this.axios.get("http://localhost:8902/orderdetail/getall").then((resp)=>{
                    let data = resp.data;
                    console.log(data);
                    // 筛选出与被选中订单匹配的订单详情项
                    data = data.filter((item) => item.oid === oid);
                    this.selectedOrder = data;
                })
            },
            close(){
                this.orderDetailVisible= false
            },
            //订单状态改变
            handleStatusChange(){
                let id = this.selectedId;
                console.log('id',id);
                let status = this.status;
                console.log('status',status);
                this.axios.post("http://localhost:8902/order/save/", {id,status}).then((resp)=>{
                    console.log('resp',resp.data);
                    this.$message.success('订单状态更新成功');
                    this.orderDetailVisible= false;
                    this.getList();
                }).catch(error => {
                    this.$message.error(`订单状态更新失败：${error}`);
                });
            },
        },
    }
</script>

<style lang="scss" scoped>
    .search{
        margin: 10px;
        .el-input{
            width: 200px;
            margin-right: 20px
        }
    }

</style>