package cn.mycom.common;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.mycom.base.controller.SysAdminBaseController;
import cn.mycom.spring.context.SpringContextHelper;
import cn.mycom.sysadmin.mgrrole.service.SysAdminMgrRoleService;
import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.sysadmin.servicemgr.service.SysAdminServiceMgrService;
import cn.mycom.utils.http.request.RequestUtils;

/**
 * 公共工具类
 * @author vinseven
 * @date 2018-01-01
 */
public class CommonUtils {
	private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * 获取登录用户，从 cookies中
	 * 
	 * @param request
	 * @return
	 */
	public static ServiceMgr getLoginMgr(HttpServletRequest request,HttpServletResponse response) {
		ServiceMgr mgr = null;
		try {
			/*
			 * 直接数据库登录方式 SysAdminServiceMgrBO mgrbo =
			 * SpringContext.getBean(SysAdminServiceMgrBO.class); ServiceMgr
			 * mgrDB = mgrbo.findServiceMgrByLoginName("caixunadmin"); if(null
			 * != mgrDB){ SysAdminMgrRoleBO rolebo =
			 * SpringContext.getBean(SysAdminMgrRoleBO.class);
			 * mgrDB.setMgrRole(rolebo.findMgrRoleById(mgrDB.getRoleId()));
			 * mgrDB.setClientIP(WebUtils.getClientIP(request)); mgr = mgrDB; }
			 */
			SysAdminBaseController c = new SysAdminBaseController();
			/*
			 */
			mgr = c.getUserInfoByCache(request);
			if (null != mgr) {
				mgr.setClientIP(RequestUtils.getClientIp(request));
				Date now = CommonUtils.getNow();
				if(null != mgr.getLastOpTime()){
					logger.info("start 超过20分钟操作，重新设置缓存...now:"+now.getTime());
					//超过20分钟操作，重新设置缓存
					if(null != response && ((now.getTime() - mgr.getLastOpTime().getTime()) / 1000 / 60) > 20){
						logger.info("enter 超过20分钟操作，重新设置缓存...now:"+now.getTime());
						c.setUserInfoToCache(request, response, mgr);
					}
					logger.info("end 超过20分钟操作，重新设置缓存...now:"+now.getTime());

				}
			}
		} catch (Exception e) {
		}
		return mgr;

	}

	/**
	 * 获取登录用户，从request.getSession()
	 * 
	 * @param request
	 * @return
	 */
	public static void resetLoginMgr(HttpServletRequest request) {
		ServiceMgr mgr = null;
		try {
			SysAdminServiceMgrService mgrservice = SpringContextHelper.getBean(SysAdminServiceMgrService.class);
			if (null != mgrservice) {
				SysAdminBaseController c = new SysAdminBaseController();
				/*
				 */
				mgr = c.getUserInfoByCache(request);
				if (null != mgr) {
					ServiceMgr mgrDB = mgrservice.findServiceMgrByLoginName(mgr.getLoginName());
					if (null != mgrDB && ServiceMgr.isAllowLogin_yes.equals(mgrDB.getIsAllowLogin())) {
						SysAdminMgrRoleService rolebo = SpringContextHelper.getBean(SysAdminMgrRoleService.class);
						mgrDB.setMgrRole(rolebo.findMgrRoleById(mgrDB.getRoleId()));
						c.setUserInfoToCache(request, null, mgrDB);
					} else {
						c.removeUserInfoByCache(request);
					}
				}
			}
		} catch (Exception e) {
		}
	}
	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getNow() {
		Date now = new Date();
		return now;
	}

}
