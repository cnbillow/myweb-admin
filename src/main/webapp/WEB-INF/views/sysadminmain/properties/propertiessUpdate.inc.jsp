<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="x-body" id="updateBodyId">
		<form id="updateopformId" class="layui-form layui-form-middle">
			<input type="hidden" name="act" value="update"/>
			<input type="hidden" name="propKey" id="propKeyId" value="${propKey}"/>
			<jsp:include page="form.inc.jsp"/>
			<div class="layui-form-item">
				<label for="" class="layui-form-label"> </label>
				<button class="layui-btn layui-bg-blue" lay-filter="update" lay-submit="" type="button">编辑属性配置</button>
			</div>
		</form>
	</div>