package com.kaifantech.init.sys.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.AppTool;

public class BaseJsonJoint extends BaseSqlJoint {
	public static String jsonItemUpdate(String key, Object value) {
		String sql = " (CASE WHEN " + i("json") + " IS NULL OR " + i("json") + "='' THEN JSON_OBJECT(" + s(key) + ", "
				+ s(value) + ") ELSE JSON_SET(" + i("json") + ", " + s("$." + key) + ", " + s(value) + ") END)";
		return sql;
	}

	public static String jsonItemUpdate(Map<String, Object> map) {
		String inserts = "";
		String updates = "";
		for (Entry<String, Object> item : map.entrySet()) {
			inserts += (AppTool.isNull(inserts) ? "" : " , ") + s(item.getKey()) + ", " + s(item.getValue());
			updates += (AppTool.isNull(updates) ? "" : " , ") + s("$." + item.getKey()) + ", " + s(item.getValue());
		}
		String sql = " (CASE WHEN " + i("json") + " IS NULL OR " + i("json") + "='' THEN JSON_OBJECT(" + inserts
				+ ") ELSE JSON_SET(" + i("json") + ", " + updates + ") END)";
		return sql;
	}

	public static String jsonItemInsert(Map<String, Object> map) {
		String inserts = "";
		for (Entry<String, Object> item : map.entrySet()) {
			inserts += (AppTool.isNull(inserts) ? "" : " , ") + s(item.getKey()) + ", " + s(item.getValue());
		}
		String sql = " JSON_OBJECT(" + inserts + ") ";
		return sql;
	}

	public static String jsonItemInsert(Object key, Object value) {
		String sql = "JSON_OBJECT(" + s(key) + ", " + s(value) + ")";
		return sql;
	}

	public static String jsonArrayInsert(List<?> list) {
		List<Object> items = new ArrayList<>();
		for (Object o : list) {
			JSONObject json = (JSONObject) o;
			for (Entry<String, Object> item : json.entrySet()) {
				items.add(jsonItemInsert(item.getKey(), item.getValue()));
			}
		}
		return "JSON_ARRAY(" + join(",", list) + ")";
	}
}
