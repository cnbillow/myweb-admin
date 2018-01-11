<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include/taglib.jsp"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>未登录</title>
<script type="text/javascript">
window.location.href="${cpath}/sysadminLogin/login.do";
</script>
</head>