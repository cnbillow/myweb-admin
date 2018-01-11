/**
 * 异步请求 post
 * tisson.util.doRequest('myform',ctx+'/user/list',{username:'liming'},function(backDataz){})
 * 
 * @param form 表单ID
 * @param url 请求路径
 * @param param 参数对象，如：{a: 'test', b: 2}
 * @param fn 回调函数
 */
function doPostRequest(form, url, param, fn) {
	var params = form || param || {};
	if (typeof form == 'string') {
		params = $.extend(param || {},serializeObject($("#" + form)));
	}
	$.ajax({
		type : 'POST',
		url : url,
		data : params,
		dataType : 'json',
		success : function(data, textStatus) {
			if(!data.success){
				webLoading("hide");	
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
			if (typeof (fn) == 'function') {
				fn.call(this, data);
			}
		},
		error : function() {
			webAlert("请求失败！系统繁忙！");
			webLoading("hide");	
			return;
		},
		beforeSend : function() {
		},
		complete : function() {
			webLoading("hide");	
		}
	});
}
/**
 *  将form表单元素的值序列化成对象
 * @param form  表单ID
 * @returns object
 */
function serializeObject(form) {
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
}
/**
 *************************************************************异步请求 post end
*/
/**
 * 是否为空
 */
function isNull(str){
	if ( str == "" || str == null || str == undefined || str == 'undefined') return true;
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
}
/**
 * 是否为空
 */
function isNullObj(obj){
	if (obj != null && obj != undefined) return false;
	return true;
}