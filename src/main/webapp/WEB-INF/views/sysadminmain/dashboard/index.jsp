<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.mycom.common.CommonUtils"%>
<%@page import="cn.mycom.sysadmin.servicemgr.domain.ServiceMgr"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的桌面</title>
<jsp:include page="/include/meta.inc.jsp" />
<jsp:include page="/include/css.inc.jsp" />
</head>
<body>
	<div class="x-body">
		<blockquote class="layui-elem-quote layui-quote-nm font-14px">
			<font class="font-14px">
			欢迎您！<font class="font-blue font-14px"><b class="font-14px">[${roleName}:${loginName}]</b></font><br/>
			最后登录时间：${loginLog.loginTimeStr}<br/>
			最后登录IP：${loginLog.ip}<br/>
			</font>
			</blockquote>
			<%--
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md3">
				<div class="info-box">
					<span class="info-box-icon" style="background-color: #00a65a !important; color: white;">
						<i class="layui-icon font-44px" aria-hidden="true">&#xe657;</i>
					</span>
					<div class="info-box-content">
						<span class="info-box-text">新订单</span> <span
							class="info-box-number">654</span>
					</div>
				</div>
			</div>
			<div class="layui-col-md3">
				<div class="info-box">
					<span class="info-box-icon" style="background-color: #f39c12 !important; color: white;">
						<i class="layui-icon font-44px" aria-hidden="true">&#xe60a;</i>
					</span>
					<div class="info-box-content">
						<span class="info-box-text">账单</span> <span
							class="info-box-number">85</span>
					</div>
				</div>
			</div>
			<div class="layui-col-md3">
				<div class="info-box">
					<span class="info-box-icon" style="background-color: #dd4b39 !important; color: white;">
						<i class="layui-icon font-44px" aria-hidden="true">&#xe62c;</i>
					</span>
					<div class="info-box-content">
						<span class="info-box-text">今日总PV</span> <span
							class="info-box-number">85</span>
					</div>
				</div>
			</div>
			<div class="layui-col-md3">
				<div class="info-box">
					<span class="info-box-icon" style="background-color: #00c0ef  !important; color: white;">
						<i class="layui-icon font-44px" aria-hidden="true">&#xe613;</i>
					</span>
					<div class="info-box-content">
						<span class="info-box-text">今日总UV</span> <span
							class="info-box-number">95</span>
					</div>
				</div>
			</div>
		</div>
		
		<fieldset class="layui-elem-field">
			<legend>信息统计</legend>
			<div class="layui-field-box">
				<table class="layui-table" lay-even>
					<thead>
						<tr>
							<th>统计</th>
							<th>渠道</th>
							<th>订单</th>
							<th>产品编码</th>
							<th>PV</th>
							<th>UV</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>总数</td>
							<td>92</td>
							<td>9</td>
							<td>0</td>
							<td>8</td>
							<td>20</td>
						</tr>
						<tr>
							<td>今日</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
						</tr>
						<tr>
							<td>昨日</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
						</tr>
						<tr>
							<td>本周</td>
							<td>2</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
						</tr>
						<tr>
							<td>本月</td>
							<td>2</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
						</tr>
					</tbody>
				</table>
				<table class="layui-table">
					<thead>
						<tr>
							<th colspan="2" scope="col">服务器信息</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th width="30%">服务器计算机名</th>
							<td><span id="lbServerName">http://127.0.0.1/</span></td>
						</tr>
						<tr>
							<td>服务器IP地址</td>
							<td>192.168.1.1</td>
						</tr>
					</tbody>
				</table>
			</div>
		</fieldset>
			 --%>
	</div>
</body>
<jsp:include page="/include/js.inc.jsp" />
</html>