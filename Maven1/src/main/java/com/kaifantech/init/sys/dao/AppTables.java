package com.kaifantech.init.sys.dao;

public class AppTables {
	public static final String SYS_DICTIONARY_INFO = UdfDbs.BUSS_DB + "." + "sys_dictionary_info ";
	public static final String SYS_DICTIONARY_DATA_INFO = UdfDbs.BUSS_DB + "." + "sys_dictionary_data_info ";
	public static final String SYS_DICTIONARY_TYPE_INFO = UdfDbs.BUSS_DB + "." + "sys_dictionary_type_info ";

	public static final String CONTROL_INFO = UdfDbs.OP_DB + "." + "control_info ";
	public static final String CONTROL_PI_INFO = UdfDbs.OP_DB + "." + "control_pi_info ";
	public static final String TASK_ARMACT_INFO = UdfDbs.OP_DB + "." + "task_armact_info ";

	public static final String TASK_SITE_LOGIC = UdfDbs.OP_DB + "." + "task_site_logic ";
	public static final String TASK_SITE_INFO = UdfDbs.OP_DB + "." + "task_site_info ";
	public static final String TASK_SITE_DEV = UdfDbs.OP_DB + "." + "task_site_dev ";

	public static final String SINGLETASK_INFO = UdfDbs.OP_DB + "." + "singletask_info ";
	public static final String SINGLETASK_ROLE = UdfDbs.OP_DB + "." + "singletask_role ";
	public static final String SINGLETASK_GROUP = UdfDbs.OP_DB + "." + "singletask_group ";

	public static final String AGV_INFO = UdfDbs.OP_DB + "." + "agv_info ";
	public static final String IOT_CLIENT_INFO = UdfDbs.IOT_DB + "." + "iot_client_info ";
	public static final String IOT_RELATION_INFO = UdfDbs.IOT_DB + "." + "iot_relation_info ";

	public static final String TASK_PATH_INFO = UdfDbs.OP_DB + "." + "task_path_info ";
	public static final String TASK_PATH_MEMORY = UdfDbs.OP_DB + "." + "task_path_memory ";

	public static final String TASKEXE_S2C_TASK = UdfDbs.OP_DB + "." + "taskexe_s2c_task ";
	public static final String TASKEXE_S2C_DETAIL = UdfDbs.OP_DB + "." + "taskexe_s2c_detail ";
	public static final String TASKEXE_S2C_DETAIL_WORKS = UdfDbs.OP_DB + "." + "taskexe_s2c_detail_works ";
	public static final String TASKEXE_S2C_DETAIL_JOBS = UdfDbs.OP_DB + "." + "taskexe_s2c_detail_jobs ";

	public static final String AGV_STATUS_LOG = UdfDbs.OP_DB + "." + "agv_status_log ";

	public static final String WMS_INVENTORY_REQUEST_MAIN = UdfDbs.WMS_DB + "." + "wms_inventory_request_main ";
	public static final String WMS_TRANSFER_REQUEST_MAIN = UdfDbs.WMS_DB + "." + "wms_transfer_request_main ";
	public static final String WMS_RECEIPT_REQUEST_MAIN = UdfDbs.WMS_DB + "." + "wms_receipt_request_main ";
	public static final String WMS_SHIPMENT_REQUEST_MAIN = UdfDbs.WMS_DB + "." + "wms_shipment_request_main ";
	public static final String WMS_INVENTORY_REQUEST_DETAIL = UdfDbs.WMS_DB + "." + "wms_inventory_request_detail ";
	public static final String WMS_TRANSFER_REQUEST_DETAIL = UdfDbs.WMS_DB + "." + "wms_transfer_request_detail ";
	public static final String WMS_RECEIPT_REQUEST_DETAIL = UdfDbs.WMS_DB + "." + "wms_receipt_request_detail ";
	public static final String WMS_SHIPMENT_REQUEST_DETAIL = UdfDbs.WMS_DB + "." + "wms_shipment_request_detail ";

	public static final String SKU_INFO = UdfDbs.WMS_DB + "." + "sku_info ";
	public static final String SKU_TYPE = UdfDbs.WMS_DB + "." + "sku_type ";

	public static final String LAP_INFO = UdfDbs.IOT_DB + "." + "lap_info ";
	public static final String LAP_FORKLIFT_INFO = UdfDbs.IOT_DB + "." + "lap_agv_info ";

	public static final String ALLOCATION_ITEM_INFO = UdfDbs.WMS_DB + "." + "allocation_item_info ";
	public static final String ALLOCATION_INVENTORY_INFO = UdfDbs.WMS_DB + "." + "allocation_inventory_info ";
	public static final String ALLOCATION_COLUMN_INFO = UdfDbs.WMS_DB + "." + "allocation_column_info ";
	public static final String ALLOCATION_AREA_INFO = UdfDbs.WMS_DB + "." + "allocation_area_info ";
	public static final String ALLOC_ITEM_RELATION = UdfDbs.WMS_DB + "." + "alloc_item_relation ";
	public static final String ALLOC_TXM_INFO = UdfDbs.WMS_DB + "." + "alloc_txm_info ";
	public static final String ALLOC_ITEM_INFO_WITH_RELATION = "(SELECT ai.*,ar.areaId,ar.colId,ar.zId,ar.columnId from "
			+ ALLOCATION_ITEM_INFO + " ai, " + ALLOC_ITEM_RELATION
			+ " ar where ai.id=ar.id and ai.delflag=0 and ar.delflag=0)";

	public static final String LED_DEV_INFO = UdfDbs.IOT_DB + "." + "led_dev_info ";

	public static final String I_ALL_REQUEST = UdfDbs.DE_DB + "." + "i_all_request ";
	public static final String O_ALL_RESPONSE = UdfDbs.DE_DB + "." + "o_all_response ";

	public static final String CONNECT_MSG_AGV = UdfDbs.IOT_DB + "." + "connect_msg_agv ";
	public static final String CONNECT_MSG_SOCKETDEV = UdfDbs.IOT_DB + "." + "connect_msg_socketdev ";
	public static final String CONNECT_ROBOTICARM_MSG = UdfDbs.IOT_DB + "." + "connect_roboticarm_msg ";

	public static final String CACHE_KEY_TABLE = UdfDbs.CACHE_DB + "." + SystemCacheDao.DEFAULT_TABLE;

	public static final String LY_LOG = UdfDbs.BUSS_DB + "." + "ly_log ";
	public static final String LY_BUTTOM = UdfDbs.BUSS_DB + "." + "ly_buttom ";
	public static final String LY_RESOURCES = UdfDbs.BUSS_DB + "." + "ly_resources ";
	public static final String LY_RES_USER = UdfDbs.BUSS_DB + "." + "ly_res_user ";
	public static final String EMPLOYEE_ROLE = UdfDbs.BUSS_DB + "." + "employee_role ";
	public static final String LY_ROLE_RES = UdfDbs.BUSS_DB + "." + "ly_role_res ";
	public static final String LY_USER = UdfDbs.BUSS_DB + "." + "ly_user ";
	public static final String LY_USER_ROLE = UdfDbs.BUSS_DB + "." + "ly_user_role ";
	public static final String LY_USERLOGIN = UdfDbs.BUSS_DB + "." + "ly_userlogin ";
	public static final String USER_SRC = UdfDbs.BUSS_DB + "." + "user_src ";

	public static final String LY_SERVER_INFO = UdfDbs.BUSS_DB + "." + "ly_server_info ";
}
