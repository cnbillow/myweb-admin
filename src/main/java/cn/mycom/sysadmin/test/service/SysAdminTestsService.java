package cn.mycom.sysadmin.test.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mycom.sysadmin.test.domain.Tests;
import cn.mycom.sysadmin.test.mapper.SysAdminTestsDao;
import cn.mycom.utils.http.domain.Page;

/**
 * 来访人员服务层
 */
@Service
public class SysAdminTestsService {
	private static Logger logger = Logger.getLogger(SysAdminTestsService.class);
	@Autowired
	private SysAdminTestsDao propertiesDao;

	/**
	 ******************************* 分页 start********************************************************
	 */
	/**
	 * 分页查找
	 */
	public Page<Tests> getPageTestss(Page<Tests> page, Tests bean) {
		List<Tests> list = new ArrayList<Tests>();
		list = propertiesDao.getPageTestss(page, bean);
		page.setTotalRecord(propertiesDao.totalPageTestss(bean));
		if (null == list) {
			list = new ArrayList<Tests>();
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
	public Tests findTestsById(Tests bean) {
		return propertiesDao.findTestsById(bean);
	}

	public void addTests(Tests bean) {
		propertiesDao.addTests(bean);
	}

	public void updateTests(Tests bean) {
		propertiesDao.updateTests(bean);
	}
	
	public void deleteTests(Tests bean) {
		propertiesDao.deleteTests(bean);
	}
}
