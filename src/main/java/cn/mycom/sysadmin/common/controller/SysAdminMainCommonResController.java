package cn.mycom.sysadmin.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.mycom.annotation.MenuPermissionsMapping;
import cn.mycom.base.controller.SysAdminBaseController;
import cn.mycom.common.BusinessUtils;
import cn.mycom.constants.Constants;
import cn.mycom.utils.http.domain.CommonResult;
import cn.mycom.utils.http.response.ResponseUtils;
import cn.mycom.utils.prop.PropSystemUtils;
import cn.mycom.utils.string.StringUtils;
/**
 * 公共资源控制器
 * 
 * @author vinseven
 * @date 2018-01-01
 */
@Controller
@RequestMapping("/sysadminMain/res")
public class SysAdminMainCommonResController extends SysAdminBaseController {
	private static final Logger logger = LoggerFactory.getLogger(SysAdminMainCommonResController.class);

	/* 后台管理下载upload目录下面的文件 */
	@SuppressWarnings("deprecation")
	@RequestMapping("downLoadFile")
	@MenuPermissionsMapping("Common:downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileName,
			String fileUrlEncode,String isRemote) {
		String displayFileName = fileName;
		if (fileName == null || "".equals(fileName) || fileUrlEncode == null || "".equals(fileUrlEncode)) {
			ResponseUtils.printJson(response, false, "文件为空，下载失败！", null);
			return;
		}
		OutputStream out = null;
		String path = request.getRealPath("/");
		if(Boolean.valueOf(isRemote)){
			//判断是否远程路径
			path = PropSystemUtils.websiteUploadPath;
		}
		try {
			String fileUrl = new String(URLDecoder.decode(fileUrlEncode));//路径做urlencode
			if (fileUrl != null && !"".equals(fileName)) {
				File file = new File(path + fileUrl);
				InputStream in = new FileInputStream(file);
				if (null != in) {
					response.reset();
					response.setContentType("application/octet-stream; charset=utf-8");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + new String(displayFileName.getBytes("UTF-8"), "iso-8859-1"));
					out = response.getOutputStream();
					int length = -1;
					byte[] b = new byte[256];
					while (-1 != (length = in.read(b))) {
						out.write(b, 0, length);
					}
					out.flush();
					in.close();
				}
			} else {
				ResponseUtils.printJson(response, false, "文件为空，下载失败！", null);
				return;
			}
		} catch (IOException e) {
			logger.error(StringUtils.getExceptionInfo(e));
			ResponseUtils.printJson(response, false, "文件为空，下载失败！", null);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(StringUtils.getExceptionInfo(e));
				}
			}
		}
	}

	/**
	 * 通用上传文件，上传图片等 url
	 * action=UM,兼容百度UM编辑器
	 * @param request
	 * @param response
	 * @param bean
	 */
	@RequestMapping("uploadFile")
	@MenuPermissionsMapping("Common:uploadFile")
	public void htmlEditorsToUploadFile(HttpServletRequest request, HttpServletResponse response,String field) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", -1);
		map.put("success", false);
		String originalName = "";
		String message = "上传失败！";
		long size = 0;
		/**
		 * 设置用户信息文件
		 */
		MultipartHttpServletRequest multipartRequest = null;
		if(StringUtils.isNull(field)){
			field = Constants.default_upload_field_name;//默认字段
		}
		try {
			multipartRequest = (MultipartHttpServletRequest) request;
			// 列表图片
			CommonsMultipartFile commonUploadFile = (CommonsMultipartFile) multipartRequest.getFile(field);

			if (null == commonUploadFile || (null != commonUploadFile && commonUploadFile.getSize() == 0)) {
				logger.error("htmlEditorsToUpdateFile 上传失败,文件不能为空！");
				message = "上传失败！文件不能为空！";
			}
			originalName = commonUploadFile.getOriginalFilename();
			size = commonUploadFile.getSize();
			long maxSize = BusinessUtils.getUploadMaxSize(commonUploadFile);
			CommonResult result = BusinessUtils.getUploadMultipartFileResult( multipartRequest, field,
					BusinessUtils.getUploadFileRootPath(commonUploadFile), true, maxSize);
			if (result.isSuccess()) {
				// 上传成功
				logger.error("htmlEditorsToUpdateFile 上传成功！url-" + result.getMessage());
				map.put("error", 0);
				map.put("success", true);
				message = "上传成功！";
				if(Boolean.valueOf(request.getParameter("notServerUrl"))) {
					map.put("url","/"+result.getMessage().replaceAll(PropSystemUtils.websiteResServiceUrl, ""));
				}else {
					map.put("url",result.getMessage());
				}
			} else {
				String resultCode = result.getCode();
				if (Constants.default_upload_code_overSize.equals(resultCode)) {
					logger.error("htmlEditorsToUpdateFile 上传失败,文件超过限制大小" + BusinessUtils.getStoreDescBybyte(maxSize)
							+ "！");
					message = "上传失败！文件超过限制大小" + BusinessUtils.getStoreDescBybyte(maxSize) + "！";
				} else {
					throw new Exception(resultCode);
				}
			}
		} catch (Exception e) {
			logger.error("mailTemplatesToAddFile 上传失败！ error : ", e);
			message = "上传失败！";
		}
		map.put("message", message);
		if("UM".equals(request.getParameter("action"))) {
			Map<String,Object> newmap = new HashMap<String,Object>();
			newmap.put("originalName", originalName);
			newmap.put("name", originalName);
			newmap.put("size", size);
			newmap.put("url", map.get("url"));
			if(Boolean.valueOf(String.valueOf(map.get("success")))) {
				newmap.put("state", "SUCCESS");
			}else {
				newmap.put("state", "FAILED");
			}
			ResponseUtils.printJson(response, newmap);
		}else {
			ResponseUtils.printJson(response, map);
		}
	}

}
