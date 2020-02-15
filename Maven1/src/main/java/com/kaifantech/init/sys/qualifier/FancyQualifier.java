package com.kaifantech.init.sys.qualifier;

import com.kaifantech.init.sys.ServicePrefix;

public class FancyQualifier extends BaseQualifier {
	private static final String DEFAULT_SERVICE_PREFIX = ServicePrefix.FANCY;
	public static final String TASKEXE_CHECK_SERVICE = DEFAULT_SERVICE_PREFIX + "TaskexeCheckService";
	public static final String TASKEXE_MODULE = DEFAULT_SERVICE_PREFIX + "TaskexeModule";
	public static final String CTRL_MODULE = DEFAULT_SERVICE_PREFIX + "TaskexeModule";
	public static final String TASKEXE_ADD_SERVICE = DEFAULT_SERVICE_PREFIX + "TaskexeAddService";

	public static final String TASKEXE_DETAIL_JOBS_SERVICE = DEFAULT_SERVICE_PREFIX + "TaskexeDetailJobsService";

	public static final String FETCH_OP_SERVICE = DEFAULT_SERVICE_PREFIX + "TaskexeFetchDealer";
	public static final String DELIVER_OP_SERVICE = DEFAULT_SERVICE_PREFIX + "TaskexeDeliverDealer";

	public static final String TASKEXE_WATCH_SERVICE = DEFAULT_SERVICE_PREFIX + "TaskexeWatchDealer";

	public static final String DEFAULT_FETCH_OP_SERVICE = FETCH_OP_SERVICE;
	public static final String DEFAULT_DELIVER_OP_SERVICE = DELIVER_OP_SERVICE;
	public static final String DEFAULT_TASKEXE_WATCH_SERVICE = TASKEXE_WATCH_SERVICE;
}
