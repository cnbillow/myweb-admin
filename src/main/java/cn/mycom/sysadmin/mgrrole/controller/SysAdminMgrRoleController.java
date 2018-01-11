package cn.mycom.sysadmin.mgrrole.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mycom.annotation.MenuPermissionsMapping;
import cn.mycom.common.CommonUtils;
import cn.mycom.constants.Constants;
import cn.mycom.spring.layui.domain.CommonLayuiResult;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.mgrrole.domain.MgrRole;
import cn.mycom.sysadmin.mgrrole.service.SysAdminMgrRoleService;
import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.http.request.RequestUtils;
import cn.mycom.utils.http.response.ResponseUtils;
import cn.mycom.utils.string.StringUtils;

/** 
 * 管理员角色控制器
 * 
 * @author vinseven
 * @date 2018-01-01
*/
@Controller
@RequestMapping("/sysadminMain/mgrRole")
public class SysAdminMgrRoleController {
	private static final Logger logger=LoggerFactory.getLogger(SysAdminMgrRoleController.class); 
	@Autowired
	private SysAdminMgrRoleService mgrRoleBO;
	
	/**
	 * 角色信息首页
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("index")
	@MenuPermissionsMapping(value="System:role:list",mpType=MenuPermission.mpType_menu)
	public String index(HttpServletRequest request,HttpServletResponse response,MgrRole bean) throws Exception{
		return "sysadminmain/mgrRole/mgrroles";
	}
	/**
	 * 添加，查看，修改
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("operatorPage")
	@MenuPermissionsMapping(value="System:role:add,System:role:update,System:role:show",mpType=MenuPermission.mpType_menu)
	public String operatorPage(HttpServletRequest request,HttpServletResponse response,String act,String roleId) throws Exception{
		request.setAttribute("act", act);
		request.setAttribute("roleId", roleId);
		return "sysadminmain/mgrRole/mgrrolesOperatorPage";
	}
	/**
	 * 角色信息列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/interface/mgrRoles")
	@MenuPermissionsMapping(value="System:role:list")
	public void mgrRoles(HttpServletRequest request,HttpServletResponse response,MgrRole bean) throws Exception{
		logger.info("查询角色信息列表 start...");
		CommonLayuiResult<MgrRole> result = new CommonLayuiResult<MgrRole>().defaultResult();
		try{
			ServiceMgr user = CommonUtils.getLoginMgr(request,response);
			if(null != user){
				RequestUtils<MgrRole> util = new RequestUtils<MgrRole>();
				Page<MgrRole> page = util.getPage(request, "limit","20","page","1");
				page = mgrRoleBO.getPageMgrRoles(page, bean);
				logger.info("查询角色信息列表成功");
				result = new CommonLayuiResult<MgrRole>().instance(page);
				ResponseUtils.printJson(response,result);
			}else{
				throw new Exception();
			}
		}catch(Exception e){
			logger.info("查询角色信息列表失败！"+StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response,new CommonLayuiResult<MgrRole>().errorResult());
		}
		logger.info("查询角色信息列表 end...");
	}
	
	/**
	 * 角色信息查看
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/mgrRolesToShow")
	@MenuPermissionsMapping(value="System:role:show,System:role:update")
	public void mgrRolesToShow(HttpServletRequest request,HttpServletResponse response,
			MgrRole bean){
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		if(null != user){
			String roleId = String.valueOf(bean.getRoleId());
			try{
				MgrRole find = mgrRoleBO.findMgrRoleById(roleId);
				if(null != find){
					logger.info("查询角色信息成功:roleId:"+roleId);
					ResponseUtils.printJson(response, true, "查询角色信息成功！",  find);
				}else{
					throw new Exception();
				}
			}catch(Exception e){
				logger.error("查询角色信息失败！roleId:"+roleId+","+StringUtils.getExceptionInfo(e));
				ResponseUtils.printJson(response, false, "查询角色信息失败！",  null);
			}
		}	
	}
	
	/**
	 * 角色信息添加
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/mgrRolesToAdd")
	@MenuPermissionsMapping(value="System:role:add")
	public void mgrRolesAdd(HttpServletRequest request,HttpServletResponse response,
			MgrRole bean){
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		if(null != user){
			try{
				MgrRole finduser = mgrRoleBO.findMgrRoleById(String.valueOf(user.getRoleId()));
				//需要组装权限列表menuPermissionIdsString
				int result = setMenuPermissionAndArea(finduser,bean);
				if(0 == result){
					MgrRole findrole = mgrRoleBO.findMgrRoleByRoleName(bean.getRoleName());
					if(null != findrole){
						logger.info("添加失败:角色名称不能重复，roleName:"+bean.getRoleName());
						ResponseUtils.printJson(response, false, "添加失败，角色名称不能重复！",  bean);
						return;
					}
					mgrRoleBO.addMgrRole(bean);
					logger.info("添加成功:roleName:"+bean.getRoleName());
					ResponseUtils.printJson(response, true, "添加成功！",  bean);
				}else if(-1 == result){
					logger.info("添加失败:菜单权限参数错误！finduser roles:"+finduser.getMenuPermissionIdsString()+",bean roles:"+bean.getMenuPermissionIdsString());
					ResponseUtils.printJson(response, false, "添加失败，菜单权限参数错误！",  bean);
				}
			}catch(Exception e){
				logger.error("添加失败！"+StringUtils.getExceptionInfo(e));
				ResponseUtils.printJson(response, false, "添加失败！",  null);
			}
		}
	}

	/**
	 * 角色信息修改
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/mgrRolesToUpdate")
	@MenuPermissionsMapping(value="System:role:update")
	public void mgrRolesToUpdate(HttpServletRequest request,HttpServletResponse response,
			MgrRole bean){
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		if(null != user){
			try{
				MgrRole finduser = mgrRoleBO.findMgrRoleById(String.valueOf(user.getRoleId()));
				//查找旧的数据
				MgrRole find = mgrRoleBO.findMgrRoleById(String.valueOf(bean.getRoleId()));
				if(null != find){
					if(find.getRoleId().intValue() == Constants.SUPER_ROLE_ID){
						if(null != finduser && finduser.getRoleId().intValue() != find.getRoleId().intValue()){
							logger.info("修改失败:超级管理员角色不能被其他角色编辑！finduser roles:"+finduser.getRoleId()+",find roles:"+find.getRoleId());
							ResponseUtils.printJson(response, false, "修改失败，超级管理员角色不能被其他角色编辑！",  bean);
							return;
						}
					}
					bean.setRoleId(find.getRoleId());
					//需要组装权限列表menuPermissionIdsString
					int result = setMenuPermissionAndArea(finduser,bean);
					if(0 == result){
						MgrRole findrole = mgrRoleBO.findMgrRoleByRoleName(bean.getRoleName());
						if(null != findrole && !findrole.getRoleName().equals(find.getRoleName())){
							logger.info("修改失败:角色名称不能重复，roleName:"+bean.getRoleName());
							ResponseUtils.printJson(response, false, "修改失败，角色名称不能重复！",  bean);
							return;
						}
						
						mgrRoleBO.updateMgrRoleById(bean);
						logger.info("修改成功:roleId:"+bean.getRoleId()+",roleName:"+bean.getRoleName());
						ResponseUtils.printJson(response, true, "修改成功！",  bean);
					}else if(-1 == result){
						logger.info("修改失败:菜单权限参数错误！finduser roles:"+finduser.getMenuPermissionIdsString()+",bean roles:"+bean.getMenuPermissionIdsString());
						ResponseUtils.printJson(response, false, "修改失败，菜单权限参数错误！",  bean);
					}
				}else{
					logger.error("修改失败！原始数据不存在！");
					ResponseUtils.printJson(response, false, "修改失败！原始数据不存在！",  null);
				}
			}catch(Exception e){
				logger.error("修改失败！"+StringUtils.getExceptionInfo(e));
				ResponseUtils.printJson(response, false, "修改失败！",  null);
			}
		}	
	}

	/**
	 * 角色信息删除
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/mgrRolesToDelete")
	@MenuPermissionsMapping(value="System:role:delete")
	public void mgrRolesToDelete(HttpServletRequest request,HttpServletResponse response,
			MgrRole bean){
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		if(null != user){
			try{
				//查找旧的数据
				MgrRole find = mgrRoleBO.findMgrRoleById(String.valueOf(bean.getRoleId()));
				if(find.getRoleId().intValue() == Constants.SUPER_ROLE_ID){
					logger.info("删除失败:超级管理员角色不能被删除！find roles:"+find.getRoleId());
					ResponseUtils.printJson(response, false, "删除失败，超级管理员角色不能被删除！",  bean);
					return;
				}
				if(String.valueOf(find.getRoleId().intValue()).equals(user.getRoleId())){
					logger.info("删除失败:当前管理员角色不能被删除！find roles:"+find.getRoleId());
					ResponseUtils.printJson(response, false, "删除失败，当前管理员角色不能被删除！",  bean);
					return;
				}
				String roleId = String.valueOf(bean.getRoleId());
				mgrRoleBO.deleteMgrRoleById(roleId);
				logger.info("删除成功:roleId:"+roleId);
				ResponseUtils.printJson(response, true, "删除成功！",  null);
			}catch(Exception e){
				logger.error("删除失败！"+StringUtils.getExceptionInfo(e));
				ResponseUtils.printJson(response, false, "删除失败！",  null);
			}
		}	
	}
	//需要组装权限列表menuPermissionIdsString
	//-1 菜单权限参数错误
	private int setMenuPermissionAndArea(MgrRole finduser,MgrRole bean) {
		List<String> currentidlist = finduser.getMenuPermissionIds();
		if(!(null != currentidlist && currentidlist.size() > 0)){
			return -1;
		}
		String menuPermissionIdsString = bean.getMenuPermissionIdsString();
		if(null != menuPermissionIdsString){
			List<String> idlist = new ArrayList<String>();
			String[] ids = menuPermissionIdsString.split(",");
			if(null != ids && ids.length > 0){
				for(String id : ids){
					if(!StringUtils.checkListValue(currentidlist,id)){
						return -1;
					}
					idlist.add(id);
				}
				bean.setMenuPermissionIds(idlist);
			}
		}
		return 0;
	}
} 
