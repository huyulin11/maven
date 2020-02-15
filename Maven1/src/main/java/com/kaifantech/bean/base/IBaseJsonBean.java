package com.kaifantech.bean.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IBaseJsonBean {
	String getJson();

	void setJson(String json);

	default JSONObject getJsonObject() {
		try {
			return JSONObject.parseObject(getJson());
		} catch (Exception e) {
			return null;
		}
	}

	default JSONArray getJsonArray() {
		try {
			return JSONArray.parseArray(getJson());
		} catch (Exception e) {
			return new JSONArray();
		}
	}

	default JSONArray getJsonArray(String key) {
		try {
			JSONObject obj = this.getJsonObject();
			return obj.getJSONArray(key);
		} catch (Exception e) {
			return new JSONArray();
		}
	}

	default void setJson(JSONObject json) {
		setJson(json.toJSONString());
	}

	default void setJsonItem(String key, String value) {
		JSONObject obj = this.getJsonObject();
		if (obj == null) {
			obj = new JSONObject();
		}
		obj.put(key, value);
		setJson(obj.toJSONString());
	}

	default String getJsonItem(String key) {
		JSONObject obj = this.getJsonObject();
		if (obj == null) {
			return null;
		}
		return obj.getString(key);
	}

	default <T> T getJsonItem(String key, Class<T> clazz) {
		JSONObject obj = this.getJsonObject();
		if (obj == null) {
			return null;
		}
		return obj.getObject(key, clazz);
	}
}
