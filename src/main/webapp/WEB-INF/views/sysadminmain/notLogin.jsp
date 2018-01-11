<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include/taglib.jsp"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>未登录</title>
</head>
<body>
<script type="text/javascript">
function tologin(){
	if(window.parent){ 
		parent.location.href="${cpath}/sysadminLogin/login.do";
	}else{
		window.location.href="${cpath}/sysadminLogin/login.do";
	}
}
</script>
<%
if("Home".equals(request.getParameter("page"))){
%>
	<script type="text/javascript">
		tologin();
	</script>
<%}else{ %>	
	当前未登录，请点击<a href="javascript:;" onclick="tologin()">登录</a>！
<%}%>		
</body>
</html>