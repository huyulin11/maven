package com.kaifantech.init.sys.params;

import com.calculatedfun.util.AppTool;
import com.kaifantech.cache.manager.AppCache;
import com.kaifantech.cache.manager.AppConf;
import com.kaifantech.cache.worker.ICacheWorker;

public class AppBaseParameters {
	public static ICacheWorker cache() {
		return AppCache.worker();
	}

	public static ICacheWorker conf() {
		return AppConf.worker();
	}

	public static String flag(boolean flag) {
		return flag ? "TRUE" : "FALSE";
	}

	public static String flag(int val) {
		return "" + val;
	}

	public static boolean flag(String flag) {
		return "TRUE".equals(flag);
	}

	public static int integer(String flag) {
		if (AppTool.isNull(flag)) {
			return 0;
		}
		return Integer.parseInt(flag);
	}

	public static long getLong(String flag) {
		return Long.parseLong(flag);
	}
}