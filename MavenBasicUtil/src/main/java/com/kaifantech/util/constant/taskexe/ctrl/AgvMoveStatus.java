package com.kaifantech.util.constant.taskexe.ctrl;

import com.calculatedfun.util.AppTool;

public class AgvMoveStatus {
	public static final String PAUSE_USER = "PAUSE_USER";
	public static final String PAUSE_SYS = "PAUSE_SYS";
	public static final String PAUSE_SELF = "PAUSE_SELF";
	public static final String PAUSE_OUT_ERR = "PAUSE_OUT_ERR";
	public static final String PAUSE_REPATH_ERR = "PAUSE_REPATH_ERR";
	public static final String PAUSE_CACHE_ERR = "PAUSE_CACHE_ERR";
	public static final String CONTINUE = "CONTINUE";
	public static final String SHUTDOWN = "SHUTDOWN";

	public static boolean contains(String tasktype) {
		if (CONTINUE.equals(tasktype) || PAUSE_USER.equals(tasktype) || PAUSE_SYS.equals(tasktype)) {
			return true;
		}
		return false;
	}

	public static boolean cannotCache(String moveStatus) {
		if (!AppTool.equals(moveStatus, CONTINUE, PAUSE_USER, PAUSE_SYS, PAUSE_SELF, PAUSE_CACHE_ERR)) {
			return true;
		}
		return false;
	}
}
