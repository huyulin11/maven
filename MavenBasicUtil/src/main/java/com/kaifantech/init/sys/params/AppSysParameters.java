package com.kaifantech.init.sys.params;

import com.kaifantech.init.sys.UdfBusinessInfo;
import com.kaifantech.init.sys.BaseBusinessInfo;

public class AppSysParameters extends AppBaseParameters {
	static {
		if (UdfBusinessInfo.CURRENT_CLIENT.equals(BaseBusinessInfo.Clients.YUFENG)) {
			isConnectIotServer = true;
			isConnectIotClient = false;
		} else {
		}
	}

	public static void setTaskstop(Integer agvId, boolean flag) {
		cache().set(CacheKeys.agvTaskStop(agvId), flag(flag));
	}

	public static boolean getTaskstop(Integer agvId) {
		return flag(cache().getOrInit(CacheKeys.agvTaskStop(agvId), "FALSE"));
	}

	private static boolean isInitTaskSiteInfo = false;

	public synchronized static boolean isInitTaskSiteInfo() {
		return isInitTaskSiteInfo;
	}

	public synchronized static void setInitTaskSiteInfo(boolean yesOrNo) {
		cache().hset(CacheKeys.initData(), "IS_INIT_TASK_SITE_INFO", flag(yesOrNo));
		AppSysParameters.isInitTaskSiteInfo = yesOrNo;
	}

	private static boolean isInitTaskSiteLogic = false;

	public synchronized static boolean isInitTaskSiteLogic() {
		return isInitTaskSiteLogic;
	}

	public synchronized static void setInitTaskSiteLogic(boolean yesOrNo) {
		cache().hset(CacheKeys.initData(), "IS_INIT_TASK_SITE_LOGIC", flag(yesOrNo));
		AppSysParameters.isInitTaskSiteLogic = yesOrNo;
	}

	private static boolean isInitTaskInfo = false;

	public synchronized static boolean isInitTaskInfo() {
		return isInitTaskInfo;
	}

	public synchronized static void setInitTaskInfo(boolean yesOrNo) {
		cache().hset(CacheKeys.initData(), "IS_INIT_TASK_INFO", flag(yesOrNo));
		AppSysParameters.isInitTaskInfo = yesOrNo;
	}

	private static boolean isInitAllocInfo = false;

	public synchronized static boolean isInitAllocInfo() {
		return isInitAllocInfo;
	}

	public synchronized static void setInitAllocInfo(boolean yesOrNo) {
		cache().hset(CacheKeys.initData(), "IS_INIT_ALLOC_INFO", flag(yesOrNo));
		AppSysParameters.isInitAllocInfo = yesOrNo;
	}

	private static boolean isInitLed = false;

	public synchronized static boolean isInitLed() {
		return isInitLed;
	}

	private static boolean isCheckSql = flag(conf().getOrInit("IS_CHECK_SQL", "FALSE"));

	public synchronized static boolean isCheckSql() {
		return isCheckSql;
	}

	public synchronized static void setInitLed(boolean yesOrNo) {
		cache().hset(CacheKeys.initData(), "IS_INIT_LED", flag(yesOrNo));
		AppSysParameters.isInitLed = yesOrNo;
	}

	private static boolean isConnectIotClient = true;

	public static boolean isConnectIotClient() {
		return isConnectIotClient;
	}

	private static boolean isConnectIotServer = true;

	public static boolean isConnectIotServer() {
		return isConnectIotServer;
	}

	private static boolean isOpenPi = false;

	public static boolean isOpenPi() {
		return isOpenPi;
	}

	public static boolean setOpenPi(boolean isOpenPi) {
		cache().set(CacheKeys.IS_OPEN_PI, flag(isOpenPi));
		AppSysParameters.isOpenPi = isOpenPi;
		return isOpenPi;
	}

	public static void setOpenPi() {
		String flag = cache().getOrInit(CacheKeys.IS_OPEN_PI, conf().getOrInit(CacheKeys.IS_OPEN_PI, flag(true)));
		AppSysParameters.isOpenPi = flag(flag);
	}

	private static boolean isReceiptNeedScan = flag(cache().getOrInit(CacheKeys.IS_RECEIPT_NEED_SCAN, "TRUE"));

	public static boolean isReceiptNeedScan() {
		return isReceiptNeedScan;
	}

	public static boolean isLocalTest() {
		return flag(cache().getOrInit(CacheKeys.IS_LOCAL_TEST, conf().getOrInit(CacheKeys.IS_LOCAL_TEST, "FALSE")));
	}

	public static boolean isAllowCacheTask() {
		return flag(cache().getOrInit(CacheKeys.IS_ALLOW_CACHE_TASK,
				conf().getOrInit(CacheKeys.IS_ALLOW_CACHE_TASK, "FALSE")));
	}

	private static boolean isShutdownThenToInit = flag(cache().getOrInit(CacheKeys.IS_SHUTDOWN_THEN_TO_INIT, "FALSE"));

	public static boolean isShutdownThenToInit() {
		return isShutdownThenToInit;
	}

	public static void setShutdownThenToInit(boolean isShutdownThenToInit) {
		AppSysParameters.isShutdownThenToInit = isShutdownThenToInit;
	}

	private static int generalSearchInterval = 500;

	public static int getGeneralSearchInterval() {
		return generalSearchInterval;
	}

	public static void setGeneralSearchInterval(int generalSearchInterval) {
		AppSysParameters.generalSearchInterval = generalSearchInterval;
	}

	private static long timeoutMillSeconds = getLong(conf().getOrInit("TIMEOUT_MILL_SECONDS", 600000));

	public static long getTimeoutMillSeconds() {
		return timeoutMillSeconds;
	}

	public static void setTimeoutMillSeconds(long timeoutMillSeconds) {
		AppSysParameters.timeoutMillSeconds = timeoutMillSeconds;
	}
}