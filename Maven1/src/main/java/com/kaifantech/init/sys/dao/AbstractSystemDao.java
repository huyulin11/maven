package com.kaifantech.init.sys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.interceptor.tool.AppSqlRunner;

public abstract class AbstractSystemDao extends AppSystemDao implements ISystemTableDao {
	public abstract String getDefaultTable();

	public String get(String table, Object param) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT " + i("value") + " FROM ").append(" ").append(getTableDBName(table))
					.append(" where " + i("key") + "=").append(s(param)).append(orderBy("key")).append(limitRows(1));
			String value = jdbcTemplate().queryForObject(sb.toString(), String.class);
			return value;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Map<String, Object> getMap(String table, String param) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM ").append(" ").append(getTableDBName(table)).append(" where " + i("key") + "=")
					.append(s(param)).append(orderBy("key")).append(limitRows(1));
			Map<String, Object> value = jdbcTemplate().queryForMap(sb.toString());
			return value;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public String getByValue(String table, String param) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT " + i("value") + " FROM ").append(" ").append(getTableDBName(table))
					.append(" where " + i("value") + "=").append(s(param)).append(orderBy("key")).append(limitRows(1));
			String value = jdbcTemplate().queryForObject(sb.toString(), String.class);
			return value;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Map<String, Object>> getList(String table) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM ").append(" ").append(getTableDBName(table)).append(orderBy("key"))
					.append(limitRows(500));
			return jdbcTemplate().queryForList(sb.toString());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int getCount(String table) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT count(*) FROM ").append(" ").append(getTableDBName(table));
			return jdbcTemplate().queryForObject(sb.toString(), Integer.class);
		} catch (EmptyResultDataAccessException e) {
			return 0;
		}
	}

	public List<Map<String, Object>> getList(String table, String key) {
		try {
			StringBuilder where = new StringBuilder();
			String whereKey = AppSqlRunner.whereItemSelect("key", key);
			if (!AppTool.isNull(whereKey))
				where.append(where(whereKey));
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM ").append(" ").append(getTableDBName(table)).append(where).append(orderBy("key"))
					.append(limitRows(500));
			return jdbcTemplate().queryForList(sb.toString());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Map<String, Object>> getListBy(String table, Object... wheres) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM ").append(" ").append(getTableDBName(table)).append(where(wheres))
					.append(orderBy("key")).append(limitRows(500));
			return jdbcTemplate().queryForList(sb.toString());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public String get(Object param) {
		return get(getDefaultTable(), param);
	}

	public String getFresh(String param) {
		return getFresh(getDefaultTable(), param);
	}

	public String getFresh(String param, int seconds) {
		return getFresh(getDefaultTable(), param, seconds);
	}

	public String getFresh(String table, String param) {
		return getFresh(table, param, 10);
	}

	public String getFresh(String table, String param, int seconds) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT " + i("value") + " FROM ").append(" ").append(getTableDBName(table))
					.append(" where " + i("key") + "=").append(s(param))
					.append("AND updatetime> SUBDATE(NOW(), INTERVAL ").append(seconds).append(" SECOND)")
					.append(orderBy("key")).append(limitRows(1));
			String value = jdbcTemplate().queryForObject(sb.toString(), String.class);
			return value;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public String hget(String param1, Object param2) {
		return get(param1, param2);
	}

	public Map<String, Object> hgetMap(String param1, String param2) {
		return getMap(param1, param2);
	}

	public String hgetByValue(String param1, String param2) {
		return getByValue(param1, param2);
	}

	public Map<String, String> hgetAll(String table) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ").append(" ").append(getTableDBName(table));
		List<Map<String, Object>> values = jdbcTemplate().queryForList(sb.toString());
		Map<String, String> rtnMap = new HashMap<>();
		for (Map<String, Object> map : values) {
			rtnMap.put(map.get("key").toString(), map.get("value").toString());
		}
		return rtnMap;
	}

	public Map<String, String> hgetAllFresh(String table) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ").append(" ").append(getTableDBName(table))
				.append(" WHERE updatetime> SUBDATE(NOW(), INTERVAL 10 SECOND)");
		List<Map<String, Object>> values = jdbcTemplate().queryForList(sb.toString());
		Map<String, String> rtnMap = new HashMap<>();
		for (Map<String, Object> map : values) {
			rtnMap.put(map.get("key").toString(), map.get("value").toString());
		}
		return rtnMap;
	}

	public long hset(Object table, Object key, Object value) {
		StringBuilder sb = new StringBuilder();
		if (UdfDbs.DB_TYPE.equals(DbTypes.SQLSERVER)) {
			sb.append(replace(getTableDBName(table.toString()))).append(" T ")
					.append("USING (SELECT " + s(key) + " AS [KEY] ," + s(value)
							+ " AS [VALUE] ) B ON (T.[KEY] = B.[KEY]) ")
					.append("WHEN MATCHED THEN UPDATE ").append("SET T.[VALUE] = B.[VALUE] ")
					.append("WHEN NOT MATCHED THEN").append(" INSERT([KEY],[VALUE]) ")
					.append("VALUES(B.[KEY],B.[VALUE]);");
		} else {
			sb.append(replace(getTableDBName(table.toString()))).append("(" + i("key") + ", " + i("value") + ")")
					.append(" values (").append(s(key)).append(",").append(s(value)).append(")");
		}
		return jdbcTemplate().update(sb.toString());
	}

	public long hset(Object table, Object key, Object value, Object notValue) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ").append(getTableDBName(table.toString())).append(" set " + i("value") + "= ")
				.append(s(value)).append(where("" + i("key") + "=" + s(key), "" + i("value") + "!=" + s(notValue)));
		jdbcTemplate().execute(sb.toString());
		return 1;
	}

	public List<Map<String, Object>> hgetQuantity(String table) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ").append(" ").append(getTableDBName(table));
		List<Map<String, Object>> values = jdbcTemplate().queryForList(sb.toString());
		return values;
	}

	public long hsetQuantity(String table, String currentDate, String destination) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(getTableDBName(table))
				.append(" (" + i("date") + ", " + i(destination) + ") VALUES (").append(s(currentDate)).append(", '1')")
				.append(" ON DUPLICATE KEY UPDATE ").append(destination).append(" = ").append(destination)
				.append(" + 1 ");
		jdbcTemplate().execute(sb.toString());
		return 1;
	}

	public long del(String key, String value) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ").append(getTableDBName(key)).append(" where " + i("key") + "=").append(s(value));
		long row = AppSystemDao.jdbcTemplate().update(sb.toString());
		return row;
	}

	public long delSub(String key, String value) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ").append(getTableDBName(key)).append(" set delflag=2 where delflag=0 AND " + i("key") + "=")
				.append(s(value));
		long row = AppSystemDao.jdbcTemplate().update(sb.toString());
		return row;
	}

	public long delSure(String key, String value) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ").append(getTableDBName(key)).append(" set delflag=1 where delflag=2 AND " + i("key") + "=")
				.append(s(value));
		long row = AppSystemDao.jdbcTemplate().update(sb.toString());
		return row;
	}

	public long delLogic(String key, String value) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ").append(getTableDBName(key)).append(" set delflag=1 where " + i("key") + "=")
				.append(s(value));
		long row = AppSystemDao.jdbcTemplate().update(sb.toString());
		return row;
	}
}