<!--购物车-->
<template>
    <div class="cart">
        <!--继续购物按钮，直接返回商城主页-->
        <div class="back">
            <el-page-header @back="returnPage" content="购物车">
            </el-page-header>
        </div>


        <div class="container">
            <!--收货地址-->
            <div style="border-bottom: 1px solid;margin-bottom: 30px">
                <el-form :model="receipt" :rules="rules" ref="receiptForm" class="receipt">
                    <h1 style="padding-bottom: 10px">收货信息</h1>
                    <el-form-item label="收货人：" prop="name" label-width="100px">
                        <el-input v-model="receipt.screenname" :disabled="!editMode"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号码：" prop="phone" label-width="100px">
                        <el-input v-model="receipt.phone" :disabled="!editMode"></el-input>
                    </el-form-item>
                    <el-form-item label="收货地址：" prop="address" label-width="100px">
                        <el-input v-model="receipt.address" :disabled="!editMode"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button v-if="!editMode" type="primary" @click="handleEdit">修改收货信息</el-button>
                        <el-button v-if="editMode" @click="handleSave" type="primary">保存</el-button>
                    </el-form-item>
                </el-form>
            </div>


            <el-table :data="Cart" style="width: 100%" border>
                <el-table-column prop="id" label="商品id"></el-table-column>
                <el-table-column prop="name" label="商品名称"></el-table-column>
                <el-table-column prop="price" label="单价"></el-table-column>
                <el-table-column prop="num" label="数量">
                    <template slot-scope="scope">
                        <el-input-number v-model="scope.row.num" @change="updateTotal(scope.row)" :min="1"></el-input-number>
                    </template>
                </el-table-column>

                <el-table-column prop="total" label="总价"></el-table-column>

                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="danger" size="small" @click="removeFromCart(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>

            </el-table>

            <div class="total-price">总价：{{ totalPrice }}</div>
            <div class="actions">
                <el-button type="primary" @click="checkout">结算</el-button>
                <el-button style="margin-left: 10px" @click="clearCart">清空购物车</el-button>
            </div>
        </div>

        <!--点击结算后显示收件人信息-->
        <el-dialog :visible.sync="dialogVisible" title="订单详情">
            <div class="order-detail">
                <el-descriptions direction="vertical"  :column="3" border>
                    <el-descriptions-item label="订单号">{{ order.oid }}</el-descriptions-item>
                    <el-descriptions-item label="下单时间">{{ order.ordertime }}</el-descriptions-item>
                    <el-descriptions-item label="收货人">{{ order.screenname }}</el-descriptions-item>
                    <el-descriptions-item label="手机号码">{{ order.phone }}</el-descriptions-item>
                    <el-descriptions-item label="收货地址">{{ order.address }}</el-descriptions-item>
                    <el-descriptions-item label="订单状态">{{ order.status }}</el-descriptions-item>
                </el-descriptions>
                <el-table :data="order.orderItems" style="width: 100%; margin-top: 20px;" border>
                    <el-table-column prop="oid" label="订单编号"></el-table-column>
                    <el-table-column prop="gid" label="商品编号"></el-table-column>
                    <el-table-column prop="gname" label="商品名"></el-table-column>
                    <el-table-column prop="price" label="商品单价"></el-table-column>
                    <el-table-column prop="quantity" label="数量"></el-table-column>
                    <el-table-column prop="total" label="总价"></el-table-column>
                </el-table>
                <div class="order-amount">订单总金额：{{ order.totalprice }}</div>
            </div>
            <div slot="footer" v-if="order.status !== '已完成'">
                <el-button type="primary" @click="close">确定</el-button>
                <el-button @click="cancelOrder">取消订单</el-button>
            </div>
        </el-dialog>

    </div>
</template>
<script>
    export default {
        name: 'cart',
        data() {
            return {
                receipt: {}, // 收货信息
                rules: { // 表单验证规则
                    screenname: [
                        { required: true, message: '用户名不能为空', trigger: 'blur' }
                    ],
                    phone: [
                        { required: true, message: '用户电话不能为空', trigger: 'blur' },
                        { pattern: /^[0-9]*$/, message: '用户电话必须为数字', trigger: 'blur' }
                    ],
                    address: [
                        { required: true, message: '收货地址不能为空', trigger: 'blur' }
                    ]
                },
                editMode: false ,// 是否为编辑模式


                Cart: [], // 购物车
                num: 1,
                dialogVisible: false, // 订单详情对话框是否可见
                order: {
                    oid: '',//订单编号
                    uid: '',//下单用户id
                    screenname: '',
                    phone: '',
                    address: '',
                    ordertime: '',
                    orderItems: [],
                    totalprice: '',
                    status: ''
                }
            };
        },
        computed: {
            //计算购物车总价
            totalPrice() {
                console.log(this.Cart, 'Cart');
                // 使用数组的 reduce 方法对所有商品的 total 进行累加,注意小数的计算要加上parseFloat和toFixed
                return this.Cart.reduce((sum, item) => sum + parseFloat(item.total), 0).toFixed(2);
            },
        },
        created() {
            this.Cart = JSON.parse(localStorage.getItem("shopcart"));
        },
        mounted(){
            this.getUser();
        },
        methods: {
            // 返回上一级
            returnPage() {
                if (window.history.length <= 1) {
                    this.$router.push({path: "/dash"});
                    return false;
                } else {
                    this.$router.go(-1);
                }
            },
            //获取用户收货信息
            getUser(){
                this.axios.get('http://localhost:8902/user/getall').then((resp)=>{
                    let data = resp.data;
                    //若直接用this.Form获取过滤的结果，得到是含有单个user对象的数组，而不是纯JavaScript对象（即不是数组）
                    let item = data.filter((user) => user.uid === JSON.parse(localStorage.getItem('user')).uid);
                    if (item.length > 0) {
                        this. receipt= {...item[0]};
                        console.log(this.receipt,'收货信息')
                    }
                })
            },
            // 进入地址编辑模式
            handleEdit() {
                this.editMode = true;
            },
            // 保存收货地址信息
            handleSave(){
                this.editMode = false;
            },


            //移除购物车商品
            removeFromCart(index) {
                let cart = JSON.parse(localStorage.getItem('shopcart') || '[]');
                for (let i = 0; i < cart.length; i++) {
                    if (cart[i].id === this.Cart[index].id) {
                        cart.splice(i, 1); // 移除购物车中的商品
                        break;
                    }
                }
                localStorage.setItem('shopcart', JSON.stringify(cart));
                this.Cart = JSON.parse(localStorage.getItem("shopcart"));
                this.$message.success('商品已从购物车中删除');
            },
            //清空购物车
            clearCart() {
                this.Cart = [];
                localStorage.removeItem('shopcart');
            },
            // 更新商品数量和小计
            updateTotal(item) {
                item.total = (item.num * item.price).toFixed(2);
                localStorage.setItem('shopcart', JSON.stringify(this.Cart));//保存到本地
            },
            // 结算
            checkout() {
                // 判断收货地址是否为空
                if (!this.receipt.screenname || !this.receipt.phone || !this.receipt.address) {
                    this.$message.error('请完善收货信息');
                    return;
                }

                this.$confirm('核对要购买的商品以及收货信息是否正确', '确认购买', {
                    confirmButtonText: '继续购买',
                    cancelButtonText: '返回确认',
                    type: 'warning'
                }).then(() => {
                    this.dialogVisible = true;
                    // 遍历购物车中的商品，生成订单项
                    const orderItems = [];
                    for (let i = 0; i < this.Cart.length; i++) {
                        orderItems.push({
                            oid: Date.now().toString(),// 生成订单编号
                            gid: this.Cart[i].id,
                            gname: this.Cart[i].name,
                            price: this.Cart[i].price,
                            quantity: this.Cart[i].num,
                            total: this.Cart[i].total,
                        });
                    }
                    //前端生成订单
                    this.order = {
                        oid: Date.now().toString(),// 生成订单编号
                        uid: JSON.parse(localStorage.getItem('user')).uid,// 下单用户id
                        screenname: this.receipt.screenname,
                        phone: this.receipt.phone,
                        address: this.receipt.address,
                        ordertime: new Date().toJSON(),// 下单时间
                        orderItems:orderItems,
                        totalprice: this.totalPrice,
                        status: '已付款', // 订单状态，初始化为"未完成"
                    };
                    console.log(this.order,'this.order');
                    // debugger
                    // 存入order表
                    this.axios.post('http://localhost:8902/order/save/', this.order).then((res) => {
                        console.log(res.data, "订单");
                        // 将订单项存入orderdetail表，同时关联到order表
                        const promises = orderItems.map((item) => {
                            item.oid = this.order.oid;
                            return this.axios.post('http://localhost:8902/orderdetail/save', item);
                        });
                        // 等待所有 promise 处理完成
                        Promise.all(promises).then(() => {
                            this.$message.success(`结算成功，总价：${this.totalPrice}元`);
                            this.Cart = []; // 清空购物车
                            localStorage.removeItem('shopcart');
                        }).catch((error) => {
                            console.error(error);
                            this.$message.error('结算失败');
                        });
                    })
                }).catch(() => {
                    // 用户点击“返回”按钮或者弹窗关闭后执行的逻辑
                    // 可以不做任何操作，或者做一些清理工作
                });
            },
            close(){
                this.dialogVisible = false
            },
            cancelOrder(){
                console.log('取消订单')
            }


        }
    };
</script>
<style lang="scss" scoped>

    .cart {
        /*margin: 50px;*/

    }

    .container {
        margin-top: 30px;
    }

    .back {
        width: 100%;
        .el-page-header {
            line-height: 50px;
        }
    }
    .receipt{
        /*border: 1px solid #242424;*/
        align-items: stretch;
    }
    .total-price {
        font-size: 20px;
        font-weight: bold;
        margin-top: 20px;
    }

    .actions {
        margin-top: 20px;
    }
    /*订单金额文字*/
    .order-amount{
        margin-top: 10px;
        padding: 5px;
        background: #c3d9f2;
        border: 2px solid #d5d5d5;
        border-radius: 5px;
        font-size: 25px;
    }
    /*测试*/
    div {
        /*border: 1px solid #1DC779;*/
    }
</style>
