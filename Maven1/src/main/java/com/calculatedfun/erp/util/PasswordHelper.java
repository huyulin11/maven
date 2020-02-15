package com.calculatedfun.erp.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import com.kaifantech.entity.UserFormMap;
import com.kaifantech.util.log.AppFileLogger;
import com.calculatedfun.erp.util.PasswordHelper;

public class PasswordHelper {
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private String algorithmName = "md5";
	private int hashIterations = 2;

	public void encryptPassword(UserFormMap userFormMap) {
		String salt = randomNumberGenerator.nextBytes().toHex();
		userFormMap.put("credentialsSalt", salt);
		String newPassword = new SimpleHash(algorithmName, userFormMap.get("password"),
				ByteSource.Util.bytes(userFormMap.get("accountName") + salt), hashIterations).toHex();
		userFormMap.put("password", newPassword);
	}

	public void v(UserFormMap userFormMap) {
		String username = (String) userFormMap.get("username");
		String password = (String) userFormMap.get("password");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			Subject user = SecurityUtils.getSubject();
			user.login(token);
		} catch (LockedAccountException lae) {
			token.clear();
			AppFileLogger.warning("error", "用户已经被锁定不能登录，请与管理员联系！");
		} catch (ExcessiveAttemptsException e) {
			token.clear();
			AppFileLogger.warning("error", "账号：", username, " 登录失败次数过多,锁定10分钟!");
		} catch (AuthenticationException e) {
			token.clear();
			AppFileLogger.warning("error", "用户或密码不正确！");
		}
	}
}
