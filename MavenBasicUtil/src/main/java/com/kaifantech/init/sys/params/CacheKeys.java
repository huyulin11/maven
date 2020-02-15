package com.kaifantech.init.sys.params;

import com.kaifantech.cache.manager.AppCache;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.hex.AppByteUtil;

public class CacheKeys {
	private static boolean isTest = false;
	private static String packName = "";
	public static String separator = AppCache.getSeparator();

	public static final String IS_AUTO_TASK = "IS_AUTO_TASK";
	public static final String IS_OPEN_PI = "IS_OPEN_PI";
	public static final String IS_LOCAL_TEST = "IS_LOCAL_TEST";
	public static final String IS_ALLOW_CACHE_TASK = "IS_ALLOW_CACHE_TASK";
	public static final String IS_SHUTDOWN_THEN_TO_INIT = "IS_SHUTDOWN_THEN_TO_INIT";
	public static final String IS_RECEIPT_NEED_SCAN = "IS_RECEIPT_NEED_SCAN";
	public static final String IS_AUTO_CHARGE = "IS_AUTO_CHARGE";
	public static final String IS_ERR_BACK = "IS_ERR_BACK";
	public static final String WMS_SERVER_IP = "WMS_SERVER_IP";

	public static final String AGV_GENERAL_SEARCH_INTEVAL = "AGV_GENERAL_SEARCH_INTEVAL";
	public static final String AGV_JUDGED_TIMES_INTEVAL = "AGV_JUDGED_TIMES_INTEVAL";

	public static final String YUFENG_SOCKET_MSG = "YUFENG_SOCKET_MSG";

	public static final String AGV_CHAGRE_BATTRY_QUANTITY = "AGV_CHAGRE_BATTRY_QUANTITY";

	public static final String ROAD_LOCKS = "ROAD_LOCKS";

	public static boolean isTest() {
		return isTest;
	}

	public static String robotIp(int agvId) {
		return "ROBOT_IP_" + agvId;
	}

	public static void setTest(boolean isTest) {
		CacheKeys.isTest = isTest;
	}

	public static String scanMsg(Integer devId) {
		String keyVal = "SCAN" + separator + "MSG_LIST";
		return getScanKey(devId, keyVal);
	}

	public static String scanCmd(Integer devId) {
		String keyVal = "SCAN" + separator + "CMD_LIST";
		return getScanKey(devId, keyVal);
	}

	public static String plcMsgList(Integer devId) {
		String keyVal = "PLC" + separator + "MSG_LIST";
		return getSocketKey(devId, keyVal);
	}

	public static String plcMsgKey() {
		String keyVal = "PLC" + separator + "MSG_KEY";
		return getStableKey(null, keyVal);
	}

	public static String plcCmd(Integer devId) {
		String keyVal = "PLC" + separator + "CMD";
		return getSocketKey(devId, keyVal);
	}

	public static String taskShutdown() {
		String keyVal = "TASK_SHUTDOWN" + separator + "KEY";
		return getStableKey(null, keyVal);
	}

	public static String chargeMsgKey() {
		String keyVal = "CHARGE" + separator + "MSG_KEY";
		return getStableKey(null, keyVal);
	}

	public static String chargeMsgList(Integer devId) {
		String keyVal = "CHARGE" + separator + "MSG_LIST";
		return getSocketKey(devId, keyVal);
	}

	public static String chargeCmd(Integer devId) {
		String keyVal = "CHARGE" + separator + "CMD";
		return getSocketKey(devId, keyVal);
	}

	public static String windowMsg() {
		String keyVal = "WINDOW" + separator + "MSG";
		return getStableKey(null, keyVal);
	}

	public static String distributionMsg() {
		String keyVal = "DITRIBUTION" + separator + "MSG";
		return getStableKey(null, keyVal);
	}

	public static String windowCmd(Integer devId) {
		String keyVal = "WINDOW" + separator + "CMD";
		return getSocketKey(devId, keyVal);
	}

	public static String distributionCmd(Integer devId) {
		String keyVal = "DITRIBUTION" + separator + "CMD";
		return getSocketKey(devId, keyVal);
	}

	public static String robotMsg(Object devId) {
		String keyVal = "ROBOT" + separator + "MSG";
		return getRobotKey(devId, keyVal);
	}

	public static String initData() {
		String keyVal = "INIT_DATA";
		return getConfigurekey(keyVal);
	}

	public static String exeCtrl() {
		String keyVal = "EXE_CTRL";
		return getConfigurekey(keyVal);
	}

	public static String lastsendcmdid() {
		String keyVal = "LASTSENDCMDID";
		return getSocketkey(keyVal);
	}

	public static String initagvstartsiteid() {
		String keyVal = "INIT_AGV_START_SITEID";
		return getConfigurekey(keyVal);
	}

	public static String initwindowsiteid() {
		String keyVal = "INIT_WINDOW_SITEID";
		return getConfigurekey(keyVal);
	}

	public static String initchargesiteid() {
		String keyVal = "INIT_CHARGE_SITEID";
		return getConfigurekey(keyVal);
	}

	protected static String getStableKey(Object devId, String keyVal) {
		String key = getkeyType("FULL") + separator + (devId == null ? "ALL" : devId) + separator + keyVal;
		return key;
	}

	protected static String getSocketKey(Object devId, String keyVal) {
		String key = getkeyType("SOCKET") + separator + (devId == null ? "ALL" : devId) + separator + keyVal;
		return key;
	}

	protected static String getScanKey(Object devId, String keyVal) {
		String key = getkeyType("SCAN") + separator + (devId == null ? "ALL" : devId) + separator + keyVal;
		return key;
	}

	protected static String getAgvCacheStockKey(Object devId, String keyVal) {
		String key = getkeyType("STOCK") + separator + (devId == null ? "ALL" : devId) + separator + keyVal;
		return key;
	}

	protected static String getRobotKey(Object devId, String keyVal) {
		String key = getkeyType("ROBOT") + separator + (devId == null ? "ALL" : devId) + separator + keyVal;
		return key;
	}

	protected static String getSocketkey(String keyVal) {
		String key = getkeyType("SOCKET") + separator + keyVal;
		return key;
	}

	protected static String getConfigurekey(String keyVal) {
		String key = getkeyType("CONFIGURE") + separator + keyVal;
		return key;
	}

	public static String simulatorCurrentPlcAnswer() {
		String keyVal = "CURRENT_PLC_ANSWER";
		return getSimulatorKey(keyVal);
	}

	public static String simulatorCurrentSite() {
		String keyVal = "CURRENT_SITE";
		return getSimulatorKey(keyVal);
	}

	public static String simulatorCurrentSpeed() {
		String keyVal = "CURRENT_SPEED";
		return getSimulatorKey(keyVal);
	}

	public static String simulatorCurrentBattery() {
		String keyVal = "CURRENT_BATTERY";
		return getSimulatorKey(keyVal);
	}

	public static String simulatorCurrentButton() {
		String keyVal = "CURRENT_BUTTON";
		return getSimulatorKey(keyVal);
	}

	protected static String getSimulatorKey(String keyVal) {
		String key = getkeyType("SIMULATOR") + separator + keyVal;
		return key;
	}

	public static String getkeyType(String type) {
		String key = getPackName() + separator + type;
		return key;
	}

	public synchronized static String getSeq() {
		String is = AppCache.worker().getOrInit("SYS_SEQUENCE", "TEST_SEQ", "0");
		AppCache.worker().hset("SYS_SEQUENCE", "TEST_SEQ", "" + (Integer.parseInt(is) + 1));
		return AppByteUtil.intToHex4(256 * 256 - 1 - Integer.parseInt(is)).toUpperCase();
	}

	protected synchronized static String getPackName() {
		packName = UdfQualifier.DEFAULT_SERVICE_PREFIX.toUpperCase();
		return packName;
	}

	public static String agvTaskStop(int agvId) {
		return "TASK_STOP_" + agvId;
	}
}
