package com.calculatedfun.util.lock.logic;

import java.util.HashMap;
import java.util.Map;

import com.calculatedfun.util.AppTool;

public class AppLogicLockMgr {
	private static Map<String, AppLogicLockObj> allLocks = new HashMap<>();

	public static AppLogicLockObj get(String name) {
		AppLogicLockObj lock = allLocks.get(name);
		if (AppTool.isNull(lock)) {
			lock = new AppLogicLockObj();
			allLocks.put(name, lock);
		}
		return lock;
	}
}