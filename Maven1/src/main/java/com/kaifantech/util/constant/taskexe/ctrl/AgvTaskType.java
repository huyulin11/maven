package com.kaifantech.util.constant.taskexe.ctrl;

import com.calculatedfun.util.AppTool;
import com.kaifantech.init.sys.UdfBusinessInfo;
import com.kaifantech.init.sys.BaseBusinessInfo.Projects;

public class AgvTaskType {
	public static final String FREE = "FREE";
	public static final String RECEIPT = "RECEIPT";
	public static final String SHIPMENT = "SHIPMENT";
	public static final String INVENTORY = "INVENTORY";
	public static final String TRANSFER = "TRANSFER";
	public static final String ZUHE_RENWU = "ZUHE_RENWU";
	public static final String GOTO_CHARGE = "GOTO_CHARGE";
	public static final String BACK_CHARGE = "BACK_CHARGE";
	public static final String CHANGE_CHARGE = "CHANGE_CHARGE";

	public static final String CONFIRM = "CONFIRM";
	public static final String GOTO_INIT = "GOTO_INIT";
	public static final String RE_PATH = "RE_PATH";

	public static final String TRANSPORT = "TRANSPORT";
	public static final String FETCH = "FETCH";
	public static final String DELIVER = "DELIVER";

	public static boolean fromUser(String tasktype) {
		if (GOTO_CHARGE.equals(tasktype) || BACK_CHARGE.equals(tasktype)) {
			return true;
		}
		return false;
	}

	public static boolean match(String tasktype) {
		if (UdfBusinessInfo.CURRENT_PROJECT.equals(Projects.CSY_DAJ)) {
			if (AppTool.equals(tasktype, RECEIPT, SHIPMENT, INVENTORY, GOTO_CHARGE, BACK_CHARGE, CHANGE_CHARGE,
					GOTO_INIT)) {
				return true;
			}
		} else if (UdfBusinessInfo.CURRENT_PROJECT.equals(Projects.CSY_CDBP)) {
			if (AppTool.equals(tasktype, TRANSPORT, DELIVER, GOTO_CHARGE, GOTO_INIT, FETCH)) {
				return true;
			}
		} else if (UdfBusinessInfo.CURRENT_PROJECT.equals(Projects.LAO_FOXCONN)) {
			if (AppTool.equals(tasktype, TRANSPORT, DELIVER, GOTO_CHARGE, GOTO_INIT, FETCH)) {
				return true;
			}
		} else if (UdfBusinessInfo.CURRENT_PROJECT.equals(Projects.HONGFU_ZHENMU)) {
			if (AppTool.equals(tasktype, RECEIPT, SHIPMENT, GOTO_CHARGE, BACK_CHARGE)) {
				return true;
			}
		} else if (UdfBusinessInfo.CURRENT_PROJECT.equals(Projects.TAIKAI_JY)) {
			if (AppTool.equals(tasktype, TRANSPORT, DELIVER, GOTO_CHARGE, GOTO_INIT, FETCH)) {
				return true;
			}
		} else if (UdfBusinessInfo.CURRENT_PROJECT.equals(Projects.LAO_DBWY)) {
			if (AppTool.equals(tasktype, TRANSPORT, DELIVER, GOTO_CHARGE, GOTO_INIT)) {
				return true;
			}
		}
		return false;
	}

	public static boolean chargeOrInit(String tasktype) {
		if (AppTool.equals(tasktype, GOTO_CHARGE, BACK_CHARGE, CHANGE_CHARGE, GOTO_INIT)) {
			return true;
		}
		return false;
	}
}