<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="x-body" id="showBodyId">
	<form id="opformId" class="layui-form layui-form-middle">
		<input type="hidden" name="act" value="show" /> <input type="hidden"
			name="testId" id="testIdId" value="${testId}" />
		<div class="layui-form-item">
			<label for="L_name" class="layui-form-label"><span
				class="x-red">*</span>名称：</label>
			<div class="layui-input-inline layui-input-middle">
				<input type="text" id="L_name" name="name" required=""
					lay-verify="required" class="layui-input" value="">
			</div>
		</div>
	</form>
</div>