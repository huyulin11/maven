package com.kaifantech.util.constant.taskexe.ctrl;

public interface AgvCtrlType {
	public static final Integer AGV_PAUSE_BY_USER_VALUE = -1;
	public static final Integer AGV_PAUSE_BY_SYSTEM_VALUE = -2;
	public static final Integer AGV_CONTINUE_VALUE = 0;
	public static final String AGVS_STOP_PI = "stopPI";
	public static final String AGVS_OPEN_PI = "openPI";
	public static final String AGVS_STOP_PI_STR = "关闭交通管制";
	public static final String AGVS_OPEN_PI_STR = "打开交通管制";

	public static final String AGVS_STOP_AUTO_TASK = "stopAutoTask";
	public static final String AGVS_OPEN_AUTO_TASK = "openAutoTask";
	public static final String AGVS_STOP_AUTO_TASK_STR = "关闭自动任务功能";
	public static final String AGVS_OPEN_AUTO_TASK_STR = "打开自动任务功能";

	public static final String AGVS_STOP_AUTO_CHARGE = "stopAutoCharge";
	public static final String AGVS_OPEN_AUTO_CHARGE = "openAutoCharge";
	public static final String AGVS_STOP_AUTO_CHARGE_STR = "关闭自动充电功能";
	public static final String AGVS_OPEN_AUTO_CHARGE_STR = "打开自动充电功能";

	public static final String AGVS_STOP_ERR_BACK = "stopErrBack";
	public static final String AGVS_OPEN_ERR_BACK = "openErrBack";
	public static final String AGVS_STOP_ERR_BACK_STR = "关闭脱轨重新规划功能";
	public static final String AGVS_OPEN_ERR_BACK_STR = "打开脱轨重新规划功能";

	public static final String INFO = "SYSINFO";
	public static final String WARNING = "SYSWARNING";
	public static final String ERROR = "SYSERROR";
}
