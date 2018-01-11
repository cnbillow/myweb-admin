var loadingLayer = null;
var confirmLayer = null;
	/*********************************web alert start************************************************************************/
	function webAlert(msgString){
		_webMsgOrAlert('alert','-1',msgString);
	}
	/*********************************web alert end**************************************************************************/
	
	/*********************************web msg start************************************************************************/
	function webMsg(msgString){
		_webMsgOrAlert('msg','-1',msgString);
	}
	function webMsgSuccess(msgString,doFunction,time){
		_webMsgOrAlert('msg','1',msgString,doFunction,time);
	}
	function webMsgFailed(msgString,doFunction,time){
		_webMsgOrAlert('msg','2',msgString,doFunction,time);
	}
	function webMsgWarning(msgString,doFunction,time){
		_webMsgOrAlert('msg','0',msgString,doFunction,time);
	}
	/*********************************web msg end**************************************************************************/

	//提示框[type=包含成功1，失败2，提示0，无-1],msgString必填；doFunction可选(成功1，失败2，提示0必须填doFunction)
	function _webMsgOrAlert(tipOrAlert,type,msgString,doFunction,time){
		type = type + "";
		if(!("1" == type || "2" == type || "0" == type || "-1" == type)){
			alert('type参数错误！');
			return;
		}
		
		if(msgString == null || msgString == undefined || msgString == ''){
			msgString = "提示";
		}
		
		var hasFunction = false;
		var timei = 1500;
		if(null != doFunction){
		 if('function' != (typeof doFunction)){
			alert('doFunction不是函数！');
			return;
		 }else{
		 	hasFunction = true;
			if('number' == (typeof time)){
				timei = time;
			}
		 }
		}
		if(hasFunction){
			var icons = parseInt(type);
			if("msg" == tipOrAlert){
				layer.msg(msgString, {
				  icon: icons,
				  time: timei, //2秒关闭（如果不配置，默认是3秒）
				},function(){
					setTimeout(doFunction,(50));
				}); 
			}else{
				layer.alert(msgString, {
				  icon: icons,
				},doFunction);
			}
		}else{
			if("msg" == tipOrAlert){
				layer.msg(msgString);
			}else{
				layer.alert(msgString);
			}
		}
	}
	
	/*********************************web loading start************************************************************************/
	//加载框
	function webLoading(showOrHide){
		if("hide" == showOrHide){
			if(null != loadinglayer && undefined != loadinglayer){
				layer.close(loadinglayer);
			}
		}else{
			loadinglayer = layer.load();
		}
	}
	/*********************************web loading end************************************************************************/

	/*********************************web confirm start************************************************************************/
	//msg必填，否则没操作；doFunction是函数，不是函数没操作；
	function webConfirm(msg,doFunction){
		if(msg == null || msg == undefined || msg == ''){
			alert('msg不能空！');
			return;
		}
		if('function' != (typeof doFunction)){
			alert('doFunction不是函数！');
			return;
		}
		layer.confirm(msg, {icon: 3, title:'确定框'}, function(index){
			confirmLayer = index;
		  setTimeout(doFunction,0);
		});
	}
	/*********************************web confirm end************************************************************************/
	