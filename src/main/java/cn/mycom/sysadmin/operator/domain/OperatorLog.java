package cn.mycom.sysadmin.operator.domain;

import java.io.Serializable;
import java.util.Date;

import cn.mycom.base.domain.QueryDateBean;

/** 
 * 操作日志表
 * 
 * @author vinseven
 * @date 2018-01-01
*/

public class OperatorLog extends QueryDateBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6329189100372475782L;
	
	private int opLogId;			//主键
	private String opAuName;		//操作用户名称
	private String opAuId;			//操作用户id
	private String opBusinessName;	//操作业务名称
	private String opBusinessDesc;	//操作业务描述
	private String opMethodType;	//操作方法类型（ADD新增，UPDATE修改，DELETE删除,BATCH_DELETE批量删除）
	private String opContent; 		//记录信息
	private String opJsonContent; 	//记录信息(json格式化)
	private String opIp;			//操作用户ip
	private Date addTime;			//添加时间
	
	public int getOpLogId() {
		return opLogId;
	}
	public void setOpLogId(int opLogId) {
		this.opLogId = opLogId;
	}
	public String getOpAuName() {
		return opAuName;
	}
	public void setOpAuName(String opAuName) {
		this.opAuName = opAuName;
	}
	public String getOpAuId() {
		return opAuId;
	}
	public void setOpAuId(String opAuId) {
		this.opAuId = opAuId;
	}
	public String getOpBusinessName() {
		return opBusinessName;
	}
	public void setOpBusinessName(String opBusinessName) {
		this.opBusinessName = opBusinessName;
	}
	public String getOpBusinessDesc() {
		return opBusinessDesc;
	}
	public void setOpBusinessDesc(String opBusinessDesc) {
		this.opBusinessDesc = opBusinessDesc;
	}
	public String getOpMethodType() {
		return opMethodType;
	}
	public void setOpMethodType(String opMethodType) {
		this.opMethodType = opMethodType;
	}
	public String getOpContent() {
		return opContent;
	}
	public void setOpContent(String opContent) {
		this.opContent = opContent;
	}
	public String getOpJsonContent() {
		return opJsonContent;
	}
	public void setOpJsonContent(String opJsonContent) {
		this.opJsonContent = opJsonContent;
	}
	public String getOpIp() {
		return opIp;
	}
	public void setOpIp(String opIp) {
		this.opIp = opIp;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
} 
