import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'


//登录
import Login from '../views/Login.vue'
//注册
import Register from '../views/Register.vue'

import Manage from '../views/Manage.vue'
//后台首页
import dashboard from '../views/dashboard/index.vue'
/**
 * 系统管理
 */
//用户列表
import member from '../views/system/member.vue'
//宠物列表
import pet from '../views/system/pet.vue'
/**
 * 商品管理
 */
import goods from '../views/goods/goods.vue'
/**
 * 商品分类管理
 */
import cate from '../views/goods/Cate.vue'
/**
 * 订单管理
 */
//预约订单
import booking from '../views/order/booking'
//商品交易
import trade from '../views/order/trade'
/**
 * 留言管理
 */
//留言管理
import Comments from '../views/comments/comments'





//前台
import homePage from '../fronts/layout'
//前台首页
import dash from '../fronts/views/dashboard/dash'


//购物车
import Cart from '../fronts/views/Cart/cart'
//商品详情
import detail from '../fronts/views/buy/detail'
//个人中心
import person from '../fronts/views/person/percenter'
//留言板
import boards from '../fronts/views/message/message'
//搜索结果
import SearchResult from "../fronts/components/SearchResult.vue";
//查看更多
import ProductsPage from '../fronts/views/seeMore/ProuctsPage'




//启用路由
Vue.use(VueRouter)

const routes = [
    {
        path: '/',//默认路径
        name: 'login',
        // 允许同步加载组件
        component: Login
    },
    {
        path: '/register',
        name: 'register',
        component: Register
    },
    {
        path: '/home',
        name: 'home',
        component: HomeView
    },
    {
        path: '/about',
        name: 'about',
        // 使用该函数延迟加载主键，在使用时加载，减少程序的初始加载时间
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    },


//  延迟加载组件和同步加载组件从结果上作用上来讲是一样的
//    延迟加载的优势在于通过仅加载实际需要的组件，提高应用程序的性能并减少其消耗的内存量
    {
        path: '/manage',
        name: 'manage',
        component: Manage,
        children:[
            {
                path: '/dashboard',
                name: 'dashboard',
                component: dashboard
            },{
                path: '/system/member',
                name: 'member',
                component: member
            },
            {
                path: '/system/pet',
                name: 'pet',
                component: pet
            }, {
                path: '/goods/goods',
                name: 'goods',
                component: goods
            }, {
                path: '/goods/category',
                name: 'cate',
                component: cate,
            }, {
                path: '/order/booking',
                name: 'booking',
                component: booking
            }, {
                path: '/order/trade',
                name: 'trade',
                component: trade
            },{
                path: '/comments/comments',
                name: 'comments',
                component: Comments
            },
        ]
    },

//前台
    {
        path: '/homePage',
        name: 'homePage',
        component: homePage,
        children:[
            {
                path: '/dash',
                name: 'dash',
                component: dash,
                children:[

                ]
            },
        ]
    },
    //商品详情：动态路由，根据传来的商品显示不同的商品信息
    {
        path: '/detail/:id',
        name: 'detail',
        component: detail
    },
    //购物车
    {
        path: '/cart',
        name: 'cart',
        component: Cart
    },
    //个人中心
    {
        path: '/person',
        name: 'person',
        component: person
    },
    //留言板
    {
        path: '/boards',
        name: 'boards',
        component: boards
    },
    //搜索结果
    {
        path: '/search/:keyword',
        name: 'search',
        component: SearchResult
    },
    //查看更多
    {
        path: '/category/:catename',
        name: 'ProductsPage',
        component: ProductsPage,
        props: true
    },

]

const router = new VueRouter({
  routes
})

export default router
