<template>
  <div id="app">
    <!--显示路由匹配到的组件-->
    <router-view/>
    <!--router-link 实现路由之间的跳转-->
    <!--router-view 当你的路由path 与访问的地址相符时，会将指定的组件替换该 router-view-->
  </div>
</template>

<!--App可以看做整个vue的一个显示平台，其他的vue文件都是组件，
    究竟会显示出来什么，就看router-view匹配到了什么组件（也就是router-view被什么组件替换）-->
<!--所以在这个页面写的东西会显示在每一个子页面中-->
<style lang="less">

  /*让所有的代码占满全屏*/
  html,body,#app,el-container{
    padding: 0px;
    margin: 0px;
    height: 100%;
  }

  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  nav {
    padding: 30px;

    a {
      font-weight: bold;
      color: #2c3e50;

      &.router-link-exact-active {
        color: #42b983;
      }
    }
  }
</style>
