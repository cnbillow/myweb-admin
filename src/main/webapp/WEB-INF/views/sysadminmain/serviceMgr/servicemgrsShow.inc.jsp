<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="x-body" id="showBodyId">
		<form id="opformId" class="layui-form layui-form-middle">
			<input type="hidden" name="act" value="show"/>
			<input type="hidden" name="adminId" id="adminIdId" value="${adminId}"/>
			<div class="layui-form-item">
				<label for="L_username" class="layui-form-label"><span
					class="x-red">*</span>角色名称：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_loginName" name="loginName" class="layui-input" value="">
				</div>
			</div>
			<%--
			<div class="layui-form-item">
				<label for="L_username" class="layui-form-label"><span
					class="x-red"></span>登录密码：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="password" id="L_loginPassword" name="loginPassword"  class="layui-input" value="">
				</div>
				<div class="layui-form-mid layui-word-aux">可不填，不填为默认值</div>
			</div>
			 --%>
			<div class="layui-form-item">
				<label for="L_roleDesc" class="layui-form-label"> <span
					class="x-red"></span>帐号手机：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_mobile" name="mobile" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_roleDesc" class="layui-form-label"> <span
					class="x-red"></span>帐号邮箱：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_userMail" name="userMail" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="" class="layui-form-label"> <span
					class="x-red"></span>角色名称：</label>
				<div class="layui-input-inline layui-input-middle">
					<select name="roleId" id="roleIdId">
					</select>	
				</div>
			</div>
			<div class="layui-form-item">
				<label for="roleStatusId" class="layui-form-label"> <span
					class="x-red">*</span>管理员状态：</label>
				<div class="layui-input-inline layui-input-middle">
		            <select name="isAllowLogin" id="isAllowLoginId">
		              <option value="Y">启用</option>
		              <option value="N">禁用</option>
		            </select>
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