package com.kaifantech.init.sys.dao;

import java.util.ArrayList;
import java.util.List;

public class AppTablesInit extends AppTables {
	protected static final String SELF_UPDATE_TIME_TIP = " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP() ";
	protected static final String SELF_CREATE_TIME_TIP = " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() ";
	protected static final String JSON_TYPE_TIP = " JSON NULL DEFAULT NULL COLLATE 'utf8mb4_bin' ";
	protected static final String VARCHAR_50_TIP = " VARCHAR(50) NULL DEFAULT NULL ";
	protected static final String DELFLAG_TIP = " VARCHAR(1) NULL DEFAULT '0' ";

	public static void init() {
		List<O> list = new ArrayList<>();

		list.add(new O(LY_RES_USER, "delflag", " INT(1) NULL DEFAULT 0 "));
		list.add(new O(LY_ROLE_RES, "delflag", " INT(1) NULL DEFAULT 0 "));
		list.add(new O(AppTables.ALLOCATION_ITEM_INFO, "json", " text NULL DEFAULT NULL "));
		list.add(new O(AppTables.ALLOCATION_ITEM_INFO, "updatetime", SELF_UPDATE_TIME_TIP));
		list.add(new O(EMPLOYEE_ROLE, "delflag", " INT(1) NULL DEFAULT 0 "));
		list.add(new O(WMS_SHIPMENT_REQUEST_DETAIL, "json", JSON_TYPE_TIP + " AFTER " + BaseSqlJoint.i("id") + " "));
		list.add(new O(WMS_RECEIPT_REQUEST_MAIN, "json", JSON_TYPE_TIP));
		list.add(new O(TASKEXE_S2C_TASK, "updatetime", SELF_UPDATE_TIME_TIP));
		list.add(new O(ALLOC_TXM_INFO, "updatetime", SELF_UPDATE_TIME_TIP));
		list.add(new O(ALLOC_TXM_INFO, "createtime", SELF_CREATE_TIME_TIP));
		list.add(new O(SYS_DICTIONARY_DATA_INFO, "json", JSON_TYPE_TIP));
		list.add(new O(SYS_DICTIONARY_TYPE_INFO, "json", JSON_TYPE_TIP));
		list.add(new O(SYS_DICTIONARY_TYPE_INFO, BaseSqlJoint.COL_DELFLAG, DELFLAG_TIP));
		list.add(new O(WMS_INVENTORY_REQUEST_MAIN, "json", JSON_TYPE_TIP));
		list.add(new O(IOT_CLIENT_INFO, "json", JSON_TYPE_TIP));
		list.add(new O(WMS_TRANSFER_REQUEST_DETAIL, "json", JSON_TYPE_TIP + " AFTER " + BaseSqlJoint.i("id") + " "));
		list.add(new O(WMS_SHIPMENT_REQUEST_MAIN, "orderid", VARCHAR_50_TIP + " AFTER " + BaseSqlJoint.i("paperid")));
		list.add(new O(WMS_TRANSFER_REQUEST_MAIN, "orderid", VARCHAR_50_TIP + " AFTER " + BaseSqlJoint.i("paperid")));
		list.add(new O(WMS_TRANSFER_REQUEST_MAIN, "json", JSON_TYPE_TIP));
		list.add(new O(WMS_TRANSFER_REQUEST_MAIN, "operator", JSON_TYPE_TIP));
		list.add(new O(WMS_SHIPMENT_REQUEST_DETAIL, "relationid",
				" varchar(50) NULL DEFAULT NULL AFTER " + BaseSqlJoint.i("id")));
		list.add(new O(TASK_SITE_LOGIC, "json",
				" JSON NULL DEFAULT NULL COLLATE 'utf8mb4_bin' AFTER " + BaseSqlJoint.i("side")));
		for (O o : list) {
			String[] args = o.table.split("\\.");
			String dbName = args[0];
			String tableName = args[1].trim();
			boolean isTableExist = AppSystemDao.validateTableExist(dbName, tableName);
			if (isTableExist) {
				boolean isColumnExist = AppSystemDao.validateColumnExist(o.table, o.target);
				if (!isColumnExist) {
					AppSystemDao.addColumn(o.table, o.target, o.added);
				}
			}
		}
	}

	static class O {
		public O(String table, String target, String added) {
			this.added = added;
			this.table = table;
			this.target = target;
		}

		protected String table;
		protected String target;
		protected String added;
	}
}
