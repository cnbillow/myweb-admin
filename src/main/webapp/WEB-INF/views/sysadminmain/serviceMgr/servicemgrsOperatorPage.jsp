<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.mycom.common.CommonUtils"%>
<%@page import="cn.mycom.sysadmin.servicemgr.domain.ServiceMgr"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<jsp:include page="/include/meta.inc.jsp" />
<jsp:include page="/include/css.inc.jsp" />
<style type="text/css">
.layui-form-checkbox {
    position: relative;
    height: 18px;
    line-height: 18px;
    margin-right: 5px;
    padding-right: 7px;
    border: 1px solid #d2d2d2;
    cursor: pointer;
    font-size: 0;
    border-radius: 2px;
    -webkit-transition: .1s linear;
    transition: .1s linear;
    box-sizing: border-box;
    padding-left: 10px;
}
.layui-form-checkbox i{width: 24px !important;}
.x-body{display: none;}
</style>
</head>
<body>
	<jsp:include page="servicemgrsAdd.inc.jsp"/>
	<jsp:include page="servicemgrsUpdate.inc.jsp"/>
	<jsp:include page="servicemgrsShow.inc.jsp"/>
</body>
<jsp:include page="/include/js.inc.jsp" />
<script type="text/javascript">
	layui.use([ 'form', 'layer' ], function() {
		$ = layui.jquery;
		var form = layui.form, 
		layer = layui.layer;
		//监听提交
		form.on('submit(save)', function(data) {
			genAddAction("addopformId",'${cpath}/sysadminMain/serviceMgr/interface/serviceMgrsToAdd.do');
			return true;
		});
		//监听提交
		form.on('submit(update)', function(data) {
			genUpdateAction("updateopformId",'${cpath}/sysadminMain/serviceMgr/interface/serviceMgrsToUpdate.do');
			return true;
		});
	});
</script>
<script type="text/javascript">
act = '${act}';
container = '#'+act+'BodyId';
roles = null;
</script>
<script type="text/javascript">
layui.use(['form','element' ], function() {
	var keyId = $("#adminIdId").val();
	$(".x-body").hide();
	$(container).show();
	if("show" == act){
		showById(keyId,"show",function(mps){
			$(container).find("input").prop("disabled",true);
			$(container).find("select").prop("disabled",true);
			$(container).find("textarea").prop("disabled",true);
			disabledForm(container);
		});
	}else if("add" == act){
		webLoading();
		doPostRequest(null,'${cpath}/sysadminMain/serviceMgr/interface/mgrRoles.do', null, function (backData) {
			webLoading("hide");
			if(backData.success){
				if(!isNullObj(backData.result)){
					var rs = backData.result;
					initRole(rs);
					renderform();
				}
			}else{
				webMsg("查询失败！");
			}
		});	
	}else if("update" == act){
		webLoading();
		doPostRequest(null,'${cpath}/sysadminMain/serviceMgr/interface/mgrRoles.do', null, function (backData) {
			webLoading("hide");
			if(backData.success){
				if(!isNullObj(backData.result) && !isNullObj(backData.result)){
					roles = backData.result;
					showById(keyId,"update",function(roleId){
						initRole(roles);
						//选中默认值
						$(container).find("#roleIdId").val(roleId);
						renderform();
					});
				}
			}else{
				webMsg("查询失败！");
			}
		});
	}
});
function initRole(rs){
	if(isNullObj(rs)){
		rs = roles;
	}
	if(!isNullObj(rs)){
		for(var i in rs){
			if(!isNullObj(rs[i].roleId)){
				$(container).find("#roleIdId").append($("<option value='"+rs[i].roleId+"'>"+rs[i].roleName+"</option>"));
			}
		}
	}
}
//查看
function showById(keyId,type,showOrUpdateFunc){
	genShowAction('${cpath}/sysadminMain/serviceMgr/interface/serviceMgrsToShow.do',{adminId:keyId},function(result){
		var bean = result;
		$(container).find("#L_loginName").val(bean.loginName);
		$(container).find("#L_userMail").val(bean.userMail);
		$(container).find("#L_mobile").val(bean.mobile);
		if(type == "show"){
			$(container).find("#roleIdId").html("<option value='"+bean.roleId+"'>"+bean.roleName+"</option>");
			$(container).find("#updateTimeId").val(bean.updateTime);
			$(container).find("#addTimeId").val(bean.addTime);
		}
		$(container).find("#isAllowLoginId").val(bean.isAllowLogin);
		var roleId = bean.roleId;
		if((type == "show" || type == "update") && ('function' == (typeof showOrUpdateFunc))){
			if(type == "show"){
				setTimeout(showOrUpdateFunc(),(10));
			}else{
				setTimeout(showOrUpdateFunc(roleId),(10));
			}
		}
	});
}
</script>
</html>