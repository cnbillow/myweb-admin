package cn.mycom.base.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mycom.common.CommonUtils;
import cn.mycom.constants.Constants;
import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.utils.cache.memcached.MemcachedService;
import cn.mycom.utils.crypto.MD5;
import cn.mycom.utils.http.cookies.CookieUtils;
/**
 * 基础控制器类
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class SysAdminBaseController {
	private MemcachedService memcachedService = MemcachedService.getInstance();
	 
	 public ServiceMgr getUserInfoByCache(HttpServletRequest request){
		 String loginName = CookieUtils.getCookieValue(request, Constants.CACHE_MGR_LOGIN_NANE_COOKIES_KEY);
		 Object obj =memcachedService.get(Constants.CACHE_SERVICE_SUPERMGR_USERINFO_COOKIES_KEY+loginName);
		 if(null != obj && obj instanceof ServiceMgr){
			 return (ServiceMgr)obj;
		 }
		 return null;
	 }
	 public boolean setUserInfoToCache(HttpServletRequest request,HttpServletResponse response,ServiceMgr mgr){
		 if(null != mgr){
			mgr.setLastOpTime(CommonUtils.getNow());
			 String loginName = mgr.getLoginName();
			 String md5Enc = MD5.md5(loginName);
			 long halfHour = 30 * 60;//半小时失效
			 if(null != response){
				 CookieUtils.removeCookieForMailDomain(request, response, Constants.CACHE_MGR_LOGIN_NANE_COOKIES_KEY);
				 CookieUtils.addCookieForMailDomain(response, Constants.CACHE_MGR_LOGIN_NANE_COOKIES_KEY, md5Enc,(int)halfHour);
			 }
			 String key = Constants.CACHE_SERVICE_SUPERMGR_USERINFO_COOKIES_KEY+md5Enc;
			 memcachedService.delete(key);
			 memcachedService.set(key,mgr,new Date(halfHour * 1000));
			 return true;
		 }
		 return false;
	}
	public void removeUserInfoByCache(HttpServletRequest request){
		 String loginName = CookieUtils.getCookieValue(request, Constants.CACHE_MGR_LOGIN_NANE_COOKIES_KEY);
		 memcachedService.delete(Constants.CACHE_SERVICE_SUPERMGR_USERINFO_COOKIES_KEY+loginName);
	}
	/**
	 * 设置登录用户 记住用户名 设置request中Cookie
	 * @param session
	 * @return
	 */
	public static void setRememberNameCookiesToRequest(HttpServletRequest request){
		String s = CookieUtils.getCookieValue(request, Constants.COOKIES_SUPER_REMEMBER_NANE_KEY);
		if(null != s){
			request.setAttribute(Constants.COOKIES_SUPER_REMEMBER_NANE_KEY, s);
		}
	}
	
	public void removeRememberNameToCookies(HttpServletRequest request, HttpServletResponse response) {
		CookieUtils.removeCookieForMailDomain(request, response, Constants.COOKIES_SUPER_REMEMBER_NANE_KEY);
	}

	public void setRememberNameToCookies(HttpServletRequest request, HttpServletResponse response, String loginName) {
		//存储1年cookies
		CookieUtils.addCookieForMailDomain(response, Constants.COOKIES_SUPER_REMEMBER_NANE_KEY, loginName, 365 * 24 * 60 * 60);
	}
}
