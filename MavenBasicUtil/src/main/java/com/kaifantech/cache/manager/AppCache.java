package com.kaifantech.cache.manager;

import com.kaifantech.cache.worker.AppMysqlMmCacheWorker;
import com.kaifantech.cache.worker.ICacheWorker;

public class AppCache {
	private static ICacheWorker worker;

	public static ICacheWorker worker() {
		if (worker == null) {
			worker = new AppMysqlMmCacheWorker();
		}
		return worker;
	}

	public static String getSeparator() {
		return worker().getSeparator();
	}
}
