<template>
    <div class="loginbody">
        <div class="logindata">
            <div class="logintext">
                <h2>Hello Friend</h2>
            </div>
            <div class="formdata">
                <!--rules：表单验证功能-->
                <!--v-model：用于双向数据绑定，model：父组件的值传给子组件，单向绑定值-->
                <el-form ref="loginform" :model="LoginForm" :rules="rules">
                    <!--form-item组件：账号输入框-->
                    <el-form-item prop="account">
                        <!--表单输入控件，v-model.number：数字类型的验证-->
                        <el-input
                                v-model="LoginForm.account"
                                clearable
                                placeholder="请输入账号"
                        ></el-input>
                    </el-form-item>
                    <!--密码输入框-->
                    <el-form-item prop="password">
                        <el-input
                                v-model="LoginForm.password"
                                clearable
                                placeholder="请输入密码"
                                show-password
                        ></el-input>
                    </el-form-item >
                    <el-form-item prop="role">
                        <el-radio-group v-model="LoginForm.role">
                            <el-radio label="member">用户</el-radio>
                            <el-radio label="admin">管理员</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-form>
            </div>
            <div class="tool">
                <div>
                    <!--checkbox:选择框-->
                    <el-checkbox v-model="checked" @change="remember">
                        记住密码
                    </el-checkbox>
                </div>
                <div>
                    <!--@click：绑定点击事件-->
                    <span class="show" @click="forgetpas">
                        忘记密码？
                    </span>
                </div>
            </div>
            <div class="butt">
                <!--@click.native.prevent:不加native会被认为是要监听item里的自定义事件，prevent阻止默认事件-->
                <el-button type="primary" @click="login()">
                    登录
                </el-button>
                <!--点击就转跳到自定义事件“register”-->
                <el-button class="show" @click="register">注册</el-button>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "login",
        data() {
            return {
                //绑定了上面的model
                LoginForm: {
                    account: "",
                    password: "",
                    role:"",
                },
                radio: 'member',
                //选择框默认不选，也可以改为true默认选中
                checked: false,
                //表单验证规则
                rules: {
                    //验证用户名，密码是否合法
                    //required：是否为必填项，message：验证不通过时显示的信息
                    //max：数值或长度范围， trigger：blur：失去焦点时触发
                    account: [
                        { required: true, message: "请输入账号", trigger: "blur" },
                        { max:10, message: "不能大于10个字符", trigger: "blur" },
                    ],
                    password: [
                        { required: true, message: "请输入密码", trigger: "blur" },
                        { max: 10, message: "不能大于10个字符", trigger: "blur" },
                    ],
                },
            };
        },
        //钩子函数：在模板渲染成html后调用，对html的dom节点进行需要的操作
        mounted() {
            //若能获取到本地存储的news数据，就直接将本地news赋给LoginForm
            if(localStorage.getItem("news")){   // localStorage.getItem：获取news本地存储的值
                this.LoginForm=JSON.parse(localStorage.getItem("news"));
                this.checked=true
            }
        },
        //用户事件：可以通过父组件传给子组件的方法
        methods: {
            //登录
            login(){
                this.axios.post('http://localhost:8902/user/login/',this.LoginForm).then((resp) =>{
                    let data = resp.data;
                    console.log(data,"返回结果")
                    if (data.success) {
                        this.$message({
                            message: "登录成功啦",
                            type: "success",
                            showClose: true,//关闭对话框
                        });

                        //登录成功后,用户转跳到首页，管理员转跳到后台
                        // push会向history栈中添加一个新的记录，点击回退会返回值上一个页面，replace会替换history栈中最后一个记录，点击回退会返回值上上一个页面
                        // console.log(resp);
                        //将用户昵称，身份存储到本地，登录之后就可以用getItem获取存储的数据
                        localStorage.setItem('user',JSON.stringify(data.content));

                        if (data.content.role == "admin") {
                            this.$router.replace("/dashboard")
                          } else {
                            this.$router.replace("/dash")
                        }
                    } else {
                        this.$message({
                            message: "账户名不存在或密码错误",
                            type: "error",
                            showClose: true,
                        });
                    }
                })
            },
            //记住密码
            remember(data){
                this.checked=data;
                if(this.checked){
                    //选择了记住密码就将LoginForm里面的信息写入news里面存储到本地
                    localStorage.setItem("news",JSON.stringify(this.LoginForm));
                }else{
                    //没有选就移除本地存储的news
                    localStorage.removeItem("news")
                }
            },
            //忘记密码
            forgetpas() {
                this.$message({
                    type:"info",
                    message:"功能尚未开发额😥",
                    showClose:true
                })
            },
            //注册
            register() {
                this.$router.push("register")
            },
        },
    };
</script>

<style scoped>
    .loginbody {
        width: 100%;
        height: 100%;
        min-width: 1000px;
        background-image: url("../assets/login2.jpg");
        opacity:0.8;
        background-size: 100% 100%;
        background-position: center center;
        overflow: auto;
        background-repeat: no-repeat;
        position: fixed;
        line-height: 100%;
        padding-top: 150px;
    }

    .logintext {
        margin-bottom: 20px;
        line-height: 50px;
        text-align: center;
        font-size: 30px;
        font-weight: bolder;
        color: white;
        text-shadow: 2px 2px 4px #000000;
    }

    .logindata {
        width: 400px;
        height: 300px;
        transform: translate(-50%);
        margin-left: 50%;
    }

    .tool {
        display: flex;
        justify-content: space-between;
        color: #606266;
    }

    .butt {
        margin-top: 10px;
        text-align: center;
    }

    .show {
        cursor: pointer;
        color: #606266;
    }

    /*ui*/
    /* /deep/ .el-form-item__label {
      font-weight: bolder;
      font-size: 15px;
      text-align: left;
    }

    /deep/ .el-button {
      width: 100%;
      margin-bottom: 10px;

    } */
</style>
