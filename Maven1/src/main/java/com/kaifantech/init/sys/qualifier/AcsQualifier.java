package com.kaifantech.init.sys.qualifier;

import com.kaifantech.init.sys.ServicePrefix;

public class AcsQualifier {
	private static final String DEFAULT_SERVICE_PREFIX = ServicePrefix.ACS;

	public static final String ALLOC_AMOUNT_SERVICE = DEFAULT_SERVICE_PREFIX + "AllocAmountService";

	public static final String TASKEXE_MODULE = DEFAULT_SERVICE_PREFIX + "TaskexeModule";
	public static final String CTRL_MODULE = DEFAULT_SERVICE_PREFIX + "CtrlModule";
	public static final String AGV_SERVER_WORKER = DEFAULT_SERVICE_PREFIX + "AgvServerWorker";
	public static final String TASKEXE_CHECK_SERVICE = DEFAULT_SERVICE_PREFIX + "TaskexeCheckService";

	public static final String AGV_CMD_CTRL_MODULE = DEFAULT_SERVICE_PREFIX + "AgvCmdCtrlModule";
	public static final String AGV_MSG_RESOLUTE_MODULE = DEFAULT_SERVICE_PREFIX + "MsgResoluteModule";
	public static final String AGV_MSG_INFO_MODULE = DEFAULT_SERVICE_PREFIX + "AgvMsgInfoModule";

	public static final String AGV_CLIENT_WORKER = DEFAULT_SERVICE_PREFIX + "AgvClientWorker";

	public static final String PI_WORK_TODO_SERVICE = DEFAULT_SERVICE_PREFIX + "PiWorkService";

	public static final String TASKEXE_ADD_SERVICE = DEFAULT_SERVICE_PREFIX + "TaskexeAddService";

	public static final String IOT_CLIENT_SERVICE = DEFAULT_SERVICE_PREFIX + "IotClientService";

	public static final String TASK_SITE_INFO_SERVICE = DEFAULT_SERVICE_PREFIX + "taskSiteInfoService";

	public static final String ALLOC_CHECK_SERVICE = DEFAULT_SERVICE_PREFIX + "AllocCheckService";
}
