package cn.mycom.sysadmin.servicemgr.domain;

import java.io.Serializable;
import java.util.Date;

import cn.mycom.annotation.FileTypeAnnitation;
import cn.mycom.sysadmin.mgrrole.domain.MgrRole;

/**
 * 管理员实体类
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class ServiceMgr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6954980307214788809L;

	public static final String isAllowLogin_yes = "Y"; // 可以登录
	public static final String isAllowLogin_no = "N"; // 禁止登录
	@FileTypeAnnitation("Integer")
	private Integer id;
	private String loginName; // 帐号
	private String mobile; // 手机号码
	private String loginPassword; // 帐号登录密码
	private String isAllowLogin; // 用户是否可以登录
	private Date addTime; // 添加时间
	private Date updateTime; // 修改时间
	private String userMail;// 邮箱
	private String roleId;// 角色
	private String roleName;// 角色名称
	private MgrRole mgrRole;// 角色实体类

	private String clientIP;// 登录ip
	private String loginType;// 登录类型，wap or web

	private Date lastOpTime; // 最后操作时间

	public Date getLastOpTime() {
		return lastOpTime;
	}

	public void setLastOpTime(Date lastOpTime) {
		this.lastOpTime = lastOpTime;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getIsAllowLogin() {
		return isAllowLogin;
	}

	public void setIsAllowLogin(String isAllowLogin) {
		this.isAllowLogin = isAllowLogin;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public MgrRole getMgrRole() {
		return mgrRole;
	}

	public void setMgrRole(MgrRole mgrRole) {
		this.mgrRole = mgrRole;
	}
}
