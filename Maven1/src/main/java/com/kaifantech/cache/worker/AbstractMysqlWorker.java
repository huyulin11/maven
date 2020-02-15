package com.kaifantech.cache.worker;

import java.util.List;
import java.util.Map;

import com.calculatedfun.util.AppTool;
import com.kaifantech.init.sys.dao.ISystemTableDao;
import com.kaifantech.util.log.AppFileLogger;

public abstract class AbstractMysqlWorker implements ICacheWorker {
	private String separator = "_";

	public abstract ISystemTableDao getCacheTool();

	public String getSeparator() {
		return separator;
	}

	public List<Map<String, Object>> getList(String table) {
		return getCacheTool().getList(table);
	}

	public int getCount(String table) {
		return getCacheTool().getCount(table);
	}

	public List<Map<String, Object>> getList(String table, String key) {
		return getCacheTool().getList(table, key);
	}

	public List<Map<String, Object>> getListBy(String table, Object... wheres) {
		return getCacheTool().getListBy(table, wheres);
	}

	public String getOrInit(String param1, Object param2, Object initVal) {
		String finalVal = get(param1, param2);
		if (AppTool.isNull(finalVal) && !AppTool.isNull(initVal)) {
			finalVal = initVal.toString();
			getCacheTool().hset(param1, param2, finalVal);
		}
		return finalVal;
	}

	public String getOrInit(String param, Object initVal) {
		String finalVal = get(param);
		if (AppTool.isNull(finalVal) && !AppTool.isNull(initVal)) {
			finalVal = initVal.toString();
			getCacheTool().set(param, finalVal);
		}
		return finalVal;
	}

	public String get(String param1, Object param2) {
		return getCacheTool().hget(param1, param2);
	}

	public Map<String, Object> getMap(String param1, String param2) {
		return getCacheTool().hgetMap(param1, param2);
	}

	public String hgetByValue(String param1, Object param2) {
		return getCacheTool().hgetByValue(param1, param2.toString());
	}

	public String get(Object param) {
		return getCacheTool().get(param);
	}

	@Override
	public String getFresh(String param1, Object param2) {
		return getCacheTool().getFresh(param1.toString(), param2.toString());
	}

	public String getFresh(Object param) {
		return getCacheTool().getFresh(param.toString());
	}

	public String getCurrentDate(String param) {
		return getCacheTool().getFresh(param, 24 * 3600);
	}

	public Map<String, String> hgetAll(String key) {
		try {
			return getCacheTool().hgetAll(key);
		} catch (Exception e) {
			AppFileLogger.warning("hgetAll", "(", key, ")", "err:", e.getMessage());
			return null;
		}
	}

	public Map<String, String> hgetAllFresh(String key) {
		try {
			return getCacheTool().hgetAllFresh(key);
		} catch (Exception e) {
			AppFileLogger.warning("hgetAll", "(", key, ")", "err:", e.getMessage());
			return null;
		}
	}

	public long del(String key) {
		return getCacheTool().del(key);
	}

	public long delSub(String key, String value) {
		return getCacheTool().delSub(key, value);
	}

	public long delSure(String key, String value) {
		return getCacheTool().delSure(key, value);
	}

	public long delLogic(String key, String value) {
		return getCacheTool().delLogic(key, value);
	}

	public long del(String key, String value) {
		return getCacheTool().del(key, value);
	}

	public long clear(String key) {
		try {
			return del(key);
		} catch (Exception e) {
			AppFileLogger.warning("set", "(", key, ")", "err:", e.getMessage());
			return -1;
		}
	}

	public long hset(Object field, Object key, Object value) {
		try {
			return getCacheTool().hset(field, key, value);
		} catch (Exception e) {
			AppFileLogger.warning("hset", "(", field, "-", key, "-", value, ")", "err:", e.getMessage());
			return -1;
		}
	}

	public long hset(Object field, Object key, Object value, Object notValue) {
		try {
			return getCacheTool().hset(field, key, value, notValue);
		} catch (Exception e) {
			AppFileLogger.warning(" hset err:" + e.getMessage());
			return -1;
		}
	}

	public List<Map<String, Object>> hgetQuantity(String table) {
		try {
			return getCacheTool().hgetQuantity(table);
		} catch (Exception e) {
			AppFileLogger.warning(" hset err:" + e.getMessage());
			return null;
		}
	}

	public long hsetQuantity(String field, String key, String value) {
		try {
			return getCacheTool().hsetQuantity(field, key, value);
		} catch (Exception e) {
			AppFileLogger.warning(" hset err:" + e.getMessage());
			return -1;
		}
	}

	public long set(String key, Object value) {
		try {
			return getCacheTool().set(key, value);
		} catch (Exception e) {
			AppFileLogger.warning(" set err:" + e.getMessage());
			return -1;
		}
	}
}