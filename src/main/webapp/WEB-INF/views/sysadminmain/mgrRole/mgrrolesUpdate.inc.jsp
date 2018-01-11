<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="x-body" id="updateBodyId">
		<form id="updateopformId" class="layui-form layui-form-middle">
			<input type="hidden" name="act" value="update"/>
			<input type="hidden" name="roleId" id="roleIdId" value="${roleId}"/>
			<div class="layui-form-item">
				<label for="L_username" class="layui-form-label"><span
					class="x-red">*</span>角色名称：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_roleName" name="roleName" required=""
						lay-verify="required" class="layui-input" value="">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_roleDesc" class="layui-form-label"> <span
					class="x-red"></span>角色描述：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_roleDesc" name="roleDesc" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="" class="layui-form-label"> <span
					class="x-red"></span>权限设置：</label>
				<div class="layui-input-inline layui-input-middle">
					<ul class="mt10" id="mpsId">
						
					</ul>
				</div>
			</div>
			<div class="layui-form-item">
				<label for="roleStatusId" class="layui-form-label"> <span
					class="x-red">*</span>角色状态：</label>
				<div class="layui-input-inline layui-input-middle">
		            <select name="roleStatus" id="roleStatusId" required=""
						lay-verify="required">
		              <option value="Y">启用</option>
		              <option value="N">禁用</option>
		            </select>
		          </div>
			</div>
			<div class="layui-form-item">
				<label for="L_roleDesc" class="layui-form-label"> <span
					class="x-red"></span>排序：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_roleSort" name="roleSort" required="" value="0"
						lay-verify="required|number" autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">最大排前面</div>
			</div>
			<div class="layui-form-item">
				<label for="" class="layui-form-label"> </label>
				<button class="layui-btn layui-bg-blue" lay-filter="update" lay-submit="" type="button">编辑角色</button>
			</div>
		</form>
	</div>