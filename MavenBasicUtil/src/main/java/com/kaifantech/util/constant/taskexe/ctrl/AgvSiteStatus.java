package com.kaifantech.util.constant.taskexe.ctrl;

public class AgvSiteStatus {
	public static final String INIT = "INIT";
	public static final String MOVING = "MOVING";
	public static final String WINDOW_GET = "WINDOW_GET";
	public static final String WINDOW_STOCK = "WINDOW_STOCK";

	public static final String ALLOC_STOCK = "ALLOC_STOCK";
	public static final String ALLOC_GET = "ALLOC_GET";
	public static final String ALLOC_SCAN = "ALLOC_SCAN";

	public static final String WAITING_AUTODOOR = "WAITING_AUTODOOR";
	public static final String WAITING_LIFT = "WAITING_LIFT";
	public static final String HOOK_UP = "HOOK_UP";
	public static final String HOOK_DOWN = "HOOK_DOWN";

	public static final String REVOLVE = "REVOLVE";
	public static final String STOP_WORK = "STOP_WORK";
	public static final String WAIT = "WAIT";
	public static final String PI = "PI";

	public static final String CHARGING = "CHARGING";

	public static String alloc(String tasktype) {
		if (AgvTaskType.RECEIPT.equals(tasktype)) {
			return ALLOC_STOCK;
		}
		if (AgvTaskType.SHIPMENT.equals(tasktype)) {
			return ALLOC_GET;
		}
		if (AgvTaskType.INVENTORY.equals(tasktype)) {
			return ALLOC_SCAN;
		}
		return null;
	}

	public static String window(String tasktype) {
		if (AgvTaskType.RECEIPT.equals(tasktype)) {
			return WINDOW_GET;
		}
		if (AgvTaskType.SHIPMENT.equals(tasktype)) {
			return WINDOW_STOCK;
		}
		return null;
	}
}