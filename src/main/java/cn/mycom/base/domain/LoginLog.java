package cn.mycom.base.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 登陆历史实体类
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class LoginLog extends QueryDateBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9096278575695768240L;
	public static final int LOGINTYPE_WAP = 0;
	public static final int LOGINTYPE_WEB = 1;

	public static final int USERTYPE_SUPER_ADMIN = 1;
	public static final int USERTYPE_GENERAL_USER = 0;

	private int loginId; // 自增id
	private int loginUserId; // 登录用户id
	private String loginName; // 登录用户名称
	private String ip; // 登陆ip
	private String userMobile; // 手机号
	private String aliasNum; // 别名
	private int provcode; // 省code
	private int areacode; // 地区code
	private String cardType; // 卡类型
	private int userType; // 用户类型;0普通用户，1管理员用户
	private int loginType; // 0-wap普通用户，1web普通用户
	private Date loginTime; // 登陆时间
	private String loginTimeStr; // 登陆时间

	public String getLoginTimeStr() {
		return loginTimeStr;
	}

	public void setLoginTimeStr(String loginTimeStr) {
		this.loginTimeStr = loginTimeStr;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public int getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(int loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getAliasNum() {
		return aliasNum;
	}

	public void setAliasNum(String aliasNum) {
		this.aliasNum = aliasNum;
	}

	public int getProvcode() {
		return provcode;
	}

	public void setProvcode(int provcode) {
		this.provcode = provcode;
	}

	public int getAreacode() {
		return areacode;
	}

	public void setAreacode(int areacode) {
		this.areacode = areacode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
