package cn.mycom.sysadmin.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mycom.annotation.MenuPermissionsMapping;
import cn.mycom.base.controller.SysAdminBaseController;
import cn.mycom.base.domain.LoginLog;
import cn.mycom.base.service.CommonLoginLogService;
import cn.mycom.common.CommonUtils;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.sysadmin.servicemgr.service.SysAdminServiceMgrService;
import cn.mycom.sysadmin.servicemgr.utils.PasswordUtils;
import cn.mycom.utils.http.response.ResponseUtils;
/**
 * 管理后台首页控制器
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Controller
@RequestMapping("/sysadminMain/dashboard")
public class SysAdminMainDashboardController extends SysAdminBaseController {
	private static final Logger logger=LoggerFactory.getLogger(SysAdminMainDashboardController.class); 
	@Autowired
	private CommonLoginLogService loginLogService;
	@Autowired
	private SysAdminServiceMgrService serviceMgrService;
	//后台管理平台dashboard
	@RequestMapping("index")
	@MenuPermissionsMapping(value="Common:SysAdminMainDashboardController",mpType=MenuPermission.mpType_menu)
	public String index(HttpServletRequest request, HttpServletResponse response){
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		String roleName = user.getRoleName();
		request.setAttribute("roleName", roleName);
		String loginName = user.getLoginName();
		request.setAttribute("loginName", loginName);
		//最后登录记录
		LoginLog loginLog = loginLogService.queryLastLoginByUserId(String.valueOf(user.getId().intValue()));
		request.setAttribute("loginLog", loginLog);
		return "sysadminmain/dashboard/index";
	}
	
	//后台管理平台userInfo
	@RequestMapping("userInfo")
	@MenuPermissionsMapping(value="Common:SysAdminMainDashboardController",mpType=MenuPermission.mpType_menu)
	public String userInfo(HttpServletRequest request, HttpServletResponse response){
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		LoginLog loginLog = loginLogService.queryLastLoginByUserId(String.valueOf(user.getId().intValue()));
		request.setAttribute("loginLog", loginLog);
		request.setAttribute("user", user);
		return "sysadminmain/dashboard/userInfo";
	}
	
	//后台管理平台userInfo
	@RequestMapping("updatePaw")
	@MenuPermissionsMapping(value="Common:SysAdminMainDashboardController",mpType=MenuPermission.mpType_menu)
	public String updatePaw(HttpServletRequest request, HttpServletResponse response){
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		request.setAttribute("user", user);
		return "sysadminmain/dashboard/updatePaw";
	}
	//后台管理平台userInfo
	@RequestMapping("interface/updatePaw")
	@MenuPermissionsMapping(value="Common:SysAdminMainDashboardController",mpType=MenuPermission.mpType_permission)
	public void updatePawInterface(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		String oldpass = request.getParameter("oldpass");
		if(PasswordUtils.equalspassword(user.getLoginPassword(), oldpass)){
			ServiceMgr bean = new ServiceMgr();
			bean.setId(user.getId());
			String newpass = request.getParameter("newpass");
			bean.setLoginPassword(PasswordUtils.encPassword(newpass));
			bean.setIsAllowLogin(user.getIsAllowLogin());
			serviceMgrService.updateServiceMgrById(bean);
			SysAdminBaseController baseContro = new SysAdminBaseController();
			baseContro.removeUserInfoByCache(request);
			ResponseUtils.printJson(response, true, "修改密码成功！", null);
		}else{
			ResponseUtils.printJson(response, false, "修改密码失败！旧密码不正确！", null);
		}
		
	}
}
