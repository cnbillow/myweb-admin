package cn.mycom.sysadmin.servicemgr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mycom.annotation.MenuPermissionsMapping;
import cn.mycom.base.service.SysAdminPropertiesService;
import cn.mycom.common.CommonUtils;
import cn.mycom.constants.Constants;
import cn.mycom.spring.layui.domain.CommonLayuiResult;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.mgrrole.domain.MgrRole;
import cn.mycom.sysadmin.mgrrole.service.SysAdminMgrRoleService;
import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.sysadmin.servicemgr.service.SysAdminServiceMgrService;
import cn.mycom.sysadmin.servicemgr.utils.PasswordUtils;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.http.request.RequestUtils;
import cn.mycom.utils.http.response.ResponseUtils;
import cn.mycom.utils.string.StringUtils;

/** 
 * 管理员控制器
 * 
 * @author vinseven
 * @date 2018-01-01os
*/
@Controller
@RequestMapping("/sysadminMain/serviceMgr")
public class SysAdminServiceMgrController {
	private static final Logger logger=LoggerFactory.getLogger(SysAdminServiceMgrController.class); 
	@Autowired
	private SysAdminServiceMgrService serviceMgrBO;
	@Autowired
	private SysAdminMgrRoleService mgrRoleBO;
	private static final String keyId = "adminId";
	/**
	 * 管理员帐号信息首页
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("index")
	@MenuPermissionsMapping(value="System:administrator:list",mpType=MenuPermission.mpType_menu)
	public String index(HttpServletRequest request,HttpServletResponse response,ServiceMgr bean) throws Exception{
		return "sysadminmain/serviceMgr/servicemgrs";
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
	@MenuPermissionsMapping(value="System:administrator:add,System:administrator:update,System:administrator:show",mpType=MenuPermission.mpType_menu)
	public String operatorPage(HttpServletRequest request,HttpServletResponse response,String act,String adminId) throws Exception{
		request.setAttribute("act", act);
		request.setAttribute(keyId, adminId);
		return "sysadminmain/serviceMgr/servicemgrsOperatorPage";
	}
	/**
	 * 管理员帐号信息列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/interface/serviceMgrs")
	@MenuPermissionsMapping(value="System:administrator:list")
	public void serviceMgrs(HttpServletRequest request,HttpServletResponse response,ServiceMgr bean) throws Exception{
		logger.info("查询管理员帐号信息列表 start...");
		CommonLayuiResult<ServiceMgr> result = new CommonLayuiResult<ServiceMgr>().defaultResult();
		try{
			ServiceMgr user = CommonUtils.getLoginMgr(request,response);
			if(null != user){
				RequestUtils<ServiceMgr> util = new RequestUtils<ServiceMgr>();
				Page<ServiceMgr> page = util.getPage(request, "limit","20","page","1");
				page = serviceMgrBO.getPageServiceMgrs(page, bean);
				logger.info("查询管理员帐号信息列表成功");
				result = new CommonLayuiResult<ServiceMgr>().instance(page);
				ResponseUtils.printJson(response,result);			
			}else{
				throw new Exception();
			}
		}catch(Exception e){
			logger.info("查询管理员帐号信息列表失败！"+StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response,new CommonLayuiResult<ServiceMgr>().errorResult());
		}
		logger.info("查询管理员帐号信息列表 end...");
	}
	
	/**
	 * 可用角色信息列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/interface/mgrRoles")
	@MenuPermissionsMapping(value="System:administrator:list")
	public void mgrRoles(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info("查询角色信息列表 start...");
		try{
			ServiceMgr user = CommonUtils.getLoginMgr(request,response);
			if(null != user){
				RequestUtils<MgrRole> util = new RequestUtils<MgrRole>();
				Page<MgrRole> page = util.getPage(request, "limit",String.valueOf(Integer.MAX_VALUE),"page","1");
				MgrRole bean = new MgrRole();
				bean.setRoleStatus(MgrRole.roleStatus_yes);
				page = mgrRoleBO.getPageMgrRoles(page, bean);
				logger.info("查询角色信息列表成功");
				ResponseUtils.printJson(response, true, "查询角色信息列表成功",page);
			}else{
				throw new Exception();
			}
		}catch(Exception e){
			logger.info("查询角色信息列表失败！"+StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, false, "查询角色信息列表失败",null);
		}
		logger.info("查询角色信息列表 end...");
	}
	/**
	 * 管理员帐号信息查看
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/serviceMgrsToShow")
	@MenuPermissionsMapping(value="System:administrator:show,System:administrator:add,System:administrator:update")
	public void serviceMgrsToShow(HttpServletRequest request,HttpServletResponse response,
			ServiceMgr bean){
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		if(null != user){
			int adminid = RequestUtils.getIntegerParameter(request, keyId, "-1");			
			try{
				ServiceMgr find = serviceMgrBO.findServiceMgrById(String.valueOf(adminid));
				if(null != find){
					logger.info("查询成功:id:"+adminid);
					ResponseUtils.printJson(response, true, "查询成功！",  find);
				}else{
					throw new Exception();
				}
			}catch(Exception e){
				logger.error("查询失败！id:"+adminid+","+StringUtils.getExceptionInfo(e));
				ResponseUtils.printJson(response, false, "查询失败！",  null);
			}
		}	
	}
	
	/**
	 * 管理员帐号信息添加
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/serviceMgrsToAdd")
	@MenuPermissionsMapping(value="System:administrator:add")
	public void serviceMgrsAdd(HttpServletRequest request,HttpServletResponse response,
			ServiceMgr bean){
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		if(null != user){
			try{
				if(StringUtils.isNull(bean.getLoginName())){
					logger.error("添加失败,账号名不能为空！");
					ResponseUtils.printJson(response, false, "添加失败！账号名不能为空！",  bean);
					return;
				}
				
				if(StringUtils.isNull(bean.getLoginPassword())){
					//设置默认密码
					bean.setLoginPassword(String.valueOf(SysAdminPropertiesService.getPropValue("website.defaultPaw","defaultadmin888")));
				}
				
				String loginName = bean.getLoginName();
				ServiceMgr mgr = serviceMgrBO.findServiceMgrByLoginName(loginName);
				
				if(null != mgr){
					logger.error("添加失败账号[loginName="+loginName+"]不能重复！");
					ResponseUtils.printJson(response, false, "添加失败！账号不能重复！",  bean);
					return;
				}
				// 动态设置密码
				bean.setLoginPassword(PasswordUtils.encPassword(bean.getLoginPassword()));
				if(null == bean.getLoginPassword()){
					logger.error("添加失败密码不能为空！");
					ResponseUtils.printJson(response, false, "添加失败密码不能为空！",  bean);
					return;
				}
				serviceMgrBO.addServiceMgr(bean);
				logger.info("添加成功:loginName:"+bean.getLoginName());
				ResponseUtils.printJson(response, true, "添加成功！",  bean);
			}catch(Exception e){
				logger.error("添加失败！"+StringUtils.getExceptionInfo(e));
				ResponseUtils.printJson(response, false, "添加失败！",  null);
			}
		}
	}
	
	/**
	 * 管理员帐号信息修改
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/serviceMgrsToUpdate")
	@MenuPermissionsMapping(value="System:administrator:update")
	public void serviceMgrsToUpdate(HttpServletRequest request,HttpServletResponse response,
			ServiceMgr bean){
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		if(null != user){
			int adminid = RequestUtils.getIntegerParameter(request, keyId, "-1");			
			try{
				bean.setId(adminid);
				ServiceMgr finduser = serviceMgrBO.findServiceMgrById(String.valueOf(user.getId()));
				//查找旧的数据
				ServiceMgr find = serviceMgrBO.findServiceMgrById(String.valueOf(bean.getId()));
				if(null != find){
					String loginName = find.getLoginName();
					bean.setLoginName(loginName);//账号名不给修改
					if(find.getId().intValue() == Constants.SUPER_ADMIN_ID){
						if(ServiceMgr.isAllowLogin_no.equals(bean.getIsAllowLogin())){
							logger.info("修改失败:最高级超级管理员不能被禁用！finduser roles:"+finduser.getId()+",find roles:"+find.getId());
							ResponseUtils.printJson(response, false, "修改失败，最高级超级管理员不能被禁用！",  bean);
							return;
						}
						
						if(null != finduser && finduser.getId().intValue() != find.getId().intValue()){
							logger.info("修改失败:最高级超级管理员不能被其他管理员编辑！finduser roles:"+finduser.getId()+",find roles:"+find.getId());
							ResponseUtils.printJson(response, false, "修改失败，最高级超级管理员不能被其他管理员编辑！",  bean);
							return;
						}
					}
					if(!StringUtils.isNull(bean.getLoginPassword())){
						// 动态设置密码
						bean.setLoginPassword(PasswordUtils.encPassword(bean.getLoginPassword()));
					}
					serviceMgrBO.updateServiceMgrById(bean);
					logger.info("修改成功:id:"+bean.getId()+",loginName:"+bean.getLoginName());
					ResponseUtils.printJson(response, true, "修改成功！",  bean);
					
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
	 * 管理员帐号信息删除
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("/interface/serviceMgrsToDelete")
	@MenuPermissionsMapping(value="System:administrator:delete")
	public void serviceMgrsToDelete(HttpServletRequest request,HttpServletResponse response,
			ServiceMgr bean){
		ServiceMgr user = CommonUtils.getLoginMgr(request,response);
		if(null != user){
			int adminid = RequestUtils.getIntegerParameter(request, keyId, "-1");			
			try{
				//查找旧的数据
				ServiceMgr find = serviceMgrBO.findServiceMgrById(String.valueOf(adminid));
				if(find.getId().intValue() == Constants.SUPER_ADMIN_ID){
					logger.info("修改失败:最高级超级管理员不能被删除！find roles:"+find.getId());
					ResponseUtils.printJson(response, false, "删除失败，最高级超级管理员不能被删除！",  bean);
					return;
				}
				if(String.valueOf(find.getId().intValue()).equals(String.valueOf(user.getId()))){
					logger.info("删除失败:当前管理员不能被删除！find id:"+find.getId());
					ResponseUtils.printJson(response, false, "删除失败，当前管理员不能被删除！",  bean);
					return;
				}
				serviceMgrBO.deleteServiceMgrById(String.valueOf(adminid));
				logger.info("删除成功:adminid:"+adminid);
				ResponseUtils.printJson(response, true, "删除成功！",  null);
			}catch(Exception e){
				logger.error("删除失败！"+StringUtils.getExceptionInfo(e));
				ResponseUtils.printJson(response, false, "删除失败！",  null);
			}
		}	
	}
} 
