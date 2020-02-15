package com.kaifantech.init.sys.dao;

import java.util.List;
import java.util.Map;

public class SystemCacheDao extends AbstractSystemDao {
	public static final String DEFAULT_TABLE = "cache_key";

	@Override
	public String getDefaultTable() {
		return DEFAULT_TABLE;
	}

	static {
		AppSystemDao.validateOrCreate(UdfDbs.CACHE_DB, DEFAULT_TABLE, true);
		AppSystemDao.validateOrCreate(UdfDbs.SOCKET_DB);
	}

	public String getTableDBName(String table) {
		boolean isSocket = table.toUpperCase().indexOf("SOCKET") >= 0;
		if (!isSocket) {
			AppSystemDao.validateOrCreate(UdfDbs.CACHE_DB, table, true);
			return UdfDbs.CACHE_DB + "." + table;
		} else {
			AppSystemDao.validateOrCreate(UdfDbs.SOCKET_DB, table, true);
			return UdfDbs.SOCKET_DB + "." + table;
		}
	}

	public long set(String key, Object value) {
		return hset(DEFAULT_TABLE, key, value);
	}

	public long del(String key) {
		String finalKey = key.toUpperCase();
		if (AppSystemDao.validateTableExist(UdfDbs.CACHE_DB, finalKey)) {
			AppSystemDao.truncate(UdfDbs.CACHE_DB, finalKey);
			return (long) 1;
		}
		if (AppSystemDao.validateTableExist(UdfDbs.SOCKET_DB, finalKey)) {
			AppSystemDao.truncate(UdfDbs.SOCKET_DB, finalKey);
			return (long) 1;
		}
		long i = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT TABLE_NAME FROM information_schema." + i("TABLES") + " WHERE table_schema=")
				.append(s(UdfDbs.CACHE_DB)).append(" AND TABLE_NAME like").append(s(finalKey));
		List<Map<String, Object>> values = AppSystemDao.jdbcTemplate().queryForList(sb.toString());
		for (Map<String, Object> table : values) {
			i = i + del(table.get("TABLE_NAME").toString());
		}
		sb = new StringBuilder();
		sb.append("SELECT TABLE_NAME FROM information_schema." + i("TABLES") + " WHERE table_schema=")
				.append(s(UdfDbs.SOCKET_DB)).append(" AND TABLE_NAME like").append(s(finalKey));
		values = AppSystemDao.jdbcTemplate().queryForList(sb.toString());
		for (Map<String, Object> table : values) {
			i = i + del(table.get("TABLE_NAME").toString());
		}
		return i;
	}
}