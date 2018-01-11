package cn.mycom.base.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 属性实体类
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class Properties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3093384695927581984L;
	private String propKey; // 属性键值
	private String propValue; // 当前编号值
	private String remark; // 备注
	private Date updateTime; // 更新时间
	private Date addTime; // 添加时间

	public String getPropKey() {
		return propKey;
	}

	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
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
