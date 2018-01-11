package cn.mycom.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.mycom.annotation.MenuPermissionsMapping;
import cn.mycom.common.CommonUtils;
import cn.mycom.constants.Constants;
import cn.mycom.spring.context.SpringContextHelper;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.sysadmin.menupermission.service.SysAdminMenuPermissionService;
import cn.mycom.sysadmin.mgrrole.domain.MgrRole;
import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.utils.http.domain.CommonResult;
import cn.mycom.utils.http.response.ResponseUtils;
import cn.mycom.utils.string.StringUtils;

/**
 * 验证登录和权限拦截器
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class SysAdminInterceptor implements HandlerInterceptor {

	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory.getLogger(SysAdminInterceptor.class);
	private static final String notMethodMenuPermissionCode = "-1";

	/**
	 * 请求被执行之前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		CommonResult errResult = new CommonResult();
		String errorMsg = "";
		String loginName = "";
		int errorType = 0;//-1，未登录，-2，无权限
		//获取登录用户信息
		boolean outTypeHtml = true;//默认输出html格式
		String methodMenuPermissionCode = notMethodMenuPermissionCode;
		boolean hashAllowPermission = false;//判断是否允许权限
		ServiceMgr loginMgr = CommonUtils.getLoginMgr(request,response);
		boolean isAnno = false;
		try{
			if(handler instanceof HandlerMethod){
				isAnno = true;
				MenuPermissionsMapping anno = ((HandlerMethod) handler).getMethodAnnotation(MenuPermissionsMapping.class);
				if(null != anno){
					//有增加权限注解
					String mpType = anno.mpType();
					methodMenuPermissionCode = anno.value();
					if(!MenuPermission.mpType_menu.equals(mpType)){
						outTypeHtml = false;//不是菜单权限类型，则输出json格式数据
					}
				}else{
					hashAllowPermission = true;//无菜单权限注解，则允许权限
				}
			}
			if(null != loginMgr){
				loginName = loginMgr.getLoginName();
				logger.info("超级管理员["+loginName+"]请求合法性验证  start["+System.currentTimeMillis()+"]...");
				logger.info("超级管理员请求methodMenuPermission=["+methodMenuPermissionCode+"]合法性验证通过...");
				//1,有注解权限，则进行管理员权限判断
				hashAllowPermission = checkHashAllowPermission(hashAllowPermission,loginMgr.getMgrRole(),methodMenuPermissionCode);
				
				//2,有菜单权限编码还无权限，则进行数据库校验菜单权限编码
				hashAllowPermission = getDBMPCode(hashAllowPermission,methodMenuPermissionCode);
				
				if (hashAllowPermission) {
					logger.info("超级管理员请求合法性验证通过...");
					logger.info("超级管理员["+loginName+"]请求合法性验证  end["+System.currentTimeMillis()+"]...");
					request.setAttribute("menuList", loginMgr.getMgrRole().getMenuPermissions());
					return true;
				}else{
					// 返回的错误信息
					logger.error("超级管理员请求非法！当前暂无访问权限！...");
					errResult.setErrorCode("NotPermission");
					errorMsg = "当前暂无访问权限！";
					errorType = -2;
				}
				logger.info("超级管理员["+loginName+"]请求合法性验证  end["+System.currentTimeMillis()+"]...");
			}else{
				if(!isAnno){
					return true;
				}
				logger.error("超级管理员请求非法！当前未登录！...");
				errResult.setErrorCode("NotLogin");
				errorMsg = "当前未登录！";
				errorType = -1;
			}
			//链接类型是html类型
			if(outTypeHtml){
				String path = request.getContextPath();
				if(-1 == errorType){
					if("Home".equals(methodMenuPermissionCode)){
						ResponseUtils.writerResponseUrl(response,path+"/sysadminMain/notLogin.do?page=Home");
					}else{
						ResponseUtils.writerResponseUrl(response,path+"/sysadminMain/notLogin.do");
					}
				}else if(-2 == errorType){
					ResponseUtils.writerResponseUrl(response,path+"/sysadminMain/notPermission.do");
				}else{
					ResponseUtils.writerResponseUrl(response,path+"/sysadminMain/error.do");
				}
			}else{
				//链接类型是json接口类型
				ResponseUtils.printJson(response, false, errorMsg,  errResult);
			}
		}catch(Exception e){
			errResult.setErrorCode("-999");
			ResponseUtils.printJson(response, false, "系统繁忙，请稍后再试！",  errResult);
		}
		return false;
	}

	/**
	 * 判断是否有权限
	 * @param hashAllowPermission
	 * @param role
	 * @param methodMenuPermissionCode，多个可以用逗号“,”分隔
	 * @return
	 */
	private boolean checkHashAllowPermission(boolean hashAllowPermission,MgrRole role,String methodMenuPermissionCode) {
		//hashAllowPermission=true,直接返回有权限
		if(hashAllowPermission) return hashAllowPermission;
		//methodMenuPermissionCode=空,直接返回有权限
		if(StringUtils.isNull(methodMenuPermissionCode)) return true;
		if(0 == methodMenuPermissionCode.indexOf(Constants.MenuPermissionsMappingCodeCommon)){
			//methodMenuPermissionCode 为Common:开头,直接返回有权限
			return true;
		}
		String[] methodMenuPermissionCodeArr = methodMenuPermissionCode.split(",");
		boolean result = false;
		if(null != methodMenuPermissionCodeArr){
			logger.info("当前访问的菜单-权限列表["+methodMenuPermissionCode+"]...");
			if(null == role) return result;
			List<String> clist = role.getMenuPermissionCodes();
			if(null != clist){
				logger.info("角色[roleId="+role.getRoleId()+",roleName="+role.getRoleName()+"]所有菜单-权限列表["+clist+"]...");
				for(String code : clist){
					for(String methodMenuPermissionCodeA : methodMenuPermissionCodeArr){
						if(code.equals(methodMenuPermissionCodeA)){
							return  true;//鉴权通过
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * //有菜单权限编码还无权限，则进行数据库校验菜单权限编码
	 * @param hashAllowPermission
	 * @param methodMenuPermission
	 * @return
	 */
	private boolean getDBMPCode(boolean hashAllowPermission,String methodMenuPermission) {
		if(hashAllowPermission) return hashAllowPermission;
		boolean permission = false;
		//有菜单权限编码，还是无菜单权限，则进行数据库查询，数据库查询无记录，则允许权限
		if(!hashAllowPermission && !notMethodMenuPermissionCode.equals(methodMenuPermission)){
			Object objMPBO = SpringContextHelper.getBean(SysAdminMenuPermissionService.class);
			if(objMPBO instanceof SysAdminMenuPermissionService){
				SysAdminMenuPermissionService mpBO = (SysAdminMenuPermissionService)objMPBO;
				MenuPermission mp = mpBO.findMenuPermissionCodeIsMust(methodMenuPermission);
				if(null == mp){
					permission = true;//数据库无记录，则允许权限
				}
			}
		}
		return permission;
		
	}

	/**
	 * 请求执行完之后生成视图之前拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 请求处理之后拦截
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
