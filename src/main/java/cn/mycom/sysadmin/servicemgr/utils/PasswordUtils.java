package cn.mycom.sysadmin.servicemgr.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 密码工具类
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class PasswordUtils {
	@SuppressWarnings("deprecation")
	public static String encPassword(String loginPassword) {
		return DigestUtils.shaHex(loginPassword);
	}

	public static boolean equalspassword(String encPassword, String loginPassword) {
		if (null != encPassword) {
			return encPassword.equals(encPassword(loginPassword));
		}
		return false;
	}
}
