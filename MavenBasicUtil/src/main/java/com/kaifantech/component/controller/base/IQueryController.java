package com.kaifantech.component.controller.base;

import java.util.List;

import com.calculatedfun.erp.plugin.PageView;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.BaseSqlConstants;

public interface IQueryController<TT extends FormMap> extends IBaseGetter<TT>, IBaseAppCompnent<TT> {
	default PageView defaultFindByPage(Integer pageNow, Integer pageSize, boolean withDel) throws Exception {
		TT formMap = getFormMap();
		formMap = toFormMap(formMap, pageNow, pageSize);
		if (!withDel) {
			formMap.set(BaseSqlConstants.COL_DELFLAG, "0");
		}
		List<TT> data = defaultFindByPage(formMap);
		return getPageView().setRecords(data);
	}

	default List<TT> defaultFindByPage(TT formMap) throws Exception {
		return getMapper().findByPage(formMap);
	}

	default PageView defaultFindFirstPage() throws Exception {
		return defaultFindByPage(1, 10, false);
	}

	default List<TT> defaultFindList(TT formMap) throws Exception {
		return getMapper().findByNames(formMap);
	}
}