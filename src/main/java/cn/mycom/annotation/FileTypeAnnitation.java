package cn.mycom.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * 需要记录操作日志的，int主键加注解@FileTypeAnnitation("Integer")
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
public @interface FileTypeAnnitation {
	public abstract String value();
}
