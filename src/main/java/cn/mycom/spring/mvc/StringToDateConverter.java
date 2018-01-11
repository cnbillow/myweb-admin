package cn.mycom.spring.mvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.core.convert.converter.Converter;

/**
 * 将日期字符串转换为日期的转换实现
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class StringToDateConverter implements Converter<String, Date> {

	/**
	 * 带时分秒的日期格式
	 */
	public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 不带时分秒的日期格式
	 */
	public final static String YYYY_MM_DD = "yyyy-MM-dd";

	public Date convert(String source) {
		if (source != null) {

			// 处理不带时分秒的
			if (source.length() == 10 || source.trim().length() == 10)
				return this.stringToDate(source, YYYY_MM_DD);

			// 处理带时分秒的
			else if (source.length() == 19 || source.trim().length() == 19)
				return this.stringToDate(source, YYYY_MM_DD_HH_MM_SS);
		}

		return null;
	}

	/**
	 * 将字符型数字转为Date类型
	 */
	private Date stringToDate(String dateString, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
		try {
			Date date = sdf.parse(dateString);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}