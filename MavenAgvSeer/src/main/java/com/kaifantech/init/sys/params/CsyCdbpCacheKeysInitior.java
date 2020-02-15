package com.kaifantech.init.sys.params;

import com.kaifantech.cache.manager.AppCache;
import com.kaifantech.init.sys.qualifier.UdfQualifier;

public class CsyCdbpCacheKeysInitior {
	static {
		AppCache.worker().del(UdfQualifier.DEFAULT_SERVICE_PREFIX + "_socket%");
	}

	public static void clear() {
		AppCache.worker().del(UdfQualifier.DEFAULT_SERVICE_PREFIX + "%plc%list%");
		AppCache.worker().del(UdfQualifier.DEFAULT_SERVICE_PREFIX + "%charge%list%");
	}

	public static void clearOnceADay() {
		AppCache.worker().del(UdfQualifier.DEFAULT_SERVICE_PREFIX + "%robot_msg%");
	}
}
