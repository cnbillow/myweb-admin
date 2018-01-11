<%@page import="cn.mycom.utils.prop.PropSystemUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.mycom.common.CommonUtils"%>
<%@page import="cn.mycom.sysadmin.servicemgr.domain.ServiceMgr"%>
<!DOCTYPE html>
<html>
<head>
<%
	request.setAttribute("resServiceUrl",
			PropSystemUtils.websiteResServiceUrl);
%>
<meta charset="UTF-8">
<title>上传test</title>
<jsp:include page="/include/meta.inc.jsp" />
<jsp:include page="/include/css.inc.jsp" />
</head>
<body>
	<div class="x-body">
		<form class="layui-form layui-form-middle"
			action="${cpath }/sysadminMain/res/uploadFile.do" method="post"
			enctype="multipart/form-data">
			<input type="file" name="commonUploadFile"><br />
			<button type="submit">上传图片</button>
		</form>

		<form class="layui-form layui-form-middle">
			<blockquote class="layui-elem-quote">为节省服务器开销，以下示例均未配置真实上传接口，所以每次上传都会报提示：请求上传接口出现异常，这属于正常现象。</blockquote>
			<fieldset class="layui-elem-field layui-field-title"
				style="margin-top: 30px;">
				<legend>选完文件后不自动上传</legend>
			</fieldset>

			<div class="layui-upload">
				<button type="button" class="layui-btn layui-btn-normal" id="test8">选择文件</button>
				<button type="button" class="layui-btn" id="test9">开始上传</button>
				<img id="showImgId"/>
			</div>
		</form>
	</div>
</body>
<jsp:include page="/include/js.inc.jsp" />
<script type="text/javascript">
layui.use('upload', function(){
	  var $ = layui.jquery,upload = layui.upload;
		//选完文件后不自动上传
		upload.render({
			elem : '#test8',
			url : '${cpath }/sysadminMain/res/uploadFile.do?field=commonUploadFile',//固定字段，跟程序结合
			auto : false,
			field:"commonUploadFile",//固定字段，跟程序结合
			//multiple: true,
			bindAction : '#test9',
			done : function(res) {
				webMsg(res.message);
				if(res.success){
					$("#showImgId").attr("src",res.url);
				}
			},error: function(){
				webMsg("系统繁忙，请求失败！");
		    }
		});
	});
</script>
</html>
