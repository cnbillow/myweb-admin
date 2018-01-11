<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.mycom.common.CommonUtils"%>
<%@page import="cn.mycom.sysadmin.servicemgr.domain.ServiceMgr"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>属性配置管理</title>
<jsp:include page="/include/meta.inc.jsp" />
<jsp:include page="/include/css.inc.jsp" />
</head>
<body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="javascript:;" onclick="goindex()">首页</a>
        <a href="#">属性配置管理</a>
        <a>
          <cite>属性配置列表</cite></a>
      </span>
      <a class="layui-btn layui-btn-small layui-refresh" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
    <div class="x-body">
      <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so">
          <input type="text" name="propKey" id="propKeyId"  placeholder="请输入属性键值，属性值，备注" autocomplete="off" class="layui-input layui-input-middle">
          <button class="layui-btn" id="queryBtnId" lay-submit="" data-type="reload" type="button"><i class="layui-icon">&#xe615;</i></button>
          <button class="layui-btn layui-btn-primary" id="resetBtnId" lay-submit="" type="button"><i class="layui-icon">&#x1002;</i></button>
        </form>
      </div>
      <xblock>
        <!-- <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除</button> -->
        <button class="layui-btn pt0"  data-permission="System:properties:add" onclick="x_admin_show_big('新增属性配置','${cpath}/sysadminMain/properties/operatorPage.do?act=add')">&nbsp;<i class="layui-icon"></i>新增&nbsp;&nbsp;</button>
      </xblock>
      <!-- 示例-970 -->
	<table class="layui-table" lay-data="{ url:'${cpath}/sysadminMain/properties/interface/propertiess.do',cellMinWidth: 80, page:true,loading:false,done: function(res, curr, count){donefunction(res, curr, count);initMenuPermission();}, id:'tablecontainerRecord'}" lay-filter="operator">
	  <thead>
	    <tr>
	      <th lay-data="{type:'checkbox', fixed: 'left'}"></th>
	      <th lay-data="{field:'propKey'}">属性键值</th>
	      <th lay-data="{field:'propValue'}">属性参数长值</th>
	      <th lay-data="{field:'remark',width:280}">备注</th>
	      <th lay-data="{field:'updateTime',width:160, sort: true}">修改时间</th>
	      <th lay-data="{align:'center',width:180, toolbar: '#baroperatorTpl'}">操作</th>
	    </tr>
	  </thead>
	</table>
	<!-- 操作模版 -->
	<script type="text/html" id="baroperatorTpl">
  		<a data-permission="System:properties:show" class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail" title="查看">查看</a>
  		<a data-permission="System:properties:update" class="layui-btn layui-btn-xs" lay-event="edit" title="编辑">编辑</a>
  		<a data-permission="System:properties:delete" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" title="删除">删除</a>
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
		    var key = data.propKey;
		    console.log(key);
		    if(obj.event === 'detail'){
		    	x_admin_show('查看属性配置','${cpath}/sysadminMain/properties/operatorPage.do?act=show&propKey='+key);
		    } else if(obj.event === 'del'){
			      genDelAction('${cpath}/sysadminMain/properties/interface/propertiessToDelete.do',
			    		  {propKey:key},'确定删除此记录？',function(){$('#queryBtnId').click();},null);
		    } else if(obj.event === 'edit'){
		    	x_admin_show('编辑属性配置','${cpath}/sysadminMain/properties/operatorPage.do?act=update&propKey='+key);
		    }
		  });
	  	//查询操作
	  	var $ = layui.$, active = {
		  reload: function(){
			  webLoading();
		      var propKeyReload = $('#propKeyId').val();
		      //执行重载
		      table.reload('tablecontainerRecord', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		        	propKey: propKeyReload
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
			$('#propKeyId').val("");
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
		arr.push("System:properties:delete");
		arr.push("System:properties:add");
		arr.push("System:properties:update");
		arr.push("System:properties:show");
		permission_show(arr);
	}
	</script>
</html>