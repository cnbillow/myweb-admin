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
		<form class="layui-form layui-form-middle">
			<div class="layui-row">
				<div class="layui-col-md12">	
					<div class="layui-form-item">
						<label for="" class="layui-form-label"> <span
							class="x-red">*</span>文章内容：</label>
						<div class="layui-input-inline layui-input-big">
							<textarea placeholder="请输入内容" id="L_content"  rows="6" name="content" class="layui-textarea"></textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<jsp:include page="/include/js.inc.jsp" />
<jsp:include page="/include/js.um.inc.jsp" />
<script type="text/javascript">
layui.use(['form','element'], function(){
	var ue = UM.getEditor('L_content');
	});
</script>
</html>
