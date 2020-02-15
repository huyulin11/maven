package com.kaifantech.init.sys.qualifier;

import com.kaifantech.init.sys.ServicePrefix;

public class SeerQualifier {
	public static final String DEFAULT_SERVICE_PREFIX = ServicePrefix.SEER;

	public static final String ALLOC_CHECK_SERVICE = DEFAULT_SERVICE_PREFIX + "AllocCheckService";
	public static final String ALLOC_AMOUNT_SERVICE = DEFAULT_SERVICE_PREFIX + "AllocAmountService";

	public static final String TASKEXE_MODULE = DEFAULT_SERVICE_PREFIX + "TaskexeModule";
	public static final String CTRL_MODULE = DEFAULT_SERVICE_PREFIX + "CtrlModule";
	public static final String AGV_MSG_RESOLUTE_MODULE = DEFAULT_SERVICE_PREFIX + "MsgResoluteModule";
	public static final String AGV_MSG_INFO_MODULE = DEFAULT_SERVICE_PREFIX + "AgvMsgInfoModule";

	public static final String AGV_CLIENT_STATUS_WORKER = DEFAULT_SERVICE_PREFIX + "AgvClientStatusWorker";
	public static final String AGV_CLIENT_CTRL_WORKER = DEFAULT_SERVICE_PREFIX + "AgvClientCtrlWorker";
	public static final String AGV_CLIENT_NAVI_WORKER = DEFAULT_SERVICE_PREFIX + "AgvClientNaviWorker";
	public static final String AGV_CLIENT_CONF_WORKER = DEFAULT_SERVICE_PREFIX + "AgvClientConfWorker";
	public static final String AGV_CLIENT_OTHER_WORKER = DEFAULT_SERVICE_PREFIX + "AgvClientOtherWorker";

	public static final String AGV_SERVER_WORKER = DEFAULT_SERVICE_PREFIX + "AgvServerWorker";

	public static final String TASKEXE_ADD_SERVICE = DEFAULT_SERVICE_PREFIX + "TaskexeAddService";
	public static final String TASKEXE_CHECK_SERVICE = DEFAULT_SERVICE_PREFIX + "TaskexeCheckService";

	public static final String AGV_CMD_TASK_MODULE = DEFAULT_SERVICE_PREFIX + "AgvCmdTaskModule";
	public static final String AGV_CMD_CTRL_MODULE = DEFAULT_SERVICE_PREFIX + "AgvCmdCtrlModule";

	public static final String ALLOC_INFO_SERVICE = DEFAULT_SERVICE_PREFIX + "AllocInfoService";

	public static final String IOT_CLIENT_SERVICE = DEFAULT_SERVICE_PREFIX + "IotClientService";

	public static final String TASK_SITE_INFO_SERVICE = DEFAULT_SERVICE_PREFIX + "taskSiteInfoService";
}
