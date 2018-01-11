package cn.mycom.sysadmin.common.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mycom.annotation.MenuPermissionsMapping;
import cn.mycom.base.controller.SysAdminBaseController;
import cn.mycom.base.domain.Properties;
import cn.mycom.base.service.SysAdminPropertiesService;
import cn.mycom.common.CommonUtils;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.mgrrole.domain.MgrRole;
import cn.mycom.sysadmin.mgrrole.service.SysAdminMgrRoleService;
import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.utils.http.response.ResponseUtils;
import cn.mycom.utils.string.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 * 管理后台公共控制器
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Controller
@RequestMapping("/sysadminMain")
public class SysAdminMainCommonController extends SysAdminBaseController {
	private static final Logger logger = LoggerFactory.getLogger(SysAdminMainCommonController.class);
	@Autowired
	private SysAdminMgrRoleService mgrRoleService;
	
	// 后台管理平台首页
	@RequestMapping("index")
	@MenuPermissionsMapping(value = "Common:SysAdminMainCommonController", mpType = MenuPermission.mpType_menu)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		// 跳转到工具
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		if (null != user) {
			List<Properties> propList = SysAdminPropertiesService.getCachePropList();
			logger.info("SysAdminMainCommonController propList" + JSONObject.toJSONString(propList));
			String roleName = user.getRoleName();
			request.setAttribute("roleName", roleName);
			logger.info("SysAdminMainCommonController index:roleName" + roleName);
			String loginName = user.getLoginName();
			request.setAttribute("loginName", loginName);
			request.setAttribute("jsonString", (JSON) JSONObject.toJSON(user));
			logger.info("SysAdminMainCommonController index:loginName" + loginName);
		} else {
			return "sysadminmain/indexNotLogin";
		}
		return "sysadminmain/index";
	}

	// 后台管理未登录页面
	@RequestMapping("notLogin")
	public String notLogin(HttpServletRequest request) {
		return "sysadminmain/notLogin";
	}

	// 后台管理无权限页面
	@RequestMapping("notPermission")
	public String notPermission(HttpServletRequest request) {
		return "sysadminmain/notPermission";
	}

	// 后台管理error页面
	@RequestMapping("error")
	public String error(HttpServletRequest request) {
		return "sysadminmain/error";
	}
	/**
	 * 当前角色信息查看
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/currentMgrRole")
	@MenuPermissionsMapping("Common:currentMgrRole")
	public void currentMgrRole(HttpServletRequest request,HttpServletResponse response,
			MgrRole bean,String mpCodes){
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		if(null != user){
			String roleId = String.valueOf(user.getRoleId());
			try{
				MgrRole find = mgrRoleService.findMgrRoleById(roleId);
				if(null != find){
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					if(null != mpCodes && mpCodes.split(",").length > 0){
						logger.info("查询当前角色moCodes["+mpCodes+"]信息成功:roleId:"+roleId);
						for(String mp : mpCodes.split(",")){
							Map<String, Object> mapMpCode = new LinkedHashMap<String, Object>();
							mapMpCode.put("mpCode", mp);
							mapMpCode.put("success", false);
							if(find.getMenuPermissionCodes().contains(mp)){
								mapMpCode.put("success",true);
							}
							list.add(mapMpCode);
						}
						ResponseUtils.printJson(response, true, "查询当前角色mpCodes信息成功！",  list);
					}else{
						logger.info("查询当前角色信息成功:roleId:"+roleId);
						ResponseUtils.printJson(response, true, "查询当前角色信息成功！",  list);
					}
				}else{
					throw new Exception();
				}
			}catch(Exception e){
				logger.error("查询当前角色信息失败！roleId:"+roleId+","+StringUtils.getExceptionInfo(e));
				ResponseUtils.printJson(response, false, "查询当前角色信息失败！",  null);
			}
		}	
	}
	// 后台管理登录信息
	@RequestMapping("getSysadminInfo")
	@MenuPermissionsMapping(value = "Common:SysAdminMainCommonController:getSysadminInfo", mpType = MenuPermission.mpType_menu)
	public void getSysadminInfo(HttpServletRequest request, HttpServletResponse response) {
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		if (null != user) {
			logger.info("getSysadminInfo result : " + JSONObject.toJSONString(user));
			ResponseUtils.printJson(response, true, "用户信息查询成功！", user);
		} else {
			ResponseUtils.printJson(response, false, "用户信息查询失败！", null);
		}
	}
}
