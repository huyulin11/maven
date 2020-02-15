package com.kaifantech.util.socket.factory.server;

import com.kaifantech.cache.manager.AppCache;
import com.kaifantech.init.sys.params.SeerCacheKeys;

public class SeerAgvNettyServerUtil {
	public static String currentA(Integer agvId) {
		return AppCache.worker().getOrInit(SeerCacheKeys.currentA(), "" + agvId, "1.2");
	}

	public static String currentX(Integer agvId) {
		return AppCache.worker().getOrInit(SeerCacheKeys.currentX(), "" + agvId, "1000");
	}

	public static String currentY(Integer agvId) {
		return AppCache.worker().getOrInit(SeerCacheKeys.currentY(), "" + agvId, "900");
	}

	public static String currentBattery(Integer agvId) {
		return AppCache.worker().getOrInit(SeerCacheKeys.currentBattery(), "" + agvId, "95");
	}

	public static String taskFinished(Integer agvId) {
		return AppCache.worker().getOrInit(SeerCacheKeys.taskFinished(), "" + agvId, "1");
	}

	public static String pauseStat(Integer agvId) {
		return AppCache.worker().getOrInit(SeerCacheKeys.pauseStat(), "" + agvId, "0");
	}

	public static String getMsgByCmd(String cmdReceived, Integer agvId) {
		String xPatten = "AX_VALUE";
		String yPatten = "AY_VALUE";
		String aPatten = "AA_VALUE";
		String batteryPatten = "BATTERY_VALUE";
		String pauseStat = "PAUSE_STAT";
		String taskFinishedPatten = "TASK_FINISHED_VALUE";
		String msgFromAGV = "cmd=position;pause_stat=" + pauseStat + ";battery=" + batteryPatten + ";error=0;x="
				+ xPatten + ";y=" + yPatten + ";a=" + aPatten + ";z=-2;gAlarm=1;speed=48;" + "task=1-Q-5-6-1-114.xml;"
				+ "veer_angle=75.000000;task_step=7;" + "task_isfinished=" + taskFinishedPatten + ";"
				+ "task_error=327688;walk_path_id=-1?";
		msgFromAGV = msgFromAGV.replaceAll(xPatten, "" + (currentX(agvId)));
		msgFromAGV = msgFromAGV.replaceAll(yPatten, "" + (currentY(agvId)));
		msgFromAGV = msgFromAGV.replaceAll(aPatten, "" + (currentA(agvId)));
		msgFromAGV = msgFromAGV.replaceAll(pauseStat, "" + (pauseStat(agvId)));
		msgFromAGV = msgFromAGV.replaceAll(taskFinishedPatten, "" + (taskFinished(agvId)));
		msgFromAGV = msgFromAGV.replaceAll(batteryPatten, "" + (currentBattery(agvId)));
		return msgFromAGV;
	}
}