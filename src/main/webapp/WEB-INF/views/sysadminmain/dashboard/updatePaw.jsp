<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.mycom.common.CommonUtils"%>
<%@page import="cn.mycom.sysadmin.servicemgr.domain.ServiceMgr"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改密码</title>
<jsp:include page="/include/meta.inc.jsp" />
<jsp:include page="/include/css.inc.jsp" />
</head>
<body>
	<div class="x-body">
		<form id="opformId" class="layui-form layui-form-middle">
			<div class="layui-form-item">
				<label for="L_username" class="layui-form-label">登录帐号 </label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_loginName" name="loginName" disabled=""
						class="layui-input" value="${user.loginName}">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_repass" class="layui-form-label"> <span
					class="x-red">*</span>旧密码
				</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="password" id="L_oldpass" name="oldpass" required=""
						lay-verify="required" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>新密码
				</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="password" id="L_newpass" name="newpass" required=""
						lay-verify="required|pass" autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">6到16个字符</div>
			</div>
			<div class="layui-form-item">
				<label for="L_repass" class="layui-form-label"> <span
					class="x-red">*</span>确认密码
				</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="password" id="L_repass" name="repass" required=""
						lay-verify="required|equalpass" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="" class="layui-form-label"> </label>
				<button class="layui-btn layui-bg-blue" lay-filter="save" lay-submit="" type="button">修改密码</button>
			</div>
		</form>
	</div>
</body>
<jsp:include page="/include/js.inc.jsp" />
<script type="text/javascript">
	layui.use([ 'form', 'layer' ], function() {
		$ = layui.jquery;
		var form = layui.form, 
		layer = layui.layer;
		//自定义验证规则
		  form.verify({
			equalpass: function(value){
				var npv = $("#L_newpass").val();
				var rpv = $("#L_repass").val();
		      if(npv != rpv){
		        return '确认密码不一致';
		      }
		    }
		    ,pass: [/(.+){6,16}$/, '密码必须6到16位']
		  });
		//监听提交
		form.on('submit(save)', function(data) {
			webLoading();
			doPostRequest("opformId",'${cpath}/sysadminMain/dashboard/interface/updatePaw.do', null, function (backData) {
				webLoading("hide");
				if(backData.success){
					webMsgSuccess("修改密码成功！请重新登录！",function(){
		                parent.location.href="${cpath}/sysadminLogin/login.do";
		            });
				}else{
					var errMsg = "修改密码失败！请稍后再试！";
					if(!isNull(backData.message)){
						errMsg = backData.message;
					}
					webMsgFailed(errMsg,function(){});
				}
			});
			return true;
		});
	});
</script>
</html>