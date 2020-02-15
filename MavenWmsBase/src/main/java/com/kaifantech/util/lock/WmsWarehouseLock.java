package com.kaifantech.util.lock;

import com.calculatedfun.util.AppTool;
import com.kaifantech.cache.manager.AppConf;
import com.kaifantech.init.sys.params.AppConfKeys;

public class WmsWarehouseLock {
	private static final String COLD_LOCK = "COLD_LOCK";

	public static synchronized String getLockedObj() {
		String lockedPaperid = AppConf.worker().get(AppConfKeys.WMS_ALLOC_LOCK, COLD_LOCK);
		if (AppTool.isNull(lockedPaperid)) {
			return null;
		}
		return lockedPaperid;
	}

	public static synchronized void lock(String paperid) throws WarehouseLockException {
		if (AppTool.isAnyNull(paperid)) {
			throw new NullPointerException();
		}
		String lockedPaperid = AppConf.worker().get(AppConfKeys.WMS_ALLOC_LOCK, COLD_LOCK);
		if (!AppTool.isNull(lockedPaperid) && !lockedPaperid.equals(paperid)) {
			throw new WarehouseLockException(lockedPaperid);
		}
		AppConf.worker().hset(AppConfKeys.WMS_ALLOC_LOCK, COLD_LOCK, paperid);
	}

	public static synchronized void release() {
		AppConf.worker().hset(AppConfKeys.WMS_ALLOC_LOCK, COLD_LOCK, "");
	}
}
