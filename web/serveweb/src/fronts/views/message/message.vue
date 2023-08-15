<!--用户留言板-->
<template>
    <el-card class="el-card-d" shadow="always">
        <div class="infinite-list-wrapper" style="overflow:auto;">
            <!-- 时间轴 ：无限滚动-->
            <el-timeline infinite-scroll-disabled="disabled">

                <div>
                    <!-- 时间轴节点 -->
                    <el-timeline-item v-for="(item,index) in messages"  :key="index" :timestamp='item.addtime' placement="top">
                        <!-- 时间轴的每一张小卡篇 -->
                        <el-card class="el-card-m">
                            <!-- 用户留言 -->
                            <div class="member" style="margin-bottom: 20px">
                                <!--<h4>{{item.uid}}：</h4>-->
                                <h4>{{  item.screenname }}：</h4>
                                <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{item.content}}</p>
                            </div>
                            <!-- 管理员回复 -->
                            <div class="admin" v-if="item.replycontent">
                                <span>{{item.replytime}}</span>
                                <h4 style="padding: 3px 0">&nbsp;&nbsp;&nbsp;管理员回复：</h4>
                                <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{item.replycontent}}</p>
                            </div>
                        </el-card>

                    </el-timeline-item>
                </div>

            </el-timeline>

        </div>

        <div class="el-card-messages">
            <el-input type="textarea" :rows="5" placeholder="输入留言" maxlength="200" v-model="form.content"></el-input>
            <el-button type="info" round class="submit-message" @click='submitForm()'>留言</el-button>
        </div>


    </el-card>
</template>

<script>
    export default {
        name:'boards',

        data() {
            return {
                //留言
                messages: [],
                form:{
                    content:'',
                    uid:'',
                    addtime:'',
                    screenname:''
                },
            }
        },
        //初始化调用
        mounted() {
            this.getList();
        },
        methods: {
            //在不增加数据库字段的情况下，将message.uid替换为对应的用户昵称了
            getList(){
                const messageUrl = 'http://localhost:8902/message/getall';
                const userUrl = 'http://localhost:8902/user/getall';
                const requests = [this.axios.get(messageUrl), this.axios.get(userUrl)];
                //使用axios的all方法同时发送请求
                this.axios.all(requests).then(this.axios.spread((...responses)=>{
                    const messageResp = responses[0];
                    const userResp = responses[1];
                    console.log(messageResp, userResp);
                    const messageData = messageResp.data;
                    const userData = userResp.data;
                    this.messages = [];
                    for(let msg of messageData){
                        const uid = msg.uid;
                        const user = userData.find(u=>u.uid===uid);//查找对应的user信息
                        msg.screenname = user ? user.screenname : '';
                        this.messages.push(msg);
                    }
                })).catch((error)=>{
                    console.log(error);
                });
            },


            //提交留言到数据库
            submitForm(){
                this.form.addtime = new Date().toJSON();
                this.form.uid = JSON.parse(localStorage.getItem('user')).uid;
                // console.log(this.form.screenname,'name');
                console.log(this.form.uid,'uid');
                //怎么获取用户id？
                this.axios.post("http://localhost:8902/message/save",this.form).then((resp)=>{
                    let data = resp.data;
                    if(data.success){
                        //清空表单
                        this.form = {};
                        //查询提交的数据
                        this.getList();
                        this.$message({
                            message:'留言成功！',
                            type: 'success',
                            center: true
                        })
                    }
                })
            }

        }
    }
</script>

<style lang="scss" scoped>
    .el-card-d{
        float: left;
        margin-top: 20px;
        margin-left: 10%;
        width: 80%;
        height: 95%;
    }
    .infinite-list-wrapper{
        width: 100%;
        height: 520px;
    }
    /*留言按钮*/
    .submit-message{
        width: 100%;
        background: rgb(235, 245, 247);
        color: cadetblue;
        letter-spacing:20px;
    }
    .el-card-m{
        .admin{
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            align-items: baseline;
            padding: 10px 20px;
            text-align: left;
            background-color: rgba(195, 217, 242, 0.35);
            border-radius: 10px;
            span{
                padding: 3px 0;
                color: #999999;
                font-size: 14px;

            }
        }
        .member{
            min-height: 60px;
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            align-items: baseline;
            padding: 0 20px;
            text-align: left;
        }
    }

    /deep/ .el-timeline-item__timestamp.is-top {
        margin-bottom: 8px;
        padding-top: 4px;
        display: flex;
        font-size: 15px;
    }

</style>


