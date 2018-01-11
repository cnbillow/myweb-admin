package cn.mycom.sysadmin.properties.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mycom.annotation.MenuPermissionsMapping;
import cn.mycom.base.domain.Properties;
import cn.mycom.base.service.SysAdminPropertiesService;
import cn.mycom.spring.layui.domain.CommonLayuiResult;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.http.request.RequestUtils;
import cn.mycom.utils.http.response.ResponseUtils;
import cn.mycom.utils.string.StringUtils;

/**
 * 属性控制器
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Controller
@RequestMapping("/sysadminMain/properties")
public class SysAdminPropertiesController {
	private static final Logger logger = LoggerFactory.getLogger(SysAdminPropertiesController.class);
	@Autowired
	private SysAdminPropertiesService propertiesService;

	/**
	 * 信息首页
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("index")
	@MenuPermissionsMapping(value = "System:properties:list", mpType = MenuPermission.mpType_menu)
	public String index(HttpServletRequest request, HttpServletResponse response, Properties bean) throws Exception {
		return "sysadminmain/properties/propertiess";
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
	@MenuPermissionsMapping(value = "System:properties:add,System:properties:update,System:properties:show", mpType = MenuPermission.mpType_menu)
	public String operatorPage(HttpServletRequest request, HttpServletResponse response, String act, String propKey)
			throws Exception {
		request.setAttribute("act", act);
		request.setAttribute("propKey", propKey);
		return "sysadminmain/properties/propertiessOperatorPage";
	}

	/**
	 * 信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/interface/propertiess")
	@MenuPermissionsMapping(value = "System:properties:list")
	public void serviceMgrs(HttpServletRequest request, HttpServletResponse response, Properties bean) throws Exception {
		logger.info("查询信息列表 start...");
		CommonLayuiResult<Properties> result = new CommonLayuiResult<Properties>().defaultResult();
		try {
			RequestUtils<Properties> util = new RequestUtils<Properties>();
			Page<Properties> page = util.getPage(request, "limit", "20", "page", "1");
			page = propertiesService.getPagePropertiess(page, bean);
			logger.info("查询信息列表成功");
			result = new CommonLayuiResult<Properties>().instance(page);
			ResponseUtils.printJson(response, result);
		} catch (Exception e) {
			logger.info("查询信息列表失败！" + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, new CommonLayuiResult<Properties>().errorResult());
		}
		logger.info("查询信息列表 end...");
	}

	/**
	 * 信息查看
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/propertiessToShow")
	@MenuPermissionsMapping(value = "System:properties:show,System:properties:add,System:properties:update")
	public void serviceMgrsToShow(HttpServletRequest request, HttpServletResponse response, Properties bean) {
		String propKey = bean.getPropKey();
		try {
			Properties find = propertiesService.findPropertiesByKey(bean);
			if (null != find) {
				logger.info("查询成功:propKey:" + propKey);
				ResponseUtils.printJson(response, true, "查询成功！", find);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			logger.error("查询失败！propKey:" + propKey + "," + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, false, "查询失败！", null);
		}
	}

	/**
	 * 信息添加
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/propertiessToAdd")
	@MenuPermissionsMapping(value = "System:properties:add")
	public void serviceMgrsAdd(HttpServletRequest request, HttpServletResponse response, Properties bean) {
		String propKey = bean.getPropKey();
		try {
			if (StringUtils.isNull(propKey)) {
				logger.error("添加失败,属性键值不能为空！");
				ResponseUtils.printJson(response, false, "添加失败！属性键值不能为空！", bean);
				return;
			}

			Properties findBean = propertiesService.findPropertiesByKey(bean);

			if (null != findBean) {
				logger.error("添加失败，属性键值[propKey=" + propKey + "]不能重复！");
				ResponseUtils.printJson(response, false, "添加失败！属性键值不能重复！", bean);
				return;
			}
			
			propertiesService.addProperties(bean);
			logger.info("添加成功:propKey:" + propKey);
			ResponseUtils.printJson(response, true, "添加成功！", bean);
		} catch (Exception e) {
			logger.error("添加失败！" + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, false, "添加失败！", null);
		}
	}

	/**
	 * 信息修改
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/propertiessToUpdate")
	@MenuPermissionsMapping(value = "System:properties:update")
	public void serviceMgrsToUpdate(HttpServletRequest request, HttpServletResponse response, Properties bean) {
		try {
			// 查找旧的数据
			Properties find = propertiesService.findPropertiesByKey(bean);
			if (null != find) {
				String propKey = find.getPropKey();
				bean.setPropKey(propKey);
				propertiesService.updateProperties(bean);
				logger.info("修改成功:propKey:" + bean.getPropKey());
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

	/**
	 * 信息删除
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/propertiessToDelete")
	@MenuPermissionsMapping(value = "System:properties:delete")
	public void serviceMgrsToDelete(HttpServletRequest request, HttpServletResponse response, Properties bean) {
			try {
				propertiesService.deleteProperties(bean);
				logger.info("删除成功:propKey:" + bean.getPropKey());
				ResponseUtils.printJson(response, true, "删除成功！", null);
			} catch (Exception e) {
				logger.error("删除失败！" + StringUtils.getExceptionInfo(e));
				ResponseUtils.printJson(response, false, "删除失败！", null);
			}
		}
}
