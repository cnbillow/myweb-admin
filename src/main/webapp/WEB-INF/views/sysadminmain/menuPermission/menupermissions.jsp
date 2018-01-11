<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.mycom.common.CommonUtils"%>
<%@page import="cn.mycom.sysadmin.servicemgr.domain.ServiceMgr"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>菜单权限管理</title>
<jsp:include page="/include/meta.inc.jsp" />
<jsp:include page="/include/css.inc.jsp" />
</head>
<body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="javascript:;" onclick="goindex()">首页</a>
        <a href="#">菜单权限管理</a>
        <a>
          <cite>菜单权限列表</cite></a>
      </span>
      <a class="layui-btn layui-btn-small layui-refresh" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
    <div class="x-body">
			<div class="layui-row">
				<div class="layui-form layui-col-md12 x-so">
		          <div class="layui-input-inline">
		            <select name="contrller" id="mpPId">
		              
		            </select>
		          </div>
		          <div class="layui-input-inline">
		            <select name="contrller" id="mpStatusId">
		              <option value="">菜单权限状态</option>
		              <option value="Y">已启用</option>
		              <option value="N">禁用</option>
		            </select>
		          </div>
		          <div class="layui-input-inline">
		            <select name="contrller" id="mpTypeId">
		              <option value="">菜单权限类型</option>
		              <option value="Menu">菜单</option>
		              <option value="Permission">权限</option>
		            </select>
		        </div>
		      </div>
		  </div>
         <div class="layui-row mt0">
         	<div class="layui-form layui-col-md12 x-so">
	        	<input type="text" name="mpName" id="mpNameId"  placeholder="请输入菜单名称，菜单url，菜单权限编码" autocomplete="off" class="layui-input layui-input-middle">
          		<button class="layui-btn" id="queryBtnId" lay-submit="" data-type="reload" type="button"><i class="layui-icon">&#xe615;</i></button>
          		<button class="layui-btn layui-btn-primary" id="resetBtnId" lay-submit="" type="button"><i class="layui-icon">&#x1002;</i></button>
      		</div>
      	</div>
      <xblock>
        <!-- <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除</button> -->
        <button class="layui-btn pt0"  data-permission="System:menu:add" onclick="x_admin_show_big('新增菜单权限','${cpath}/sysadminMain/menuPermission/operatorPage.do?act=add')">&nbsp;<i class="layui-icon"></i>新增&nbsp;&nbsp;</button>
      </xblock>
      <!-- 示例-970 -->
	<table class="layui-table" lay-data="{ url:'${cpath}/sysadminMain/menuPermission/interface/menuPermissions.do',cellMinWidth: 80, page:true,loading:false,done: function(res, curr, count){donefunction(res, curr, count);initMenuPermission();}, id:'tablecontainerRecord'}" lay-filter="operator">
	  <thead>
	    <tr>
	      <th lay-data="{field:'mpPName',templet:'#parentTpl'}">父级菜单权限名称</th>
	      <th lay-data="{field:'mpName',templet:'#thisTpl'}">菜单权限名称</th>
	      <th lay-data="{field:'mpCode'}">权限编码</th>
	      <th lay-data="{field:'mpUrl'}">菜单权限URL</th>
	      <th lay-data="{field:'mpType',templet: '#mpTypeTpl'}">菜单类型</th>
	      <th lay-data="{field:'mpStatus',templet: '#mpStatusTpl'}">菜单权限状态</th>
	      <th lay-data="{field:'mpSort', sort: true}">排序</th>
	      <th lay-data="{field:'updateTime',width:160, sort: true}">修改时间</th>
	      <th lay-data="{align:'center',width:180, toolbar: '#baroperatorTpl'}">操作</th>
	    </tr>
	  </thead>
	</table>
	<!-- 操作模版 -->
	<script type="text/html" id="baroperatorTpl">
  		<a data-permission="System:menu:show" class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail" title="查看">查看</a>
  		<a data-permission="System:menu:update" class="layui-btn layui-btn-xs" lay-event="edit" title="编辑">编辑</a>
  		<a data-permission="System:menu:delete" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" title="删除">删除</a>
	</script>
	<script type="text/html" id="mpStatusTpl">
	{{#  if(d.mpStatus === 'Y'){ }}
		<span class="layui-btn layui-btn-normal layui-btn-xs">已启用</span>
  	{{#  } else { }}
    	<span class="layui-btn layui-btn-primary layui-btn-xs">禁用</span>
	{{#  } }}
	</script>
	<script type="text/html" id="parentTpl">
		<a href="javascript:;" onclick="selectPId('{{d.mpPId}}')" class="font-blue">{{d.mpPName}}</a>
	</script>
	<script type="text/html" id="thisTpl">
	{{#  if(d.mpType === 'Menu'){ }}
		<a href="javascript:;" onclick="selectPId('{{d.mpId}}')" class="font-blue">{{d.mpName}}</a>
	{{#  } else { }}
		<a href="javascript:;">{{d.mpName}}</a>
	{{#  } }}
	</script>
	<script type="text/html" id="mpTypeTpl">
	{{#  if(d.mpType === 'Menu'){ }}
		<span class="layui-btn layui-btn-normal layui-btn-xs">菜单</span>
  	{{#  } else { }}
    	<span class="layui-btn layui-btn-primary layui-btn-xs">权限</span>
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
		    	x_admin_show_big('查看菜单权限','${cpath}/sysadminMain/menuPermission/operatorPage.do?act=show&mpId='+data.mpId);
		    } else if(obj.event === 'del'){
			      genDelAction('${cpath}/sysadminMain/menuPermission/interface/menuPermissionsToDelete.do',
			    		  {mpId:data.mpId},'确定删除此记录？',function(){$('#queryBtnId').click();},null);
		    } else if(obj.event === 'edit'){
		    	x_admin_show_big('编辑菜单权限','${cpath}/sysadminMain/menuPermission/operatorPage.do?act=update&mpId='+data.mpId);
		    }
		  });
	  	//查询操作
	  	var $ = layui.$, active = {
		  reload: function(){
			  webLoading();
		      var mpNameReload = $('#mpNameId').val();
		      var mpPIdReload = $("#mpPId").val();
		      var mpTypeReload = $("#mpTypeId").val();
		      var mpStatusReload = $("#mpStatusId").val();
		      //执行重载
		      table.reload('tablecontainerRecord', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		        	mpName: mpNameReload,
		        	mpPId:mpPIdReload,
		        	mpType:mpTypeReload,
		        	mpStatus:mpStatusReload,
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
			$('#mpNameId').val("");
			$('#mpPId').next().find("dd").eq(0).click();
			$('#mpTypeId').next().find(".layui-select-tips").click();
			$('#mpStatusId').next().find(".layui-select-tips").click();
			$('#queryBtnId').click();
		  });
	  	//查询，重置按钮 end

	});
	</script>
	<script type="text/javascript">
	$(document).ready(function(){
		layui.use(['layer'], function() {
			setMpPidOptions(function(){
				//初始化loading
				webLoading();
				$('#resetBtnId').click();
			});
		});
	});
	function setMpPidOptions(){
		webLoading();
		doPostRequest(null,'${cpath}/sysadminMain/menuPermission/interface/menus.do', null, function (backData) {
			webLoading("hide");
			if(backData.success){
				if(!isNullObj(backData.result)){
					var rs = backData.result;
					$("#mpPId").html('<option value="0">顶级菜单</option>');
					setmp(rs,1);
					renderform();
				}
			}else{
				webMsg("查询失败！");
			}
		});
	}
	function setmp(rs,index){
		if(!isNullObj(rs)){
			for(var imp in rs){
				var mp = rs[imp];
				if(!isNullObj(mp.mpId)){
					var str = "";
					for(var i = 0; i < index; i++){
						str = str+"---";
					}
					$("#mpPId").append('<option value="'+mp.mpId+'">'+str+mp.mpName+'</option>');
					setmp(mp.children,index+1);
				}
			}
		}
	}
	//初始化菜单权限
	function initMenuPermission(){
		$("#mpPId").next().find("dl").addClass("text-left");
		var arr = new Array();
		arr.push("System:menu:delete");
		arr.push("System:menu:add");
		arr.push("System:menu:update");
		arr.push("System:menu:show");
		permission_show(arr);
	}
	function selectPId(pid){
		$("#mpPId").val(pid);
		renderform();
		$('#queryBtnId').click();
	}
	</script>
</html>