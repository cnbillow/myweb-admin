<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="x-body" id="showBodyId">
		<form id="opformId" class="layui-form layui-form-middle">
			<input type="hidden" name="act" value="show"/>
			<input type="hidden" name="roleId" id="roleIdId" value="${roleId}"/>
			<div class="layui-form-item">
				<label for="L_username" class="layui-form-label"><span
					class="x-red">*</span>角色名称：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_roleName" name="roleName" class="layui-input" value="">
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
						<%--
						<li><span><input name="menuPermissionIdsString"
								class="checkboxClass" type="checkbox" value="1"
								id="listMenuPermissions-1" /><label for="listMenuPermissions-1">系统管理</label></span>
							<ul style="margin-left: 35px;">
								<li><span><input name="menuPermissionIdsString"
										class="checkboxClass" type="checkbox" value="7"
										id="listMenuPermissions-7" /><label
										for="listMenuPermissions-7">角色管理</label></span>
									<ul style="margin-left: 35px;">
										<li><span><input name="menuPermissionIdsString"
												class="checkboxClass" type="checkbox" value="8"
												id="listMenuPermissions-8" /><label
												for="listMenuPermissions-8">查看</label></span></li>
										<li><span><input name="menuPermissionIdsString"
												class="checkboxClass" type="checkbox" value="9"
												id="listMenuPermissions-9" /><label
												for="listMenuPermissions-9">创建</label></span></li>
										<li><span><input name="menuPermissionIdsString"
												class="checkboxClass" type="checkbox" value="10"
												id="listMenuPermissions-10" /><label
												for="listMenuPermissions-10">修改</label></span></li>
										<li><span><input name="menuPermissionIdsString"
												class="checkboxClass" type="checkbox" value="11"
												id="listMenuPermissions-11" /><label
												for="listMenuPermissions-11">删除</label></span></li>
									</ul>
								</li>
								<li><span><input name="menuPermissionIdsString"
										class="checkboxClass" type="checkbox" value="12"
										id="listMenuPermissions-12" /><label
										for="listMenuPermissions-12">管理员管理</label></span>
									<ul style="margin-left: 35px;">
										<li><span><input name="menuPermissionIdsString"
												class="checkboxClass" type="checkbox" value="13"
												id="listMenuPermissions-13" /><label
												for="listMenuPermissions-13">查看</label></span></li>
										<li><span><input name="menuPermissionIdsString"
												class="checkboxClass" type="checkbox" value="14"
												id="listMenuPermissions-14" /><label
												for="listMenuPermissions-14">创建</label></span></li>
										<li><span><input name="menuPermissionIdsString"
												class="checkboxClass" type="checkbox" value="15"
												id="listMenuPermissions-15" /><label
												for="listMenuPermissions-15">修改</label></span></li>
										<li><span><input name="menuPermissionIdsString"
												class="checkboxClass" type="checkbox" value="16"
												id="listMenuPermissions-16" /><label
												for="listMenuPermissions-16">删除</label></span></li>
									</ul>
								</li>
							</ul>
						</li>
						<li><span><input name="menuPermissionIdsString"
								class="checkboxClass" type="checkbox" checked='checked'
								value="17" id="listMenuPermissions-17" /><label
								for="listMenuPermissions-17">数据统计</label></span>
							<ul style="margin-left: 35px;">
								<li><span><input name="menuPermissionIdsString"
										class="checkboxClass" type="checkbox" checked='checked'
										value="18" id="listMenuPermissions-18" /><label
										for="listMenuPermissions-18">订单数据</label></span>
									<ul style="margin-left: 35px;">
									</ul>
								</li>
								<li><span><input name="menuPermissionIdsString"
										class="checkboxClass" type="checkbox" checked='checked'
										value="19" id="listMenuPermissions-19" /><label
										for="listMenuPermissions-19">每日对账</label></span>
									<ul style="margin-left: 35px;">
									</ul>
								</li>
								<li><span><input name="menuPermissionIdsString"
										class="checkboxClass" type="checkbox" checked='checked'
										value="20" id="listMenuPermissions-20" /><label
										for="listMenuPermissions-20">页面访问统计</label></span>
									<ul style="margin-left: 35px;">
									</ul>
								</li>
								<li><span><input name="menuPermissionIdsString"
										class="checkboxClass" type="checkbox" value="21"
										id="listMenuPermissions-21" /><label
										for="listMenuPermissions-21">日报导出</label></span>
									<ul style="margin-left: 35px;">
									</ul>
								</li>
								<li><span><input name="menuPermissionIdsString"
										class="checkboxClass" type="checkbox" value="22"
										id="listMenuPermissions-22" /><label
										for="listMenuPermissions-22">周报导出</label></span>
									<ul style="margin-left: 35px;">
									</ul>
								</li>
								<li><span><input name="menuPermissionIdsString"
										class="checkboxClass" type="checkbox" value="23"
										id="listMenuPermissions-23" /><label
										for="listMenuPermissions-23">渠道编码管理</label></span>
									<ul style="margin-left: 35px;">
										<li><span><input name="menuPermissionIdsString"
												class="checkboxClass" type="checkbox" value="24"
												id="listMenuPermissions-24" /><label
												for="listMenuPermissions-24">创建</label></span></li>
										<li><span><input name="menuPermissionIdsString"
												class="checkboxClass" type="checkbox" value="25"
												id="listMenuPermissions-25" /><label
												for="listMenuPermissions-25">修改</label></span></li>
										<li><span><input name="menuPermissionIdsString"
												class="checkboxClass" type="checkbox" value="26"
												id="listMenuPermissions-26" /><label
												for="listMenuPermissions-26">删除</label></span></li>
									</ul>
								</li>
							</ul>
						</li>
					</ul>
					 --%>
				</div>
			</div>
			<div class="layui-form-item">
				<label for="roleStatusId" class="layui-form-label"> <span
					class="x-red">*</span>角色状态：</label>
				<div class="layui-input-inline layui-input-middle">
		            <select name="roleStatus" id="roleStatusId">
		              <option value="Y">启用</option>
		              <option value="N">禁用</option>
		            </select>
		          </div>
			</div>
			<div class="layui-form-item">
				<label for="L_roleDesc" class="layui-form-label"> <span
					class="x-red"></span>排序：</label>
				<div class="layui-input-inline layui-input-middle">
					<input type="text" id="L_roleSort" name="roleSort" value="0"  autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">最大排前面</div>
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