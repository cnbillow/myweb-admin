package cn.mycom.sysadmin.mgrrole.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.mycom.constants.Constants;
import cn.mycom.sysadmin.mgrrole.domain.MgrRole;
import cn.mycom.sysadmin.mgrrole.mapper.SysAdminMgrRoleDao;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.prop.PropSystemUtils;
import cn.mycom.utils.string.StringUtils;

/**
 * 管理员角色 服务层
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Service
public class SysAdminMgrRoleService {
	@Autowired
	private SysAdminMgrRoleDao mgrRoleDao;
	@Resource(name="simpleJdbcTemplate")
	private JdbcTemplate simpleJdbcTemplate;
	private static final Logger logger = LoggerFactory.getLogger(SysAdminMgrRoleService.class);

	/**
	 * 查询角色 by roleName
	 * 
	 * @param roleName
	 * @return
	 */
	public MgrRole findMgrRoleByRoleName(String roleName) {
		return mgrRoleDao.findMgrRoleByRoleName(roleName);
	}

	/**
	 * 查找角色 不用 roleId
	 * 
	 * @param roleId
	 * @return
	 */
	public MgrRole findMgrRoleById(String roleId) {
		MgrRole role = new MgrRole();
		role.setRoleId(Integer.parseInt(roleId));
		MgrRole find = mgrRoleDao.findMgrRoleById(role);
		return find;
	}

	/**
	 * 分页获取角色
	 * 
	 * @param page
	 * @param bean
	 * @return
	 */
	public Page<MgrRole> getPageMgrRoles(Page<MgrRole> page, MgrRole bean) {
		List<MgrRole> list = new ArrayList<MgrRole>();
		list = mgrRoleDao.getPageMgrRoles(page, bean);
		page.setTotalRecord(mgrRoleDao.totalPageMgrRoles(bean));
		if (null == list) {
			list = new ArrayList<MgrRole>();
		}
		page.setResults(list);
		return page;
	}

	public void updateMgrRoleById(MgrRole bean) throws Exception {
		bean.setUpdateTime(new Date());
		if (1 != mgrRoleDao.updateMgrRoleById(bean)) {
			throw new Exception();
		}
	}

	public void addMgrRole(MgrRole bean) throws Exception {
		Date now = new Date();
		bean.setAddTime(now);
		bean.setUpdateTime(now);
		setDefaultValue(bean);
		if (1 != mgrRoleDao.addMgrRole(bean)) {
			throw new Exception();
		}
	}

	public void deleteMgrRoleById(String roleId) throws Exception {
		MgrRole bean = new MgrRole();
		bean.setRoleId(Integer.parseInt(roleId));
		setDefaultValue(bean);
		if (1 != mgrRoleDao.deleteMgrRoleById(bean)) {
			throw new Exception();
		}
	}

	/**
	 * 设置默认值
	 * 
	 * @param bean
	 */
	private void setDefaultValue(MgrRole bean) {
		if (StringUtils.isNull(bean.getRoleStatus())) {
			bean.setRoleStatus("Y");
		}
		if (null == bean.getRoleSort()) {
			bean.setRoleSort(Constants.default_sort_index);
		}
	}

	/**
	 * 添加 xxx_mgrRole_menuPermission表
	 * 
	 * @param role
	 */
	public void addMgrRoleMenuPermission(MgrRole role) {
		List<MgrRole> roles = new ArrayList<MgrRole>();
		roles.add(role);
		addMgrRoleMenuPermissions(roles);
	}

	/**
	 * 批量添加 xxx_mgrRole_menuPermission表
	 * 
	 * @param role
	 */
	public void addMgrRoleMenuPermissions(List<MgrRole> roles) {
		for (MgrRole role : roles) {
			role.setAddTime(new Date());
		}
		mgrRoleDao.addMgrRoleMenuPermissions(roles);
	}

	/**
	 * 初始化超级管理角色
	 * 
	 * @param roleid
	 */
	public void initSuperRole(String roleid) {
		logger.info("initSuperRole roleid:" + roleid);
		if (null == roleid || "".equals(roleid)) {
			logger.info("initSuperRole roleid is null !");
			return;
		}
		String baseTablePrefix= (String) PropSystemUtils.getPropValue("baseTablePrefix", "base_mgr_core_");
		try {
			Connection conn = simpleJdbcTemplate.getDataSource().getConnection();
			try {
				conn.setAutoCommit(false);
				// 批量删除菜单权限,批量删除权限 start
				logger.info("initSuperRole delete "+baseTablePrefix+"mgrrole_menupermission start...");
				String sqlDelete = "delete from "+baseTablePrefix+"mgrrole_menupermission where roleId=" + roleid;
				logger.info("initSuperRole delete "+baseTablePrefix+"mgrrole_menupermission sql:" + sqlDelete + "...");
				PreparedStatement ps = conn.prepareStatement(sqlDelete);
				ps.execute();
				// 提交事务，关闭连接
				conn.commit();
				logger.info("initSuperRole delete "+baseTablePrefix+"mgrrole_menupermission end...");
				// 批量删除菜单权限,批量删除权限 end

				//批量插入菜单权限,批量插入权限  start
				Date now = new Date();
				java.sql.Date addTime = new java.sql.Date(now.getTime());
				logger.info("initSuperRole batch insert "+baseTablePrefix+"mgrrole_menupermission start...");
				String sqlInsert = "INSERT INTO "+baseTablePrefix+"mgrrole_menupermission select "+ roleid +" as roleId,mpId, ? from "+baseTablePrefix+"menu_permission";
				logger.info("initSuperRole batch insert "+baseTablePrefix+"mgrrole_menupermission sql:"+sqlInsert+"...");
				PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
				psInsert.setDate(1, addTime);
				psInsert.execute();
				//提交事务，关闭连接
				conn.commit();
				logger.info("initSuperRole batch insert "+baseTablePrefix+"mgrrole_menupermission end...");
				//批量插入菜单权限,批量插入权限  end
				conn.close();
			} catch (Exception e) {
				conn.rollback();
				logger.error("initSuperRole roleid error ! " + StringUtils.getExceptionInfo(e));
				throw e;
			} finally {
				logger.error("initSuperRole roleid finally！");
			}
		} catch (Exception e) {
			logger.error("initSuperRole roleid 异常！ roleid={}", roleid);
		}
	}
	
	public List<String> getMgrRoleIds() {
		List<String> roleIds = new ArrayList<String>();
		Page<MgrRole> page = new Page<>();
		page.setPageSize(Integer.MAX_VALUE);
		MgrRole bean = new MgrRole();
		List<MgrRole> list = mgrRoleDao.getPageMgrRoles(page, bean);
		if (null != list && list.size() > 0) {
			for (MgrRole role : list) {
				roleIds.add(String.valueOf(role.getRoleId()));
			}
		}
		return roleIds;
	}
}
