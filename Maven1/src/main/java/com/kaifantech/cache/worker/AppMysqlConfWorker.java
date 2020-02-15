package com.kaifantech.cache.worker;

import com.kaifantech.init.sys.dao.ISystemTableDao;
import com.kaifantech.init.sys.dao.SystemConfDao;

public class AppMysqlConfWorker extends AbstractMysqlWorker {
	private ISystemTableDao dao;

	public ISystemTableDao getCacheTool() {
		if (dao == null) {
			dao = new SystemConfDao();
		}
		return dao;
	}
}