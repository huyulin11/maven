package com.kaifantech.init.sys.dao;

import java.util.List;
import java.util.Map;

public class SystemConfDao extends AbstractSystemDao {
	public static final String DEFAULT_TABLE = "conf_key";

	@Override
	public String getDefaultTable() {
		return DEFAULT_TABLE;
	}

	static {
		AppSystemDao.validateOrCreate(UdfDbs.CONF_DB, DEFAULT_TABLE, false);
	}

	public String getTableDBName(String table) {
		AppSystemDao.validateOrCreate(UdfDbs.CONF_DB, table, false);
		return UdfDbs.CONF_DB + "." + table;
	}

	public long set(String key, Object value) {
		return hset(DEFAULT_TABLE, key, value);
	}

	public long del(String key) {
		if (AppSystemDao.validateTableExist(UdfDbs.CONF_DB, key)) {
			AppSystemDao.truncate(UdfDbs.CONF_DB, key);
			return (long) 1;
		}
		long i = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT TABLE_NAME FROM information_schema." + i("TABLES") + " WHERE table_schema=")
				.append(s(UdfDbs.CONF_DB)).append(" AND TABLE_NAME like").append(s(key));
		List<Map<String, Object>> values = AppSystemDao.jdbcTemplate().queryForList(sb.toString());
		for (Map<String, Object> table : values) {
			i = i + del(table.get("TABLE_NAME").toString());
		}
		return i;
	}
}