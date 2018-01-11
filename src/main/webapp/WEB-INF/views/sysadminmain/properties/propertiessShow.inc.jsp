<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="x-body" id="showBodyId">
		<form id="opformId" class="layui-form layui-form-middle">
			<input type="hidden" name="act" value="show"/>
			<input type="hidden" name="propKey" id="propKeyId" value="${propKey}"/>
			<div class="layui-form-item">
				<label for="L_propKey" class="layui-form-label"><span
					class="x-red">*</span>属性键值：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_propKey" name="propKey"  class="layui-input" value="">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_propValue" class="layui-form-label"><span
					class="x-red"></span>属性参数长值：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_propValue" name="propValue" class="layui-input" value="">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_remark" class="layui-form-label"> <span
					class="x-red"></span>备注：</label>
				<div class="layui-input-inline layui-input-big">
					<input type="text" id="L_remark" name="remark" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="updateTimeId" class="layui-form-label"> <span
					class="x-red">*</span>修改时间：</label>
				<div class="layui-input-inline layui-input-middle">
		            <input type="text" id="updateTimeId" name="roleSort"  autocomplete="off" class="layui-input">
		          </div>
			</div>
			<div class="layui-form-item">
				<label for="updateTimeId" class="layui-form-label"> <span
					class="x-red">*</span>添加时间：</label>
				<div class="layui-input-inline layui-input-middle">
		     		<input type="text" id="addTimeId" name="roleSort"  autocomplete="off" class="layui-input">
		          </div>
			</div>
		</form>
	</div>