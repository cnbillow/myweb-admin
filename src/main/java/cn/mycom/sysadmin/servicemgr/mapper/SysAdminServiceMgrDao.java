package cn.mycom.sysadmin.servicemgr.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.string.StringUtils;
/**
 * 超级服务号管理员数据层
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Repository
public class SysAdminServiceMgrDao {
	private static Logger logger = Logger.getLogger(SysAdminServiceMgrDao.class);
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	protected final static String BASE_NAME_SPACE = "cn.mycom.sysadmin.servicemgr.mapper.SysAdminServiceMgrDao";
	
	/**
	*******************************分页 start********************************************************
	*/
	/**
	* 分页查找管理员
	*/
	public List<ServiceMgr> getPageServiceMgrs(Page<ServiceMgr> page, ServiceMgr bean) {
		Map<String,Object> map = getPageMap(bean);
		map.put("page", page);//需要分页
		List<ServiceMgr> lists = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".getPageServiceMgrs",map);
		if(null != lists && lists.size() > 0){
			return lists;
		}
		return null;
	}
	/**
	* 分页查找管理员总数
	*/
	@SuppressWarnings("rawtypes")
	public int totalPageServiceMgrs(ServiceMgr bean) {
		Map<String,Object> pm =	getPageMap(bean);
		List<Page> result = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".totalPageServiceMgrs",pm);
		if(null != result && result.size() > 0){
			Page page = result.get(0);
			return page.getTotalRecord();
		}
		return 0;	
	}	
	/**
	* 查找列表 map 构造
	*/
	private Map<String, Object> getPageMap(ServiceMgr bean){
		Map<String,Object> pm=new HashMap<String,Object>();
		pm.put("roleId", bean.getRoleId());
		pm.put("loginName", bean.getLoginName());
		pm.put("mobile", bean.getMobile());
		pm.put("isAllowLogin", bean.getIsAllowLogin());
		return pm;
	}
	/**
	*******************************分页 end********************************************************
	*/
	
	/**
	 * 查询登录帐号 by loginName
	 * @param loginName
	 * @return
	 */
	public ServiceMgr findServiceMgrByLoginName(String loginName){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loginName", loginName);
			List<ServiceMgr> mgrs = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".findServiceMgrByLoginName",map);
			if(null != mgrs && mgrs.size() == 1){
				//帐号不能有多个相同，相同则异常，只能唯一条记录
				return mgrs.get(0);
			}
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return null;
	}
	
	/**
	 * 查询登录帐号 by id
	 * @param loginName
	 * @return
	 */
	public ServiceMgr findServiceMgrById(ServiceMgr bean){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", String.valueOf(bean.getId().intValue()));
			List<ServiceMgr> mgrs = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".findServiceMgrsById",map);
			if(null != mgrs && mgrs.size() == 1){
				//帐号不能有多个相同，相同则异常，只能唯一条记录
				return mgrs.get(0);
			}
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return null;
	}
	
	
	public List<ServiceMgr> findServiceMgrsByRoleId(String roleId) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleId", roleId);
			List<ServiceMgr> mgrs = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".findServiceMgrsByRoleId",map);
			return mgrs;
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}	
		return null;
	}

	public int updateServiceMgrById(ServiceMgr bean) {
		int result = -1;
		try{
			Map<String, Object> map = getAddOrUpdateMap(bean);
			map.put("id", bean.getId());
			sqlSessionTemplate.update(BASE_NAME_SPACE+".updateServiceMgrById",map);
			result = 1;
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}

	public int addServiceMgr(ServiceMgr bean) {
		int result = -1;
		try{
			Map<String, Object> map = getAddOrUpdateMap(bean);
			map.put("loginName", bean.getLoginName());
			map.put("addTime", new Date());
			sqlSessionTemplate.insert(BASE_NAME_SPACE+".addServiceMgr",map);
			result = 1;
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}
	/**
	* add和update map 构造
	*/
	private Map<String, Object> getAddOrUpdateMap(ServiceMgr bean){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("loginPassword", bean.getLoginPassword());
		map.put("mobile", bean.getMobile());
		map.put("isAllowLogin", bean.getIsAllowLogin());
		map.put("userMail", bean.getUserMail());
		map.put("roleId", bean.getRoleId());
		map.put("updateTime", new Date());
		return map;
	}
	public int deleteServiceMgrById(ServiceMgr bean) {
		int result = -1;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("adminId", String.valueOf(bean.getId().intValue()));
			sqlSessionTemplate.delete(BASE_NAME_SPACE+".deleteServiceMgrById",map);
			result = 1;
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}

	public int updateLockServiceMgr(String adminId) {
		int result = -1;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("adminId", adminId);
			map.put("updateTime", new Date());
			sqlSessionTemplate.update(BASE_NAME_SPACE+".updateLockServiceMgr",map);
			result = 1;
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}
	
	public int updateUnlockServiceMgr(String adminId) {
		int result = -1;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("adminId", adminId);
			map.put("updateTime", new Date());
			sqlSessionTemplate.update(BASE_NAME_SPACE+".updateUnlockServiceMgr",map);
			result = 1;
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}
}
