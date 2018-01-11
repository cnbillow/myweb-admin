package cn.mycom.constants;

import cn.mycom.utils.prop.PropSystemUtils;
/**
 * 常量实体类
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public abstract class Constants {

	public static final String CACHE_MGR_VERIFY_CODE_COOKIES_KEY = PropSystemUtils.projectCode
			+ "_MGR_VERIFY_CODE_COOKIES_KEY";// 验证码 cookies key
	public static final String CACHE_MGR_LOGIN_NANE_COOKIES_KEY = PropSystemUtils.projectCode
			+ "_MGR_LOGIN_NANE_COOKIES_KEY";// 用户名 cookies key
	public static final String CACHE_SERVICE_SUPERMGR_USERINFO_COOKIES_KEY = PropSystemUtils.projectCode
			+ "_SERVICE_SUPERMGR_USERINFO_COOKIES_KEY";// 用户信息 cookies key
	public static final String COOKIES_SUPER_REMEMBER_NANE_KEY = PropSystemUtils.projectCode
			+ "_COOKIES_SUPER_REMEMBER_NANE_KEY";// 记住用户名
	public static final String CACHE_ALL_OPERATOR_LOG_CONFIG_KEY = PropSystemUtils.projectCode
			+ "_ALL_OPERATOR_LOG_CONFIG_KEY";// 所有log配置
	public static final int SUPER_ROLE_ID = (Integer) PropSystemUtils.getPropValue("SUPER_ROLE_ID", 1);
	public static final int SUPER_ADMIN_ID = (Integer) PropSystemUtils.getPropValue("SUPER_ADMIN_ID", 1);
	public static final String MenuPermissionsMappingCodeCommon = "Common:";// methodMenuPermissionCode
																			// 为Common:开头,直接返回有权限
	public static Integer default_sort_index = 100;// 越大越前面
	public static Integer defaultMenuPermissionTopId = 0;// 默认顶级菜单
	public static String defaultMenuPermissionTopName = "顶级菜单";
	public static String default_upload_path = "default";//默认上传的路径
	public static final long default_upload_img_size = 1024 * 1024 * 10;//10M 图片类型10M
	public static final long default_upload_file_size = 1024 * 1024 * 100;//100M 文件类型100M
	public static String default_upload_code_overSize = "-777";//文件超出大小
	public static String default_upload_code_systemerror = "-999";//异常
	public static String default_upload_field_name = "commonUploadFile";//默认字段名称

	public static final String CACHE_ALL_PROP_KEY = PropSystemUtils.projectCode +"_ALL_PROP_KEY";//所有属性配置key
	public static final long CACHE_MENU_PERMISSION_ROLE_ID_TIMEOUT = 1000 * 60 * 60 * 12;//缓存12小时
	public static final String CACHE_MENU_PERMISSION_ROLE_ID =  PropSystemUtils.projectCode
			+ "_MENU_PERMISSION_ROLE_ID_";// 角色缓存，key	
}
