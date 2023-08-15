<template>
    <div class="registerbody">
        <div class="registerdata">
            <div class="registertext">
                <h2>Create Account</h2>
            </div>
            <div class="formdata">
            <!--账号，密码，密码确认，用户名，性别，电话，地址-->
            <el-form :model="registerForm" status-icon :rules="rules" ref="registerForm">
                <el-form-item prop="account">
                    <el-input
                            v-model="registerForm.account"
                            clearable
                            placeholder="请输入账号"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                            type="password"
                            v-model="registerForm.password"
                            show-password
                            clearable
                            placeholder="请输入密码"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="checkPass">
                    <el-input
                            type="password"
                            v-model="registerForm.checkPass"
                            autocomplete="off"
                            clearable
                            placeholder="请确认密码"
                    ></el-input>
                </el-form-item>

                <el-form-item prop="screenname">
                    <el-input
                            v-model.number="registerForm.screenname"
                            autocomplete="off"
                            clearable
                            placeholder="请输入用户名"
                    ></el-input>
                </el-form-item>

                <!--<el-form-item prop="sex">-->
                    <!--<el-select v-model="registerForm.sex" clearable placeholder="请选择性别">-->
                        <!--<el-option label="男" value="男"></el-option>-->
                        <!--<el-option label="女" value="女"></el-option>-->
                    <!--</el-select>-->
                <!--</el-form-item>-->

                <el-form-item prop="phone">
                    <el-input
                            v-model.number="registerForm.phone"
                            clearable
                            placeholder="请输入电话"
                    ></el-input>
                </el-form-item>
            </el-form>
            </div></div>
            <div class="butt">
                <el-button @click="resetForm()">重置</el-button>
                <el-button type="primary" @click="submitForm()">提交</el-button>
                <el-button @click="backForm()">返回</el-button>
            </div>
    </div>
</template>


<script>
    export default {
        name: "register",
        data() {
            //自定义校验函数
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.registerForm.checkPass !== '') {
                        this.$refs.registerForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.registerForm.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };


            return {
                registerForm: {
                    account:'',
                    password: '',
                    screenname:'',
                    phone:''
                },
                rules: {
                    account: [
                        { required:true, message:' 请输入账号' ,trigger: 'blur' },
                        { pattern: /^\d{4,8}$/,message:"账号只能由4~8位数字组成",trigger:'blur'}
                    ],
                    password: [
                        { validator: validatePass, trigger: 'blur' },
                        { min :6 ,message:"密码至少六位",trigger:'blur'}
                    ],
                    checkPass: [
                        { validator: validatePass2, trigger: 'blur' }
                    ],
                    screenname: [
                        { required: true ,message:"请输入用户名", trigger: 'blur' },
                        { max: 10, message: "不能大于十个字符", trigger: "blur" },
                    ],
                    phone: [
                        { required: true ,message:"请输入手机号", trigger: 'blur' },
                        { pattern: /^1[3456789]\d{9}$/, message: '手机号格式不正确', trigger: 'blur'  },
                    ],
                }
            };
        },
        methods: {
            submitForm() {
                this.axios.post('http://localhost:8902/user/register/',this.registerForm).then((resp) =>{
                    let data = resp.data;
                    console.log(data,"返回结果");
                    if (data.success) {
                        this.$message({
                            message: "注册成功啦",
                            type: "success",
                            showClose: true,//关闭对话框
                        });
                        console.log(resp);
                        this.$router.push("/")
                    } else {
                        this.$message({
                            message: "注册失败，该账号已存在",
                            type: "error",
                            showClose: true,
                        });
                    }
                })
            },
            resetForm() {
                this.$refs.registerForm.resetFields();
            },
            backForm() {
                this.$router.push('/')
            }
        }
    }
</script>

<style scoped>
    .registerbody {
        width: 100%;
        height: 100%;
        min-width: 1000px;
        background-image: url("../assets/login3.jpg");
        opacity: 0.8;
        background-size: 100% 100%;
        background-position: center center;
        overflow: auto;
        background-repeat: no-repeat;
        position: fixed;
        line-height: 100%;
        padding-top: 150px;
    }

    .registertext {
        margin-bottom: 20px;
        line-height: 50px;
        text-align: center;
        font-size: 30px;
        font-weight: bolder;
        color: white;
        text-shadow: 2px 2px 4px #000000;
    }

    .registerdata {
        width: 400px;
        height: 300px;
        transform: translate(-50%);
        margin-left: 50%;
    }

    .butt {
        margin-top: 80px;
        text-align: center;
    }

    /*ui*/
    /*/deep/ .el-select {*/
        /*display: inline-block;*/
        /*position: relative;*/
        /*width: 400px;*/
    /*}*/

    /deep/ .el-button {
      margin: 30px;
    }
</style>
