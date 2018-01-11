<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.mycom.common.CommonUtils"%>
<%@page import="cn.mycom.sysadmin.servicemgr.domain.ServiceMgr"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>操作日志列表</title>
<jsp:include page="/include/meta.inc.jsp" />
<jsp:include page="/include/css.inc.jsp" />
</head>
<body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="javascript:;" onclick="goindex()">首页</a>
        <a href="#">操作日志</a>
        <a>
          <cite>操作日志列表</cite></a>
      </span>
      <a class="layui-btn layui-btn-small layui-refresh" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
    <div class="x-body">
      <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so">
        <input class="layui-input" id="queryStartId" placeholder="创建日期查询起始日期" type="text">
        &nbsp;-&nbsp;<input class="layui-input" id="queryEndId" placeholder="创建日期查询结束日期" type="text">
          <input type="text" name="opAuName" id="opAuNameId"  placeholder="请输入关键字（操作用户，操作业务名称 等）" autocomplete="off" class="layui-input layui-input-middle">
          <button class="layui-btn" id="queryBtnId" lay-submit="" data-type="reload" type="button"><i class="layui-icon">&#xe615;</i></button>
          <button class="layui-btn layui-btn-primary" id="resetBtnId" lay-submit="" type="button"><i class="layui-icon">&#x1002;</i></button>
        </form>
      </div>
      <!-- 示例-970 -->
	<table class="layui-table" lay-data="{ url:'${cpath}/sysadminMain/log/interface/operator.do',cellMinWidth: 80, page:true,loading:false,done: function(res, curr, count){donefunction(res, curr, count);initMenuPermission();}, id:'tablecontainerRecord'}" lay-filter="operator">
	  <thead>
	    <tr>
	      <th lay-data="{field:'opAuName',width:130}">操作用户</th>
	      <th lay-data="{field:'opBusinessName'}">操作业务名称 </th>
	      <th lay-data="{field:'opBusinessDesc'}">操作业务描述</th>
	      <th lay-data="{field:'opMethodType',templet: '#opMethodTypeTpl'}">操作方法类型</th>
	      <th lay-data="{field:'opContent',templet: '#contentTpl'}">记录信息</th>
	      <th lay-data="{field:'opIp'}">操作IP</th>
	      <th lay-data="{field:'addTime',width:160, sort: true}">创建时间</th>
	    </tr>
	  </thead>
	</table>
	<script type="text/html" id="contentTpl">
		{{ d.opContent }}
		<br/><br/>
		{{ d.opJsonContent }}
	</script>
	<script type="text/html" id="opMethodTypeTpl">
	{{#  if(d.opMethodType === 'ADD'){ }}
		新增
  	{{#  } else if(d.opMethodType === 'UPDATE'){ }}
    	修改
	{{#  } else if(d.opMethodType === 'DELETE'){ }}
		删除
	{{#  } else if(d.opMethodType === 'BATCH_DELETE'){ }}
		批量删除
	{{#  } else { }}
    	{{d.opMethodType}}
	{{#  } }}
	</script>
	</div>
  </body>
<jsp:include page="/include/js.inc.jsp" />
<script>
		layui.use('table', function(){
		  var table = layui.table;
	  	//查询操作
	  	var $ = layui.$, active = {
		  reload: function(){
			  webLoading();
		      var opAuNameReload = $('#opAuNameId').val();
		      var queryStartReload = $('#queryStartId').val();
		      var queryEndReload = $('#queryEndId').val();
		      var accuTypeReload = $('#accuTypeId').val();
		      var isAcceptedReload = $('#isAcceptedId').val();
		       if(isNull(accuTypeReload)){
		    	   accuTypeReload = null;
		       }
		       if(isNull(isAcceptedReload)){
		    	   isAcceptedReload = null;
		       }
		      
		      //执行重载
		      table.reload('tablecontainerRecord', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		        	dateFormat1:"yyyy-MM-dd HH:mm:ss",
		        	opAuName: opAuNameReload,
		        	accuType: accuTypeReload,
		        	isAccepted: isAcceptedReload,
		        	startDateStr1:queryStartReload,
		        	endDateStr1:queryEndReload
		        }
		      });
		    },
		  };
	  	//查询，重置按钮 start
		$('#queryBtnId').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });
		$('#resetBtnId').on('click', function(){
			$('#opAuNameId').val("");
			$('#queryStartId').val("");
			$('#queryEndId').val("");
			$('#queryBtnId').click();
		  });
	  	//查询，重置按钮 end
	  });
	</script>
	<script type="text/javascript">
	$(document).ready(function(){
		layui.use(['layer','laydate'], function() {
		  var laydate = layui.laydate;
		  //执行一个laydate实例
		  laydate.render({
		    elem: '#queryStartId' //指定元素
	    	,type: 'datetime'
		  });
		  laydate.render({
		    elem: '#queryEndId' //指定元素
	    	,type: 'datetime'
		  });
			//初始化loading
			webLoading();
			$('#resetBtnId').click();
		});
	});
	
	//初始化菜单权限
	function initMenuPermission(){
	}
	</script>
</html>