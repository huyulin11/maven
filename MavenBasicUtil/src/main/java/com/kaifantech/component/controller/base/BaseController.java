package com.kaifantech.component.controller.base;

import com.calculatedfun.erp.plugin.PageView;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.kaifantech.util.log.AppFileLogger;

public class BaseController<TT extends FormMap> implements IBaseAppCompnent<TT> {
	public PageView pageView = null;

	public static final String METHOD_ADD = "新增";
	public static final String METHOD_EDIT = "修改";
	public static final String METHOD_DELETE = "删除";

	public PageView getPageView(Integer pageNow, Integer pageSize) {
		if (AppTool.isNull(pageNow)) {
			pageView = new PageView(1);
		} else {
			pageView = new PageView(pageNow);
		}
		if (AppTool.isNull(pageSize)) {
			pageSize = 40;
		}
		pageView.setPageSize(pageSize);
		return pageView;
	}

	public PageView getPageView() {
		return pageView;
	}

	public static void main(String[] args) {
		AppFileLogger.warning(new BaseController<>().isArrayParam("sysDicDataFormMap.dicvalue[0]"));
		;
	}
}