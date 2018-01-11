package cn.mycom.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.mycom.annotation.FileTypeAnnitation;
import cn.mycom.spring.context.SpringContextHelper;
import cn.mycom.sysadmin.operator.domain.OperatorLogConfig;
import cn.mycom.sysadmin.operator.service.SysAdminOperatorLogService;
import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.utils.string.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 用于记录日志的方面组件，演示Spring AOP的各种通知类型。
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class OperateLogAspect {
	private static final Logger logger=LoggerFactory.getLogger(OperateLogAspect.class); 
	
	//@Pointcut("execution(* cn..*.dao.*Dao.add*(..)) or execution(* cn..*.dao.*Dao.update*(..)) or execution(* cn..*.dao.*Dao.delete*(..))")  
	//public void anyMethod(){}//定义一个切入点 
	
	/**
	 * 前置通知、后置通知、最终通知使用的方法
	//@Before("anyMethod()")
	public void log() {
		// 记录日志
		System.out.println("-->记录用户操作信息");
	}
	*/

	/**
	 * 环绕通知使用的方法
	 */
	//@Around("anyMethod()")
	public Object tolog(ProceedingJoinPoint p) throws Throwable {
		// 目标组件的类名
		String className = p.getTarget().getClass().getName();
		// 调用的方法名
		String method = p.getSignature().getName();
		String inteceptMethod = className + "." + method;
		logger.info("拦截方法名称："+inteceptMethod +" start...");
		Object obj = null;
		Object argsBean = null;//添加时候的实体信息（或者修改，删除的参数实体信息）
		Object findObj = null;//删除或者修改时候，通过pks查找的实体信息
		//config 使用字段列表
		String mtype = null;
		String classDomainName = null;
		String recordFields = null;
		String findDomainMethod = null;
		String pKIds = null;
		/*控制当前登录用户，目的是多线程时候，记录登录用户记录*/
		ServiceMgr currentLoginUser = null;
		try{
			SysAdminOperatorLogService opservice = (SysAdminOperatorLogService) getBean(SysAdminOperatorLogService.class);
			OperatorLogConfig config = opservice.findOperatorLogConfigByMethod(inteceptMethod);
			StringBuffer logContent = new StringBuffer();
			boolean allowOPDb = true;
			if(null != config){
				mtype = config.getOplMethodType();
				classDomainName = config.getOplDomainName();
				recordFields = config.getOplRecordFields();
				if(null != recordFields){
					recordFields = recordFields.replaceAll(" ", "");
				}
				findDomainMethod = config.getOplFindDomainMethod();
				pKIds = config.getOplPKIds();
				//获取操作日志配置，进行操作日志处理
				Object[] argsObj = p.getArgs();
				if(null != argsObj && argsObj.length > 0){
					argsBean = argsObj[0];
					//遍历获取 request,用于多线程记录日志时候，在多线程中传参request
					for(Object objReq : argsObj){
						
						if(null == currentLoginUser && objReq instanceof ServiceMgr){
							currentLoginUser =  (ServiceMgr)objReq;
						}
					}
				}
				if(null == argsBean){
					allowOPDb = false;
				}
				logger.info("拦截方法配置类型config.getOplMethodType()："+config.getOplMethodType());
				if(OperatorLogConfig.oplMethodType_ADD.equals(mtype)){
					logContent.append(getBeanContentByRecordFields(argsBean,classDomainName, recordFields));
				}else{
					//通过主键，查找不到数据，则不进行操作日志记录
					findObj = findBeanByPKIds(argsBean,classDomainName, recordFields,findDomainMethod,pKIds);
					if(findObj == null){
						allowOPDb = false;
					}
					if(OperatorLogConfig.oplMethodType_UPDATE.equals(mtype)){
						logContent.append(getBeanContentDeffByRecordFields(findObj,argsBean, classDomainName, recordFields,pKIds));
					}else if(OperatorLogConfig.oplMethodType_DELETE.equals(mtype)){
						logContent.append(getBeanContentByRecordFields(findObj, classDomainName, recordFields));
					}else if(OperatorLogConfig.oplMethodType_BATCH_DELETE.equals(mtype)){
						//批量未完善
					}
				}
				
			}
			logger.info("拦截方法配置 aop 环绕切面方法["+inteceptMethod+"]开始 start...");
			// 执行目标组件的方法之前
			obj = p.proceed();
			logger.info("拦截方法配置 aop 环绕切面方法["+inteceptMethod+"]开始 end...");
			Object requestOrLoinUser = null;
			requestOrLoinUser = httpServletRequest();
			if(currentLoginUser != null){
				requestOrLoinUser = currentLoginUser;
			}
			if(null != config && allowOPDb && "1".equals(String.valueOf(obj))){
				if(OperatorLogConfig.oplMethodType_ADD.equals(mtype)){
					opservice.addOperatorLog(requestOrLoinUser, config, logContent.toString(),((JSON) JSONObject.toJSON(argsBean)).toString());
				}else if(OperatorLogConfig.oplMethodType_UPDATE.equals(mtype)){
					List<Object> objlist = new ArrayList<Object>();
					//修改的时候，把原始数据和参数实体作为json保存起来
					objlist.add(findObj);
					objlist.add(argsBean);
					opservice.addOperatorLog(requestOrLoinUser, config, logContent.toString(),((JSON) JSONObject.toJSON(objlist)).toString());
				}else if(OperatorLogConfig.oplMethodType_DELETE.equals(mtype)){
					opservice.addOperatorLog(requestOrLoinUser, config, logContent.toString(),((JSON) JSONObject.toJSON(findObj)).toString());
				}else if(OperatorLogConfig.oplMethodType_BATCH_DELETE.equals(mtype)){
					//批量未完善
				}
			}
			// 执行目标组件的方法之后
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取SysAdminOperatorLogBO失败！"+e.getMessage());
		}
		logger.info("拦截方法名称："+inteceptMethod +" end...");
		return obj;
	}
	
	/**
	 * 获取request
	 * @return
	 */
	private HttpServletRequest httpServletRequest() {
		HttpServletRequest req = null;
		try{
			req =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		}catch(Exception e){
			req = null;
		}
		return req;
	}
	/**
	 * 反射获取，获取添加内容的字段列表文本
	 * @param argsBean
	 * @param oplRecordFields 逗号分隔的字段
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String getBeanContentByRecordFields(Object argsBean,String classDomainName, String oplRecordFields) {
		logger.info("反射获取，获取添加内容的字段列表文本：classDomainName="+classDomainName +",oplRecordFields="+oplRecordFields+" start...");
		String result = "";
		if(null != argsBean){
			StringBuffer sb = new StringBuffer();
			try{
				Class clazz = argsBean.getClass();
				if(clazz.getName().equals(classDomainName)){
					Field[] fileds = getDeclaredFiledsByRecords(oplRecordFields, clazz);
					if(null == fileds){
						return "";
					}
					for(Field filed : fileds){
						String fn = filed.getName();
						String fnMethod = getMethodByFieldName(fn);
						Method method = null;
						try {
							method = clazz.getDeclaredMethod(fnMethod, null);
							Object o = null;
							try {
								o = method.invoke(argsBean, null);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
							sb.append(fn+"="+o+",");
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
					}
				}
				if(sb.length() > 1){
					sb.setLength(sb.length()-1);
				}
			}catch(Exception e){
				logger.info("反射获取，获取添加内容的字段列表文本：classDomainName="+classDomainName +",oplRecordFields="+oplRecordFields+" error["+StringUtils.getExceptionInfo(e)+"]...");
			}
			result = sb.toString();
		}
		logger.info("反射获取，获取添加内容的字段列表文本：classDomainName="+classDomainName +",oplRecordFields="+oplRecordFields+" result["+result+"]...");
		logger.info("反射获取，获取添加内容的字段列表文本：classDomainName="+classDomainName +",oplRecordFields="+oplRecordFields+" end...");
		return result;
	}
	
	/**
	 * 反射获取，获取旧实体和新修改实体之间的改变值
	 * @param oldBean
	 * @param argsBean
	 * @param oplRecordFields 逗号分隔的字段
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String getBeanContentDeffByRecordFields(Object oldBean,Object argsBean,String classDomainName, String oplRecordFields,String pKIds) {
		if(null != argsBean && null != oldBean){
			StringBuffer sb = new StringBuffer();
			Class clazz = argsBean.getClass();
			Class clazzOld = oldBean.getClass();
			if(null != clazz && null != clazzOld && 
					clazz.getName().equals(classDomainName) && clazzOld.getName().equals(classDomainName)){
				Field[] fileds = getDeclaredFiledsByRecords(oplRecordFields, clazz);
				Field[] pkfileds = getDeclaredFiledsByRecords(pKIds, clazz);
				if(null == fileds){
					return "";
				}
				for(Field filed : fileds){
					String fn = filed.getName();
					String fnMethod = getMethodByFieldName(fn);
					Method method = null;
					Method methodOld = null;
					try {
						method = clazz.getDeclaredMethod(fnMethod, null);
						methodOld = clazzOld.getDeclaredMethod(fnMethod, null);
						Object o = null;
						Object oold = null;
						try {
							o = method.invoke(argsBean, null);
							oold = methodOld.invoke(oldBean, null);
							//判断是否为主键，主键必须记录
							boolean ispk = false;
							if(null != pkfileds){
								for(Field f : pkfileds){
									if(f.getName().equals(fn)){
										ispk = true;
										break;
									}
								}
							}
							//是主键，或者记录不相同，需要增加记录数据
							if(ispk || !String.valueOf(o).equals(String.valueOf(oold))){
								sb.append(fn+"="+o+",");
							}
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
				}
			}
			if(sb.length() > 1){
				sb.setLength(sb.length()-1);
			}
			return sb.toString();
		}
		return "";
	}
	
	/**
	 * 反射获取，获取对象的字段列表文本 by pKIds
	 * @param argsBean
	 * @param classDomainName
	 * @param recordFields
	 * @param findDomainMethod
	 * @param pKIds
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object findBeanByPKIds(Object argsBean, String classDomainName,
			String recordFields,String findDomainMethod,String pKIds) {
		//类路径+"."+方法名称
		String className = "";
		String methodName = "";
		int classIndex = findDomainMethod.lastIndexOf(".");
		if(-1 != classIndex){
			className = findDomainMethod.substring(0,classIndex);
			methodName = findDomainMethod.substring(classIndex+1);
		}
		try {
			Class clazz = Class.forName(className);
			Class clazzArgs = Class.forName(classDomainName);
			Object objArgs=clazzArgs.newInstance();
			//获取dao
			Object dao = getBean(clazz);
			if(null != dao){
				Method method = clazz.getDeclaredMethod(methodName, clazzArgs);
				//设置参数的主键，进行查询单实体操作
				setArgsByBeanAndPKs(argsBean,pKIds,objArgs);
				Object findObj = method.invoke(dao, objArgs);
				return findObj;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 设置参数的主键，进行查询单实体操作
	 * @param argsBean
	 * @param pKIds
	 * @param objArgs
	 */
	private void setArgsByBeanAndPKs(Object argsBean, String pKIds,
			Object objArgs) {
		Field[] fields = getDeclaredFiledsByRecords(pKIds, argsBean.getClass());
		for(Field f : fields){
			String fieldType = "String";
			try {
				Object o = f.getAnnotation(FileTypeAnnitation.class);
				if(o instanceof FileTypeAnnitation){
					FileTypeAnnitation fta = (FileTypeAnnitation)o;
					fieldType = fta.value();
				}
				String getmethod = getMethodByFieldName(f.getName());
				Method getm = argsBean.getClass().getDeclaredMethod(getmethod, null);
				Object key = getm.invoke(argsBean, null);
				
				String setmethod = setMethodByFieldName(f.getName());
				Method setm = null;
				if("Integer".equals(fieldType)){
					setm = objArgs.getClass().getDeclaredMethod(setmethod, Integer.class);
				}else if("Long".equals(fieldType)){
					setm = objArgs.getClass().getDeclaredMethod(setmethod, Long.class);
				}else{
					setm = objArgs.getClass().getDeclaredMethod(setmethod, String.class);
				}
				setm.invoke(objArgs, key);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * 获取声明字段by记录列表
	 * @param recordFields
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Field[] getDeclaredFiledsByRecords(String recordFields, Class clazz) {
		Field[] fileds = null;
		if(null != recordFields && !"".equals(recordFields)){
			String[] oplRecordFieldArr = recordFields.split(",");
			fileds = new Field[oplRecordFieldArr.length];
			int i = -1;
			for(String f : oplRecordFieldArr){
				try {
					i++;
					fileds[i] = clazz.getDeclaredField(f);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
			if(i == -1){
				fileds = null;
			}
		}
		return fileds;
	}
	
	/**
	 * get set Method start
	 */
	private String getMethodByFieldName(String fn) {
		String fnMethod = "get";
		fnMethod = fnMethod + methodByFiled(fn);
		return fnMethod;
	}
	private String setMethodByFieldName(String fn) {
		String fnMethod = "set";
		fnMethod = fnMethod + methodByFiled(fn);
		return fnMethod;
	}
	private String methodByFiled(String fn) {
		String fnMethod = "";
		if(fn.length() > 1){
			fnMethod += fn.substring(0,1).toUpperCase()+fn.substring(1);
		}else{
			fnMethod += fn.substring(0,1).toUpperCase();
		}
		return fnMethod;
	}
	/**
	 * get set Method end
	 */
	
	/**
	 * SpringContext 获取bean start
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unused")
	private Object getBean(String name){
		try{
			return SpringContextHelper.getBean(name);
		}catch(Exception e){
			logger.error("获取getBean["+name+"]失败！");
		}
		return null;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object getBean(Class clazz){
		try{
			return SpringContextHelper.getBean(clazz);
		}catch(Exception e){
			logger.error("获取getBean["+clazz+"]失败！");
		}
		return null;
	}
	/**
	 * SpringContext 获取bean end
	 * @param name
	 * @return
	 */
}