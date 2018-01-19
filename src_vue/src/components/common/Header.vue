<template>
    <div class="header">
        <div class="logo">后台管理系统</div>
        <div class="user-info">
            <!--<el-dropdown trigger="click" @command="handleCommand">
                <span class="el-dropdown-link">
                    <img class="user-logo" src="../../../static/img/img.jpg">
                    [超级系统管理员]
                    {{username}}
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="loginout">退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>-->
            <el-menu :default-active="activeIndex2"  class="el-menu-demo comtext" mode="horizontal" @select="handleSelect"  background-color="#242F42" text-color="#fff" active-text-color="#fff">
			  <!--<el-menu-item index="1">处理中心</el-menu-item>-->
			  <el-submenu index="2">
			    <template slot="title">[超级管理员]:Admin</template>
			    <el-menu-item index="2-1">选项1</el-menu-item>
			    <el-menu-item index="2-2">选项2</el-menu-item>
			    <el-menu-item index="2-3">选项3</el-menu-item>
			  </el-submenu>
			  <!--<el-menu-item index="3"><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item>-->
			</el-menu>
        </div>
    </div>
</template>
<script>
	import $ from 'jquery'
    export default {
        data() {
            return {
                name: 'linxin',
                activeIndex2:'1'
            }
        },
        created(){
        	console.log($);
        },
        computed:{
            username(){
                let username = localStorage.getItem('ms_username');
                return username ? username : this.name;
            }
        },
        methods:{
            handleCommand(command) {
                if(command == 'loginout'){
                    localStorage.removeItem('ms_username')
                    this.$router.push('/login');
                }
            },
             handleSelect(key, keyPath) {
		        console.log(key, keyPath);
		    }
        }
    }
</script>
<style >
    .header {
        position: relative;
        box-sizing: border-box;
        width: 100%;
        height: 70px;
        font-size: 22px;
        line-height: 70px;
        color: #fff;
        
    }
    .header .logo{
        float: left;
        width:250px;
        text-align: center;
    }
    .user-info {
        float: right;
        padding-right: 100px;
        font-size: 16px;
        color: #fff;
    }
    .user-info .el-dropdown-link{
        position: relative;
        display: inline-block;
        padding-left: 50px;
        color: #fff;
        cursor: pointer;
        vertical-align: middle;
    }
    .user-info .user-logo{
        position: absolute;
        left:0;
        top:15px;
        width:40px;
        height:40px;
        border-radius: 50%;
    }
    .el-dropdown-menu__item{
        text-align: center;
    }
    
    .comtext{
    	background: #242f42 !important;
    	margin-top: 8px;
    }
    .el-menu--horizontal .el-submenu .el-submenu__title{
    	color: #FFFFFF !important;
    }
    .comtext:hover{
    	background: #242F42 !important;
    }
    .el-menu--horizontal .el-submenu .el-submenu__title:hover{
    	background: #242f42 !important;
    }
    .el-menu--horizontal >.el-submenu:hover .el-submenu__title:hover{
    	border-bottom: none !important;
    }
    .el-menu--horizontal .el-submenu .el-submenu__title{
    	border-bottom: none !important;
    }
</style>
