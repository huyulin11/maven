package com.kaifantech.init.sys.params;

public class SeerCacheKeys extends CacheKeys {
	public static String currentA() {
		String keyVal = "CURRENT" + separator + "A";
		return getStableKey(null, keyVal);
	}

	public static String currentX() {
		String keyVal = "CURRENT" + separator + "X";
		return getStableKey(null, keyVal);
	}

	public static String currentY() {
		String keyVal = "CURRENT" + separator + "Y";
		return getStableKey(null, keyVal);
	}

	public static String currentBattery() {
		String keyVal = "CURRENT" + separator + "BATTERY";
		return getStableKey(null, keyVal);
	}

	public static String taskFinished() {
		String keyVal = "TASK" + separator + "FINISHED";
		return getStableKey(null, keyVal);
	}

	public static String pauseStat() {
		String keyVal = "PAUSE" + separator + "STAT";
		return getStableKey(null, keyVal);
	}

	public static String agvMsgKey() {
		String keyVal = "AGV" + separator + "MSG_KEY";
		return getSocketKey(null, keyVal);
	}

	public static String agvMsgType(Integer devId) {
		String keyVal = "AGV" + separator + "MSG_TYPE";
		return getSocketKey(devId, keyVal);
	}

	public static String agvMsgCache(Integer devId) {
		String keyVal = "AGV" + separator + "MSG_CACHE";
		return getSocketKey(devId, keyVal);
	}

	public static String agvCmdType(Integer devId) {
		String keyVal = "AGV" + separator + "CMD_TYPE";
		return getSocketKey(devId, keyVal);
	}

	public static String agvCmdCache(Integer devId) {
		String keyVal = "AGV" + separator + "CMD_CACHE";
		return getSocketKey(devId, keyVal);
	}

	public static String agvMsgList(Integer devId) {
		String keyVal = "AGV" + separator + "MSG_LIST";
		return getSocketKey(devId, keyVal);
	}

	public static String agvCacheStock(Integer devId) {
		String keyVal = "AGV" + separator + "CACHE_STOCK";
		return getAgvCacheStockKey(devId, keyVal);
	}

	public static String agvMsgTest(Integer devId) {
		String keyVal = "AGV" + separator + "MSG_TEST";
		return getSocketKey(devId, keyVal);
	}

	public static String agvCmdList(Integer devId) {
		String keyVal = "AGV" + separator + "CMD_LIST";
		return getSocketKey(devId, keyVal);
	}
}
