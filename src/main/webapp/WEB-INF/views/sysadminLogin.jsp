<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<jsp:include page="/include/taglib.jsp"/>
	<meta charset="UTF-8">
	<title>${websiteName}-管理登录</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="shortcut icon" href="${favicon}" type="image/x-icon" />
    <jsp:include page="/include/css.inc.jsp"/>
    <style type="text/css">
    body {
	    height: 100% !important;
	    overflow: auto !important;
	}
	.login{margin-top: 5% !important;}
	</style>
</head>
<body class="login-bg">
    <div class="login">
        <div class="message">${websiteName}-管理登录</div>
        <div id="darkbannerwrap"></div>
        <form method="post" class="layui-form" >
            <input id="mobile"  type="text" placeholder="请输入账号"  value='${empty cookiesSuperLoginName?"":cookiesSuperLoginName}' lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input id='password' type="password" placeholder="请输入密码" lay-verify="required" class="layui-input">
            <hr class="hr15">
            <input class="text" type="text" id='loginValidateCode' lay-verify="required" placeholder="请输入验证码">
            <img class="mt10" src="" id='imgCode'/><span class="ml10 font-14px font-blue pointer other">换一张</span>
            <hr class="hr15">
            <input value="登录" lay-submit lay-filter="login" class="btn-login" style="width:100%;" type="button">
            <br/><h3 class="mt10 font-16px">${copyright}</h3>
            <hr class="hr20" >
        </form>
    </div>
</body>
	<jsp:include page="/include/js.inc.jsp"/>
	<script type="text/javascript" src="${cpath}/static/scripts_sys/sys_login.js?version=${version}" charset="utf-8"></script>
    <script>
        $(function  () {
            layui.use('form', function(){
              var form = layui.form;
              //监听提交
              form.on('submit(login)', function(data){
                return true;
              });
            });
        });
    </script>
</html>