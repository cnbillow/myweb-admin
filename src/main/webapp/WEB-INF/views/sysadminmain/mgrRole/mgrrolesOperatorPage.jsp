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
	<jsp:include page="mgrrolesAdd.inc.jsp"/>
	<jsp:include page="mgrrolesUpdate.inc.jsp"/>
	<jsp:include page="mgrrolesShow.inc.jsp"/>
</body>
<jsp:include page="/include/js.inc.jsp" />
<script type="text/javascript">
	layui.use([ 'form', 'layer' ], function() {
		$ = layui.jquery;
		var form = layui.form, 
		layer = layui.layer;
		//监听提交
		form.on('submit(save)', function(data) {
			genAddAction("addopformId",'${cpath}/sysadminMain/mgrRole/interface/mgrRolesToAdd.do');
			return true;
		});
		//监听提交
		form.on('submit(update)', function(data) {
			genUpdateAction("updateopformId",'${cpath}/sysadminMain/mgrRole/interface/mgrRolesToUpdate.do');
			return true;
		});
	});
</script>
<script type="text/javascript">
act = '${act}';
container = '#'+act+'BodyId';
adminMenus = null;
</script>
<script type="text/javascript">
layui.use(['form','element' ], function() {
	var keyId = $("#roleIdId").val();
	$(".x-body").hide();
	$(container).show();
	if("show" == act){
		showById(keyId,"show",function(mps){
			initMenuPermissions(mps,"layui-form-checked");
			disabledForm(container);
			$(container).find(".layui-form-checkbox").removeClass("layui-checkbox-disbaled").removeClass("layui-disabled").addClass("layui-form-checked");
		});
	}else if("add" == act){
		webLoading();
		doPostRequest(null,'${cpath}/sysadminMain/getSysadminInfo.do', null, function (backData) {
			webLoading("hide");
			if(backData.success){
				if(!isNullObj(backData.result) && !isNullObj(backData.result.mgrRole)
						 && !isNullObj(backData.result.mgrRole.menuPermissions)){
					var topmenus = backData.result.mgrRole.menuPermissions;
					adminMenus  = topmenus;
					initMenuPermissions(topmenus,"");
					initEventAllCheckBox();
				}
			}else{
				webMsg("查询失败！");
			}
		});	
	}else if("update" == act){
		webLoading();
		doPostRequest(null,'${cpath}/sysadminMain/getSysadminInfo.do', null, function (backData) {
			webLoading("hide");
			if(backData.success){
				if(!isNullObj(backData.result) && !isNullObj(backData.result.mgrRole)
						 && !isNullObj(backData.result.mgrRole.menuPermissions)){
					adminMenus = backData.result.mgrRole.menuPermissions;
					showById(keyId,"update",function(menuPermissionIds){
						initMenuPermissions(null,"");
						initEventAllCheckBox();
						menus = menuPermissionIds;
						//选中默认值
						$(container).find(".layui-form-checkbox").each(function(){
							var thiss = $(this);
							var value = $(thiss).prev().val();
							for(var ii in menus){
								var iitem = menus[ii];
								if(iitem == value){
									$(thiss).addClass("layui-form-checked");
									$(thiss).prev().prop("checked",true);
									break;
								}
							}
						});
					});
				}
			}else{
				webMsg("查询失败！");
			}
		});
	}
});

//查看
function showById(keyId,type,showOrUpdateFunc){
	genShowAction('${cpath}/sysadminMain/mgrRole/interface/mgrRolesToShow.do',{roleId:keyId},function(result){
		var bean = result;
		$(container).find("#L_roleName").val(bean.roleName);
		$(container).find("#L_roleDesc").val(bean.roleDesc);
		$(container).find("#L_roleSort").val(bean.roleSort);
		if(type == "show"){
			$(container).find("#updateTimeId").val(bean.updateTime);
			$(container).find("#addTimeId").val(bean.addTime);
		}
		$(container).find("#roleStatusId").val(bean.roleStatus);
		var mps = bean.menuPermissions;
		var menuPermissionIds = bean.menuPermissionIds;
		if((type == "show" || type == "update") && ('function' == (typeof showOrUpdateFunc))){
			if(type == "show"){
				setTimeout(showOrUpdateFunc(mps),(10));
			}else{
				setTimeout(showOrUpdateFunc(menuPermissionIds),(10));
			}
		}
	});
}
//渲染菜单权限
var tmpl = '<li><span><input name="menuPermissionIdsString"'+
		'class="checkboxClass" type="checkbox" value="{index}"'+
		'id="listMenuPermissions-{index}" /><div class="layui-unselect {lfchecked} layui-form-checkbox" lay-skin=""><i class="layui-icon">&#xe618;</i></div><label for="listMenuPermissions-{index}">{mpName}</label></span>'+
		'</li>';
var tmplChildren = '<li><span><input name="menuPermissionIdsString"'+
		'class="checkboxClass" type="checkbox" value="{index}"'+
		'id="listMenuPermissions-{index}" /><div class="layui-unselect {lfchecked} layui-form-checkbox" lay-skin=""><i class="layui-icon">&#xe618;</i></div><label for="listMenuPermissions-{index}">{mpName}</label></span>'+
		'<ul style="margin-left: 35px;">{childrenUl}</ul></li>';		
function initMenuPermissions(topmenus,lfchecked){
	if(null == topmenus){
		topmenus = adminMenus;
	}
	if(isNullObj(topmenus) || (!isNullObj(topmenus) && topmenus.length <= 0) ) {
		$(container+" #mpsId").append($("<li>暂无权限</li>")); return;
	}
	for(var i in topmenus){
		var liAll = "";
		var item = topmenus[i];//顶级菜单
		var children1 = item.children;
		if(!isNullObj(children1)){
			var clis1 = getChildrenLis(children1,lfchecked);
			var children1 = item.children;
			liAll = liAll + replaceItem(tmplChildren,item,lfchecked);
			liAll = liAll.replace("{childrenUl}",clis1);
		}else{
			liAll = replaceItem(tmpl,item,lfchecked);
		}
		if(null != liAll && "" != liAll){
			$(container+" #mpsId").append($(liAll));
		}
	}
}

//获取第二级菜单
function getChildrenLis(menus,lfchecked){
	var liAll = "";
	if(isNullObj(menus)) return liAll;
	for(var i in menus){
		var item = menus[i];//二级菜单
		var children1 = item.children;
		if(!isNullObj(children1)){
			var clis1 = getChildrenLisLast(children1,lfchecked);
			liAll = liAll + replaceItem(tmplChildren,item,lfchecked);
			liAll = liAll.replace("{childrenUl}",clis1);
		}else{
			liAll = liAll + replaceItem(tmpl,item,lfchecked);
		}
	}
	return liAll;
}
//获取第三级菜单
function getChildrenLisLast(menus,lfchecked){
	var lis = "";
	if(isNullObj(menus)) return lis;
	for(var i in menus){
		var itemc = menus[i];//二级菜单
		lis = lis + replaceItem(tmpl,itemc,lfchecked);
	}
	return lis;
}
function replaceItem(tmpl,item,lfchecked){
	var out = "";
	if(null != item && !isNullObj(item.mpId)){
		if(isNull(lfchecked)){
			lfchecked = "";
		}
		out = tmpl.replace("{mpName}",item.mpName).replace("{lfchecked}",lfchecked).replace("{index}",item.mpId).replace("{index}",item.mpId).replace("{index}",item.mpId);
	}
	return out;
}

function initEventAllCheckBox(){
	$(".layui-form-checkbox").each(function(){
		var thiss = $(this);
		$(thiss).click(function(event){
			if($(thiss).hasClass("layui-form-checked")){
				setChildUnChecked(thiss);
			}else{
				$(thiss).addClass("layui-form-checked");
				$(thiss).prev().prop("checked",true);
				setParentChecked(thiss);
			}
			event.stopPropagation();//阻止事件冒泡即可
		});
	});
}
function setParentChecked(thiss){
	var pthisAll = $(thiss).parent().parent().parent().prev().children(".layui-form-checkbox");
	if(!isNullObj(pthisAll) && !isNullObj(pthisAll.first())){
		var pthis = pthisAll.first();
		$(pthis).addClass("layui-form-checked");
		$(pthis).prev().prop("checked",true);
		setParentChecked(pthis);
	}
}

function setChildUnChecked(thiss){
	$(thiss).removeClass("layui-form-checked");
	$(thiss).prev().prop("checked",false);
	if(!isNullObj($(thiss).parent().parent().find(".layui-form-checkbox"))){
		$(thiss).parent().parent().find(".layui-form-checkbox").removeClass("layui-form-checked");
		$(thiss).parent().parent().find(".layui-form-checkbox").prev().prop("checked",false);
	}
}
</script>
</html>