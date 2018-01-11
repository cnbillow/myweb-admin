package cn.mycom.spring.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class SpringContextHelper implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}
	
	/**
	 * 根据名字获取bean
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}
	
	/**
	 * 根据bean类型获取bean
	 * @param classType
	 * @return
	 */
	public static <T> T getBean(Class<T> classType){
		checkApplicationContext();
		return applicationContext.getBean(classType);
	}
	
	public void setApplicationContext(
			ApplicationContext applicationContext) {
		SpringContextHelper.applicationContext = applicationContext;
	}
	
	/**
	 * 检测applicationContext是否为空
	 */
	private static void checkApplicationContext(){
		if(applicationContext==null){
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHelper");
		}
	}

}
