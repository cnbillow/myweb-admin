<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="x-body" id="addBodyId">
		<form id="addopformId" class="layui-form layui-form-middle">
			<input type="hidden" name="act" value="add"/>
			<jsp:include page="form.inc.jsp"/>
			<div class="layui-form-item">
				<label for="" class="layui-form-label"> </label>
				<button class="layui-btn layui-bg-blue" lay-filter="save" lay-submit="" type="button">新增菜单权限</button>
			</div>
		</form>
	</div>