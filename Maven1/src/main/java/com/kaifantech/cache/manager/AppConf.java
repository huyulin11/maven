package com.kaifantech.cache.manager;

import com.kaifantech.cache.worker.AppMysqlConfWorker;
import com.kaifantech.cache.worker.ICacheWorker;

public class AppConf {
	private static ICacheWorker worker;

	public static ICacheWorker worker() {
		if (worker == null) {
			worker = new AppMysqlConfWorker();
		}
		return worker;
	}
}
