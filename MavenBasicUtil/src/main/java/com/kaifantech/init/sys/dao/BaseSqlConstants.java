package com.kaifantech.init.sys.dao;

import java.util.Arrays;
import java.util.List;

public class BaseSqlConstants {
	public static final String IDTF_L = UdfDbs.DB_TYPE.equals(DbTypes.SQLSERVER) ? "[" : "`";
	public static final String IDTF_R = UdfDbs.DB_TYPE.equals(DbTypes.SQLSERVER) ? "]" : "`";

	public static final String SELECT = " SELECT ";
	public static final String FROM = " FROM ";
	public static final String WHERE = "WHERE";
	public static final String AND = " AND ";
	public static final String OR = " OR ";
	public static final String UPDATE = " UPDATE ";
	public static final String SET = " SET ";
	public static final String SELECT_FROM = " SELECT * FROM ";
	public static final String INSERT_INTO = " INSERT INTO ";
	public static final String REPLACE_INTO = UdfDbs.DB_TYPE.equals(DbTypes.SQLSERVER) ? " MERGE INTO "
			: " REPLACE INTO ";
	public static final String COUNT_FROM = " SELECT COUNT(1) FROM ";
	public static final String ORDER_BY = " ORDER BY ";

	public static final String SHOW_COLUMNS_FROM = "SHOW COLUMNS FROM ";

	public static final String COLUMN_KEY = "COLUMN_KEY";
	public static final String UNI_KEY = "UNI_KEY";
	public static final String TABLE_KEY = "TABLE_KEY";
	public static final String TABLE_KEY_LIST = "TABLE_KEY_LIST";
	public static final String ORDER_BY_KEY = "ORDER_BY_KEY";
	public static final String ADDED_SQL_KEY = "ADDED_SQL_KEY";

	public static final String COL_DELFLAG = "DELFLAG";
	public static final String COL_SORTFLAG = "SORTFLAG";
	public static final String COL_UPDATETIME = "UPDATETIME";

	public static final String LIST = "list";

	public static final String SELF_HELP_SQL = "SELF_HELP_SQL";
	public static final String ALIAS = "ALIAS";

	public static final String TARGET_STATUS = "TARGET_STATUS";
	public static final String FROM_STATUS = "FROM_STATUS";

	public static final String ADD_TO_JSON = "ADD_TO_JSON";
	public static final String FIELD = "FIELD";

	public static final String JSON_COL = "JSON";
	public static final String OPERATOR_COL = "OPERATOR";

	public static final String FINDBYWHERE = "FINDBYWHERE";
	public static final String FINDBYPAGE = "FINDBYPAGE";
	public static final String DELETEBYNAMES = "DELETEBYNAMES";
	public static final String DELETEBYLOGIC = "DELETEBYLOGIC";
	public static final String DELETEBYSUB = "DELETEBYSUB";
	public static final String DELETEBYSURE = "DELETEBYSURE";

	public static final String UPDATESTATUS = "UPDATESTATUS";
	public static final String DELETEBYATTRIBUTE = "DELETEBYATTRIBUTE";
	public static final String FINDONE = "FINDONE";
	public static final String FINDBYNAMES = "FINDBYNAMES";
	public static final String FINDBYATTRIBUTE = "FINDBYATTRIBUTE";
	public static final String ADDENTITY = "ADDENTITY";
	public static final String EDITENTITY = "EDITENTITY";
	public static final String FINDBYFRIST = "FINDBYFRIST";
	public static final String BATCHSAVE = "BATCHSAVE";
	public static final String BATCHINSERT = "BATCHINSERT";
	public static final String TRUNCATE = "TRUNCATE";

	public static final List<String> SPECICAL_KEYS = Arrays.asList(TABLE_KEY, ADD_TO_JSON, ADDED_SQL_KEY);
}
