package com.kaifantech.cache.worker;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.calculatedfun.util.AppTool;
import com.kaifantech.util.log.AppFileLogger;

import redis.clients.jedis.Jedis;

public class AppRedisCacheWorker implements ICacheWorker {
	private String separator = ":";
	private Jedis jedis;

	private Jedis getCacheTool() {
		return jedis;
	}

	public AppRedisCacheWorker(Jedis jedis) {
		this.jedis = jedis;
	}

	public String getOrInit(String param1, Object param2, Object initVal) {
		String finalVal = get(param1, param2.toString());
		if (AppTool.isNull(finalVal) && !AppTool.isNull(initVal)) {
			finalVal = initVal.toString();
			getCacheTool().hset(param1, param2.toString(), finalVal);
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
		return getCacheTool().hget(param1, param2.toString());
	}

	public String get(Object param) {
		return getCacheTool().get(param.toString());
	}

	public Map<String, String> hgetAll(String redisKey) {
		try {
			return getCacheTool().hgetAll(redisKey);
		} catch (Exception e) {
			AppFileLogger.warning("cachework hgetAll err:" + e.getMessage());
			return null;
		}
	}

	public long del(String key) {
		return getCacheTool().del(key);
	}

	public long clear(String key) {
		try {
			long rtmVal = 0;
			Set<String> set = jedis.hkeys(key);
			for (String tempKey : set) {
				rtmVal += getCacheTool().del(tempKey);
			}
			return rtmVal;
		} catch (Exception e) {
			AppFileLogger.warning("cachework set err:" + e.getMessage());
			return -1;
		}
	}

	public long hset(Object key, Object field, Object value) {
		try {
			return getCacheTool().hset(key.toString(), field.toString(), value.toString());
		} catch (Exception e) {
			AppFileLogger.warning("cachework hset err:" + e.getMessage());
			return -1;
		}
	}

	public long hset(Object key, Object field, Object value, Object fromValue) {
		try {
			return getCacheTool().hset(key.toString(), field.toString(), value.toString());
		} catch (Exception e) {
			AppFileLogger.warning("cachework hset err:" + e.getMessage());
			return -1;
		}
	}

	public long set(String key, Object value) {
		try {
			return Long.parseLong(getCacheTool().set(key, value.toString()));
		} catch (Exception e) {
			AppFileLogger.warning("cachework set err:" + e.getMessage());
			return -1;
		}
	}

	public String getSeparator() {
		return separator;
	}

	@Override
	public List<Map<String, Object>> hgetQuantity(String string) {
		return null;
	}

	@Override
	public long hsetQuantity(String string, String currentDate, String destination) {
		return -1;
	}

	@Override
	public String hgetByValue(String param1, Object param2) {
		return null;
	}

	@Override
	public long del(String key, String value) {
		return 0;
	}

	@Override
	public Map<String, Object> getMap(String param1, String param2) {
		return null;
	}

	@Override
	public String getFresh(String param1, Object param2) {
		return null;
	}
}