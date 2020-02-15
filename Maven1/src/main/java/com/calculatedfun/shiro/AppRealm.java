package com.calculatedfun.shiro;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.calculatedfun.util.AppTool;
import com.kaifantech.entity.ResFormMap;
import com.kaifantech.entity.RoleFormMap;
import com.kaifantech.entity.UserFormMap;
import com.kaifantech.init.sys.params.AppCtrlParameters;
import com.kaifantech.mappings.AppResMapper;
import com.kaifantech.mappings.AppRoleMapper;
import com.kaifantech.mappings.AppUserMapper;

public class AppRealm extends AuthorizingRealm {
	@Inject
	private AppResMapper resourcesMapper;

	@Inject
	private AppRoleMapper roleMapper;

	@Inject
	private AppUserMapper userMapper;

	@Autowired
	private SessionDAO sessionDAO;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		if (loginName != null) {
			String userId = SecurityUtils.getSubject().getSession().getAttribute("userSessionId").toString();
			List<ResFormMap> rs = resourcesMapper.findUserResourcess(userId);
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for (ResFormMap resources : rs) {
				info.addStringPermission(resources.get("resKey").toString());
			}
			return info;
		}
		return null;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();

		if (AppCtrlParameters.isOneUserOneLogin()) {
			Session sessionLocal = SecurityUtils.getSubject().getSession();
			Collection<Session> sessions = sessionDAO.getActiveSessions();
			for (Session session : sessions) {
				if (username
						.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
					if (!sessionLocal.getId().equals(session.getId())) {
						if (AppCtrlParameters.isOneUserTickOff()) {
							session.setTimeout(0);
						} else {
							throw new AppUserLoggedException();
						}
					}
				}
			}
		}

		UserFormMap userFormMap = new UserFormMap();
		userFormMap.put("accountName", username);
		List<UserFormMap> results = userMapper.findByNames(userFormMap);
		if (AppTool.isNull(results) || results.size() > 1) {
			throw new UnknownAccountException();
		}

		HashMap<String, Object> result = results.get(0);
		if ("2".equals(result.get("locked"))) {
			throw new LockedAccountException();
		}
		if ("0".equals(result.get("locked"))) {
			throw new DisabledAccountException();
		}

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, result.get("password"),
				ByteSource.Util.bytes(username + "" + result.get("credentialsSalt")), getName());

		RoleFormMap roleFormMap = new RoleFormMap();
		roleFormMap.set("userId", result.get("id"));
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute("userSession", result);
		session.setAttribute("userRole", roleMapper.seletUserRole(roleFormMap));
		session.setAttribute("userSessionId", result.get("id"));
		session.setAttribute("userSessionAccount", result.get("accountName"));
		return authenticationInfo;
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 更新用户信息缓存.
	 */
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	/**
	 * 清除用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	/**
	 * 清除用户信息缓存.
	 */
	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	/**
	 * 清空所有缓存
	 */
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	/**
	 * 清空所有认证缓存
	 */
	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}