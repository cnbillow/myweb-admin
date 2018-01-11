package cn.mycom.sysadmin.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mycom.annotation.MenuPermissionsMapping;
import cn.mycom.spring.layui.domain.CommonLayuiResult;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.test.domain.Tests;
import cn.mycom.sysadmin.test.service.SysAdminTestsService;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.http.request.RequestUtils;
import cn.mycom.utils.http.response.ResponseUtils;
import cn.mycom.utils.string.StringUtils;

/**
 * tests控制器
 */
@Controller
@RequestMapping("/sysadminMain/tests")
public class SysAdminTestsController {
	private static final Logger logger = LoggerFactory.getLogger(SysAdminTestsController.class);
	@Autowired
	private SysAdminTestsService testsService;

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
	@MenuPermissionsMapping(value = "Tests:list", mpType = MenuPermission.mpType_menu)
	public String index(HttpServletRequest request, HttpServletResponse response, Tests bean) throws Exception {
		return "sysadminmain/tests/testss";
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
	@MenuPermissionsMapping(value = "Tests:add,Tests:update,Tests:show", mpType = MenuPermission.mpType_menu)
	public String operatorPage(HttpServletRequest request, HttpServletResponse response, String act, Integer testId)
			throws Exception {
		request.setAttribute("act", act);
		request.setAttribute("testId", testId);
		return "sysadminmain/tests/testssOperatorPage";
	}

	/**
	 * 信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/interface/testss")
	@MenuPermissionsMapping(value = "Tests:list")
	public void testss(HttpServletRequest request, HttpServletResponse response, Tests bean) throws Exception {
		logger.info("查询信息列表 start...");
		CommonLayuiResult<Tests> result = new CommonLayuiResult<Tests>().defaultResult();
		try {
			bean.setQueryDate();
			RequestUtils<Tests> util = new RequestUtils<Tests>();
			Page<Tests> page = util.getPage(request, "limit", "20", "page", "1");
			page = testsService.getPageTestss(page, bean);
			logger.info("查询信息列表成功");
			result = new CommonLayuiResult<Tests>().instance(page);
			ResponseUtils.printJson(response, result);
		} catch (Exception e) {
			logger.info("查询信息列表失败！" + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, new CommonLayuiResult<Tests>().errorResult());
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
	@RequestMapping("/interface/testssToShow")
	@MenuPermissionsMapping(value = "Tests:show,Tests:add,Tests:update")
	public void testssToShow(HttpServletRequest request, HttpServletResponse response, Tests bean) {
		int id = bean.getTestId();
		try {
			Tests find = testsService.findTestsById(bean);
			if (null != find) {
				logger.info("查询成功:id:" + id);
				ResponseUtils.printJson(response, true, "查询成功！", find);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			logger.error("查询失败！id:" + id + "," + StringUtils.getExceptionInfo(e));
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
	@RequestMapping("/interface/testssToAdd")
	@MenuPermissionsMapping(value = "Tests:add")
	public void testssAdd(HttpServletRequest request, HttpServletResponse response, Tests bean) {
		try {
			if (!interceptFieldAdd(request, response, bean)) {
				return;
			}
			testsService.addTests(bean);
			logger.info("添加成功:id:" + bean.getTestId());
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
	@RequestMapping("/interface/testssToUpdate")
	@MenuPermissionsMapping(value = "Tests:update")
	public void testssToUpdate(HttpServletRequest request, HttpServletResponse response, Tests bean) {
		try {
			// 查找旧的数据
			Tests find = testsService.findTestsById(bean);
			if (null != find) {
				if (!interceptFieldUpdate(request, response, bean, find)) {
					return;
				}
				testsService.updateTests(bean);
				logger.info("修改成功:id:" + bean.getTestId());
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

	private boolean interceptFieldAdd(HttpServletRequest request, HttpServletResponse response, Tests bean) {
		return interceptField(request, response, bean, 1, null);
	}

	private boolean interceptFieldUpdate(HttpServletRequest request, HttpServletResponse response, Tests bean,
			Tests find) {
		return interceptField(request, response, bean, 2, find);
	}

	private boolean interceptField(HttpServletRequest request, HttpServletResponse response, Tests bean, int type,
			Tests find) {
		String msg = (type == 1 ? "添加" : "修改");
		if (StringUtils.isNull(bean.getName())) {
			logger.error(msg + "失败,名称不能为空！");
			ResponseUtils.printJson(response, false, msg + "失败！名称不能为空！", bean);
			return false;
		}
		return true;
	}

	/**
	 * 信息删除
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/testssToDelete")
	@MenuPermissionsMapping(value = "Tests:delete")
	public void testssToDelete(HttpServletRequest request, HttpServletResponse response, Tests bean) {
		try {
			testsService.deleteTests(bean);
			logger.info("删除成功:id:" + bean.getTestId());
			ResponseUtils.printJson(response, true, "删除成功！", null);
		} catch (Exception e) {
			logger.error("删除失败！" + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, false, "删除失败！", null);
		}
	}
}
