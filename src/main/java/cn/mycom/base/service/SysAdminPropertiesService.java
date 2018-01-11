package cn.mycom.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mycom.base.domain.Properties;
import cn.mycom.base.mapper.SysAdminPropertiesDao;
import cn.mycom.constants.Constants;
import cn.mycom.spring.context.SpringContextHelper;
import cn.mycom.utils.cache.memcached.MemcachedService;
import cn.mycom.utils.http.domain.Page;

/**
 * 属性服务层
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Service
public class SysAdminPropertiesService {
	private static Logger logger = Logger.getLogger(SysAdminPropertiesService.class);
	@Autowired
	private SysAdminPropertiesDao propertiesDao;
	private static MemcachedService memcachedService = MemcachedService.getInstance();

	public static Integer getIntegerPropValue(String propKey, Integer defaultValue) {
		try {
			return Integer.parseInt(getPropValue(propKey));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Integer getIntegerPropValue(String propKey) {
		return getIntegerPropValue(propKey, null);
	}
	
	public static String getPropValue(String propKey, String defaultValue) {
		List<Properties> props = getCachePropList();
		if (null != props) {
			for (Properties p : props) {
				if (null != p.getPropKey() && p.getPropKey().equals(propKey)) {
					return p.getPropValue();
				}
			}
		}
		return defaultValue;
	}
	public static String getPropValue(String propKey) {
		return getPropValue(propKey, null);
	}

	@SuppressWarnings("unchecked")
	public static List<Properties> getCachePropList() {
		List<Properties> props = null;
		try {
			Object obj = memcachedService.get(Constants.CACHE_ALL_PROP_KEY);
			if (obj instanceof List) {
				props = (List<Properties>) obj;
			} else {
				props = resetCachePropList();
			}
		} catch (Exception e) {
			logger.error("getCachePropList error ", e);
		}
		return props;
	}

	// 添加修改删除方法，重置缓存
	public static List<Properties> resetCachePropList() {
		try {
			SysAdminPropertiesDao propertiesDao = SpringContextHelper.getBean(SysAdminPropertiesDao.class);
			Page<Properties> page = new Page<Properties>();
			page.setPageSize(Integer.MAX_VALUE);
			List<Properties> props = propertiesDao.getPagePropertiess(page, new Properties());
			memcachedService.set(Constants.CACHE_ALL_PROP_KEY, props, new Date(1000 * 60 * 60 * 24));
			return props;
		} catch (Exception e) {
			logger.error("resetCachePropList error ", e);
		}
		return null;
	}

	/**
	 ******************************* 分页 start********************************************************
	 */
	/**
	 * 分页查找
	 */
	public Page<Properties> getPagePropertiess(Page<Properties> page, Properties bean) {
		List<Properties> list = new ArrayList<Properties>();
		list = propertiesDao.getPagePropertiess(page, bean);
		page.setTotalRecord(propertiesDao.totalPagePropertiess(bean));
		if (null == list) {
			list = new ArrayList<Properties>();
		}
		page.setResults(list);
		return page;
	}

	/**
	 ******************************* 分页 end********************************************************
	 */

	/**
	 * 查询 by id
	 * 
	 * @return
	 */
	public Properties findPropertiesByKey(Properties bean) {
		return propertiesDao.findPropertiesByKey(bean);
	}

	public void addProperties(Properties bean) {
		propertiesDao.addProperties(bean);
		SysAdminPropertiesService.resetCachePropList();
	}

	public void updateProperties(Properties bean) {
		propertiesDao.updateProperties(bean);
		SysAdminPropertiesService.resetCachePropList();
	}

	public void deleteProperties(Properties bean) {
		propertiesDao.deleteProperties(bean);
		SysAdminPropertiesService.resetCachePropList();
	}
}
