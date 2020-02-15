package com.kaifantech.component.controller.base;

import com.calculatedfun.erp.plugin.PageView;

public interface IAppPageView extends IAppCompnent {
	public PageView getPageView();

	public PageView getPageView(Integer pageNow, Integer pageSize);
}