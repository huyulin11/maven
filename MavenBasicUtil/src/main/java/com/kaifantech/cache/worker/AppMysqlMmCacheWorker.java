package com.kaifantech.cache.worker;

import com.kaifantech.init.sys.dao.ISystemTableDao;
import com.kaifantech.init.sys.dao.SystemCacheDao;

public class AppMysqlMmCacheWorker extends AbstractMysqlWorker {
	private ISystemTableDao dao;

	public ISystemTableDao getCacheTool() {
		if (dao == null) {
			dao = new SystemCacheDao();
		}
		return dao;
	}
}