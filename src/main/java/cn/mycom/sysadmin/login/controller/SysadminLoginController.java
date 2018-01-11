package cn.mycom.sysadmin.login.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.mycom.annotation.MenuPermissionsMapping;
import cn.mycom.base.controller.SysAdminBaseController;
import cn.mycom.base.service.CommonLoginLogService;
import cn.mycom.constants.Constants;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.mgrrole.service.SysAdminMgrRoleService;
import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.sysadmin.servicemgr.service.SysAdminServiceMgrService;
import cn.mycom.sysadmin.servicemgr.utils.PasswordUtils;
import cn.mycom.utils.cache.memcached.MemcachedService;
import cn.mycom.utils.crypto.VerifyCodeUtil;
import cn.mycom.utils.http.cookies.CookieUtils;
import cn.mycom.utils.http.domain.CommonResult;
import cn.mycom.utils.http.request.RequestUtils;
import cn.mycom.utils.http.response.ResponseUtils;
import cn.mycom.utils.prop.PropSystemUtils;
import cn.mycom.utils.string.StringUtils;
/**
 * 管理后台登录控制器
 * @author richinfo
 *
 */
@Controller
@RequestMapping("sysadminLogin")
public class SysadminLoginController extends SysAdminBaseController {
	private static final Logger logger = LoggerFactory.getLogger(SysadminLoginController.class);
	private MemcachedService memcachedService = MemcachedService.getInstance();

	@Autowired
	private SysAdminServiceMgrService serviceMgrBO;
	@Autowired
	private SysAdminMgrRoleService mgrRoleBO;
	@Autowired
	private CommonLoginLogService loginLogService;
	@RequestMapping(value = "login")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		return "sysadminLogin";
	}

	/**
	 * 后台管理平台登录
	 * 
	 * @param request
	 * @param response
	 * @param serviceMgr
	 *            [loginName,password],isRemember["true","false"]是否记住用户名,
	 *            verifyCode验证码
	 */
	@RequestMapping(value = "/toLogin", method = RequestMethod.POST)
	@MenuPermissionsMapping(value="Common:SysadminLoginController",mpType=MenuPermission.mpType_permission)
	public void toLogin(HttpServletRequest request, HttpServletResponse response, ServiceMgr serviceMgr,
			String isRemember, String verifyCode) {
		String loginName = null;
		if (null != serviceMgr) {
			loginName = serviceMgr.getLoginName();
		}
		isRemember = "true";//默认记住用户名
		/* 设置是否记住登录用户名 */
		if (Boolean.parseBoolean(isRemember)) {
			setRememberNameToCookies(request, response, loginName);
		} else {
			removeRememberNameToCookies(request, response);
		}
		CommonResult result = new CommonResult();
		// 判断验证码是否失效
		String key = CookieUtils.getCookieValue(request, Constants.CACHE_MGR_VERIFY_CODE_COOKIES_KEY);
		logger.info("CookieUtils.getCookieValue(request, Constants.CACHE_MGR_VERIFY_CODE_COOKIES_KEY) key:" + key);
		Object vcode = memcachedService.get(getVCodeMemcachedKey(key));
		logger.info("memcachedService.get(getVCodeMemcachedKey(key)) vcode:" + vcode);
		if (null != vcode) {
			// 判断验证码
			if (null != verifyCode && StringUtils.equals(String.valueOf(vcode).toLowerCase(), verifyCode.toLowerCase())) {
				// 登录逻辑
				String password = PasswordUtils.encPassword(serviceMgr.getLoginPassword());
				serviceMgr.setLoginType(RequestUtils.recognizeVersion(request));
				try {
					result = serviceMgrBO.login(loginName, password);
					ServiceMgr mgr = null;
					Object mgrObj = result.getResult();
					if (mgrObj instanceof ServiceMgr) {
						mgr = (ServiceMgr) mgrObj;
					}
					if (result.isSuccess() && null != mgr) {
						if (null != mgr && !StringUtils.isNull(mgr.getRoleId())) {
							if(String.valueOf(Constants.SUPER_ROLE_ID).equals(mgr.getRoleId())){
								//超级管理员，先删除菜单权限，再插入全部菜单权限
								mgrRoleBO.initSuperRole(mgr.getRoleId());
							}
							// 登录成功，获取菜单权限
							mgr.setMgrRole(mgrRoleBO.findMgrRoleById(mgr.getRoleId()));
							result.setCode("S_OK");
							result.setMessage("登录成功！");
							result.setResult(mgr);
							setUserInfoToCache(request, response, mgr);
							try {
								logger.info("toLogin 记录管理平台登录历史");
								// 记录登录历史
								loginLogService.insertServiceMgr(request, mgr);
							} catch (Exception e) {
								logger.info("toLogin 记录管理平台登录历史失败:" + e);
							}
							ResponseUtils.printJson(response, true, "登录成功！", result);
						} else {
							result.setCode("S007");
							result.setMessage("登录失败！<br/>当前管理员角色未配置！");
							ResponseUtils.printJson(response, false, "登录失败！", result);
						}
					} else {
						ResponseUtils.printJson(response, false, "登录失败！", result);
					}
				} catch (Exception e) {
					logger.error(StringUtils.getExceptionInfo(e));
					ResponseUtils.printJson(response, false, "失败！", null);
				}
			} else {
				result.setCode("S005");
				result.setMessage("验证码错误！");
				ResponseUtils.printJson(response, false, "登录失败！", result);
			}
		} else {
			result.setCode("S006");
			result.setMessage("验证码已经失效！");
			ResponseUtils.printJson(response, false, "登录失败！", result);
		}
	}

	/**
	 * 查询是否登录接口（登录了返回{"result":{实体字段},"msg":null,"success":true}）
	 * （未登录了返回{"result":null,"msg":null,"success":false}）
	 * 
	 * @param qo
	 * @param response
	 * @return
	 */
	@RequestMapping("checkLogin")
	@MenuPermissionsMapping(value="Common:SysadminLoginController",mpType=MenuPermission.mpType_permission)
	public void checkLogin(HttpServletRequest request, HttpServletResponse response) {
		Object o = getUserInfoByCache(request);
		if (o instanceof ServiceMgr) {
			ResponseUtils.printJson(response, true, "用户已经登录！", o);
		} else {
			ResponseUtils.printJson(response, false, "用户未登录！", null);
		}
	}

	/**
	 * 后台管理平台退出
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("toLogout")
	@MenuPermissionsMapping(value="Common:SysadminLoginController",mpType=MenuPermission.mpType_permission)
	public void toLogout(HttpServletRequest request, HttpServletResponse response) {
		Object o = getUserInfoByCache(request);
		if (o instanceof ServiceMgr) {
			removeUserInfoByCache(request);
		}
		o = getUserInfoByCache(request);
		if(o == null){
			ResponseUtils.printJson(response, true, "退出成功！", null);
		}else{
			ResponseUtils.printJson(response, false, "退出失败！", null);
		}
	}

	/**
	 * 登录页面验证码获取
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("loginValidateCode")
	public void loginValidateCode(HttpServletRequest request, HttpServletResponse response) {
		// 设置页面不缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_UPPER, 4, null);

		// 将验证码放到HttpSession里面
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		CookieUtils.addCookieForMailDomain(response, Constants.CACHE_MGR_VERIFY_CODE_COOKIES_KEY, sessionId);
		logger.info("本次生成的验证码为[" + verifyCode + "],已存放到addCookieForMailDomain中,sessionId:" + sessionId);
		memcachedService.delete(getVCodeMemcachedKey(sessionId));
		memcachedService.add(getVCodeMemcachedKey(sessionId), verifyCode, new Date(60 * 1000));// 1分钟失效
		logger.info("本次生成的验证码为[" + verifyCode + "],已存放到Memcached中");

		// 设置输出的内容的类型为JPEG图像
		response.setContentType("image/jpeg");
		BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 150, 50, 3, true, Color.WHITE,
				new Color(30, 49, 66), new Color(255, 114, 0));
		// 写给浏览器
		try {
			ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			logger.error("loginValidateCode error : " + e);
		}
	}

	private String getVCodeMemcachedKey(String sessionId) {
		return PropSystemUtils.projectCode + sessionId;
	}
}
