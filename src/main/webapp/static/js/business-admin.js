showAttr = new Array();
Array.prototype.contains = function ( needle ) {
  for (i in this) {
    if (this[i] == needle) return true;
  }
  return false;
}
//跳转首页
function goindex(event){
	if(window.parent){ 
		parent.location.href=cpathSysadminMainIndex;
	}else{
		window.location.href=cpathSysadminMainIndex;
	}	
	if(!isNullObj(event)){
		event.stopPropagation();
	}
}
//批量查询权限moCodes(permissionnames为对应的权限标签标记，只查询全部的a标签和button标签),
//返回数据为数组[{'mpCode':'xxx','success':'true|false'}]
function permission_show(mpCodes){
	webLoading();
	var dataResult = null;
	if(null != mpCodes){
		$.ajax({
			url:cpath+"/sysadminMain/interface/currentMgrRole.do?mpCodes="+mpCodes.join(","),
			type:"POST",
		    cache:false, 
		    async:false,  
		    dataType: "json", 
			success:function(data){
				webLoading("hide");	
				if(data.success){
					dataResult = data.result;
					for(var i in dataResult){
						var mpcodeArr = dataResult[i];
						var mpCode = mpcodeArr['mpCode'];
						if(mpcodeArr['success']){
							var ele = "[data-permission='"+mpCode+"']";
							$(ele).show();
							if(!isNullObj(showAttr)){
								if(!showAttr.contains(ele)){
									showAttr.push(ele);
								}
							}
						}
					}
				}
			},error:function() {
				webAlert("查询权限失败！");
				webLoading("hide");	
			}
		});
	}
}
//layui加载表格完成后的回调
function donefunction(data,curr,count){
	var w = $(".layui-table-header").width();
	console.log(w);
	webLoading("hide");
	$(".layui-table-cell").each(function(){
		var c = $(this).text();
		if(!isNull(c)){
			$(this).attr("title",c);
		}
	});
	$(".layui-table-header, .layui-table-sort, .layui-table-sort-asc, .layui-table-sort-desc").click(function(){
		//console.log($(this).attr("class"));
		//console.log("showAttr:"+showAttr.toString());
		$(showAttr.toString()).show();
	});
	if(data.errorCode == "-999"){
		webAlert("系统繁忙！请稍后再试！");
		return;
	}else if(data.errorCode == "NotPermission"){
		webAlert("当前帐号暂无访问权限！");
		return;
	}else if(data.errorCode == "NotLogin"){
		webAlert("当前帐号未登录！");
		return;
	}
}

//通用新增方法 start
function genAddAction(formId,url){
	genAddAction(formId,url,null,null);
}
function genAddAction(formId,url,successFun,failedFun){
	_genAddOrUpdateAction("新增",formId,url,successFun,failedFun);
}
//通用新增方法 end

//通用修改方法 end
function genUpdateAction(formId,url){
	genUpdateAction(formId,url,null,null);
}
function genUpdateAction(formId,url,successFun,failedFun){
	_genAddOrUpdateAction("修改",formId,url,successFun,failedFun);
}
//通用修改方法 end

function _genAddOrUpdateAction(msg,formId,url,successFun,failedFun){
	webLoading();
	doPostRequest(formId,url, null, function (backData) {
		webLoading("hide");
		if(backData.success){
			webMsgSuccess(msg+"成功！",function(){
				if('function' == (typeof successFun)){
					setTimeout(successFun,(10));
				}else{
					if(window.parent){ 
						parent.location.reload();
					}else{
						window.location.reload();
					}
				}
            });
		}else{
			var errMsg = msg+"失败！请稍后再试！";
			if(!isNull(backData.message)){
				errMsg = backData.message;
			}
			if('function' == (typeof failedFun)){
				webMsgFailed(errMsg,function(){setTimeout(failedFun,(10));});
			}else{
				webMsgFailed(errMsg,function(){});
			}
		}
	});
}

//通用删除方法 start
function genDelAction(url,data,tip){
	genDelAction(url,data,tip,null,null);
}
function genDelAction(url,data,tip,successFun,failedFun){
	if(isNull(tip)){
		tip = '确定删除此记录？';
	}
	webConfirm(tip, function(index){
		webLoading();
		doPostRequest(null,url,data, function (backData) {
			webLoading("hide");
			if(backData.success){
				webMsgSuccess("删除成功！",function(){
					if('function' == (typeof successFun)){
						setTimeout(successFun,(10));
					}
				});
			}else{
				var errMsg = "删除失败！请稍后再试！";
				if(!isNull(backData.message)){
					errMsg = backData.message;
				}
				webMsgFailed(errMsg,function(){
					if('function' == (typeof failedFun)){
						setTimeout(failedFun,(10));
					}
				});
			}
		});
	});
}
//通用删除方法 end

//通用查看方法 start
function genShowAction(url,data,successFunc){
	webLoading();
	doPostRequest(null,url, data, function (backData) {
		webLoading("hide");
		if(backData.success){
			setTimeout(successFunc(backData.result),(10));
		}else{
			webMsgFailed("查看失败！",function(){});
		}
	});
}
//通用查看方法 end

/*设置下拉
function setSelectClick(id,value){
	$(id).each(function(){
		var thiss = $(this);
		var isFind = false;
		$(thiss).next().find("dl").find("dd").each(function(){
			if($(this).attr("lay-value") == value){
				$(this).click();
				isFind = true;
				return;
			}
		});
		if(isFind){
			return;
		}
	});
}*/
//重新渲染表单
function renderform(){
	layui.use(['form','element' ], function() {
		var form = layui.form;
		form.render();
	});
}
function disabledForm(container){
	$(container).find("input").prop("disabled",true);
	$(container).find("select").prop("disabled",true);
	$(container).find("textarea").prop("disabled",true);
	renderform();
}