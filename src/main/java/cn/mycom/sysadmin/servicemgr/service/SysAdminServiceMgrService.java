package cn.mycom.sysadmin.servicemgr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.sysadmin.servicemgr.mapper.SysAdminServiceMgrDao;
import cn.mycom.utils.http.domain.CommonResult;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.string.StringUtils;


/**
 * 超级服务号管理员服务层
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Service
public class SysAdminServiceMgrService {
	@Autowired
	private SysAdminServiceMgrDao serviceMgrDao;
	/**
	 * S_OK:登录成功！；S001:登录密码不能为空！;S002:用户名不存在！;S003：登录密码错误！；S004：登录帐号已经被禁用，请联系管理员！；
	 * @param loginName
	 * @param loginPassword
	 * @return
	 */
	public CommonResult login(String loginName,String loginPassword){
		CommonResult result = new CommonResult();
		ServiceMgr findmgr = findServiceMgrByLoginName(loginName);
		if(null != findmgr){
			if(null!= loginPassword){
				if(loginPassword.equals(findmgr.getLoginPassword())){
					if(ServiceMgr.isAllowLogin_yes.equals(findmgr.getIsAllowLogin())){
						result.setMessage("登录成功！");
						result.setResult(findmgr);
						result.setSuccess(true);
					}else{
						result.setCode("S004");
						result.setMessage("登录帐号已经被禁用，请联系管理员！");
					}
				}else{
					result.setCode("S003");
					result.setMessage("登录密码错误！");
				}
			}else{
				result.setCode("S001");
				result.setMessage("登录密码不能为空！");
				
			}
		}else{
			result.setCode("S002");
			result.setMessage("用户名不存在！");
		}
		return result;
	}
	/**
	 * 查询登录帐号 by loginName
	 * @param loginName
	 * @return
	 */
	public ServiceMgr findServiceMgrByLoginName(String loginName){
		return serviceMgrDao.findServiceMgrByLoginName(loginName);
	}
	public ServiceMgr findServiceMgrById(String id){
		ServiceMgr bean = new ServiceMgr();
		try{
			bean.setId(Integer.parseInt(id));
		}catch(Exception e){
			bean.setId(null);
		}
		return serviceMgrDao.findServiceMgrById(bean);
	}
	public List<ServiceMgr> findServiceMgrsByRoleId(String roleId) {
		return serviceMgrDao.findServiceMgrsByRoleId(roleId);
	}
	
	/**
	 * 分页获取管理员
	 * @param page
	 * @param bean
	 * @return
	 */
	public Page<ServiceMgr> getPageServiceMgrs(Page<ServiceMgr> page,ServiceMgr bean){
		List<ServiceMgr> list = new ArrayList<ServiceMgr>();
		list = serviceMgrDao.getPageServiceMgrs(page,bean);
		page.setTotalRecord(serviceMgrDao.totalPageServiceMgrs(bean));
		if(null == list){
			list = new ArrayList<ServiceMgr>();
		}
		page.setResults(list);
		return page;
	}

	public void updateServiceMgrById(ServiceMgr bean) throws Exception{
		setDefaultValue(bean);
		if(1 != serviceMgrDao.updateServiceMgrById(bean)){
			throw new Exception();
		}
	}
	public void addServiceMgr(ServiceMgr bean) throws Exception {
		bean.setAddTime(new Date());
		setDefaultValue(bean);
		if(1 != serviceMgrDao.addServiceMgr(bean)){
			throw new Exception();
		}
	}
	/**
	 * 设置默认值
	 * @param bean
	 */
	private void setDefaultValue(ServiceMgr bean){
		if(StringUtils.isNull(bean.getIsAllowLogin())){
			bean.setIsAllowLogin("Y");
		}
	}
	
	public void deleteServiceMgrById(String adminId) throws Exception{
		ServiceMgr bean = new ServiceMgr();
		bean.setId(Integer.parseInt(adminId));
		if(1 != serviceMgrDao.deleteServiceMgrById(bean)){
			throw new Exception();
		}
	}
	//锁定超级服务号管理员
	public void updateLockServiceMgr(String mgrId) throws Exception {
		if(1 != serviceMgrDao.updateLockServiceMgr(mgrId)){
			throw new Exception();
		}
	}
	//解锁超级服务号管理员
	public void updateUnlockServiceMgr(String mgrId) throws Exception {
		if(1 != serviceMgrDao.updateUnlockServiceMgr(mgrId)){
			throw new Exception();
		}
	}

}
