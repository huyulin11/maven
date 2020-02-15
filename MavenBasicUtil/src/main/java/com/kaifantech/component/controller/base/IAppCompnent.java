package com.kaifantech.component.controller.base;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.Common;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.BaseSqlConstants;

public interface IAppCompnent {
	default <T extends FormMap> T getFormMap(Class<T> clazz) {
		return getFormMap(clazz, true);
	}

	default <T extends FormMap> T newInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}

	@SuppressWarnings("unchecked")
	default <T extends FormMap> T getFormMap(Class<T> clazz, boolean checkPrefix) {
		HttpServletRequest request = AppSession.request();
		Enumeration<String> allParams = request.getParameterNames();
		T tParent = null;
		try {
			tParent = newInstance(clazz);
			FormMap mapParent = (FormMap) tParent;
			while (allParams.hasMoreElements()) {
				String thisParam = allParams.nextElement().toString();
				String shortParam = null;
				String paramsObjName = tParent.getClass().getSimpleName().toUpperCase();
				if (thisParam.toUpperCase().startsWith(paramsObjName)) {
					if (thisParam.endsWith("[]")) {
						String[] as = request.getParameterValues(thisParam);
						shortParam = thisParam.substring(paramsObjName.length() + 1);
						mapParent.put(shortParam, as);
					} else if (isArrayParam(thisParam)) {
						String num = thisParam.substring(thisParam.lastIndexOf('[') + 1, thisParam.lastIndexOf(']'));
						Map<String, T> sonMap = (Map<String, T>) mapParent.get(BaseSqlConstants.LIST);
						if (AppTool.isNull(sonMap)) {
							sonMap = new HashMap<String, T>();
						}
						T tSon = sonMap.get(num);
						if (AppTool.isNull(tSon)) {
							tSon = newInstance(clazz);
						}
						FormMap mapSon = (FormMap) tSon;
						String as = request.getParameter(thisParam);
						shortParam = thisParam.substring(paramsObjName.length() + 1, thisParam.lastIndexOf('['));
						mapSon.set(shortParam, as);
						sonMap.put(num, tSon);
						mapParent.put(BaseSqlConstants.LIST, sonMap);
					} else {
						String as = request.getParameter(thisParam);
						shortParam = thisParam.substring(paramsObjName.length() + 1);
						if (!Common.isEmpty(as)) {
							mapParent.put(shortParam, as);
						}
					}
				} else if (!checkPrefix) {
					if (thisParam.endsWith("[]")) {
						String[] as = request.getParameterValues(thisParam);
						shortParam = thisParam;
						mapParent.put(shortParam, as);
					} else if (isArrayParam(thisParam)) {
						String num = thisParam.substring(thisParam.lastIndexOf('[') + 1, thisParam.lastIndexOf(']'));
						Map<String, T> sonMap = (Map<String, T>) mapParent.get(BaseSqlConstants.LIST);
						if (AppTool.isNull(sonMap)) {
							sonMap = new HashMap<String, T>();
						}
						T tSon = sonMap.get(num);
						if (AppTool.isNull(tSon)) {
							tSon = newInstance(clazz);
						}
						FormMap mapSon = (FormMap) tSon;
						String as = request.getParameter(thisParam);
						shortParam = thisParam.substring(0, thisParam.lastIndexOf('['));
						mapSon.set(shortParam, as);
						sonMap.put(num, tSon);
						mapParent.put(BaseSqlConstants.LIST, sonMap);
					} else {
						String as = request.getParameter(thisParam);
						shortParam = thisParam;
						if (!Common.isEmpty(as)) {
							mapParent.put(shortParam, as);
						}
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return tParent;
	}

	@SuppressWarnings("unchecked")
	default FormMap getSimpleFormMap() {
		HttpServletRequest request = AppSession.request();
		Enumeration<String> allParams = request.getParameterNames();
		FormMap formMap = new FormMap();
		while (allParams.hasMoreElements()) {
			String thisParam = allParams.nextElement().toString();
			String shortParam = null;
			if (thisParam.endsWith("[]")) {
				String[] as = request.getParameterValues(thisParam);
				shortParam = thisParam;
				formMap.put(shortParam, as);
			} else if (isArrayParam(thisParam)) {
				String num = thisParam.substring(thisParam.lastIndexOf('[') + 1, thisParam.lastIndexOf(']'));
				Map<String, FormMap> sonMap = (Map<String, FormMap>) formMap.get(BaseSqlConstants.LIST);
				if (AppTool.isNull(sonMap)) {
					sonMap = new HashMap<String, FormMap>();
				}
				FormMap tSon = sonMap.get(num);
				if (AppTool.isNull(tSon)) {
					tSon = new FormMap();
				}
				FormMap mapSon = (FormMap) tSon;
				String as = request.getParameter(thisParam);
				shortParam = thisParam.substring(0, thisParam.lastIndexOf('['));
				mapSon.set(shortParam, as);
				sonMap.put(num, tSon);
				formMap.put(BaseSqlConstants.LIST, sonMap);
			} else {
				String as = request.getParameter(thisParam);
				shortParam = thisParam;
				if (!Common.isEmpty(as)) {
					formMap.put(shortParam, as);
				}
			}
		}
		return formMap;
	}

	default boolean isArrayParam(String param) {
		Pattern pattern = Pattern.compile("[\\w|.]*\\[[0-9]*\\]");
		Matcher matcher = pattern.matcher(param);
		return matcher.matches();
	}
}
