package cn.mycom.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mycom.base.domain.LoginLog;
import cn.mycom.base.mapper.CommonLoginLogDao;
import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.utils.date.DateUtils;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.http.request.RequestUtils;
/**
 * 公共登录服务层
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Service
public class CommonLoginLogService {
	@Autowired
	private CommonLoginLogDao loginLogDao;

	/**
	 * 插入登录日志表
	 * 
	 * @param serviceMgr
	 */
	public void insertServiceMgr(HttpServletRequest request, ServiceMgr serviceMgr) {
		LoginLog loginLog = new LoginLog();
		loginLog.setLoginName(serviceMgr.getLoginName());
		loginLog.setLoginUserId(serviceMgr.getId());
		loginLog.setUserMobile(serviceMgr.getMobile());
		loginLog.setUserType(LoginLog.USERTYPE_SUPER_ADMIN);
		loginLog.setLoginType("web".equals(RequestUtils.recognizeVersion(request)) ? LoginLog.LOGINTYPE_WEB
				: LoginLog.LOGINTYPE_WAP);
		insert(request, loginLog);
	}

	/**
	 * 插入登录日志表
	 * 
	 * @param loginLog
	 */
	public void insert(HttpServletRequest request, LoginLog loginLog) {
		loginLog.setProvcode(0);
		loginLog.setAreacode(0);
		loginLog.setIp(RequestUtils.getClientIp(request));
		loginLogDao.insertLoginLog(loginLog);
	}

	public LoginLog queryLastLoginByUserId(String loginUserId) {
		LoginLog loginLog = loginLogDao.queryLastLoginByUserId(loginUserId);
		if(null == loginLog){
			loginLog = new LoginLog();
			loginLog.setLoginTimeStr("暂无");
			loginLog.setIp("暂无");
		}else{
			loginLog.setLoginTimeStr(DateUtils.formatDate(loginLog.getLoginTime(), DateUtils.parsePatterns[1]));
		}
		return loginLog;
	}
	
	public Page<LoginLog> getPageLoginLogs(Page<LoginLog> page, LoginLog bean) {
		List<LoginLog> list = new ArrayList<LoginLog>();
		list = loginLogDao.getPageLoginLogs(page,bean);
		page.setTotalRecord(loginLogDao.totalPageLoginLogs(bean));
		if(null == list){
			list = new ArrayList<LoginLog>();
		}
		page.setResults(list);
		return page;
	}
}
