package cn.mycom.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * 菜单权限注解
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.METHOD })
public @interface MenuPermissionsMapping {
	public abstract String mpType() default "";
	public abstract String value();
}
