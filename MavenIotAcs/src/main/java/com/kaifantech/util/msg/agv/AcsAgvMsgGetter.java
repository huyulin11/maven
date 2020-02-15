package com.kaifantech.util.msg.agv;

import java.util.HashMap;
import java.util.Map;

import com.calculatedfun.util.AppTool;
import com.kaifantech.util.observable.ClientMsgObservable;

public class AcsAgvMsgGetter {
	private static Map<Integer, String> currentMsgMap = new HashMap<>();
	private static Map<Integer, Boolean> currentConnectedMap = new HashMap<>();

	public static void setMsg(Integer agvId, String msg) {
		currentMsgMap.put(agvId, msg);
	}

	public static void setConnected(Integer agvId, Boolean msg) {
		currentConnectedMap.put(agvId, msg);
		notice(agvId, getMsg(agvId));
	}

	public static boolean isConnected(Integer agvId) {
		Boolean flag = currentConnectedMap.get(agvId);
		return !AppTool.isNull(flag) && flag;
	}

	public static String getMsg(Integer agvId) {
		String msg = currentMsgMap.get(agvId);
		if (AppTool.isNull(msg)) {
			return null;
		}
		return msg;
	}

	private static Map<Integer, ClientMsgObservable> observableMap = new HashMap<>();
	private static Map<Integer, java.util.Observer> catcherMap = new HashMap<>();

	public static synchronized ClientMsgObservable getObservable(Integer agvId) {
		ClientMsgObservable obj = observableMap.get(agvId);
		if (!AppTool.isNull(obj)) {
			return obj;
		}
		obj = new ClientMsgObservable();
		observableMap.put(agvId, obj);
		return null;
	}

	public static void setCatcher(Integer agvId, java.util.Observer catcher) {
		catcherMap.put(agvId, catcher);
	}

	public static void notice(Integer agvId, String agvMsgStr) {
		ClientMsgObservable obj = getObservable(agvId);
		if (AppTool.isNull(obj)) {
			return;
		}
		if (obj.countObservers() == 0) {
			java.util.Observer catcher = catcherMap.get(agvId);
			if (!AppTool.isNull(catcher))
				obj.addObserver(catcher);
		}
		obj.msg(agvMsgStr);
	}

}
