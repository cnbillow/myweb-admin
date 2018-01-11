<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.mycom.common.CommonUtils"%>
<%@page import="cn.mycom.sysadmin.servicemgr.domain.ServiceMgr"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>角色管理</title>
<jsp:include page="/include/meta.inc.jsp" />
<jsp:include page="/include/css.inc.jsp" />
</head>
<body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="javascript:;" onclick="goindex()">首页</a>
        <a href="#">角色管理</a>
        <a>
          <cite>角色列表</cite></a>
      </span>
      <a class="layui-btn layui-btn-small layui-refresh" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
    <div class="x-body">
      <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so">
          <div class="layui-input-inline">
            <select name="contrller" id="roleStatusId">
              <option value="">角色状态</option>
              <option value="Y">已启用</option>
              <option value="N">禁用</option>
            </select>
          </div>
          <input type="text" name="roleName" id="roleNameId"  placeholder="请输入角色名称" autocomplete="off" class="layui-input layui-input-middle">
          <button class="layui-btn" id="queryBtnId" lay-submit="" data-type="reload" type="button"><i class="layui-icon">&#xe615;</i></button>
          <button class="layui-btn layui-btn-primary" id="resetBtnId" lay-submit="" type="button"><i class="layui-icon">&#x1002;</i></button>
        </form>
      </div>
      <xblock>
        <!-- <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除</button> -->
        <button class="layui-btn pt0"  data-permission="System:role:add" onclick="x_admin_show_big('新增角色','${cpath}/sysadminMain/mgrRole/operatorPage.do?act=add')">&nbsp;<i class="layui-icon"></i>新增&nbsp;&nbsp;</button>
      </xblock>
      <!-- 示例-970 -->
	<table class="layui-table" lay-data="{ url:'${cpath}/sysadminMain/mgrRole/interface/mgrRoles.do',cellMinWidth: 80, page:true,loading:false,done: function(res, curr, count){donefunction(res, curr, count);initMenuPermission();}, id:'tablecontainerRecord'}" lay-filter="operator">
	  <thead>
	    <tr>
	      <th lay-data="{type:'checkbox', fixed: 'left'}"></th>
	      <th lay-data="{field:'roleId', sort: true}">ID</th>
	      <th lay-data="{field:'roleName'}">角色名称</th>
	      <th lay-data="{field:'roleStatus',templet: '#roleStatusTpl'}">角色状态</th>
	      <th lay-data="{field:'roleSort', sort: true}">排序</th>
	      <th lay-data="{field:'updateTime',width:160, sort: true}">修改时间</th>
	      <th lay-data="{align:'center',width:180, toolbar: '#baroperatorTpl'}">操作</th>
	    </tr>
	  </thead>
	</table>
	<!-- 操作模版 -->
	<script type="text/html" id="baroperatorTpl">
  		<a data-permission="System:role:show" class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail" title="查看">查看</a>
  		<a data-permission="System:role:update" class="layui-btn layui-btn-xs" lay-event="edit" title="编辑">编辑</a>
  		<a data-permission="System:role:delete" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" title="删除">删除</a>
	</script>
	<script type="text/html" id="roleStatusTpl">
	{{#  if(d.roleStatus === 'Y'){ }}
		<span class="layui-btn layui-btn-normal layui-btn-xs">已启用</span>
  	{{#  } else { }}
    	<span class="layui-btn layui-btn-primary layui-btn-xs">禁用</span>
	{{#  } }}
	</script>
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
		    if(obj.event === 'detail'){
		    	x_admin_show_big('查看角色','${cpath}/sysadminMain/mgrRole/operatorPage.do?act=show&roleId='+data.roleId);
		    } else if(obj.event === 'del'){
			      genDelAction('${cpath}/sysadminMain/mgrRole/interface/mgrRolesToDelete.do',
			    		  {roleId:data.roleId},'确定删除此记录？',function(){$('#queryBtnId').click();},null);
		    } else if(obj.event === 'edit'){
		    	x_admin_show_big('编辑角色','${cpath}/sysadminMain/mgrRole/operatorPage.do?act=update&roleId='+data.roleId);
		    }
		  });
	  	//查询操作
	  	var $ = layui.$, active = {
		  reload: function(){
			  webLoading();
		      var roleNameReload = $('#roleNameId').val();
		      var roleStatusReload = $("#roleStatusId").val();
		      //执行重载
		      table.reload('tablecontainerRecord', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		        	roleName: roleNameReload,
		        	roleStatus:roleStatusReload
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
			$('#roleNameId').val("");
			$('#roleStatusId').next().find(".layui-select-tips").click();
			$('#queryBtnId').click();
		  });
	  	//查询，重置按钮 end

	});
	</script>
	<script type="text/javascript">
	$(document).ready(function(){
		layui.use(['layer'], function() {
			//初始化loading
			webLoading();
			$('#resetBtnId').click();
		});
	});
	
	//初始化菜单权限
	function initMenuPermission(){
		var arr = new Array();
		arr.push("System:role:delete");
		arr.push("System:role:add");
		arr.push("System:role:update");
		arr.push("System:role:show");
		permission_show(arr);
	}
	</script>
</html>