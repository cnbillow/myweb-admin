<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.mycom.common.CommonUtils"%>
<%@page import="cn.mycom.sysadmin.servicemgr.domain.ServiceMgr"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tests管理</title>
<jsp:include page="/include/meta.inc.jsp" />
<jsp:include page="/include/css.inc.jsp" />
</head>
<body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="javascript:;" onclick="goindex()">首页</a>
        <a href="#">Tests管理</a>
        <a>
          <cite>Tests列表</cite></a>
      </span>
      <a class="layui-btn layui-btn-small layui-refresh" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
    <div class="x-body">
      <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so">
          <input class="layui-input" id="queryStartId" placeholder="创建日期查询起始日期" type="text">
          &nbsp;-&nbsp;<input class="layui-input" id="queryEndId" placeholder="创建日期查询结束日期" type="text">
          <input type="text" name="name" id="nameId"  placeholder="请输入名称" autocomplete="off" class="layui-input layui-input-middle">
          <button class="layui-btn" id="queryBtnId" lay-submit="" data-type="reload" type="button"><i class="layui-icon">&#xe615;</i></button>
          <button class="layui-btn layui-btn-primary" id="resetBtnId" lay-submit="" type="button"><i class="layui-icon">&#x1002;</i></button>
        </form>
      </div>
      <xblock>
        <!-- <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除</button> -->
        <button class="layui-btn pt0"  data-permission="Tests:add" onclick="x_admin_show('新增Tests','${cpath}/sysadminMain/tests/operatorPage.do?act=add')">&nbsp;<i class="layui-icon"></i>新增&nbsp;&nbsp;</button>
      </xblock>
      <!-- 示例-970 -->
	<table class="layui-table" lay-data="{ url:'${cpath}/sysadminMain/tests/interface/testss.do',cellMinWidth: 80, page:true,loading:false,done: function(res, curr, count){donefunction(res, curr, count);initMenuPermission();}, id:'tablecontainerRecord'}" lay-filter="operator">
	  <thead>
	    <tr>
	      <th lay-data="{field:'testId'}">testId</th>
	      <th lay-data="{field:'name'}">名称</th>
	      <th lay-data="{field:'updateTime',width:160, sort: true}">修改时间</th>
	      <th lay-data="{field:'addTime',width:160, sort: true}">添加时间</th>
	      <th lay-data="{align:'center',width:180, toolbar: '#baroperatorTpl'}">操作</th>
	    </tr>
	  </thead>
	</table>
	<!-- 操作模版 -->
	<script type="text/html" id="baroperatorTpl">
  		<a data-permission="Tests:show" class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail" title="查看">查看</a>
  		<a data-permission="Tests:update" class="layui-btn layui-btn-xs" lay-event="edit" title="编辑">编辑</a>
  		<a data-permission="Tests:delete" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" title="删除">删除</a>
	</script>
	<%--
	有状态类型的，可以增加模板
	<script type="text/html" id="statusTpl">
	{{#  if(d.status === '1' || d.status === 1){ }}
		<span class="layui-btn layui-btn-normal layui-btn-xs">已启用</span>
  	{{#  } else { }}
    	<span class="layui-btn layui-btn-primary layui-btn-xs">禁用</span>
	{{#  } }}
	</script>
	 --%>
	</div>
  </body>
<jsp:include page="/include/js.inc.jsp" />
<script>
		layui.use('table', function(){
		  var table = layui.table;
		  //监听表格复选框选择
		  table.on('checkbox(operator)', function(obj){
		    console.log(obj)
		  });
		  //监听工具条
		  table.on('tool(operator)', function(obj){
		    var data = obj.data;
		    var key = data.testId;
		    console.log(key);
		    if(obj.event === 'detail'){
		    	x_admin_show('查看Tests','${cpath}/sysadminMain/tests/operatorPage.do?act=show&testId='+key);
		    } else if(obj.event === 'del'){
			      genDelAction('${cpath}/sysadminMain/tests/interface/testssToDelete.do',
			    		  {testId:key},'确定删除此记录？',function(){$('#queryBtnId').click();},null);
		    } else if(obj.event === 'edit'){
		    	x_admin_show('编辑Tests','${cpath}/sysadminMain/tests/operatorPage.do?act=update&testId='+key);
		    }
		  });
	  	//查询操作
	  	var $ = layui.$, active = {
		  reload: function(){
			  webLoading();
		      var nameReload = $('#nameId').val();
		      var queryStartReload = $('#queryStartId').val();
		      var queryEndReload = $('#queryEndId').val();
		      //执行重载
		      table.reload('tablecontainerRecord', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		        	name: nameReload,
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
			$('#nameId').val("");
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
		var arr = new Array();
		arr.push("Tests:delete");
		arr.push("Tests:add");
		arr.push("Tests:update");
		arr.push("Tests:show");
		permission_show(arr);
	}
	</script>
</html>