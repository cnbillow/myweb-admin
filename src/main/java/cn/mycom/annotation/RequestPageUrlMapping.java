package cn.mycom.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * 请求页面注解，拦截器获取到方法对应注解，则进行页面输出html
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.METHOD })
public @interface RequestPageUrlMapping {
	public abstract String value() default "";
}
