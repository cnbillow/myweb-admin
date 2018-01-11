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
	<jsp:include page="menupermissionsAdd.inc.jsp"/>
	<jsp:include page="menupermissionsUpdate.inc.jsp"/>
	<jsp:include page="menupermissionsShow.inc.jsp"/>
</body>
<jsp:include page="/include/js.inc.jsp" />
<script type="text/javascript">
	layui.use([ 'form', 'layer' ], function() {
		$ = layui.jquery;
		var form = layui.form, 
		layer = layui.layer;
		//监听提交
		form.on('submit(save)', function(data) {
			genAddAction("addopformId",'${cpath}/sysadminMain/menuPermission/interface/menuPermissionsToAdd.do');
			return true;
		});
		//监听提交
		form.on('submit(update)', function(data) {
			genUpdateAction("updateopformId",'${cpath}/sysadminMain/menuPermission/interface/menuPermissionsToUpdate.do');
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
	var keyId = $("#mpIdId").val();
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
		doPostRequest(null,'${cpath}/sysadminMain/menuPermission/interface/menus.do', null, function (backData) {
			webLoading("hide");
			if(backData.success){
				if(!isNullObj(backData.result)){
					var rs = backData.result;
					$(container).find("#mpPIdId").html('<option value="0">顶级菜单</option>');
					setmp(container,rs,1);
					renderform();
				}
			}else{
				webMsg("查询失败！");
			}
		});	
	}else if("update" == act){
		webLoading();
		doPostRequest(null,'${cpath}/sysadminMain/menuPermission/interface/menus.do', null, function (backData) {
			webLoading("hide");
			if(backData.success){
				if(!isNullObj(backData.result) && !isNullObj(backData.result)){
					rs = backData.result;
					showById(keyId,"update",function(mpPId){
						$(container).find("#mpPIdId").html('<option value="0">顶级菜单</option>');
						setmp(container,rs,1);
						//选中默认值
						$(container).find("#mpPIdId").val(mpPId);
						renderform();
					});
				}
			}else{
				webMsg("查询失败！");
			}
		});
	}
});
function setmp(container,rs,index){
	if(!isNullObj(rs)){
		for(var imp in rs){
			var mp = rs[imp];
			if(!isNullObj(mp.mpId)){
				var str = "";
				for(var i = 0; i < index; i++){
					str = str+"---";
				}
				$(container).find("#mpPIdId").append('<option value="'+mp.mpId+'">'+str+mp.mpName+'</option>');
				setmp(container,mp.children,index+1);
			}
		}
	}
}
//查看
function showById(keyId,type,showOrUpdateFunc){
	genShowAction('${cpath}/sysadminMain/menuPermission/interface/menuPermissionsToShow.do',{mpId:keyId},function(result){
		var bean = result;
		$(container).find("#mpPIdId").html("<option value='"+bean.mpPId+"'>"+bean.mpPName+"</option>");
		$(container).find("#L_mpName").val(bean.mpName);
		$(container).find("#L_mpCode").val(bean.mpCode);
		$(container).find("#L_mpUrl").val(bean.mpUrl);
		$(container).find("#L_mpIcon").val(bean.mpIcon);
		$(container).find("#L_mpSort").val(bean.mpSort);

		$(container).find("#mpStatusId").val(bean.mpStatus);
		$(container).find("#isHasPermissionId").val(bean.isHasPermission);
		$(container).find("#mpTypeId").val(bean.mpType);
		if(type == "show"){
			$(container).find("#updateTimeId").val(bean.updateTime);
			$(container).find("#addTimeId").val(bean.addTime);
		}
		var mpPId = bean.mpPId;
		if((type == "show" || type == "update") && ('function' == (typeof showOrUpdateFunc))){
			if(type == "show"){
				setTimeout(showOrUpdateFunc(),(10));
			}else{
				setTimeout(showOrUpdateFunc(mpPId),(10));
			}
		}
	});
}
</script>
</html>