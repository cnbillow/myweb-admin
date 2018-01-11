package cn.mycom.sysadmin.operator.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.mycom.sysadmin.mgrrole.mapper.SysAdminMgrRoleDao;
import cn.mycom.sysadmin.operator.domain.OperatorLog;
import cn.mycom.sysadmin.operator.domain.OperatorLogConfig;
import cn.mycom.utils.http.domain.Page;
/**
 * 操作日志数据层
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Repository
public class SysAdminOperatorLogDao {
	private static final Logger logger = LoggerFactory.getLogger(SysAdminMgrRoleDao.class);
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	protected final static String BASE_NAME_SPACE = "cn.mycom.sysadmin.operator.mapper.SysAdminOperatorLogDao";
	
	public List<OperatorLogConfig> findAllUsedConfig() {
		List<OperatorLogConfig> listConfigs = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".findAllUsedConfig",null);
		return listConfigs;
	}

	public void addOperatorLog(OperatorLog bean) {
		logger.info("addOperatorLog...");
		Map<String,Object> pm = new HashMap<String, Object>();
		pm.put("opAuName", bean.getOpAuName());
		pm.put("opAuId", bean.getOpAuId());
		pm.put("opBusinessName", bean.getOpBusinessName());
		pm.put("opBusinessDesc", bean.getOpBusinessDesc());
		pm.put("opMethodType", bean.getOpMethodType());
		pm.put("opContent", bean.getOpContent());
		pm.put("opJsonContent", bean.getOpJsonContent());
		pm.put("opIp", bean.getOpIp());
		pm.put("addTime", bean.getAddTime());
		sqlSessionTemplate.insert(BASE_NAME_SPACE+".addOperatorLog",pm);
	}

	/**
	 * 分页
	 * @param page
	 * @param bean
	 * @return
	 */
	public List<OperatorLog> getPageOperatorLogs(Page<OperatorLog> page, OperatorLog bean) {
		Map<String, Object> map = getPageMap(bean);
		map.put("page", page);//需要分页
		List<OperatorLog> lists = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".getPageOperatorLogs",map);
		if(null != lists && lists.size() > 0){
			return lists;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public int totalPageOperatorLogs(OperatorLog bean) {
		Map<String, Object> map = getPageMap(bean);
		List<Page> result = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".totalPageOperatorLogs",map);
		if(null != result && result.size() > 0){
			Page page = result.get(0);
			return page.getTotalRecord();
		}
		return 0;
	}
	/**
	 * 查找列表 map 构造
	 */
	private Map<String, Object> getPageMap(OperatorLog bean) {
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put("opAuName", bean.getOpAuName());
		pm.put("startDate1", bean.getStartDate1());
		pm.put("endDate1", bean.getEndDate1());
		return pm;
	}
}
