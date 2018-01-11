package cn.mycom.sysadmin.menupermission.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.mycom.common.CommonUtils;
import cn.mycom.constants.Constants;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.mgrrole.mapper.SysAdminMgrRoleDao;
import cn.mycom.utils.cache.memcached.MemcachedService;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.string.StringUtils;

/**
 * 超级服务号简介数据层
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Repository
public class SysAdminMenuPermissionDao {
	private static final Logger logger = LoggerFactory.getLogger(SysAdminMgrRoleDao.class);
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	private static MemcachedService memcachedService = MemcachedService.getInstance();
	protected final static String BASE_NAME_SPACE = "cn.mycom.sysadmin.menupermission.mapper.SysAdminMenuPermissionDao";

	/**
	 * 查询菜单表 by roleId
	 * 
	 * @param roleId
	 * @return
	 */
	public List<MenuPermission> findMenuPermissionsByRoleId(String roleId) {
		try {
			List<MenuPermission> listMP = cacheFindMenuPermissionsByRoleId(roleId);
			return listMP;
		} catch (Exception e) {
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return null;
	}

	/**
	 * 缓存取菜单列表
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<MenuPermission> cacheFindMenuPermissionsByRoleId(String roleId) {
		logger.info("读取缓存cacheFindMenuPermissionsByRoleId : "+roleId);
		Object obj = memcachedService.get(Constants.CACHE_MENU_PERMISSION_ROLE_ID+roleId);
		if(obj instanceof List) {
			return (List<MenuPermission>)obj;
		}
		return cacheSetMenuPermissionsByRoleId(roleId);
	}

	public List<MenuPermission> cacheSetMenuPermissionsByRoleId(String roleId) {
		if(StringUtils.isNull(roleId)) {
			return null;
		}
		List<MenuPermission> listMP = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleId", roleId);
			listMP = sqlSessionTemplate.selectList(BASE_NAME_SPACE + ".findMenuPermissionsByRoleId", map);
			if (null != listMP && listMP.size() > 0) {
				for (MenuPermission mp : listMP) {
					setMenuPermissionsByRoleIdAndPid(roleId,mp);
				}
			}
		}catch (Exception e) {
			logger.error("cacheSetMenuPermissionsByRoleId error : ",e);
		}
		if(listMP != null && listMP.size() > 0) {
			logger.info("设置缓存cacheFindMenuPermissionsByRoleId : "+roleId);
			memcachedService.set(Constants.CACHE_MENU_PERMISSION_ROLE_ID+roleId,listMP,new Date(Constants.CACHE_MENU_PERMISSION_ROLE_ID_TIMEOUT));
		}else {
			logger.info("删除缓存cacheFindMenuPermissionsByRoleId : "+roleId);
			memcachedService.delete(Constants.CACHE_MENU_PERMISSION_ROLE_ID+roleId);
			listMP = null;
		}
		return listMP;
	}

	/**
	 * 查询菜单表 by roleId+mpPid
	 * 
	 * @param roleId+mpPid
	 * @return
	 */
	public void setMenuPermissionsByRoleIdAndPid(String roleId,MenuPermission bean) {
		if(MenuPermission.mpType_permission.equals(bean.getMpType())) {
			return;
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleId", roleId);
			map.put("mpPid", bean.getMpId());
			List<MenuPermission> listMP = sqlSessionTemplate
					.selectList(BASE_NAME_SPACE + ".findMenuPermissionsByRoleIdAndPid", map);
			if (null != listMP && listMP.size() > 0) {
				for (MenuPermission mp : listMP) {
					setMenuPermissionsByRoleIdAndPid(roleId, mp);
				}
				bean.setChildren(listMP);
			}
		} catch (Exception e) {
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return;
	}

	/**
	 * 查找菜单权限是否必须要 by mpCode
	 * 
	 * @return
	 */
	public MenuPermission findMenuPermissionCodeIsMust(String mpCode) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mpCode", mpCode);
			List<MenuPermission> listMP = sqlSessionTemplate
					.selectList(BASE_NAME_SPACE + ".findMenuPermissionCodeIsMust", map);
			if (null != listMP && listMP.size() == 1) {
				return listMP.get(0);
			}
		} catch (Exception e) {
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return null;
	}

	/**************************************************************************
	 * 8
	 * 
	 */

	/**
	 ******************************* 分页 start********************************************************
	 */
	/**
	 * 分页查找
	 */
	public List<MenuPermission> getPageMenuPermissions(Page<MenuPermission> page, MenuPermission bean) {
		Map<String, Object> map = getPageMap(bean);
		map.put("page", page);// 需要分页
		List<MenuPermission> lists = sqlSessionTemplate.selectList(BASE_NAME_SPACE + ".getPageMenuPermissions", map);
		if (null != lists && lists.size() > 0) {
			return lists;
		}
		return null;
	}

	/**
	 * 分页查找管理员总数
	 */
	@SuppressWarnings("rawtypes")
	public int totalPageMenuPermissions(MenuPermission bean) {
		Map<String, Object> pm = getPageMap(bean);
		List<Page> result = sqlSessionTemplate.selectList(BASE_NAME_SPACE + ".totalPageMenuPermissions", pm);
		if (null != result && result.size() > 0) {
			Page page = result.get(0);
			return page.getTotalRecord();
		}
		return 0;
	}

	/**
	 * 查找列表 map 构造
	 */
	private Map<String, Object> getPageMap(MenuPermission bean) {
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put("mpName", bean.getMpName());
		pm.put("mpPId", bean.getMpPId());
		pm.put("mpStatus", bean.getMpStatus());
		pm.put("mpType", bean.getMpType());
		return pm;
	}

	/**
	 ******************************* 分页 end********************************************************
	 */
	/**
	 * 查询 by id
	 * 
	 * @return
	 */
	public MenuPermission findMenuPermissionById(MenuPermission bean) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mpId", bean.getMpId());
			List<MenuPermission> lists = sqlSessionTemplate.selectList(BASE_NAME_SPACE + ".findMenuPermissionById",
					map);
			if (null != lists && lists.size() > 0) {
				return lists.get(0);
			}
		} catch (Exception e) {
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return null;
	}

	/**
	 * 查询 by propKey
	 * 
	 * @return
	 */
	public MenuPermission findMenuPermissionByMpCode(String mpCode) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mpCode", mpCode);
			List<MenuPermission> lists = sqlSessionTemplate.selectList(BASE_NAME_SPACE + ".findMenuPermissionByMpCode",
					map);
			if (null != lists && lists.size() > 0) {
				return lists.get(0);
			}
		} catch (Exception e) {
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return null;
	}

	public int addMenuPermission(MenuPermission bean) {
		int result = -1;
		try {
			Date now = CommonUtils.getNow();
			bean.setAddTime(now);
			bean.setUpdateTime(now);
			sqlSessionTemplate.insert(BASE_NAME_SPACE + ".addMenuPermission", bean);
			result = 1;
		} catch (Exception e) {
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}

	public int updateMenuPermissionById(MenuPermission bean) {
		int result = -1;
		try {
			bean.setUpdateTime(CommonUtils.getNow());
			sqlSessionTemplate.update(BASE_NAME_SPACE + ".updateMenuPermissionById", bean);
			result = 1;
		} catch (Exception e) {
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}

	public int deleteMenuPermissionById(MenuPermission bean) {
		int result = -1;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mpId", bean.getMpId());
			sqlSessionTemplate.delete(BASE_NAME_SPACE + ".deleteMenuPermissionById", map);
			result = 1;
		} catch (Exception e) {
			logger.error(StringUtils.getExceptionInfo(e));
		}
		return result;
	}

	/**
	 * 递归获取子类菜单
	 * 
	 * @param bean
	 * @return
	 */
	public void setMenus(MenuPermission bean) {
		List<MenuPermission> lists = sqlSessionTemplate.selectList(BASE_NAME_SPACE + ".getMenus", bean);
		if (null != lists && lists.size() > 0) {
			for (MenuPermission mp : lists) {
				mp.setMpPId(mp.getMpId());
				setMenus(mp);
			}
			bean.setChildren(lists);
		} else {
			return;
		}
	}
}
