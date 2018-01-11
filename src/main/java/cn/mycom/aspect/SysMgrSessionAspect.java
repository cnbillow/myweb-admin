package cn.mycom.aspect;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.mycom.common.CommonUtils;
import cn.mycom.constants.Constants;
import cn.mycom.spring.context.SpringContextHelper;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.menupermission.mapper.SysAdminMenuPermissionDao;
import cn.mycom.sysadmin.mgrrole.domain.MgrRole;
import cn.mycom.sysadmin.mgrrole.service.SysAdminMgrRoleService;
import cn.mycom.utils.string.StringUtils;

/**
 * 用于方法操作后（暂时为，菜单增删改，角色增删改），拦截在service层，重置登录用户和超级管理员角色的aop拦截
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class SysMgrSessionAspect {
	private static final Logger logger = LoggerFactory.getLogger(SysMgrSessionAspect.class);

	/**
	 * 后置通知使用的方法
	 */
	public void afterOperator(JoinPoint joinPoint) throws Throwable {
		String[] afterOpMethods = new String[] { "SysAdminMgrRoleService.addMgrRole",
				"SysAdminMgrRoleService.updateMgrRoleById", "SysAdminMgrRoleService.deleteMgrRoleById",
				"SysAdminMenuPermissionService.addMenuPermission",
				"SysAdminMenuPermissionService.updateMenuPermissionById",
				"SysAdminMenuPermissionService.deleteMenuPermissionById" };
		// 目标组件的类名
		String className = joinPoint.getTarget().getClass().getName();
		// 调用的方法名
		String method = joinPoint.getSignature().getName();
		String inteceptMethod = className + "." + method;
		logger.info("拦截方法名称：" + inteceptMethod + " start...");
		try {
			// 执行目标组件的方法之前
			logger.info("SysMgrSessionAspect拦截方法配置 aop 后置切面方法[" + inteceptMethod + "]start...");
			if (StringUtils.indexOfByArray(inteceptMethod, afterOpMethods)) {
				// 超级管理员，先删除菜单权限，再插入全部菜单权限
				final SysAdminMgrRoleService mgrRoleService = (SysAdminMgrRoleService) getBean(SysAdminMgrRoleService.class);
				mgrRoleService.initSuperRole(String.valueOf(Constants.SUPER_ROLE_ID));
				Object[] argsObj = joinPoint.getArgs();
				if(null != argsObj && argsObj.length > 0) {
					final Object args = argsObj[0];
					if(args instanceof MenuPermission || args instanceof MgrRole) {
						initRoleCache(args);
					}
				}
				CommonUtils.resetLoginMgr(httpServletRequest());
			}
			// 执行目标组件的方法之后
			logger.info("SysMgrSessionAspect拦截方法配置 aop 后置切面方法[" + inteceptMethod + "]end...");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取SysAdminOperatorLogBO失败！" + e.getMessage());
		}
		logger.info("SysMgrSessionAspect拦截方法名称：" + inteceptMethod + " end...");
	}

	public void initRoleCache(final Object args) {
		final SysAdminMgrRoleService mgrRoleService = (SysAdminMgrRoleService) getBean(SysAdminMgrRoleService.class);
		Thread thread = null;
		//线程执行角色缓存
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					String roleId = null;
					SysAdminMenuPermissionDao menuPermissionDao = (SysAdminMenuPermissionDao) getBean(SysAdminMenuPermissionDao.class);
					if(args instanceof MenuPermission) {
						List<String> list = mgrRoleService.getMgrRoleIds();
						if(null != list && list.size() > 0) {
							for(String roleid : list) {
								menuPermissionDao.cacheSetMenuPermissionsByRoleId(roleid);
							}
						}
					}else if(args instanceof MgrRole) {
						//重置cacheSetMenuPermissionsByRoleId缓存
						roleId = String.valueOf(((MgrRole)args).getRoleId());
						menuPermissionDao.cacheSetMenuPermissionsByRoleId(roleId);
					}
				}catch(Exception e){
					logger.error("initRoleCache thread error : ",e);
				}
				
			}
		});
		if(args instanceof MenuPermission) {
			thread.run();//菜单则同步处理
		}else{
			thread.start();
		}
	}

	/**
	 * 获取request
	 * 
	 * @return
	 */
	private HttpServletRequest httpServletRequest() {
		HttpServletRequest req = null;
		try {
			req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		} catch (Exception e) {
			req = null;
		}
		return req;
	}

	/**
	 * SpringContext 获取bean start
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unused")
	private Object getBean(String name) {
		try {
			return SpringContextHelper.getBean(name);
		} catch (Exception e) {
			logger.error("获取getBean[" + name + "]失败！");
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private Object getBean(Class clazz) {
		try {
			return SpringContextHelper.getBean(clazz);
		} catch (Exception e) {
			logger.error("获取getBean[" + clazz + "]失败！");
		}
		return null;
	}
	/**
	 * SpringContext 获取bean end
	 * 
	 * @param name
	 * @return
	 */
}