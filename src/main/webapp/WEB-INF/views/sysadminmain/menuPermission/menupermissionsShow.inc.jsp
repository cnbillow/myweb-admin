<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="x-body" id="showBodyId">
		<form id="opformId" class="layui-form layui-form-middle">
			<input type="hidden" name="act" value="show"/>
			<input type="hidden" name="mpId" id="mpIdId" value="${mpId}"/>
			<div class="layui-form-item">
				<label for="L_username" class="layui-form-label"><span
					class="x-red">*</span>父级菜单权限：</label>
				<div class="layui-input-inline layui-input-middle">
					<select name="mpPId" id="mpPIdId">
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_roleDesc" class="layui-form-label"> <span
					class="x-red"></span>菜单权限名称：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_mpName" name="mpName" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="" class="layui-form-label"> <span
					class="x-red"></span>菜单权限图标：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_mpIcon" name="mpIcon" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_roleDesc" class="layui-form-label"> <span
					class="x-red"></span>权限编码：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_mpCode" name="mpCode" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="" class="layui-form-label"> <span
					class="x-red"></span>菜单权限URL：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_mpUrl" name="mpUrl" autocomplete="off" class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label for="roleStatusId" class="layui-form-label"> <span
					class="x-red">*</span>菜单权限状态：</label>
				<div class="layui-input-inline layui-input-middle">
		            <select name="mpStatus" id="mpStatusId">
		              <option value="Y">启用</option>
		              <option value="N">禁用</option>
		            </select>
		          </div>
			</div>
			<div class="layui-form-item">
				<label for="roleStatusId" class="layui-form-label"> <span
					class="x-red">*</span>菜单类型：</label>
				<div class="layui-input-inline layui-input-middle">
		            <select name="mpType" id="mpTypeId">
		              <option value="Menu">菜单</option>
		              <option value="Permission">权限</option>
		            </select>
		          </div>
			</div>
			<div class="layui-form-item">
				<label for="roleStatusId" class="layui-form-label"> <span
					class="x-red">*</span>是否需要权限：</label>
				<div class="layui-input-inline layui-input-middle">
		            <select name="isHasPermission" id="isHasPermissionId">
		              <option value="Y">是</option>
		              <option value="N">否</option>
		            </select>
		          </div>
			</div>
			<div class="layui-form-item">
				<label for="L_roleDesc" class="layui-form-label"> <span
					class="x-red"></span>排序：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_mpSort" name="mpSort" value="0" autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">最大排前面</div>
			</div>
			<div class="layui-form-item">
				<label for="updateTimeId" class="layui-form-label"> <span
					class="x-red">*</span>修改时间：</label>
				<div class="layui-input-inline layui-input-middle">
		            <input type="text" id="updateTimeId" name="updateTime"  autocomplete="off" class="layui-input">
		          </div>
			</div>
			<div class="layui-form-item">
				<label for="updateTimeId" class="layui-form-label"> <span
					class="x-red">*</span>添加时间：</label>
				<div class="layui-input-inline layui-input-middle">
		     		<input type="text" id="addTimeId" name="addTime"  autocomplete="off" class="layui-input">
		          </div>
			</div>
		</form>
	</div>