package cn.mycom.base.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 序列表实体类
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class SerializableNumber implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3093384695927581984L;
	private String serialKey; // 序列键值
	private int currentNumber; // 当前编号值
	private String remark; // 备注
	private Date updateTime; // 更新时间
	private Date addTime; // 添加时间

	public String getSerialKey() {
		return serialKey;
	}

	public void setSerialKey(String serialKey) {
		this.serialKey = serialKey;
	}

	public int getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
