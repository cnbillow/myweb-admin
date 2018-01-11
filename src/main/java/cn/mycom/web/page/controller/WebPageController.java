package cn.mycom.web.page.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mycom.base.controller.SysAdminBaseController;
/**
 * 公共页面控制器
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Controller
@RequestMapping("/web")
public class WebPageController extends SysAdminBaseController {
	private static final Logger logger=LoggerFactory.getLogger(WebPageController.class); 
	
	//web page error页面
	@RequestMapping("error")
	public String error(HttpServletRequest request){
		return "web/error";
	}
}
