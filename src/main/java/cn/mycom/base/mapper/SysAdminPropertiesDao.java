package cn.mycom.base.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.mycom.base.domain.Properties;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.string.StringUtils;
@Repository
public class SysAdminPropertiesDao {
	private static Logger logger = Logger.getLogger(SysAdminPropertiesDao.class);
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	protected final static String BASE_NAME_SPACE = "SysAdminPropertiesDao";
	
	/**
	*******************************分页 start********************************************************
	*/
	/**
	* 分页查找
	*/
	public List<Properties> getPagePropertiess(Page<Properties> page, Properties bean) {
		Map<String,Object> map = getPageMap(bean);
		map.put("page", page);//需要分页
		List<Properties> lists = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".getPagePropertiess",map);
		if(null != lists && lists.size() > 0){
			return lists;
		}
		return null;
	}
	/**
	* 分页查找管理员总数
	*/
	@SuppressWarnings("rawtypes")
	public int totalPagePropertiess(Properties bean) {
		Map<String,Object> pm =	getPageMap(bean);
		List<Page> result = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".totalPagePropertiess",pm);
		if(null != result && result.size() > 0){
			Page page = result.get(0);
			return page.getTotalRecord();
		}
		return 0;	
	}	
	/**
	* 查找列表 map 构造
	*/
	private Map<String, Object> getPageMap(Properties bean){
		Map<String,Object> pm=new HashMap<String,Object>();
		pm.put("propKey", bean.getPropKey());
		return pm;
	}
	/**
	*******************************分页 end********************************************************
	*/
	
	/**
	 * 查询 by propKey
	 * @return
	 */
	public Properties findPropertiesByKey(Properties bean){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("propKey", bean.getPropKey());
			List<Properties> mgrs = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".findPropertiesByKey",map);
			if(null != mgrs && mgrs.size() > 0){
				return mgrs.get(0);
			}
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return null;
	}
	
	
	public int updateProperties(Properties bean) {
		int result = -1;
		try{
			Map<String, Object> map = getAddOrUpdateMap(bean);
			sqlSessionTemplate.update(BASE_NAME_SPACE+".updateProperties",map);
			result = 1;
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}

	public int addProperties(Properties bean) {
		int result = -1;
		try{
			Map<String, Object> map = getAddOrUpdateMap(bean);
			map.put("addTime", new Date());
			sqlSessionTemplate.insert(BASE_NAME_SPACE+".addProperties",map);
			result = 1;
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}
	
	/**
	* add和update map 构造
	*/
	private Map<String, Object> getAddOrUpdateMap(Properties bean){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("propKey", bean.getPropKey());
		map.put("propValue", bean.getPropValue());
		map.put("remark", bean.getRemark());
		map.put("updateTime", new Date());
		return map;
	}
	
	public int deleteProperties(Properties bean) {
		int result = -1;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("propKey", bean.getPropKey());
			sqlSessionTemplate.delete(BASE_NAME_SPACE+".deleteProperties",map);
			result = 1;
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}
}
