package com.kaifantech.init.sys.dao;

import java.util.List;
import java.util.Map;

public interface ISystemTableDao {
	public String getTableDBName(String table);

	public String get(String table, Object param);

	public String get(Object param);

	default List<Map<String, Object>> getList(String table) {
		return null;
	}

	default int getCount(String table) {
		return 0;
	}

	default List<Map<String, Object>> getList(String table, String key) {
		return null;
	}

	default List<Map<String, Object>> getListBy(String table, Object... wheres) {
		return null;
	}

	public String getFresh(String table, String param);

	public String getFresh(String table, String param, int seconds);

	public String getFresh(String param);

	public String getFresh(String param, int seconds);

	public String hget(String param1, Object param2);

	public Map<String, Object> hgetMap(String param1, String param2);

	public String hgetByValue(String param1, String param2);

	public Map<String, String> hgetAll(String table);

	public Map<String, String> hgetAllFresh(String table);

	public long set(String key, Object value);

	public long hset(Object table, Object key, Object value);

	public long hset(Object table, Object key, Object value, Object notValue);

	public long del(String key);

	public long del(String key, String value);

	default long delSub(String key, String value) {
		return 0;
	}

	default long delSure(String key, String value) {
		return 0;
	}

	default long delLogic(String key, String value) {
		return 0;
	}

	public long hsetQuantity(String table, String data, String value);

	public List<Map<String, Object>> hgetQuantity(String table);
}