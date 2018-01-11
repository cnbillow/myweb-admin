package cn.mycom.sysadmin.menupermission.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mycom.constants.Constants;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.menupermission.mapper.SysAdminMenuPermissionDao;
import cn.mycom.utils.http.domain.Page;

/**
 * 菜单权限 服务层
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Service
public class SysAdminMenuPermissionService {
	@Autowired
	private SysAdminMenuPermissionDao menuPermissionDao;

	/********************************************************************
	 * 菜单必须是可用的 start
	 */
	/**
	 * 查询菜单表 by roleId
	 * 
	 * @param roleId
	 * @return menuPermissions , menuPermissionCodes
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, List> findMenuPermissionsByRoleId(String roleId) {
		Map<String, List> map = new HashMap<String, List>();
		List<MenuPermission> list = menuPermissionDao.findMenuPermissionsByRoleId(roleId);
		if (null != list && list.size() > 0) {
			map.put("menuPermissions", list);
			List<String> codes = new ArrayList<String>();
			List<String> ids = new ArrayList<String>();
			for (MenuPermission mp : list) {
				setCodeToList(mp, codes, ids);
			}
			map.put("menuPermissionCodes", codes);
			map.put("menuPermissionIds", ids);

		}
		return map;
	}

	/**
	 * 遍历设置code
	 * 
	 * @param mp
	 * @param codes
	 */
	private void setCodeToList(MenuPermission mp, List<String> codes, List<String> ids) {
		if (null != mp) {
			codes.add(mp.getMpCode());
			ids.add(String.valueOf(mp.getMpId().intValue()));
			List<MenuPermission> mpChildrens = mp.getChildren();
			if (null != mpChildrens) {
				for (MenuPermission mpc : mpChildrens) {
					setCodeToList(mpc, codes, ids);
				}
			}
		}
	}


	/**
	 * 查找菜单权限是否必须要 by mpCode
	 * 
	 * @return
	 */
	public MenuPermission findMenuPermissionCodeIsMust(String mpCode) {
		return menuPermissionDao.findMenuPermissionCodeIsMust(mpCode);
	}

	/********************************************************************
	 * 菜单必须是可用的 end
	 */

	/**
	 ******************************* 分页 start********************************************************
	 */
	/**
	 * 分页查找
	 */
	public Page<MenuPermission> getPageMenuPermissions(Page<MenuPermission> page, MenuPermission bean) {
		List<MenuPermission> list = new ArrayList<MenuPermission>();
		list = menuPermissionDao.getPageMenuPermissions(page, bean);
		page.setTotalRecord(menuPermissionDao.totalPageMenuPermissions(bean));
		if (null == list) {
			list = new ArrayList<MenuPermission>();
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
	public MenuPermission findMenuPermissionById(MenuPermission bean) {
		return menuPermissionDao.findMenuPermissionById(bean);
	}

	/**
	 * 查询 by code
	 * 
	 * @return
	 */
	public MenuPermission findMenuPermissionByMpCode(String mpCode) {
		return menuPermissionDao.findMenuPermissionByMpCode(mpCode);
	}

	public void addMenuPermission(MenuPermission bean) {
		menuPermissionDao.addMenuPermission(bean);
	}

	public void updateMenuPermissionById(MenuPermission bean) {
		menuPermissionDao.updateMenuPermissionById(bean);
	}

	public void deleteMenuPermissionById(MenuPermission bean) {
		menuPermissionDao.deleteMenuPermissionById(bean);
	}

	public List<MenuPermission> getMenus() {
		MenuPermission bean = new MenuPermission();
		bean.setMpType(MenuPermission.mpType_menu);
		bean.setMpPId(Constants.defaultMenuPermissionTopId);
		menuPermissionDao.setMenus(bean);
		return bean.getChildren();
	}
}
