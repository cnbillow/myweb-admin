package cn.mycom.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mycom.constants.Constants;
import cn.mycom.utils.http.cookies.CookieUtils;
import cn.mycom.utils.prop.PropSystemUtils;
/**
 * 默认全局拦截器，可以设置一些页面取值属性
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class DefaultInterceptorFilter implements Filter
{

	public void doFilter(ServletRequest servletrequest,
			ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException
	{
		HttpServletResponse response = (HttpServletResponse) servletresponse;// 获取response对象
		HttpServletRequest request = (HttpServletRequest) servletrequest;// 获取request对象
		//资源服务器路径resServiceUrl
		request.setAttribute("resServiceUrl", PropSystemUtils.websiteResServiceUrl);
		//记住用户名
		request.setAttribute("cookiesSuperLoginName", CookieUtils.getCookieValue(request, Constants.COOKIES_SUPER_REMEMBER_NANE_KEY));
		filterchain.doFilter(servletrequest, servletresponse);
	}

	public void init(FilterConfig filterconfig) throws ServletException{
		System.out.println("------------------"+PropSystemUtils.projectCode+" DefaultInterceptorFilter init---------------------------------------");
	}

	public void destroy(){
		System.out.println("------------------"+PropSystemUtils.projectCode+" DefaultInterceptorFilter destroy---------------------------------------");
	}
}
