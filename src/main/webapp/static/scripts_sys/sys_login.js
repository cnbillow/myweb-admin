var model={
		api:{login:cpath+"/sysadminLogin/toLogin.do",//登陆接口
			checkLogin:cpath+"/sysadminLogin/checkLogin.do",//检查是否登陆
			loginValidateCode:cpath+"/sysadminLogin/loginValidateCode.do"//获取图片验证码
		},
		call:function(opt) {
			$.ajax({
				type: "POST",
				url: opt.url,
				data: opt.data,
				dataType: "json",
				beforeSend: opt.beforeSend,
				success: opt.success,
				error: opt.error,
				complete: opt.complete
			})
		},//发起请求
		login:function(opt){
			model.call(opt)
		},//登陆
		checkLogin:function(opt){
			model.call(opt)
		},//检查登陆
		loginValidateCode:function(opt){
			model.call(opt)
		},//图片验证码
		checkNum:function(){
			if($("#mobile").val()==""||$("#password").val()==""){
				view.showDialog("账号或密码不能为空<br/>请输入")
				return false
			}else{
				if($('#loginValidateCode').val()==''){
                                        view.loginValidateCode()
					view.showDialog("验证码不能为空")
					return false
				}else{
					return true
				}
			}
		}//检查账号密码为空
}
var view={
		login:function(){
			var opt={
					url:model.api.login,
					data:{
						loginName:$('#mobile').val(),
						loginPassword:$('#password').val(),
						isRemember:$('.checkbox').is(':checked'),
						verifyCode:$('#loginValidateCode').val()
					},
		    		beforeSend:function(){webLoading()},
		    		success:function(data){
		    			webLoading("hide");
		    			if(data.success==true){
		    				if(data.code=="S_OK"){
			    				window.location.href=cpath+"/sysadminMain/index.do";
			    			}else{view.showDialog(data.message)}
		    			}else{
		    				view.loginValidateCode()
		    				$('#loginValidateCode').val('')
		    				view.showDialog(data.message)}
		    		},
		    		error:function(){webLoading("hide");view.showDialog("账号或密码错误<br/>请重新输入")},
		    		complete:function(){webLoading("hide");}
			}
			model.login(opt)
		},
		checkLogin:function(){
			var opt={
					url:model.api.checkLogin,
					data:{
						
					},
		    		beforeSend:function(){},
		    		success:function(data){
		    			if(data.success==true){
		    				window.location.href=cpath+"/sysadminMain/index.do"
		    			}
		    		},
		    		error:function(){},
		    		complete:function(){}
			}
			model.login(opt)
		},
		loginValidateCode:function(){
		    $('#imgCode').attr('src',model.api.loginValidateCode+"?t="+new Date())
		},
		bindLogin:function(){
			$('.btn-login').click(function(){
				if(model.checkNum()){
					view.login()
				}
			})
			$(document).keydown(function (event) {
			    if(event.which==13){
			    	if(model.checkNum()){
			    		view.login()
			    	}
			    }
			});
		},//绑定登陆
		bindChangeCode:function(){
			$('.other').click(function(){
				view.loginValidateCode()
			})
		},//换验证码
		showDialog:function(str){
			webAlert(str);
		},//显示弹窗
		init:function(){
			view.checkLogin();
			view.loginValidateCode();
			view.bindChangeCode();
			view.bindLogin();
		}
}
$(function(){
	view.init()
})