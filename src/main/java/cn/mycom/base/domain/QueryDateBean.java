package cn.mycom.base.domain;

import java.util.Date;

import cn.mycom.utils.date.DateUtils;
import cn.mycom.utils.string.StringUtils;

/**
 * 支持查询时间实体类
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class QueryDateBean {

	public void setQueryDate() {
		String format1 = this.getDateFormat1();
		if (!StringUtils.isNull(this.getStartDateStr1()) || !StringUtils.isNull(this.getEndDateStr1())) {
			if (!StringUtils.isNull(this.getStartDateStr1())) {
				try {
					this.setStartDate1(DateUtils.parseDate(this.getStartDateStr1(), format1));
				} catch (Exception e) {
					this.setStartDate1(null);
				}
			}
			if (!StringUtils.isNull(this.getEndDateStr1())) {
				try {
					this.setEndDate1(DateUtils.parseDate(this.getEndDateStr1(), format1));
				} catch (Exception e) {
					this.setEndDate1(null);
				}
			}
		}

		String format2 = this.getDateFormat2();
		if (!StringUtils.isNull(this.getStartDateStr2()) || !StringUtils.isNull(this.getEndDateStr2())) {
			if (!StringUtils.isNull(this.getStartDateStr2())) {
				try {
					this.setStartDate2(DateUtils.parseDate(this.getStartDateStr2(), format2));
				} catch (Exception e) {
					this.setStartDate2(null);
				}
			}
			if (!StringUtils.isNull(this.getEndDateStr2())) {
				try {
					this.setEndDate2(DateUtils.parseDate(this.getEndDateStr2(), format2));
				} catch (Exception e) {
					this.setEndDate2(null);
				}
			}
		}

		String format3 = this.getDateFormat3();
		if (!StringUtils.isNull(this.getStartDateStr3()) || !StringUtils.isNull(this.getEndDateStr3())) {
			if (!StringUtils.isNull(this.getStartDateStr3())) {
				try {
					this.setStartDate3(DateUtils.parseDate(this.getStartDateStr3(), format3));
					;
				} catch (Exception e) {
					this.setStartDate3(null);
				}
			}
			if (!StringUtils.isNull(this.getEndDateStr3())) {
				try {
					this.setEndDate3(DateUtils.parseDate(this.getEndDateStr3(), format3));
					;
				} catch (Exception e) {
					this.setEndDate3(null);
				}
			}
		}
	}

	private String dateFormat1 = "yyyy-MM-dd HH:mm:ss";
	private String dateFormat2 = "yyyy-MM-dd HH:mm:ss";
	private String dateFormat3 = "yyyy-MM-dd HH:mm:ss";

	public String getDateFormat1() {
		return dateFormat1;
	}

	public void setDateFormat1(String dateFormat1) {
		this.dateFormat1 = dateFormat1;
	}

	public String getDateFormat2() {
		return dateFormat2;
	}

	public void setDateFormat2(String dateFormat2) {
		this.dateFormat2 = dateFormat2;
	}

	public String getDateFormat3() {
		return dateFormat3;
	}

	public void setDateFormat3(String dateFormat3) {
		this.dateFormat3 = dateFormat3;
	}

	private String startDateStr1;

	private String endDateStr1;

	private Date startDate1;

	private Date endDate1;

	private String startDateStr2;

	private String endDateStr2;

	private Date startDate2;

	private Date endDate2;

	private String startDateStr3;

	private String endDateStr3;

	private Date startDate3;

	private Date endDate3;

	public String getStartDateStr1() {
		return startDateStr1;
	}

	public void setStartDateStr1(String startDateStr1) {
		this.startDateStr1 = startDateStr1;
	}

	public String getEndDateStr1() {
		return endDateStr1;
	}

	public void setEndDateStr1(String endDateStr1) {
		this.endDateStr1 = endDateStr1;
	}

	public Date getStartDate1() {
		return startDate1;
	}

	public void setStartDate1(Date startDate1) {
		this.startDate1 = startDate1;
	}

	public Date getEndDate1() {
		return endDate1;
	}

	public void setEndDate1(Date endDate1) {
		this.endDate1 = endDate1;
	}

	public String getStartDateStr2() {
		return startDateStr2;
	}

	public void setStartDateStr2(String startDateStr2) {
		this.startDateStr2 = startDateStr2;
	}

	public String getEndDateStr2() {
		return endDateStr2;
	}

	public void setEndDateStr2(String endDateStr2) {
		this.endDateStr2 = endDateStr2;
	}

	public Date getStartDate2() {
		return startDate2;
	}

	public void setStartDate2(Date startDate2) {
		this.startDate2 = startDate2;
	}

	public Date getEndDate2() {
		return endDate2;
	}

	public void setEndDate2(Date endDate2) {
		this.endDate2 = endDate2;
	}

	public String getStartDateStr3() {
		return startDateStr3;
	}

	public void setStartDateStr3(String startDateStr3) {
		this.startDateStr3 = startDateStr3;
	}

	public String getEndDateStr3() {
		return endDateStr3;
	}

	public void setEndDateStr3(String endDateStr3) {
		this.endDateStr3 = endDateStr3;
	}

	public Date getStartDate3() {
		return startDate3;
	}

	public void setStartDate3(Date startDate3) {
		this.startDate3 = startDate3;
	}

	public Date getEndDate3() {
		return endDate3;
	}

	public void setEndDate3(Date endDate3) {
		this.endDate3 = endDate3;
	}

}