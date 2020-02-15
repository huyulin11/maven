package com.kaifantech.init.sys.params;

import java.util.HashMap;
import java.util.Map;

import com.calculatedfun.util.AppTool;

public class SystemLock {
	private static Map<Integer, Object> agvsLocks = new HashMap<>();

	public static Object agv(Integer id) {
		Object lock = agvsLocks.get(id);
		if (AppTool.isNull(lock)) {
			agvsLocks.put(id, new Object());
			lock = agvsLocks.get(id);
		}
		return lock;
	}
}
