package cn.mycom.sysadmin.test.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.mycom.common.CommonUtils;
import cn.mycom.sysadmin.test.domain.Tests;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.string.StringUtils;
/**
 * tests数据层
 */
@Repository
public class SysAdminTestsDao {
	private static Logger logger = Logger.getLogger(SysAdminTestsDao.class);
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	protected final static String BASE_NAME_SPACE = "SysAdminTestsDao";
	
	/**
	*******************************分页 start********************************************************
	*/
	/**
	* 分页查找
	*/
	public List<Tests> getPageTestss(Page<Tests> page, Tests bean) {
		Map<String,Object> map = getPageMap(bean);
		map.put("page", page);//需要分页
		List<Tests> lists = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".getPageTestss",map);
		if(null != lists && lists.size() > 0){
			return lists;
		}
		return null;
	}
	/**
	* 分页查找管理员总数
	*/
	@SuppressWarnings("rawtypes")
	public int totalPageTestss(Tests bean) {
		Map<String,Object> pm =	getPageMap(bean);
		List<Page> result = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".totalPageTestss",pm);
		if(null != result && result.size() > 0){
			Page page = result.get(0);
			return page.getTotalRecord();
		}
		return 0;	
	}	
	/**
	* 查找列表 map 构造
	*/
	private Map<String, Object> getPageMap(Tests bean){
		Map<String,Object> pm=new HashMap<String,Object>();
		pm.put("name", bean.getName());
		pm.put("startDate1", bean.getStartDate1());
		pm.put("endDate1", bean.getEndDate1());
		return pm;
	}
	/**
	*******************************分页 end********************************************************
	*/
	
	/**
	 * 查询 by propKey
	 * @return
	 */
	public Tests findTestsById(Tests bean){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("testId", bean.getTestId());
			List<Tests> mgrs = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".findTestsById",map);
			if(null != mgrs && mgrs.size() > 0){
				return mgrs.get(0);
			}
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return null;
	}
	
	
	public int updateTests(Tests bean) {
		int result = -1;
		try{
			bean.setUpdateTime(CommonUtils.getNow());
			sqlSessionTemplate.update(BASE_NAME_SPACE+".updateTests",bean);
			result = 1;
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}

	public int addTests(Tests bean) {
		int result = -1;
		try{
			Date now = CommonUtils.getNow();
			bean.setUpdateTime(now);
			bean.setAddTime(now);
			sqlSessionTemplate.insert(BASE_NAME_SPACE+".addTests",bean);
			result = 1;
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}
	
	public int deleteTests(Tests bean) {
		int result = -1;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("testId", bean.getTestId());
			sqlSessionTemplate.delete(BASE_NAME_SPACE+".deleteTests",map);
			result = 1;
		}catch(Exception e){
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}
}
