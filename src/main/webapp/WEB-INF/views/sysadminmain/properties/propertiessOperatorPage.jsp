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
	<jsp:include page="propertiessAdd.inc.jsp"/>
	<jsp:include page="propertiessUpdate.inc.jsp"/>
	<jsp:include page="propertiessShow.inc.jsp"/>
</body>
<jsp:include page="/include/js.inc.jsp" />
<script type="text/javascript">
	layui.use([ 'form', 'layer' ], function() {
		$ = layui.jquery;
		var form = layui.form, 
		layer = layui.layer;
		//监听提交
		form.on('submit(save)', function(data) {
			genAddAction("addopformId",'${cpath}/sysadminMain/properties/interface/propertiessToAdd.do');
			return true;
		});
		//监听提交
		form.on('submit(update)', function(data) {
			genUpdateAction("updateopformId",'${cpath}/sysadminMain/properties/interface/propertiessToUpdate.do');
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
	var keyId = $("#propKeyId").val();
	$(".x-body").hide();
	$(container).show();
	//初始化show add update
	if("show" == act){
		showById(keyId,"show",function(){
			$(container).find("input").prop("disabled",true);
			$(container).find("select").prop("disabled",true);
			$(container).find("textarea").prop("disabled",true);
			disabledForm(container);
		});
	}else if("add" == act){
		
	}else if("update" == act){
		showById(keyId,"update",function(){
			$(container).find("#L_propKey").prop("disabled",true);
		});
	}
});

//查看
function showById(keyId,type,showOrUpdateFunc){
	genShowAction('${cpath}/sysadminMain/properties/interface/propertiessToShow.do',{propKey:keyId},function(result){
		var bean = result;
		$(container).find("#L_propKey").val(bean.propKey);
		$(container).find("#L_propValue").val(bean.propValue);
		$(container).find("#L_remark").val(bean.remark);
		if(type == "show"){
			$(container).find("#updateTimeId").val(bean.updateTime);
			$(container).find("#addTimeId").val(bean.addTime);
		}
		if((type == "show" || type == "update") && ('function' == (typeof showOrUpdateFunc))){
			if(type == "show"){
				setTimeout(showOrUpdateFunc(),(10));
			}else{
				setTimeout(showOrUpdateFunc(),(10));
			}
		}
	});
}
</script>
</html>