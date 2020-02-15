package com.kaifantech.component.controller.base;

import com.calculatedfun.util.AppReflectHelper;
import com.calculatedfun.util.FormMap;

public interface IBaseAppCompnent<TT extends FormMap> extends IAppPageView {
	default Class<TT> getTClass() {
		return getTClass(0);
	}

	@SuppressWarnings("unchecked")
	default Class<TT> getTClass(int index) {
		return (Class<TT>) AppReflectHelper.getType(this.getClass(), index);
	}

	default TT getFormMap() {
		return getFormMap(true);
	}

	default TT getFormMap(boolean checkPrefix) {
		return getFormMap(getTClass(), checkPrefix);
	}

	default TT toFormMap(TT t, Integer pageNow, Integer pageSize) {
		FormMap formMap = (FormMap) t;
		formMap.put("paging", getPageView(pageNow, pageSize));
		return t;
	}

	default TT toFormMap(TT t) {
		FormMap formMap = (FormMap) t;
		formMap.put("paging", getPageView(1, 10));
		return t;
	}
}