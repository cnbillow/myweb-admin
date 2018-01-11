package cn.mycom.exceptionresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.mycom.annotation.MenuPermissionsMapping;
import cn.mycom.sysadmin.menupermission.domain.MenuPermission;
import cn.mycom.utils.http.domain.CommonResult;
import cn.mycom.utils.string.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * springMVC统一处理错误
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(DefaultHandlerExceptionResolver.class);
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error("服务调用异常" + ex.getMessage());
		logger.error(StringUtils.getExceptionInfo(ex));
		//是否输出html
		boolean outTypeHtml = true;
		if(handler instanceof HandlerMethod){
			MenuPermissionsMapping anno = ((HandlerMethod) handler).getMethodAnnotation(MenuPermissionsMapping.class);
			if(null != anno && MenuPermission.mpType_permission.equals(anno.mpType())){
				//有增加页面注解
				outTypeHtml = false;//不是页面注解，则输出json格式数据
			}
		}
		ModelAndView mav = new ModelAndView();
		if(outTypeHtml){
			mav = new ModelAndView("web/error", null);
		}else{
			CommonResult result = new CommonResult();
			result.setErrorCode("-999");
			result.setMessage("系统繁忙！请稍后再试！");
			//链接类型是json接口类型
			String out = JSONObject.toJSONString(result);
			mav.addObject("out", out);
			mav.setViewName("web/blank");
		}
		return mav;
	}

}
