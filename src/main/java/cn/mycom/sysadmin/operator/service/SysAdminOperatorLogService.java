package cn.mycom.sysadmin.operator.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mycom.common.CommonUtils;
import cn.mycom.constants.Constants;
import cn.mycom.sysadmin.operator.domain.OperatorLog;
import cn.mycom.sysadmin.operator.domain.OperatorLogConfig;
import cn.mycom.sysadmin.operator.mapper.SysAdminOperatorLogDao;
import cn.mycom.sysadmin.servicemgr.domain.ServiceMgr;
import cn.mycom.utils.cache.memcached.MemcachedService;
import cn.mycom.utils.http.domain.Page;
import cn.mycom.utils.http.request.RequestUtils;
import cn.mycom.utils.string.StringUtils;


/**
 * 操作日志 服务层
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Service
public class SysAdminOperatorLogService {
	//缓存服务器(数据少异动，需要缓存)
    private static MemcachedService cache = MemcachedService.getInstance();
	@Autowired
	private SysAdminOperatorLogDao operatorLogDao;
	
	/**
	 * 查询 操作日志配置
	 * @param methodName
	 * @return
	 */
	public OperatorLogConfig findOperatorLogConfigByMethod(String methodName){
		List<OperatorLogConfig> list = findAllUsedConfig();
		if(list != null && list.size() > 0){
			for(OperatorLogConfig config : list){
				if(null != methodName && methodName.equals(config.getOplInterceptMethod())){
					return config;
				}
			}
		}
		return null;
	}
	
	/**
	 * 查询所有拦截方法配置表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<OperatorLogConfig> findAllUsedConfig(){
		//缓存获取
		String key = Constants.CACHE_ALL_OPERATOR_LOG_CONFIG_KEY;
		cache.delete(key);//重置key
		Object o = cache.get(key);
		if(o instanceof List){
			return (List<OperatorLogConfig>)o;
		}
		List<OperatorLogConfig> list = operatorLogDao.findAllUsedConfig();
		cache.set(key, list, new Date(1000*60*60*24));//24小时失效
		return list;
	}
	
	/**
	 * 新增日志
	 * paramRequestOrLoginUser 传HttpServletRequest 或者 ServiceMgr
	 * @param bean
	 */
	public void addOperatorLog(Object paramRequestOrLoginUser,OperatorLogConfig bean,String logContent,String logJsonContent) {
		try{
			OperatorLog log = new OperatorLog();
			if(paramRequestOrLoginUser instanceof HttpServletRequest){
				HttpServletRequest request = (HttpServletRequest)paramRequestOrLoginUser;
				ServiceMgr mgr = CommonUtils.getLoginMgr(request,null);
				if(null != mgr){
					//设置操作用户
					log.setOpAuId(String.valueOf(mgr.getId()));
					log.setOpAuName(mgr.getLoginName());
					log.setOpIp(mgr.getClientIP());
				}
				if(StringUtils.isNull(log.getOpIp())){
					try{
						log.setOpIp(RequestUtils.getClientIp(request));
					}catch(Exception e){}
				}
			}else if(paramRequestOrLoginUser instanceof ServiceMgr){
				ServiceMgr mgr = (ServiceMgr)paramRequestOrLoginUser;
				//设置操作用户
				log.setOpAuId(String.valueOf(mgr.getId()));
				log.setOpAuName(mgr.getLoginName());
				log.setOpIp(mgr.getClientIP());
			}
			log.setOpBusinessName(bean.getOplBusinessName());
			log.setOpBusinessDesc(bean.getOplBusinessDesc());
			log.setOpMethodType(bean.getOplMethodType());
			String pex = "";
			if(OperatorLogConfig.oplMethodType_ADD.equals(bean.getOplMethodType())){
				pex = "<b>新增数据</b><br/>";
			}else if(OperatorLogConfig.oplMethodType_UPDATE.equals(bean.getOplMethodType())){
				pex = "<b>修改数据</b><br/>";
			}else if(OperatorLogConfig.oplMethodType_DELETE.equals(bean.getOplMethodType())){
				pex = "<b>删除数据</b><br/>";
			}else if(OperatorLogConfig.oplMethodType_BATCH_DELETE.equals(bean.getOplMethodType())){
				pex = "<b>批量删除数据</b><br/>";
			}
			log.setOpContent(pex+logContent);
			if(OperatorLogConfig.oplMethodType_UPDATE.equals(bean.getOplMethodType())){
				log.setOpJsonContent("json数据(原始数据为第一条记录，参数数据为第二条记录)："+logJsonContent);
			}else{
				log.setOpJsonContent("json数据："+logJsonContent);
			}
			log.setAddTime(CommonUtils.getNow());
			operatorLogDao.addOperatorLog(log);
		}catch(Exception e){
			System.out.println(StringUtils.getExceptionInfo(e));
		}
	}

	public Page<OperatorLog> getPageOperatorLogs(Page<OperatorLog> page, OperatorLog bean) {
		List<OperatorLog> list = new ArrayList<OperatorLog>();
		list = operatorLogDao.getPageOperatorLogs(page,bean);
		page.setTotalRecord(operatorLogDao.totalPageOperatorLogs(bean));
		if(null == list){
			list = new ArrayList<OperatorLog>();
		}
		page.setResults(list);
		return page;
	}
}
