package com.kaifantech.util.lock;

import com.calculatedfun.util.AppTool;
import com.kaifantech.cache.manager.AppConf;
import com.kaifantech.init.sys.params.AppConfKeys;

public class WmsAllocLock {
	private static final String COLD_LOCK = "COLD_LOCK";

	public static synchronized String getLockedObj(Object alloc) {
		String lockedPaperid = AppConf.worker().get(AppConfKeys.WMS_ALLOC_LOCK, alloc.toString());
		if (AppTool.isNull(lockedPaperid)) {
			return null;
		}
		return lockedPaperid;
	}

	public static synchronized void lock(Object alloc, String paperid) throws WarehouseLockException {
		if (AppTool.isAnyNull(alloc, paperid)) {
			throw new NullPointerException();
		}
		String lockedPaperid = getLockedObj(COLD_LOCK);
		if (!AppTool.isNull(lockedPaperid) && !lockedPaperid.equals(paperid)) {
			throw new WarehouseLockException(lockedPaperid);
		}
		AppConf.worker().hset(AppConfKeys.WMS_ALLOC_LOCK, COLD_LOCK, paperid);
		AppConf.worker().hset(AppConfKeys.WMS_ALLOC_LOCK, alloc.toString(), paperid);
	}

	public static synchronized void release(Object alloc, String paperid) {
		String lockedPaperid = getLockedObj(COLD_LOCK);
		if (!AppTool.isNull(lockedPaperid) && lockedPaperid.equals(paperid)) {
			AppConf.worker().hset(AppConfKeys.WMS_ALLOC_LOCK, COLD_LOCK, "");
		}
		AppConf.worker().hset(AppConfKeys.WMS_ALLOC_LOCK, alloc.toString(), "");
	}
}
