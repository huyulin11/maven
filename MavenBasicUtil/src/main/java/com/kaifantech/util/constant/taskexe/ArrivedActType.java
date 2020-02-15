
package com.kaifantech.util.constant.taskexe;

import com.calculatedfun.util.AppTool;

public class ArrivedActType {
	public static final String START = "0";
	public static final String ALLOC_STOCK = "1";
	public static final String ALLOC_GET = "2";
	public static final String ALLOC_SCAN = "3";
	public static final String CHARGE = "4";
	public static final String WINDOW_STOCK = "5";
	public static final String WINDOW_GET = "6";
	public static final String TURN_LEFT = "7";
	public static final String TURN_RIGHT = "8";
	public static final String TURN_MIDDLE = "11";
	public static final String TURN_BACK = "12";

	public static final String STOP = "9";
	public static final String NORMAL_STOP = "NS";
	public static final String SLOW_STOP = "S";
	public static final String STOP_WORK = "SW";

	public static final String CHANGE_SPEED = "10";

	public static final String AUTODOOR_OPEN = "B";
	public static final String LIFT_READY = "C";
	public static final String LIGHT_READY = "D";
	public static final String HOOK_UP = "E";
	public static final String HOOK_DOWN = "F";
	public static final String ARRIVAL = "A";

	public static final String REVOLVE = "R";
	public static final String WAIT = "W";
	public static final String PI = "PI";

	public static boolean noContinueAct(String act) {
		if (AppTool.equals(act, TURN_LEFT, TURN_RIGHT, TURN_MIDDLE, CHANGE_SPEED, START, STOP, SLOW_STOP,
				NORMAL_STOP)) {
			return true;
		}
		return false;
	}

	public static boolean isStopAct(String act) {
		if (!noContinueAct(act)) {
			return true;
		}
		if (AppTool.equals(act, STOP, SLOW_STOP, STOP_WORK, NORMAL_STOP, WAIT)) {
			return true;
		}
		return false;
	}
}
