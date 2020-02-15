package com.kaifantech.util.log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.calculatedfun.util.AppFile;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.DateFactory;
import com.kaifantech.init.sys.SystemInitiator;

public class AppFileLogger {
	private static Map<Integer, String> msgTips = new HashMap<>();

	public static final String NORMAL = "NORMAL";
	public static final String SITE_CACHE = "SITE_CACHE";
	public static final String ERROR = "ERROR";
	public static final String WARNING = "WARNING";
	public static final String SQL = "SQL";
	public static final String WMS_INFO = "WMS_INFO";
	public static final String WMS_ERROR = "WMS_ERROR";
	public static final String SPEED_INFO = "SPEED_INFO";
	public static final String AGVPI_INFO = "AGVPI_INFO";
	public static final String CONNECT_INFO = "CONNECT_INFO";

	public static String getTips(Integer agvId) {
		return msgTips.get(agvId);
	}

	public static String setErrorTips(Integer agvId, Object... contents) {
		String content = error(contents);
		msgTips.put(agvId, content);
		return content;
	}

	public static String setWarningTips(Integer agvId, Object... contents) {
		String content = warning(contents);
		msgTips.put(agvId, content);
		return content;
	}

	public static String setPiTips(Integer agvId, Object... contents) {
		String content = piLogs(contents);
		msgTips.put(agvId, content);
		return content;
	}

	public static String setConnectTips(Integer agvId, Object... contents) {
		String content = connectInfo(contents);
		msgTips.put(agvId, content);
		return content;
	}

	public static String log(Object... contents) {
		String content = AppTool.join(contents);
		return WriteLogsTo(NORMAL, content);
	}

	public static String out(String content) {
		String info = WriteLogsTo(NORMAL, content);
		System.err.println(info);
		return info;
	}

	public static String siteCacheInfo(String content) {
		return WriteLogsTo(SITE_CACHE, content);
	}

	public static String piLogs(Object... contents) {
		String content = AppTool.join(contents);
		String info = WriteLogsTo(AGVPI_INFO, content);
		return info;
	}

	public static String connectInfo(Object... contents) {
		String content = AppTool.join(contents);
		String info = WriteLogsTo(CONNECT_INFO, content);
		return info;
	}

	public static String wmsInfo(String content) {
		return WriteLogsTo(WMS_INFO, content);
	}

	public static String wmsErr(String content) {
		return WriteLogsTo(WMS_ERROR, content);
	}

	public static String speedInfo(String content) {
		return WriteLogsTo(SPEED_INFO, content);
	}

	public static String error(Object... contents) {
		String content = AppTool.join(contents);
		String info = WriteLogsTo(ERROR, content);
		System.err.println(info);
		return info;
	}

	public static String warning(Object... contents) {
		String content = AppTool.join(contents);
		String info = WriteLogsTo(WARNING, content);
		System.out.println(info);
		return info;
	}

	public static String sql(Object... contents) {
		String content = AppTool.join(contents);
		String info = WriteLogsTo(SQL, content);
		return info;
	}

	private static String logPath(String type) throws IOException {
		return AppFile.getOrCreateFile(SystemInitiator.LOG_PATH + DateFactory.currentDate2String("yyMMdd"),
				type + DateFactory.currentDate2String("yyMMdd") + ".txt");
	}

	public static String WriteLogsTo(String type, String content) {
		try {
			return WriteLogsTo(type, content, logPath(type + "_"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void find(Integer agvId, Object... contents) {
		String content = AppTool.join(contents);
		WriteLogsTo("find", content, agvId);
	}

	public static String WriteLogsTo(String type, String content, Integer agvId) {
		try {
			return WriteLogsTo(type, content, logPath(type + agvId + "_"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String WriteLogsTo(String type, String content, String filepath) {
		String innerContent = type + "_" + DateFactory.getCurrTime() + "_" + content;
		return AppFile.Write(innerContent, filepath);
	}

}
