package cn.mycom.sysadmin.test.domain;

import java.io.Serializable;
import java.util.Date;

import cn.mycom.annotation.FileTypeAnnitation;
import cn.mycom.base.domain.QueryDateBean;

/**
 * tests表
 */
public class Tests extends QueryDateBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4161854046353914738L;
	@FileTypeAnnitation("Integer")
	private Integer testId;
	private String name;
	private Date updateTime;
	private Date addTime;

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
