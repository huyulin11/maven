package com.kaifantech.cache.worker;

import java.util.List;
import java.util.Map;

public interface ICacheWorker {
	String getOrInit(String param1, Object param2, Object initVal);

	String getOrInit(String param, Object initVal);

	String get(String param1, Object param2);

	Map<String, Object> getMap(String param1, String param2);

	String hgetByValue(String param1, Object param2);

	String getFresh(String param1, Object param2);

	default String getFresh(Object param) {
		return null;
	}

	default List<Map<String, Object>> getList(String param) {
		return null;
	}

	default int getCount(String param) {
		return 0;
	}

	default List<Map<String, Object>> getList(String param, String key) {
		return null;
	}

	default List<Map<String, Object>> getListBy(String param, Object... wheres) {
		return null;
	}

	String get(Object param);

	default String getCurrentDate(String param) {
		return null;
	}

	Map<String, String> hgetAll(String redisKey);

	default Map<String, String> hgetAllFresh(String redisKey) {
		return null;
	}

	long del(String key);

	long del(String key, String value);

	default long delSub(String key, String value) {
		return 0;
	}

	default long delSure(String key, String value) {
		return 0;
	}

	default long delLogic(String key, String value) {
		return 0;
	}

	long clear(String key);

	long hset(Object key, Object field, Object value);

	long hset(Object key, Object field, Object value, Object notValue);

	default long hset(Object key, Object field, boolean value) {
		return hset(key, field, value ? "TRUE" : "FALSE");
	}

	long set(String key, Object value);

	String getSeparator();

	long hsetQuantity(String string, String currentDate, String integer);

	List<Map<String, Object>> hgetQuantity(String string);
}