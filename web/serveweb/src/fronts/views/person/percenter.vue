<!--个人中心-->
<template>
    <div class="center">
        <!--头部顶栏-->
        <topnav></topnav>

        <!--主体-->
        <div class="main-container">
            <el-row :guyyer="20" class="row-box">
                <!--用户卡片-->
                <el-col :md="12">
                    <el-card class="user">
                        <!--头像-->
                        <!--<div id="active_hover" >-->
                            <!--<img src="../../assets/logo.png" style="width: 50px">-->
                        <!--</div>-->
                        <!--标题-->
                        <div>
                            <h2>基本信息</h2>
                        </div>
                        <!--信息-->
                        <div class="userInfo">
                            <el-descriptions>
                                <el-descriptions-item label="用户名"> <span>{{Form.screenname}}</span> </el-descriptions-item>
                                <el-descriptions-item label="账号"> <span>{{Form.account}}</span> </el-descriptions-item>
                                <el-descriptions-item label="身份"> <span>{{Form.role}}</span> </el-descriptions-item>
                                <el-descriptions-item label="电话"> <span>{{Form.phone}}</span> </el-descriptions-item>
                                <el-descriptions-item label="地址"> <span>{{Form.address}}</span> </el-descriptions-item>
                            </el-descriptions>

                            <el-button type="primary" @click="show">编辑</el-button>
                        </div>
                    </el-card>

                    <!--用户卡片，编辑信息-->
                    <el-dialog title="编辑修改" :visible.sync="dialogFormVisible" :before-close="handleClose">
                        <el-form :model="form" :rules="rules" class="user-card" label-width="80px">
                            <el-form-item label="昵称">
                                <el-input v-model="form.screenname" :readonly="editingField !== 'screenname' || !EditMode" autocomplete="off"></el-input>
                                <el-button v-if="editingField !== 'screenname'" type="primary" icon="el-icon-edit" circle @click="handleEdit('screenname')">
                                </el-button>
                                <el-button v-else type="success" icon="el-icon-check" circle @click="submitForm('screenname')"></el-button>
                            </el-form-item>
                            <el-form-item label="账号">
                                <el-input v-model="form.account" :readonly="editingField !== 'account' || !EditMode" autocomplete="off"></el-input>
                                <el-button v-if="editingField !== 'account'" type="primary" icon="el-icon-edit" circle @click="handleEdit('account')">
                                </el-button>
                                <el-button v-else type="success" icon="el-icon-check" circle @click="submitForm('account')"></el-button>
                            </el-form-item>
                            <el-form-item label="密码">
                                <el-input v-model="form.password" :readonly="editingField !== 'password' || !EditMode" autocomplete="on"></el-input>
                                <el-button v-if="editingField !== 'password'" type="primary" icon="el-icon-edit" circle @click="handleEdit('password')">
                                </el-button>
                                <el-button v-else type="success" icon="el-icon-check" circle @click="submitForm('password')"></el-button>
                            </el-form-item>
                            <el-form-item label="电话">
                                <el-input v-model="form.phone" :readonly="editingField !== 'phone' || !EditMode" autocomplete="off"></el-input>
                                <el-button v-if="editingField !== 'phone'" type="primary" icon="el-icon-edit" circle @click="handleEdit('phone')">
                                </el-button>
                                <el-button v-else type="success" icon="el-icon-check" circle @click="submitForm('phone')"></el-button>
                            </el-form-item>
                            <el-form-item label="地址">
                                <el-input v-model="form.address" :readonly="editingField !== 'phone' || !EditMode" autocomplete="off"></el-input>
                                <el-button v-if="editingField !== 'address'" type="primary" icon="el-icon-edit" circle @click="handleEdit('address')">
                                </el-button>
                                <el-button v-else type="success" icon="el-icon-check" circle @click="submitForm('address')"></el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>


                </el-col>


                <!--宠物卡片-->
                <el-col :md="12" >
                    <el-card class="pet">
                        <div class="petHead">
                            <h2>我的宠物</h2>
                            <el-button type="text" size="small" icon="el-icon-plus" @click="addPet()">添加宠物</el-button>
                        </div>
                        <br>
                        <br>
                        <el-table :data="pets" class="pet-wrapper">
                            <el-table-column prop="pid" label="宠物编号" align="center"></el-table-column>
                            <el-table-column prop="pname" label="昵称" align="center"></el-table-column>
                            <el-table-column prop="breed" label="品种" align="center"></el-table-column>
                            <el-table-column>
                                <template slot-scope="scope">
                                    <el-button type="text" @click="showDetail(scope.$index)">查看详情</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-card>
                    <!--宠物详情卡片，可以修改信息-->
                    <el-dialog title="宠物详情" :visible.sync="detailVisible" >
                        <el-form v-if="selectedPet" class="pet-details" label-width="80px">
                            <el-form-item label='编号(只读)' v-if="!editMode">
                                <el-input v-model="selectedPet.pid" readonly="readonly"></el-input>
                            </el-form-item>
                            <el-form-item label="昵称">
                                <el-input v-model="selectedPet.pname" :readonly="!editMode" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="品种">
                                <el-select v-model="selectedPet.breed" :disabled="!editMode" autocomplete="off" placeholder="请选择宠物品种">
                                    <el-option v-for="breed in breedOptions" :key="breed" :label="breed" :value="breed"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="性别">
                                <el-select v-model="selectedPet.gender" :disabled="!editMode" autocomplete="off" placeholder="请选择宠物性别">
                                    <el-option label="雌性" value="0" />
                                    <el-option label="雄性" value="1" />
                                </el-select>
                            </el-form-item>
                            <el-form-item label="年龄">
                                <el-input v-model="selectedPet.age" :readonly="!editMode" autocomplete="off"></el-input>
                            </el-form-item>
                        </el-form>
                        <el-button v-if="!editMode" type="primary" @click="editMode = true">修改</el-button>
                        <el-button v-else @click="updatePet()">提交</el-button>
                        <el-button type="danger" @click="deletePet()" style="margin-left: 10px">删除该宠物</el-button>
                    </el-dialog>
                </el-col>


                <!--订单卡片-->
                <el-col :md="24" >
                    <el-card class="order">
                        <h2>我的订单</h2>
                        <br>
                        <br>
                        <el-table :data="orders" style="width: 100%">
                            <el-table-column prop="oid" label="订单编号"></el-table-column>
                            <el-table-column prop="ordertime" label="下单时间"></el-table-column>
                            <el-table-column prop="status" label="订单状态"></el-table-column>
                            <el-table-column prop="totalprice" label="订单总价"></el-table-column>
                            <el-table-column>
                                <template slot-scope="scope">
                                    <el-button type="text" @click="showOrder(orders[scope.$index].oid)">
                                        查看详情
                                    </el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-card>
                    <!--订单详情卡片-->
                    <el-dialog title="订单详情" :visible.sync="orderDetailVisible" >
                        <el-table :data="selectedOrder" class="order-details" label-width="80px">
                            <el-table-column prop="oid"  label='订单编号' width="200px"></el-table-column>
                            <el-table-column prop="gid" label="商品编号"></el-table-column>
                            <el-table-column prop="gname" label="商品名"></el-table-column>
                            <el-table-column prop="price" label="商品单价"></el-table-column>
                            <el-table-column prop="quantity" label="商品数量"></el-table-column>
                        </el-table>
                        <el-button @click="close">关闭</el-button>
                    </el-dialog>

                </el-col>
            </el-row>
        </div>



    </div>
</template>

<script>
    import topnav from '../../components/topnav';

    export default {
        name:"percenter",
        data() {
            return {
                dialogFormVisible: false,
                //用户信息
                Form:{
                    uid:'',//用户id
                    screenname: '',	//用户昵称
                    account:'',	//用户ID
                    role:'',    //角色
                    sex: '',	//用户性别
                    address: '',	//用户地址
                    phone:'',   //用户电话
                },
                //编辑卡片
                form:{
                    uid:'',
                    account:'',
                    password:'',
                    screenname:'',
                    phone:'',
                    address:'',
                },
                EditMode: false,
                //追踪正在编辑的字段
                editingField: "",
                rules: {
                    account: [
                        { required:true, message:' 请输入账号' ,trigger: 'blur' },
                        { pattern: /^\d{4,8}$/,message:"账号只能由4~8位数字组成",trigger:'blur'}
                    ],
                    password: [
                        // { validator: validatePass, trigger: 'blur' },
                        { min :6 ,message:"密码至少六位",trigger:'blur'}
                    ],
                    phone: [
                        { required: true ,message:"请输入手机号", trigger: 'blur' },
                        { pattern: /^1[3456789]\d{9}$/, message: '手机号格式不正确', trigger: 'blur'  },
                    ],
                    screenname: [
                        { required: true, message: '请输入昵称', trigger: 'blur'},
                        { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                    ],
                    address: [
                        { required: true, message: '请输入地址', trigger: 'change'}
                    ],
                    sex: [
                        { required: true, message: '请选择性别', trigger: 'change'}
                    ],
                },


                detailVisible:false,// 是否展示宠物详细信息对话框
                editMode: false, // 是否处于编辑模式
                //宠物信息
                pets:[],
                //被查看的宠物信息
                selectedPet:{
                    pid: '',
                    pname:'',
                    breed:'',
                    gender:'',
                    age:'',
                },
                //宠物种类表
                breedOptions:['猫咪','狗狗','水族','小宠'],


                orderDetailVisible:false,// 是否展示详细信息对话框
                //订单信息
                orders:[],
                //被查看的订单详情
                selectedOrder:{
                    oid:'',
                    gname:'',
                    gid:'',
                    price:'',
                    quantity:''
                }



            };
        },
        //注册组件
        components:{
            topnav
        },
        mounted(){
            this.getUser();
            this.getPet();
            this.getOrder();
        },
        methods: {
            //获取用户信息
            getUser(){
                this.axios.get('http://localhost:8902/user/getall').then((resp)=>{
                    let data = resp.data;
                    //若直接用this.Form获取过滤的结果，得到是含有单个user对象的数组，而不是纯JavaScript对象（即不是数组）
                    let item = data.filter((user) => user.uid === JSON.parse(localStorage.getItem('user')).uid);
                    if (item.length > 0) {
                        this.Form = {...item[0]};
                    }
                })
            },

            //关闭对话框
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            },
            show() {
                this.form = this.Form;
                this.dialogFormVisible = true
            },
            //修改个人信息
            handleEdit(field) {
                if (field === this.editingField) {
                    this.EditMode = !this.EditMode;
                } else {
                    this.EditMode = true;
                    this.editingField = field;
                }
            },

            //提交用户信息
            submitForm(){
                this.editingField = "";
                this.axios.post("http://localhost:8902/user/save/",this.form).then((resp)=>{
                    let data = resp.data;
                    if(data.success){
                        // this.dialogFormVisible = false;
                        //查询提交的数据
                        this.getUser();
                        this.$message({
                            message: '修改成功',
                            type: 'success',
                            center: true
                        })
                    }
                })
            },



            //获取宠物信息
            getPet(){
                this.axios.get("http://localhost:8902/pet/getall").then((resp)=>{
                    console.log(resp);
                    let data = resp.data;
                    // 过滤宠物主人为当前用户的宠物
                    data = data.filter((pet) => {
                        return pet.owner === JSON.parse(localStorage.getItem('user')).uid;
                    });
                    // 将数据映射为本地数据格式，存储在 Vue 的组件数据中。
                    this.pets = data.map((pet) => {
                        return {
                            pid: pet.pid,
                            pname: pet.pname,
                            breed: pet.breed,
                            gender: pet.gender,
                            age: pet.age,
                        };
                    });
                })
            },
            // 点击某个宠物时，将该宠物的相关信息赋值到组件数据中，使宠物详情界面显示该宠物的信息。
            showDetail(index) {
                this.selectedPet = this.pets[index];
                this.detailVisible = true;
                this.editMode = false;
            },
            //新增宠物
            addPet(){
                this.selectedPet = {
                    owner: JSON.parse(localStorage.getItem('user')).uid
                };
                this.detailVisible = true;
                this.editMode = true;
            },
            //提交并修改宠物信息
            updatePet(){
               this.axios.post("http://localhost:8902/pet/save/",this.selectedPet).then((resp)=>{
                   let data = resp.data;
                   if(data.success) {
                       this.editMode = false;
                       //查询提交的数据
                       this.getPet();
                       this.$message({
                           message: "操作成功啦",
                           type: "success",
                       });
                   }
                });
            },
            //删除宠物
            deletePet(){
                let id = this.selectedPet.pid;
                this.axios.delete("http://localhost:8902/pet/delete/"+id).then((resp)=>{
                    let data = resp.data;
                    console.log(data);
                    if(data.success){
                        this.detailVisible = false;
                        this.getPet();
                        this.$message({
                            message: '删除成功',
                            type: 'success',
                            center: true
                        });
                    }
                })
            },


            //重置
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },

            // 获取订单
            getOrder(){
                this.axios.get("http://localhost:8902/order/getall").then((resp)=>{
                    console.log(resp);
                    let data = resp.data;
                    // 过滤下单用户为当前用户的订单
                    data = data.filter((order) => {
                        return order.uid === JSON.parse(localStorage.getItem('user')).uid;
                    });
                    console.log(data,'筛选的订单')
                    // 将数据映射为本地数据格式，存储在 Vue 的组件数据中。
                    this.orders = data.map((order) => {
                        return {
                            oid:order.oid,
                            ordertime:order.ordertime,
                            status:order.status,
                            totalprice:order.totalprice,
                        };
                    });
                })
            },
            // 获取订单详情
            showOrder(oid){
                this.orderDetailVisible = true;
                console.log(oid,'selected');
                this.axios.get("http://localhost:8902/orderdetail/getall").then((resp)=>{
                    let data = resp.data;
                    console.log(data);
                    // 筛选出与被选中订单匹配的订单详情项
                    data = data.filter((item) => item.oid === oid);
                    console.log(data,'筛选的详情')

                    // 获取所有订单详情项对应的商品详细信息
                    // let promises = selectedOrderItems.map((item) => {
                    //     return this.axios.get(`http://localhost:8902/goods/detail?gid=${item.gid}`);
                    // });
                    //
                    //将筛选出的订单详情项存储到selectOrder中
                    // debugger
                    this.selectedOrder = data;
                    console.log(this.selectedOrder,'this.select')
                })

            },
            //关闭详情页
            close(){
                this.orderDetailVisible = false
            }
        }
    }
</script>

<style lang="scss" scoped>
    /* 铺满整个页面 */
    .center{
        height: 100vh;
        width: 100%;
    }
    /* 用户信息 */
    .user{
        height: 350px;
        margin: 10px 10px 10px 0;
        h2{
            margin-bottom: 50px;
        }
        .userInfo{
            width: 400px;
            margin: auto;
        }
    }
    .user-card{
        border: 1px solid #ccc;
        font-size: 16px;
        font-family: Arial, sans-serRif;
        padding: 20px;
        margin: 0 30px 30px;
        .el-button{
            margin: 0 10px;
        }
    }
    /deep/ .el-form-item__content {
        line-height: 40px;
        font-size: 14px;
        display: flex;
    }

        /* 宠物信息 */
    .pet{
        height: 350px;
        margin: 10px 0 10px 10px;
    }
    .petHead{
        display: flex;
        justify-content: space-between;
        align-items: center;
        h2{
            margin-left: 208px;
        }
    }
    .pet-wrapper{
        width: 100%;
        height: 241px;
        /*滑动条样式*/
        overflow-y: auto;
    }
    /* 隐藏滚动条 */
    .pet-wrapper::-webkit-scrollbar {
        width: 0;
    }
    /* 宠物卡和用户卡 */
    .pet-details{
        border: 1px solid #ccc;
        font-size: 16px;
        font-family: Arial, sans-serif;
        padding: 20px;
        margin: 0 30px 30px;
    }

</style>

