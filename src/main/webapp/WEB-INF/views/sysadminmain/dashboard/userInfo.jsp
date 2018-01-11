<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.mycom.common.CommonUtils"%>
<%@page import="cn.mycom.sysadmin.servicemgr.domain.ServiceMgr"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>个人信息</title>
<jsp:include page="/include/meta.inc.jsp" />
<jsp:include page="/include/css.inc.jsp" />
</head>
<body>
	<div class="x-body">
        <form class="layui-form layui-form-middle">
          <div class="layui-form-item">
              <label for="L_email" class="layui-form-label">
                  <span class="x-red">*</span>登录帐号
              </label>
              <div class="layui-input-inline layui-input-middle">
                  <input type="text" readonly="readonly" id="L_loginName" name=loginName
                  autocomplete="off" class="layui-input" value="${user.loginName}">
              </div>
          </div>
          <div class="layui-form-item">
              <label for="L_email" class="layui-form-label">
                  <span class="x-red">*</span>联系手机号
              </label>
              <div class="layui-input-inline layui-input-middle">
                  <input type="text" readonly="readonly" id="L_mobile" name=mobile
                  autocomplete="off" class="layui-input" value="${user.mobile}">
              </div>
          </div>
          <div class="layui-form-item">
              <label for="L_email" class="layui-form-label">
                  <span class="x-red">*</span>联系邮箱
              </label>
              <div class="layui-input-inline layui-input-middle">
                  <input type="text" readonly="readonly" id="L_userMail" name=userMail
                  autocomplete="off" class="layui-input" value="${user.userMail}">
              </div>
          </div>
          <div class="layui-form-item">
              <label for="L_email" class="layui-form-label">
                  <span class="x-red">*</span>角色名称
              </label>
              <div class="layui-input-inline layui-input-middle">
                  <input type="text" readonly="readonly" id="L_mgrRole.roleName" name=mgrRole.roleName
                  autocomplete="off" class="layui-input" value="${user.mgrRole.roleName}">
              </div>
          </div>
          <div class="layui-form-item">
              <label for="L_email" class="layui-form-label">
                  <span class="x-red">*</span>最后登录时间
              </label>
              <div class="layui-input-inline layui-input-middle">
                  <input type="text" readonly="readonly" id="L_loginTimeStr" name=loginTimeStr
                  autocomplete="off" class="layui-input" value="${loginLog.loginTimeStr}">
              </div>
          </div>
          <div class="layui-form-item">
              <label for="L_email" class="layui-form-label">
                  <span class="x-red">*</span>最后登录IP
              </label>
              <div class="layui-input-inline layui-input-middle">
                  <input type="text" readonly="readonly" id="L_ip" name=ip
                  autocomplete="off" class="layui-input" value="${loginLog.ip}">
              </div>
          </div>
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
              </label>
          </div>
      </form>
    </div>
</body>
<jsp:include page="/include/js.inc.jsp" />
</html>