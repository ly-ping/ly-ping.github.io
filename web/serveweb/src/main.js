import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

//引入elementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

//引入axios
import axios from 'axios'
import VueAxios from 'vue-axios'
// VueAxios 与 axios 的位置不能交换，否则出现 TypeError: Cannot read property 'protocol' of undefined
Vue.use( VueAxios , axios);

//引入树形控件
import TreeTable from 'vue-table-with-tree-grid'
//全局注册
Vue.component('tree-table',TreeTable);




Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
