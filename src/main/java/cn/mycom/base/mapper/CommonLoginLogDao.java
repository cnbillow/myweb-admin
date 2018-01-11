package cn.mycom.base.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.mycom.base.domain.LoginLog;
import cn.mycom.common.CommonUtils;
import cn.mycom.utils.http.domain.Page;

@Repository
public class CommonLoginLogDao {
	private static final Logger logger = LoggerFactory.getLogger(CommonLoginLogDao.class);
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	protected final static String BASE_NAME_SPACE = "cn.mycom.base.mapper.CommonLoginLogDao";

	// 新增登陆历史
	public void insertLoginLog(LoginLog loginLog) {
		try {
			loginLog.setLoginTime(CommonUtils.getNow());
			sqlSessionTemplate.insert(BASE_NAME_SPACE + ".insertLoginLog", loginLog);
		} catch (Exception e) {
			logger.error("insertLoginLog登录日志异常，", e);
		}
	}

	public LoginLog queryLastLoginByUserId(String loginUserId) {
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put("loginUserId", loginUserId);
		try {
			List<LoginLog> list = sqlSessionTemplate.selectList(BASE_NAME_SPACE + ".queryLastLoginByUserId", pm);
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("queryLastLoginByUserId登录日志异常，", e);
		}
		return null;
	}
	/**
	 * 分页
	 * @param page
	 * @param bean
	 * @return
	 */
	public List<LoginLog> getPageLoginLogs(Page<LoginLog> page, LoginLog bean) {
		Map<String, Object> map = getPageMap(bean);
		map.put("page", page);//需要分页
		List<LoginLog> lists = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".getPageLoginLogs",map);
		if(null != lists && lists.size() > 0){
			return lists;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public int totalPageLoginLogs(LoginLog bean) {
		Map<String, Object> map = getPageMap(bean);
		List<Page> result = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".totalPageLoginLogs",map);
		if(null != result && result.size() > 0){
			Page page = result.get(0);
			return page.getTotalRecord();
		}
		return 0;
	}
	/**
	 * 查找列表 map 构造
	 */
	private Map<String, Object> getPageMap(LoginLog bean) {
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put("loginName", bean.getLoginName());
		pm.put("startDate1", bean.getStartDate1());
		pm.put("endDate1", bean.getEndDate1());
		return pm;
	}
}
