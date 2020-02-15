package com.calculatedfun.component.controller.sys;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.erp.util.CurrentUser;
import com.calculatedfun.shiro.AppUserLoggedException;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.Common;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.BaseController;
import com.kaifantech.entity.UserLoginFormMap;
import com.kaifantech.init.sys.UdfBusinessInfo;
import com.kaifantech.init.sys.BaseBusinessInfo.Projects;
import com.kaifantech.init.sys.params.AppConfParameters;
import com.kaifantech.init.sys.params.AppSysParameters;
import com.kaifantech.mappings.AppUserLoginMapper;

@Controller
@RequestMapping("/")
public class LoginController extends BaseController<FormMap> {
	@Inject
	private AppUserLoginMapper userLoginErpMapper;

	@ResponseBody
	@RequestMapping("asyncLogin")
	public JSONObject doCheck(String username, String password, HttpServletRequest request) {
		try {
			if (!request.getMethod().equals("POST")) {
				return AppMsg.fail("支持POST方法提交！").toAlbbJson();
			}
			if (Common.isEmpty(username) || Common.isEmpty(password)) {
				return AppMsg.fail("用户名或密码不能为空！").toAlbbJson();
			}
			long current = System.currentTimeMillis();
			long expireTime = AppConfParameters.expireTime();
			if (!AppConfParameters.verifyAuthorizationCode()) {
				if (current - expireTime > 0) {
					return AppMsg.fail("当前项目授权为测试授权，现已过期，无法登陆！").toAlbbJson();
				}
			}

			long timeoutMillSeconds = AppSysParameters.getTimeoutMillSeconds();
			if (AppTool.equals(UdfBusinessInfo.CURRENT_PROJECT, Projects.TAIKAI_JY)) {
				timeoutMillSeconds = -1000l;
			}
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.getSession().setTimeout(timeoutMillSeconds);
			if (currentUser.isAuthenticated()) {
				return AppMsg.success().toAlbbJson();
			}
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			AppMsg msg = AppMsg.success();
			try {
				currentUser.login(token);
			} catch (LockedAccountException lae) {
				msg = AppMsg.fail("用户已经被锁定不能登录，请与管理员联系！");
			} catch (DisabledAccountException dae) {
				msg = AppMsg.fail("用户" + username + "已被禁用！");
			} catch (ExcessiveAttemptsException e) {
				msg = AppMsg.fail("账号：" + username + " 登录失败次数过多,锁定10分钟!");
			} catch (AppUserLoggedException e) {
				msg = AppMsg.fail("该用户已在其他处登录！");
			} catch (AuthenticationException e) {
				msg = AppMsg.fail("用户或密码不正确！");
			}
			UserLoginFormMap userLogin = new UserLoginFormMap();
			Session session = currentUser.getSession();
			userLogin.put("userId", session.getAttribute("userSessionId"));
			userLogin.put("accountName", username);
			userLogin.put("result", msg.isSuccess() ? "√" : msg.getMsg());
			userLogin.put("loginIP", request.getHeader("X-real-ip"));
			userLoginErpMapper.addEntity(userLogin);
			request.removeAttribute("error");
			if (msg.isFail()) {
				token.clear();
				return msg.toAlbbJson();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("登录异常，请联系管理员！").toAlbbJson();
		}
		request.getSession().setAttribute("sessionType", CurrentUser.SESSIONTYPE_BACKSTAGE);
		return AppMsg.success().toAlbbJson();
	}
}