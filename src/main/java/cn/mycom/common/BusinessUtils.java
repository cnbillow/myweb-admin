package cn.mycom.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.mycom.constants.Constants;
import cn.mycom.utils.date.DateUtils;
import cn.mycom.utils.file.FileTypeUtils;
import cn.mycom.utils.file.FileUploadUtils;
import cn.mycom.utils.http.domain.CommonResult;
import cn.mycom.utils.prop.PropSystemUtils;

/**
 * 公共业务工具类
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class BusinessUtils {
	private static Logger logger = LoggerFactory.getLogger(BusinessUtils.class);

	// 判断MultipartFile文件是否为图片类型
	public static boolean isImageTypeWitMultipartFile(CommonsMultipartFile commonUploadFile) {
		try {
			return FileTypeUtils.isImageType(commonUploadFile.getInputStream(), commonUploadFile.getBytes());
		} catch (Exception e) {
			logger.info("isImageTypeWitMultipartFile error : ", e);
			return false;
		}
	}

	// 判断MultipartFile文件是否为图片类型
	public static long getUploadMaxSize(CommonsMultipartFile commonUploadFile) {
		long maxSize = Constants.default_upload_file_size;
		try {
			if (isImageTypeWitMultipartFile(commonUploadFile)) {
				maxSize = Constants.default_upload_img_size;
			}
		} catch (Exception e) {
			logger.info("getUploadMaxSize error : ", e);
		}
		return maxSize;
	}

	public static String getUploadFileRootPath(CommonsMultipartFile commonUploadFile) {
		String path = Constants.default_upload_path;
		try {
			path = FileTypeUtils.getFileByFileName(commonUploadFile.getOriginalFilename());
			logger.info("getUploadFileRootPath result : " + path);
		} catch (Exception e) {
			logger.info("getUploadFileRootPath result error : ", e);
		}
		return path;
	}
	
	public static CommonResult getUploadMultipartFileResult(MultipartHttpServletRequest multipartRequest, String fileField, String uploadFileRootPath) {
		return getUploadMultipartFileResult( multipartRequest, fileField, null, uploadFileRootPath, null, null);
	}
	
	public static CommonResult getUploadMultipartFileResult(MultipartHttpServletRequest multipartRequest, String fileField, String uploadFileRootPath,
			boolean checkMaxSize, Long maxSize) {
		return getUploadMultipartFileResult( multipartRequest, fileField, null, uploadFileRootPath, checkMaxSize, maxSize);
	}
	
	/**
	 * 上传by CommonsMultipartFile
	 * @param request
	 * @param multipartRequest
	 * @param fileField
	 * @param customerFileName
	 * @param uploadFileRootPath
	 * @param checkMaxSize
	 * @param maxSize
	 * @return
	 */
	public static CommonResult getUploadMultipartFileResult(MultipartHttpServletRequest multipartRequest, String fileField,String customerFileName, String uploadFileRootPath,
			Boolean checkMaxSize, Long maxSize) {
		CommonResult result = new CommonResult();
		try {
			if (null != multipartRequest && null != fileField && !"".equals(fileField)) {
				/* 设置图片，文件 */
				CommonsMultipartFile imagesFile = (CommonsMultipartFile) multipartRequest.getFile(fileField);
				if (null != imagesFile && !imagesFile.isEmpty()) {
					if (null != checkMaxSize && checkMaxSize.booleanValue() && null != maxSize && maxSize.intValue() > 0) {
						if (imagesFile.getSize() > maxSize.longValue()) {
							result.setCode(Constants.default_upload_code_overSize);// -777代表文件超出指定大小
							return result;
						}
					}
					result = uploadFileByInputStream(imagesFile.getBytes(), customerFileName, uploadFileRootPath);
				}
			}
		} catch (Exception e) {
			result.setCode(Constants.default_upload_code_systemerror);
			logger.error("getUploadMultipartFileResult error : ", e);
		}
		return result;
	}

	/**
	 * 上传by InputStream流
	 * @param b
	 * @param customerFileName
	 * @param rootPath
	 * @return
	 */
	public static CommonResult uploadFileByInputStream(byte[] b, String customerFileName,
			String rootPath) {
		CommonResult result = new CommonResult();
		String fileDir = "";
		if (null != b) {
			try {
				rootPath = rootPath + getUploadPath();
				rootPath = rootPath.replace(" ", "");//去除空格路径
				fileDir = PropSystemUtils.websiteUploadPath + rootPath;
				if (null == customerFileName || "".equals(customerFileName)) {
					customerFileName = String.valueOf(new Date().getTime()) + "." + FileTypeUtils.getFileTypeByStream(b);
				}
				InputStream in = new ByteArrayInputStream(b);
				String filename = FileUploadUtils.uploadFile(in, fileDir, customerFileName);
				result.setMessage(PropSystemUtils.websiteResServiceUrl + rootPath + filename);
				result.setSuccess(true);
			} catch (Exception e) {
				String errType = e.getMessage();// 上传错误！
				logger.error("uploadFileByInputStream error : ", e);
				result.setMessage(errType);
				result.setSuccess(false);
			}
		}
		return result;
	}

	/**
	 * 自动处理，每月设置目录
	 * 
	 * @param request
	 * @return
	 */
	private static String getUploadPath() {
		String filePath = DateUtils.formatDate(new Date(), DateUtils.parsePatterns[8]);
		return "//" + filePath + "//";
	}
	
	/**
	 * 获取字节单位
	 * @param size
	 * @return
	 */
	public static String getStoreDescBybyte(long size) {
		// 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
		if (size < 1024) {
			return String.valueOf(size) + "B";
		} else {
			size = size / 1024;
		}
		// 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
		// 因为还没有到达要使用另一个单位的时候
		// 接下去以此类推
		if (size < 1024) {
			return String.valueOf(size) + "KB";
		} else {
			size = size / 1024;
		}
		if (size < 1024) {
			// 因为如果以MB为单位的话，要保留最后1位小数，
			// 因此，把此数乘以100之后再取余
			size = size * 100;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
		} else {
			// 否则如果要以GB为单位的，先除于1024再作同样的处理
			size = size * 100 / 1024;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
		}
	}
}
