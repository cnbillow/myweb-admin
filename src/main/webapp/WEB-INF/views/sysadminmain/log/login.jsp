<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.mycom.common.CommonUtils"%>
<%@page import="cn.mycom.sysadmin.servicemgr.domain.ServiceMgr"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录日志列表</title>
<jsp:include page="/include/meta.inc.jsp" />
<jsp:include page="/include/css.inc.jsp" />
</head>
<body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="javascript:;" onclick="goindex()">首页</a>
        <a href="#">登录日志</a>
        <a>
          <cite>登录日志列表</cite></a>
      </span>
      <a class="layui-btn layui-btn-small layui-refresh" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
    <div class="x-body">
      <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so">
        <input class="layui-input" id="queryStartId" placeholder="创建日期查询起始日期" type="text">
        &nbsp;-&nbsp;<input class="layui-input" id="queryEndId" placeholder="创建日期查询结束日期" type="text">
          <input type="text" name="loginName" id="loginNameId"  placeholder="请输入关键字（姓名，登录帐号，IP）" autocomplete="off" class="layui-input layui-input-middle">
          <button class="layui-btn" id="queryBtnId" lay-submit="" data-type="reload" type="button"><i class="layui-icon">&#xe615;</i></button>
          <button class="layui-btn layui-btn-primary" id="resetBtnId" lay-submit="" type="button"><i class="layui-icon">&#x1002;</i></button>
        </form>
      </div>
      <!-- 示例-970 -->
	<table class="layui-table" lay-data="{ url:'${cpath}/sysadminMain/log/interface/login.do',cellMinWidth: 80, page:true,loading:false,done: function(res, curr, count){donefunction(res, curr, count);initMenuPermission();}, id:'tablecontainerRecord'}" lay-filter="operator">
	  <thead>
	    <tr>
	      <th lay-data="{field:'loginUserId',width:120}">登录用户Id</th>
	      <th lay-data="{field:'loginName'}">登录帐号</th>
	      <th lay-data="{field:'ip'}">IP</th>
	      <th lay-data="{field:'loginTime',width:180, sort: true}">创建时间</th>
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
		      var loginNameReload = $('#loginNameId').val();
		      var queryStartReload = $('#queryStartId').val();
		      var queryEndReload = $('#queryEndId').val();
		      
		      //执行重载
		      table.reload('tablecontainerRecord', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		        	dateFormat1:"yyyy-MM-dd HH:mm:ss",
		        	loginName: loginNameReload,
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
			$('#loginNameId').val("");
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