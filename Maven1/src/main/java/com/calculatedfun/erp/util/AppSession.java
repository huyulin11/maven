package com.calculatedfun.erp.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.calculatedfun.util.AppTool;
import com.kaifantech.entity.RoleFormMap;
import com.kaifantech.entity.UserFormMap;

public class AppSession {
	public static Object getUserId() {
		Session session = SecurityUtils.getSubject().getSession();
		Object s = session.getAttribute("userSessionId");
		return s;
	}

	public static Object getUserAccount() {
		Session session = SecurityUtils.getSubject().getSession();
		Object s = session.getAttribute("userSessionAccount");
		return s;
	}

	public static Object getUserName() {
		try {
			Session session = SecurityUtils.getSubject().getSession();
			UserFormMap s = (UserFormMap) session.getAttribute("userSession");
			return s.get("userName");
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<RoleFormMap> getUserRoleList() {
		Session session = SecurityUtils.getSubject().getSession();
		List<RoleFormMap> s = (List<RoleFormMap>) session.getAttribute("userRole");
		return s;
	}

	@SuppressWarnings("unchecked")
	public static String getUserRoles() {
		Session session = SecurityUtils.getSubject().getSession();
		List<RoleFormMap> s = (List<RoleFormMap>) session.getAttribute("userRole");
		StringBuffer rsb = new StringBuffer().append("(");
		for (RoleFormMap r : s) {
			rsb.append(r.get("id") + ",");
		}
		return rsb.append("-1000)").toString();
	}

	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-real-ip");
		if (AppTool.isNull(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (AppTool.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (AppTool.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (AppTool.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static HttpServletRequest request() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

	public static String getParaValue(String key) {
		return request().getParameter(key);
	}

	public static String[] getParaValues(String key) {
		return request().getParameterValues(key);
	}

	public static String userAgent() {
		return userAgent(request());
	}

	public static String userAgent(HttpServletRequest request) {
		return request.getHeader("USER-AGENT").toLowerCase();
	}

	public static boolean isFromMobile() {
		return isFromMobile(request());
	}

	public static boolean isFromMobile(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();

		String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry"
				+ "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" + "|laystation portable)|nokia|fennec|htc[-_]"
				+ "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
		String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

		Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
		Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);
		if (null == userAgent) {
			userAgent = "";
		}
		Matcher matcherPhone = phonePat.matcher(userAgent);
		Matcher matcherTable = tablePat.matcher(userAgent);
		if (matcherPhone.find() || matcherTable.find()) {
			return true;
		} else {
			return false;
		}
	}
}
