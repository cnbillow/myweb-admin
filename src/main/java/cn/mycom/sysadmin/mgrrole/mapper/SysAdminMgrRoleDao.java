package cn.mycom.sysadmin.mgrrole.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.menupermission.service.SysAdminMenuPermissionService;
import cn.mycom.sysadmin.mgrrole.domain.MgrRole;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.string.StringUtils;
/**
 * 超级角色管理员数据层
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Repository
public class SysAdminMgrRoleDao {
	private static final Logger logger = LoggerFactory.getLogger(SysAdminMgrRoleDao.class);
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	@Autowired
	private SysAdminMenuPermissionService sysAdminMenuPermissionBO;

	protected final static String BASE_NAME_SPACE = "cn.mycom.sysadmin.mgrrole.mapper.SysAdminMgrRoleDao";
	/**
	*******************************分页 start********************************************************
	*/
	/**
	* 分页查找角色
	*/
	public List<MgrRole> getPageMgrRoles(Page<MgrRole> page, MgrRole bean) {
		Map<String,Object> map = getPageMap(bean);
		map.put("page", page);//需要分页
		List<MgrRole> lists = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".getPageMgrRoles",map);
		if(null != lists && lists.size() > 0){
			return lists;
		}
		return null;
	}
	/**
	* 分页查找角色 总数
	*/
	@SuppressWarnings("rawtypes")
	public int totalPageMgrRoles(MgrRole bean) {
		Map<String,Object> pm =	getPageMap(bean);
		List<Page> result = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".totalPageMgrRoles",pm);
		if(null != result && result.size() > 0){
			Page page = result.get(0);
			return page.getTotalRecord();
		}
		return 0;	
	}	
	/**
	* 查找列表 map 构造
	*/
	private Map<String, Object> getPageMap(MgrRole bean){
		Map<String,Object> pm=new HashMap<String,Object>();
		pm.put("roleName", bean.getRoleName());
		pm.put("roleStatus", bean.getRoleStatus());
		return pm;
	}
	/**
	*******************************分页 end********************************************************
	*/
	
	/**
	 * 查询角色 by roleName
	 * @param roleName
	 * @return
	 */
	public MgrRole findMgrRoleByRoleName(String roleName){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleName", roleName);
			List<MgrRole> mgrRoles = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".findMgrRoleByRoleName",map);
			if(null != mgrRoles && mgrRoles.size() > 0){
				return mgrRoles.get(0);
			}
		}catch(Exception e){
			logger.error("SysAdminMgrRoleDao findMgrRoleByLoginName:"+StringUtils.getExceptionInfo(e));
		}
		return null;
	}
	
	/**
	 * 查询角色 by roleId
	 * @param roleId
	 * @return
	 */
	public MgrRole findMgrRoleById(MgrRole bean){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleId", String.valueOf(bean.getRoleId().intValue()));
			List<MgrRole> mgrRoles = sqlSessionTemplate.selectList(BASE_NAME_SPACE+".findMgrRoleById",map);
			if(null != mgrRoles && mgrRoles.size() == 1){
				//角色不能有多个相同，相同则异常，只能唯一条记录
				MgrRole find = mgrRoles.get(0);
				setMgrRoleItems(find);
				return find;
			}
		}catch(Exception e){
			logger.error("SysAdminMgrRoleDao findMgrRoleById:"+StringUtils.getExceptionInfo(e));
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setMgrRoleItems(MgrRole find){
		String roleId = String.valueOf(find.getRoleId().intValue());
		//设置菜单权限及编码 menuPermissions  menuPermissionCodes
		Map<String,List> mpMap= new HashMap<String, List>();
		mpMap = sysAdminMenuPermissionBO.findMenuPermissionsByRoleId(roleId);
		List omplist = mpMap.get("menuPermissions");
		List ompCodeList = mpMap.get("menuPermissionCodes");
		List ompIdList = mpMap.get("menuPermissionIds");

		if(omplist instanceof List){
			find.setMenuPermissions((List<MenuPermission>)omplist);
			//构造顶部导航url
			for(MenuPermission mp : find.getMenuPermissions()){
				if(StringUtils.isNull(mp.getMpUrl())){
					mp.setMpUrl(getChildrenFirstUrl(mp));
				}
			}
		}
		if(ompCodeList instanceof List){
			find.setMenuPermissionCodes((List<String>)ompCodeList);
		}
		if(ompIdList instanceof List){
			find.setMenuPermissionIds(ompIdList);
			if(ompIdList.size() > 0){
				StringBuffer sbids = new StringBuffer();
				for(Object id : ompIdList){
					if(id instanceof String){
						sbids.append(id+",");
					}
				}
				sbids.setLength(sbids.length() - 1);
				find.setMenuPermissionIdsString(sbids.toString());
			}
		}
	}
	/**
	 * 设置第一个有url的子元素的路径，并且是菜单类型的
	 * @param mp
	 */
	private String getChildrenFirstUrl(MenuPermission mp) {
		List<MenuPermission> chlidren = mp.getChildren();
		if(null != chlidren && chlidren.size() > 0){
			for(MenuPermission mc : chlidren){
				if(MenuPermission.mpType_menu.equals(mc.getMpType()) && !StringUtils.isNull(mc.getMpUrl())){
					return mc.getMpUrl();
				}else{
					String s = getChildrenFirstUrl(mc);
					if(null == s){
						continue;
					}
					return s;
				}
			}
		}
		return null;
	}
	public int updateMgrRoleById(MgrRole bean) {
		int result = -1;
		try{
			Integer roleId = bean.getRoleId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleId", roleId);
			map.put("roleName", bean.getRoleName());
			map.put("roleDesc", bean.getRoleDesc());
			map.put("roleStatus", bean.getRoleStatus());
			map.put("roleSort", bean.getRoleSort());
			map.put("updateTime", bean.getUpdateTime());
			result = sqlSessionTemplate.update(BASE_NAME_SPACE+".updateMgrRoleById",map);
			if(result > 0){
				List<MgrRole> mpRoles = new ArrayList<MgrRole>();
				List<String> mpids = bean.getMenuPermissionIds();
				if(null != mpids && mpids.size() > 0){
					for(String mpId : mpids){
						MgrRole role = new MgrRole();
						role.setMpId(Integer.parseInt(mpId));
						role.setRoleId(roleId);
						role.setAddTime(new Date());
						mpRoles.add(role);
					}
				}
				//先删除关联信息，再批量插入
				sqlSessionTemplate.delete(BASE_NAME_SPACE+".deleteMgrRoleMenuPermissionsByRoleId",map);
				if(null != mpRoles && mpRoles.size() > 0){
					addMgrRoleMenuPermissions(mpRoles);
				}
			}
			result = 1;
		}catch(Exception e){
			logger.error("SysAdminMgrRoleDao updateMgrRoleById:"+StringUtils.getExceptionInfo(e));
		}
		return result;
	}

	public int addMgrRole(MgrRole bean) {
		int result = -1;
		try{
			/*
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleName", bean.getRoleName());
			map.put("roleDesc", bean.getRoleDesc());
			map.put("roleStatus", bean.getRoleStatus());
			map.put("roleSort", bean.getRoleSort());
			map.put("updateTime", bean.getUpdateTime());
			map.put("addTime", bean.getAddTime());*/
			result = sqlSessionTemplate.insert(BASE_NAME_SPACE+".addMgrRole",bean);
			Integer roleId = bean.getRoleId();
			if(result > 0){
				List<MgrRole> mpRoles = new ArrayList<MgrRole>();
				List<String> mpids = bean.getMenuPermissionIds();
				if(null != mpids && mpids.size() > 0){
					for(String mpId : mpids){
						MgrRole role = new MgrRole();
						role.setMpId(Integer.parseInt(mpId));
						role.setRoleId(roleId);
						role.setAddTime(new Date());
						mpRoles.add(role);
					}
					addMgrRoleMenuPermissions(mpRoles);
				}
			}
			result = 1;
		}catch(Exception e){
			logger.error("SysAdminMgrRoleDao addMgrRole:"+StringUtils.getExceptionInfo(e));
		}
		return result;
	}
	
	public int deleteMgrRoleById(MgrRole bean) {
		int result = -1;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleId", String.valueOf(bean.getRoleId().intValue()));
			result = sqlSessionTemplate.delete(BASE_NAME_SPACE+".deleteMgrRoleById",map);
			if(result > 0){
				sqlSessionTemplate.delete(BASE_NAME_SPACE+".deleteMgrRoleMenuPermissionsByRoleId",map);
			}
			result = 1;
		}catch(Exception e){
			logger.error("SysAdminMgrRoleDao deleteMgrRoleById:"+StringUtils.getExceptionInfo(e));
		}
		return result;	
	}

	/**
	 * 批量添加 xxx_mgrRole_menuPermission
	 * @param roles
	 */
	public int addMgrRoleMenuPermissions(List<MgrRole> roles) {
		int result = -1;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roles", roles);
			sqlSessionTemplate.insert(BASE_NAME_SPACE+".addMgrRoleMenuPermissions",map);
			result = 1;
		}catch(Exception e){
			logger.error("SysAdminMgrRoleDao addMgrRoleMenuPermissions:"+StringUtils.getExceptionInfo(e));
		}
		return result;
	}
	
}
