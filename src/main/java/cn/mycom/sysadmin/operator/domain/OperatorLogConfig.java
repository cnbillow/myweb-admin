package cn.mycom.sysadmin.operator.domain;

import java.io.Serializable;
import java.util.Date;

/** 
 * 操作日志配置表
 * 
 * @author vinseven
 * @date 2018-01-01
*/

public class OperatorLogConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5268628875363093190L;
	
	public static final String oplMethodType_ADD="ADD";
	public static final String oplMethodType_UPDATE="UPDATE";
	public static final String oplMethodType_DELETE="DELETE";
	public static final String oplMethodType_BATCH_DELETE="BATCH_DELETE";
	
	
	private int oplConfigId;			//主键
	private String oplBusinessName;		//操作业务名称
	private String oplBusinessDesc;		//操作业务描述
	private String oplMethodType;		//操作方法类型（ADD新增，UPDATE修改，DELETE删除,BATCH_DELETE批量删除）
	private String oplInterceptMethod;	//拦截方法全路径com.xxx.xx.getxxx()[拦截在dao层],必须提供实体作为参数（程序校验唯一键）
	private String oplDomainName;		//实体全路径com.xxx.xx.getxxx()
	private String oplPKIds;			//主键列表（多个逗号分隔）
	private String oplFindDomainMethod; //查找实体方法全路径com.xxx.xx.getxxx(),必须提供实体作为参数[配置在dao层]
	private String oplRecordFields;		//记录字段，默认空为全部字段
	private String oplStatus;			//启用状态（启用Y;禁用N）
	private Date updateTime;			//修改时间
	private Date addTime;				//添加时间
	public int getOplConfigId() {
		return oplConfigId;
	}
	public void setOplConfigId(int oplConfigId) {
		this.oplConfigId = oplConfigId;
	}
	public String getOplBusinessName() {
		return oplBusinessName;
	}
	public void setOplBusinessName(String oplBusinessName) {
		this.oplBusinessName = oplBusinessName;
	}
	public String getOplBusinessDesc() {
		return oplBusinessDesc;
	}
	public void setOplBusinessDesc(String oplBusinessDesc) {
		this.oplBusinessDesc = oplBusinessDesc;
	}
	public String getOplMethodType() {
		return oplMethodType;
	}
	public void setOplMethodType(String oplMethodType) {
		this.oplMethodType = oplMethodType;
	}
	public String getOplInterceptMethod() {
		return oplInterceptMethod;
	}
	public void setOplInterceptMethod(String oplInterceptMethod) {
		this.oplInterceptMethod = oplInterceptMethod;
	}
	public String getOplDomainName() {
		return oplDomainName;
	}
	public void setOplDomainName(String oplDomainName) {
		this.oplDomainName = oplDomainName;
	}
	public String getOplPKIds() {
		return oplPKIds;
	}
	public void setOplPKIds(String oplPKIds) {
		this.oplPKIds = oplPKIds;
	}
	public String getOplFindDomainMethod() {
		return oplFindDomainMethod;
	}
	public void setOplFindDomainMethod(String oplFindDomainMethod) {
		this.oplFindDomainMethod = oplFindDomainMethod;
	}
	public String getOplRecordFields() {
		return oplRecordFields;
	}
	public void setOplRecordFields(String oplRecordFields) {
		this.oplRecordFields = oplRecordFields;
	}
	public String getOplStatus() {
		return oplStatus;
	}
	public void setOplStatus(String oplStatus) {
		this.oplStatus = oplStatus;
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

	
	
} 
