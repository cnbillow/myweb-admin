package cn.mycom.sysadmin.log.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mycom.annotation.MenuPermissionsMapping;
import cn.mycom.base.domain.LoginLog;
import cn.mycom.base.service.CommonLoginLogService;
import cn.mycom.spring.layui.domain.CommonLayuiResult;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.operator.domain.OperatorLog;
import cn.mycom.sysadmin.operator.service.SysAdminOperatorLogService;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.http.request.RequestUtils;
import cn.mycom.utils.http.response.ResponseUtils;
import cn.mycom.utils.string.StringUtils;

/**
 * 操作日志控制器
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Controller
@RequestMapping("/sysadminMain/log")
public class SysAdminLogController {
	private static final Logger logger = LoggerFactory.getLogger(SysAdminLogController.class);
	@Autowired
	private SysAdminOperatorLogService operatorLogService;
	@Autowired
	private CommonLoginLogService loginLogService;

	/**
	 * 操作日志
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("operator")
	@MenuPermissionsMapping(value = "Log:operator", mpType = MenuPermission.mpType_menu)
	public String operator(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "sysadminmain/log/operator";
	}

	/**
	 * 操作日志列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/interface/operator")
	@MenuPermissionsMapping(value = "Log:operator")
	public void operatorLogs(HttpServletRequest request, HttpServletResponse response, OperatorLog bean)
			throws Exception {
		logger.info("查询操作日志列表 start...");
		CommonLayuiResult<OperatorLog> result = new CommonLayuiResult<OperatorLog>().defaultResult();
		try {
			bean.setQueryDate();
			RequestUtils<OperatorLog> util = new RequestUtils<OperatorLog>();
			Page<OperatorLog> page = util.getPage(request, "limit", "20", "page", "1");
			page = operatorLogService.getPageOperatorLogs(page, bean);
			logger.info("查询操作日志列表成功");
			result = new CommonLayuiResult<OperatorLog>().instance(page);
			ResponseUtils.printJson(response, result);
		} catch (Exception e) {
			logger.info("查询操作日志列表失败！" + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, new CommonLayuiResult<OperatorLog>().errorResult());
		}
		logger.info("查询操作日志列表 end...");
	}

	/**
	 * 登录日志
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("login")
	@MenuPermissionsMapping(value = "Log:login", mpType = MenuPermission.mpType_menu)
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "sysadminmain/log/login";
	}

	/**
	 * 登录日志列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/interface/login")
	@MenuPermissionsMapping(value = "Log:login")
	public void loginLogs(HttpServletRequest request, HttpServletResponse response, LoginLog bean)
			throws Exception {
		logger.info("查询登录日志列表 start...");
		CommonLayuiResult<LoginLog> result = new CommonLayuiResult<LoginLog>().defaultResult();
		try {
			bean.setQueryDate();
			RequestUtils<LoginLog> util = new RequestUtils<LoginLog>();
			Page<LoginLog> page = util.getPage(request, "limit", "20", "page", "1");
			page = loginLogService.getPageLoginLogs(page, bean);
			logger.info("查询登录日志列表成功");
			result = new CommonLayuiResult<LoginLog>().instance(page);
			ResponseUtils.printJson(response, result);
		} catch (Exception e) {
			logger.info("查询登录日志列表失败！" + StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, new CommonLayuiResult<OperatorLog>().errorResult());
		}
		logger.info("查询登录日志列表 end...");
	}

}
