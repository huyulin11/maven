package com.kaifantech.init.sys.dao;

import com.kaifantech.init.sys.UdfBusinessInfo;

public class BaseDbs {
	public static final DbTypes DB_TYPE = DbTypes.MYSQL;

	public static final String SYS_DB = "kf_acs";
	public static final String BAK_DB = "kf_bak";

	public static final String OP_DB = UdfBusinessInfo.SYSTEM_INIT_DB_KEY + "agv";
	public static final String BUSS_DB = UdfBusinessInfo.SYSTEM_INIT_DB_KEY + "acs";
	public static final String IOT_DB = UdfBusinessInfo.SYSTEM_INIT_DB_KEY + "iot";
	public static final String DE_DB = UdfBusinessInfo.SYSTEM_INIT_DB_KEY + "de";
	public static final String WMS_DB = UdfBusinessInfo.SYSTEM_INIT_DB_KEY + "wms";

	public static final String CONF_DB = UdfBusinessInfo.SYSTEM_INIT_DB_KEY + "_conf";
	public static final String CACHE_DB = UdfBusinessInfo.SYSTEM_INIT_DB_KEY + "_cache_conf";
	public static final String SOCKET_DB = UdfBusinessInfo.SYSTEM_INIT_DB_KEY + "_cache_socket";
}