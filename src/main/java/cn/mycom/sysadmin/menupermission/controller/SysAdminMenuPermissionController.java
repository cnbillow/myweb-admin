package cn.mycom.sysadmin.menupermission.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import cn.mycom.annotation.MenuPermissionsMapping;
import cn.mycom.constants.Constants;
import cn.mycom.spring.layui.domain.CommonLayuiResult;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.menupermission.service.SysAdminMenuPermissionService;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.http.request.RequestUtils;
import cn.mycom.utils.http.response.ResponseUtils;
import cn.mycom.utils.string.StringUtils;

/**
 * 菜单权限控制器
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Controller
@RequestMapping("/sysadminMain/menuPermission")
public class SysAdminMenuPermissionController {
	private static final Logger logger = LoggerFactory.getLogger(SysAdminMenuPermissionController.class);
	@Autowired
	private SysAdminMenuPermissionService menuPermissionService;

	/**
	 * 菜单权限首页
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("index")
	@MenuPermissionsMapping(value = "System:menu:list", mpType = MenuPermission.mpType_menu)
	public String index(HttpServletRequest request, HttpServletResponse response, MenuPermission bean)
			throws Exception {
		return "sysadminmain/menuPermission/menupermissions";
	}

	/**
	 * 添加，查看，修改
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("operatorPage")
	@MenuPermissionsMapping(value = "System:menu:add,System:menu:update,System:menu:show", mpType = MenuPermission.mpType_menu)
	public String operatorPage(HttpServletRequest request, HttpServletResponse response, String act, String mpId)
			throws Exception {
		request.setAttribute("act", act);
		request.setAttribute("mpId", mpId);
		return "sysadminmain/menuPermission/menupermissionsOperatorPage";
	}

	/**
	 * 菜单权限列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/interface/menuPermissions")
	@MenuPermissionsMapping(value = "System:menu:list")
	public void MenuPermissions(HttpServletRequest request, HttpServletResponse response, MenuPermission bean)
			throws Exception {
		logger.info("查询菜单权限列表 start...");
		CommonLayuiResult<MenuPermission> result = new CommonLayuiResult<MenuPermission>().defaultResult();
		try {
			RequestUtils<MenuPermission> util = new RequestUtils<MenuPermission>();
			Page<MenuPermission> page = util.getPage(request, "limit", "20", "page", "1");
			if (null == bean.getMpPId()) {
				bean.setMpPId(Constants.defaultMenuPermissionTopId);
			}
			page = menuPermissionService.getPageMenuPermissions(page, bean);
			logger.info("查询菜单权限列表成功");
			result = new CommonLayuiResult<MenuPermission>().instance(page);
			ResponseUtils.printJson(response, result);
		} catch (Exception e) {
			logger.info("查询菜单权限列表失败！" + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, new CommonLayuiResult<MenuPermission>().errorResult());
		}
		logger.info("查询菜单权限列表 end...");
	}

	/**
	 * 可用菜单信息列表(仅为menu类型)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/interface/menus")
	@MenuPermissionsMapping(value = "System:menu:list")
	public void mgrRoles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("查询菜单权限信息列表 start...");
		try {
			List<MenuPermission> list = menuPermissionService.getMenus();
			logger.info("查询菜单权限信息列表成功");
			ResponseUtils.printJson(response, true, "查询菜单信息列表成功", list);
		} catch (Exception e) {
			logger.info("查询菜单权限信息列表失败！" + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, false, "查询菜单信息列表失败", null);
		}
		logger.info("查询菜单权限信息列表 end...");
	}

	/**
	 * 菜单权限查看
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/menuPermissionsToShow")
	@MenuPermissionsMapping(value = "System:menu:show,System:menu:add,System:menu:update")
	public void MenuPermissionsToShow(HttpServletRequest request, HttpServletResponse response, MenuPermission bean) {
		try {
			MenuPermission find = menuPermissionService.findMenuPermissionById(bean);
			if (null != find) {
				logger.info("查询成功:id:" + bean.getMpId());
				ResponseUtils.printJson(response, true, "查询成功！", find);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			logger.error("查询失败！id:" + bean.getMpId() + "," + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, false, "查询失败！", null);
		}
	}

	/**
	 * 菜单权限添加
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/menuPermissionsToAdd")
	@MenuPermissionsMapping(value = "System:menu:add")
	public void MenuPermissionsAdd(HttpServletRequest request, HttpServletResponse response, MenuPermission bean) {
		try {
			if (!interceptFieldAdd(request, response, bean)) {
				return;
			}
			menuPermissionService.addMenuPermission(bean);
			logger.info("添加成功:mpName:" + bean.getMpName());
			ResponseUtils.printJson(response, true, "添加成功！", bean);
		} catch (Exception e) {
			logger.error("添加失败！" + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, false, "添加失败！", null);
		}
	}

	/**
	 * 菜单权限修改
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/menuPermissionsToUpdate")
	@MenuPermissionsMapping(value = "System:menu:update")
	public void MenuPermissionsToUpdate(HttpServletRequest request, HttpServletResponse response, MenuPermission bean) {
		try {
			MenuPermission find = menuPermissionService.findMenuPermissionById(bean);
			// 查找旧的数据
			if (null != find) {
				if (!interceptFieldUpdate(request, response, bean, find)) {
					return;
				}
				menuPermissionService.updateMenuPermissionById(bean);
				logger.info("修改成功:id:" + bean.getMpId() + ",mpName:" + bean.getMpName());
				ResponseUtils.printJson(response, true, "修改成功！", bean);
			} else {
				logger.error("修改失败！原始数据不存在！");
				ResponseUtils.printJson(response, false, "修改失败！原始数据不存在！", null);
			}
		} catch (Exception e) {
			logger.error("修改失败！" + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, false, "修改失败！", null);
		}
	}

	private boolean interceptFieldAdd(HttpServletRequest request, HttpServletResponse response, MenuPermission bean) {
		return interceptField(request, response, bean, 1, null);
	}

	private boolean interceptFieldUpdate(HttpServletRequest request, HttpServletResponse response, MenuPermission bean,
			MenuPermission find) {
		return interceptField(request, response, bean, 2, find);
	}

	private boolean interceptField(HttpServletRequest request, HttpServletResponse response, MenuPermission bean,
			int type, MenuPermission find) {
		String msg = (type == 1 ? "添加" : "修改");
		if (bean.getMpPId() != null && null != bean.getMpId()
				&& bean.getMpPId().intValue() == bean.getMpId().intValue()) {
			logger.error(msg + "失败！当前菜单权限父级不能为自己！");
			ResponseUtils.printJson(response, false, msg + "失败！当前菜单权限父级不能为自己！", null);
			return false;
		}
		if (StringUtils.isNull(bean.getMpName())) {
			logger.error(msg + "失败,菜单名称不能为空！");
			ResponseUtils.printJson(response, false, msg + "失败！菜单名称不能为空！", bean);
			return false;
		}
		MenuPermission findCode = menuPermissionService.findMenuPermissionByMpCode(bean.getMpCode());
		if (null != findCode) {
			if (type == 2 && !bean.getMpCode().equals(find.getMpCode())) {
				logger.error(msg + "失败，权限编码[mpCode=" + bean.getMpCode() + "]不能重复！");
				ResponseUtils.printJson(response, false, msg + "失败！权限编码不能重复！", bean);
				return false;
			}
		}
		if (Constants.defaultMenuPermissionTopId != bean.getMpPId()) {
			MenuPermission beanP = new MenuPermission();
			beanP.setMpId(bean.getMpPId());
			MenuPermission findP = menuPermissionService.findMenuPermissionById(beanP);
			if (!MenuPermission.mpType_menu.equals(findP.getMpType())) {
				logger.info(msg + "失败:父级菜单权限类型只能为Menu菜单类型！findP:" + JSONObject.toJSONString(findP));
				ResponseUtils.printJson(response, false, msg + "失败，父级菜单权限类型只能为Menu菜单类型！", bean);
				return false;
			}
		}
		if (type == 2 && MenuPermission.mpType_permission.equals(bean.getMpType())) {
			List<MenuPermission> children = getChildren(request, bean.getMpId());
			if (null != children && children.size() > 0) {
				logger.error(msg + "失败！当前菜单权限有子级，不能修改为权限类型！");
				ResponseUtils.printJson(response, false, msg + "失败！当前菜单权限有子级，不能修改为权限类型！", null);
				return false;
			}
		}

		return true;
	}

	/**
	 * 菜单权限删除
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/menuPermissionsToDelete")
	@MenuPermissionsMapping(value = "System:menu:delete")
	public void MenuPermissionsToDelete(HttpServletRequest request, HttpServletResponse response, MenuPermission bean) {
		try {
			List<MenuPermission> children = getChildren(request, bean.getMpId());
			if (null != children && children.size() > 0) {
				logger.error("删除失败！请先删除子级菜单！");
				ResponseUtils.printJson(response, false, "删除失败！请先删除子级菜单！", null);
				return;
			}
			menuPermissionService.deleteMenuPermissionById(bean);

			logger.info("删除成功:id:" + bean.getMpId());
			ResponseUtils.printJson(response, true, "删除成功！", null);
		} catch (Exception e) {
			logger.error("删除失败！" + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, false, "删除失败！", null);
		}
	}

	private List<MenuPermission> getChildren(HttpServletRequest request, Integer mpId) {
		RequestUtils<MenuPermission> util = new RequestUtils<MenuPermission>();
		MenuPermission bean = new MenuPermission();
		bean.setMpPId(mpId);
		Page<MenuPermission> page = util.getPage(request, "limit", String.valueOf(Integer.MAX_VALUE), "page",
				"1");
		menuPermissionService.getPageMenuPermissions(page, bean);
		if (null != page && null != page.getResults() && page.getResults().size() > 0) {
			return page.getResults();
		}
		return null;
	}
}
