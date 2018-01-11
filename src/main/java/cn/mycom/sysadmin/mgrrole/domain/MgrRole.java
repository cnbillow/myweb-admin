package cn.mycom.sysadmin.mgrrole.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.mycom.annotation.FileTypeAnnitation;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
/**
 * 管理员角色实体类
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class MgrRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -926632616153582500L;
	public static final String roleStatus_yes = "Y";   	//启用
	public static final String roleStatus_no = "N";   	//禁用
	@FileTypeAnnitation("Integer")
	private Integer roleId;

	private String roleName;       //角色名称（程序校验唯一键）
	private String roleDesc;       	//角色描述
	private String roleStatus;   	//角色状态（启用Y;禁用N）
	private Integer roleSort;			//排序，最大的排最前面
	private Date updateTime;   		//修改时间
	private Date addTime;   		//添加时间
	
	//用于新增修改
	private String menuPermissionIdsString;   		//菜单权限id列表。逗号“,”分隔

	//角色所属菜单-权限列表
	private List<MenuPermission> menuPermissions = new ArrayList<MenuPermission>();
	//角色所属菜单-权限编码列表
	private List<String> menuPermissionCodes = new ArrayList<String>();
	//角色所属菜单-权限id列表
	private List<String> menuPermissionIds = new ArrayList<String>();
	
	//辅助用于增加关联表xxx_mgrRole_menuPermission
	private Integer mpId;
	
	public Integer getMpId() {
		return mpId;
	}

	public void setMpId(Integer mpId) {
		this.mpId = mpId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}

	public Integer getRoleSort() {
		return roleSort;
	}

	public void setRoleSort(Integer roleSort) {
		this.roleSort = roleSort;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public List<MenuPermission> getMenuPermissions() {
		return menuPermissions;
	}

	public void setMenuPermissions(List<MenuPermission> menuPermissions) {
		this.menuPermissions = menuPermissions;
	}

	public List<String> getMenuPermissionCodes() {
		return menuPermissionCodes;
	}

	public void setMenuPermissionCodes(List<String> menuPermissionCodes) {
		this.menuPermissionCodes = menuPermissionCodes;
	}

	public List<String> getMenuPermissionIds() {
		return menuPermissionIds;
	}

	public void setMenuPermissionIds(List<String> menuPermissionIds) {
		this.menuPermissionIds = menuPermissionIds;
	}

	public String getMenuPermissionIdsString() {
		return menuPermissionIdsString;
	}

	public void setMenuPermissionIdsString(String menuPermissionIdsString) {
		this.menuPermissionIdsString = menuPermissionIdsString;
	}
}
