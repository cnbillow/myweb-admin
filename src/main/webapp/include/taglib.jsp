<%@page import="cn.mycom.utils.prop.PropSystemUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("cpath", request.getContextPath());
	request.setAttribute("cpathSysadminMainIndex", request.getContextPath()+"/sysadminMain/index.do");
	request.setAttribute("websiteName",PropSystemUtils.getPropValue("website.name", "mycom后台管理系统"));
	request.setAttribute("copyright",PropSystemUtils.getPropValue("copyright.desc", "Copyright &copy; 2017.彩讯科技股份有限公司"));
	request.setAttribute("version", "201711251200");
	request.setAttribute("favicon", "");
%>

<input type="text" value="${cpath}" style="display: none" id="cpath">
<input type="text" value="${resServiceUrl}" style="display: none" id="resServiceUrl">
<input type="text" value="${websiteName}" style="display: none" id="websiteName">
<input type="text" value="${copyright}" style="display: none" id="copyright">
<script>
cpath = '${cpath}';
cpathSysadminMainIndex = '${cpathSysadminMainIndex}';
</script>
