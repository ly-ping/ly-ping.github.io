<!--首页-->

<template>
    <div class="dashboard-container">
        <!--:gutter:单元格左右padding之和-->
        <el-row :gutter="20" class="row-box">
            <!--管理员信息卡片-->
            <el-col :md="24" :lg="16">
                <el-card class="box-card">
                    <div class="user">
                        <img src="../../assets/picture1.png" alt="">
                        <div class="userinfo">
                            <p class="screenname">你好！{{screenname}}<br>心里种花，人生才不会荒芜</p>
                            <p class="role">{{role}}</p>
                        </div>
                    </div>

                    <div class="clock">
                        <p>当前时间:</p>
                        <span class="times">{{ date }}</span>
                    </div>
                </el-card>
            </el-col>
            <!--玫瑰图？-->
            <el-col :md="24" :lg="8">
                <el-card>
                   <div id="petChart"></div>
                </el-card>
            </el-col>
        </el-row>

        <el-row :gutter="20">
            <!--用户数量-->
            <el-col :sm="24" :md="12" :lg="6">
                <el-card class="counter">
                    <div class="header">
                        <p>用户总数</p>
                    </div>
                    <div class="count">
                        <span>{{userCount}}</span>
                    </div>
                </el-card>
            </el-col>
            <!--登记宠物数量-->
            <el-col :sm="24" :md="12" :lg="6">
                <el-card class="counter">
                    <div class="header">
                        <p>宠物总数</p>
                    </div>
                    <div class="count">
                        <span>{{petCount}}</span>
                    </div>
                </el-card>
            </el-col>
            <!--留言数量-->
            <el-col :sm="24" :md="12" :lg="6">
                <el-card class="counter">
                    <div class="header">
                        <p>留言总数</p>
                    </div>
                    <div class="count">
                        <span>{{petCount}}</span>
                    </div>
                </el-card>
            </el-col>
            <!--商品总数-->
            <el-col :sm="24" :md="12" :lg="6">
                <el-card class="counter">
                    <div class="header">
                        <p>商品总数</p>
                    </div>
                    <div class="count">
                        <span>{{goodsCount}}</span>
                    </div>
                </el-card>
            </el-col>

        </el-row>
    </div>
</template>

<script>

    import * as echarts from 'echarts'

    export default {
        name: 'Dashboard',

        data() {
            return {
                //实时时间组件
                date:new Date(),
                //计数
                userCount:0,        //用户总数
                petCount:0,         //宠物总数
                messageCount:0,     //留言总数
                goodsCount:0,       //商品总数

                //宠物数据
                petData:[],
                //每个品种的数量
                petsCounts:[]
            };
        },
        created(){

        },
        mounted(){
            //时间组件
            let that= this;
            this.timer = setInterval(function() {
                that.date = new Date().toLocaleString();
            });
            //统计数量
            this.getUser();
            this.getPet();
            this.getMessage();
            this.getGoods();

            //初始化图表
            // let myChart = echarts.init(document.querySelector('#petChart'))

            this.fetchData();
        },

        methods:{
            //统计数量
            getUser() {
                this.axios.get('http://localhost:8902/user/getall',).then((resp) => {
                    this.userCount=resp.data.length;
                })},
            //宠物数
            getPet() {
                this.axios.get('http://localhost:8902/pet/getall',).then((resp) => {
                    this.petCount=resp.data.length;
                })},
            //留言数
            getMessage() {
                this.axios.get('http://localhost:8902/message/getall',).then((resp) => {
                    this.messageCount=resp.data.length;
                })},
            //商品数
            getGoods() {
                this.axios.get('http://localhost:8902/goods/getall',).then((resp) => {
                    this.goodsCount=resp.data.length;
                })},


            //echart
            fetchData() {
                this.axios.get("http://localhost:8902/pet/getall").then((resp) => {
                        this.petData = resp.data;
                        // 计算每个品种的数量
                        const breeds = {};
                        for (const pet of this.petData) {
                            if (breeds[pet.breed]) {
                                breeds[pet.breed] += 1
                            } else {
                                breeds[pet.breed] = 1
                            }
                        }
                        this.petsCounts = Object.entries(breeds).map(([breed, count]) => ({ breed, count }))
                        this.renderChart()
                    })
                    .catch((error) => {
                        console.log(error);
                    });
                },
            renderChart() {
                const petChart = echarts.init(document.getElementById('petChart'));
                // 用 this.petData 渲染图表
                const data = this.petsCounts.map((item) => ({ value: item.count, name: item.breed }));
                //指定图表配置
                const option = {
                    title:{
                        text:"宠物种类分布",
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}: {c} ({d}%)'
                    },
                    legend: {
                        top: 'bottom',
                        left: 'center'
                    },
                    series: [
                        {
                            name: '宠物品种',
                            type: 'pie',
                            radius: [40,100],
                            center:['50%','50%'],
                            avoidLabelOverlap: false,
                            itemStyle: {
                                borderRadius: 5,
                                borderColor: '#fff',
                                borderWidth: 2
                            },
                            label: {
                                show: false,
                                position: 'center',
                                formatter: '{b}: {d}%'
                            },
                            emphasis: {
                                label: {
                                    show: true,
                                    fontSize: 40,
                                    fontWeight: 'bold'
                                },
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            },
                            labelLine: {
                                show: false
                            },
                            data,
                        }
                    ]
                };
                //对实例对象设置配置
                petChart.setOption(option);
            },

            },
        //监视
        computed:{
            screenname(){
                return  JSON.parse(localStorage.getItem('user')).screenname;
            },
            role(){
                return  JSON.parse(localStorage.getItem('user')).role;
            },
        },


        beforeDestroy: function() {
            if (this.timer) {
                clearInterval(this.timer);
            }
        },
    }
</script>


<style lang="scss" scoped>
    .dashboard-container{
        margin: 10px;
    }
    .el-card{
        margin: 10px 0;
        height: 100%;
    }

    /*用户信息*/
    .user{
        padding: 20px;
        margin-bottom: 20px;
        border-bottom: 1px solid #999;
        display: flex;
        align-items: center;
        img{
            margin-left: 30px;
            height: 120px;
            width: 120px;
            border-radius: 50%;
        }
        .userinfo{
            margin-left: 40px;
            .screenname{
                font-weight: 600;
                font-size: 35px;
                margin-bottom: 5px;
            }
            .role{
                color: #555555;
            }
        }
    }
    /*实时时间*/
    .clock{
        p{
            font-size: 14px;
            align-items: center;
            color: #999999;
            line-height: 28px;
            margin-bottom: 5px;
        }
        .times{
            font-size: 25px;
            color: #333;
            letter-spacing: 2px;
        }
    }

    /*四个小盒子标题文字*/
    .header{
        border-bottom: 1px solid #999;
        text-align: left;
        p{
            margin-top: 0;
            margin-bottom: 10px;
        }
    }
    /*计数数字*/
    .count{
        margin-top: 30px;
        margin-bottom: 30px;
        span{
            font-size: 25px;
        }
    }


    /*图表*/
    #petChart{
        height: 253.6px;
    }
    
</style>