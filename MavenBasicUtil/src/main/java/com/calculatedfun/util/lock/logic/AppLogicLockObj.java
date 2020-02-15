package com.calculatedfun.util.lock.logic;

import java.util.HashMap;
import java.util.Map;

import com.calculatedfun.util.AppTool;

public class AppLogicLockObj {
	private Map<Integer, Boolean> locks = new HashMap<>();

	public AppLogicLockObj() {
	}

	public synchronized Boolean isLock(Integer id) {
		Boolean lock = locks.get(id);
		if (AppTool.isNull(lock)) {
			return false;
		}
		return lock;
	}

	public synchronized void lock(Integer id) {
		locks.put(id, true);
	}

	public synchronized void release(Integer id) {
		locks.put(id, false);
	}
}
