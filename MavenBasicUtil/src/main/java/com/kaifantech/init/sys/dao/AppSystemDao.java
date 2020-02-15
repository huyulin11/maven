package com.kaifantech.init.sys.dao;

import java.util.HashMap;
import java.util.Map;

import com.calculatedfun.util.AppTool;
import com.kaifantech.init.sys.UdfBusinessInfo;
import com.kaifantech.init.sys.BaseBusinessInfo.Projects;

public class AppSystemDao extends BaseDao {
	protected static boolean CREATE_DB_WHEN_NULL = false;
	static {
		if (Projects.BJJK_HUIRUI.equals(UdfBusinessInfo.CURRENT_PROJECT) && CREATE_DB_WHEN_NULL) {
			validateOrCreate(UdfDbs.OP_DB);
			validateOrCreate(UdfDbs.BUSS_DB);
			validateOrCreate(UdfDbs.IOT_DB);
			validateOrCreate(UdfDbs.DE_DB);
			validateOrCreate(UdfDbs.WMS_DB);
		}
	}

	public static void validateOrCreate(String dbName, String tableName, boolean isCache) {
		validateOrCreate(dbName);
		if (!validateTableExist(dbName, tableName)) {
			createTable(dbName, tableName, isCache);
		}
	}

	public static void validateOrCreate(String dbName) {
		if (!validateDBNameExist(dbName)) {
			createDB(dbName);
		}
	}

	public static boolean validateTableExist(String dbName, String tableName) {
		String sql = "SELECT COUNT(*) FROM information_schema." + i("TABLES") + " WHERE table_schema=" + s(dbName)
				+ " AND TABLE_NAME=" + s(tableName);
		int tableNum = jdbcTemplate().queryForObject(sql, Integer.class);
		if (tableNum > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void createTable(String dbName, String cacheTableName, boolean isCache) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ").append(dbName).append(".").append(cacheTableName).append(" (")
				.append(i("key") + " VARCHAR(100) NOT NULL,").append(i("value"))
				.append(isCache ? " VARCHAR(500) " : " TEXT ").append(" NOT NULL").append(",").append(i("updatetime"))
				.append(" TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, ")
				.append("PRIMARY KEY (" + i("key") + ")").append(")").append("COLLATE='utf8_general_ci'")
				.append("ENGINE=").append(isCache ? "MEMORY" : "InnoDB").append(";");
		jdbcTemplate().execute(sb.toString());
	}

	public static void addColumn(String dbName, String columnName, String addedStr) {
		String sql = "ALTER TABLE " + dbName + " ADD COLUMN " + i(columnName) + " " + addedStr;
		jdbcTemplate().execute(sql);
	}

	private static Map<String, Boolean> tableNameChecks = new HashMap<>();
	private static Map<String, Boolean> dbNameChecks = new HashMap<>();

	public static boolean validateColumnExist(String fullTableName, String columnName) {
		if (tableNameChecks == null) {
			tableNameChecks = new HashMap<>();
		}
		Boolean isExist = AppTool.v(tableNameChecks.get(fullTableName), false);
		if (isExist) {
			return true;
		}
		String sql = "SELECT COUNT(*) FROM information_schema." + i("COLUMNS")
				+ " WHERE concat(table_schema,'.',TABLE_NAME)=" + s(fullTableName) + " AND COLUMN_NAME="
				+ s(columnName);
		int tableNum = jdbcTemplate().queryForObject(sql, Integer.class);
		if (tableNum > 0) {
			tableNameChecks.put(fullTableName, true);
			return true;
		} else {
			return false;
		}
	}

	public static boolean validateDBNameExist(String dbName) {
		if (dbNameChecks == null) {
			dbNameChecks = new HashMap<>();
		}
		Boolean isExist = AppTool.v(dbNameChecks.get(dbName), false);
		if (isExist) {
			return true;
		}
		String sql = "SELECT COUNT(*) FROM information_schema." + i("SCHEMATA") + " WHERE SCHEMA_NAME=" + s(dbName);
		int tableNum = jdbcTemplate().queryForObject(sql, Integer.class);
		if (tableNum > 0) {
			dbNameChecks.put(dbName, true);
			return true;
		} else {
			return false;
		}
	}

	public static void createDB(String dbName) {
		String sql = "CREATE DATABASE " + i(dbName) + ";";
		jdbcTemplate().execute(sql);
	}

	public static void truncate(String cacheTableName) {
		String sql = "truncate " + cacheTableName;
		jdbcTemplate().execute(sql);
	}

	public static void truncate(String dbName, String cacheTableName) {
		String sql = "truncate " + dbName + "." + cacheTableName;
		jdbcTemplate().execute(sql);
	}
}
