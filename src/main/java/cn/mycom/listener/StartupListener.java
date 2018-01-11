package cn.mycom.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.mycom.aspect.SysMgrSessionAspect;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.utils.prop.PropSystemUtils;
/**
 * 启动监听器，初始化信息，配置等操作
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class StartupListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("StartupListener contextInitialized...");
		try {
			//初始化属性配置工具类
			logger.info("StartupListener contextInitialized projectCode:"+PropSystemUtils.projectCode);
			
			//初始化角色菜单权限
			logger.info("StartupListener initRoles thread start...");
			SysMgrSessionAspect initRoles = new SysMgrSessionAspect();
			initRoles.initRoleCache(new MenuPermission());
			logger.info("StartupListener initRoles thread end...");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("StartupListener contextDestroyed...");
	}
}
