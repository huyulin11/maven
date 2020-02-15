package com.kaifantech.component.service.paper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.AppTool;
import com.kaifantech.cache.manager.AppConf;
import com.kaifantech.init.sys.params.AppConfKeys;

public class AppCombinedInfo {
	public synchronized static JSONObject get(String target) {
		Map<String, Object> combined = AppConf.worker().getMap(AppConfKeys.COMBINED_TU_INFO, target);
		if (AppTool.isNull(combined)) {
			return null;
		}

		JSONObject combinedTuObj = (JSONObject) JSONObject.parse(combined.get("value").toString());
		if (!AppTool.isNull(combined.get("delflag")))
			combinedTuObj.put("delflag", combined.get("delflag"));
		return combinedTuObj;
	}

	public synchronized static JSONArray getItems(String target) {
		JSONObject obj = get(target);
		return getItems(obj);
	}

	public synchronized static JSONArray getItems(JSONObject combinedTuObj) {
		Object combinedItemsStr = combinedTuObj.get("items");
		if (AppTool.isNull(combinedItemsStr)) {
			return null;
		}
		JSONArray combinedItemsArr = JSONArray.parseArray(combinedItemsStr.toString());
		return combinedItemsArr;
	}

	public synchronized static List<Object> getDetailIds(String target) {
		if (AppTool.isNull(target)) {
			return null;
		}
		String combinedTuStr = AppConf.worker().get(AppConfKeys.COMBINED_TU_INFO, target);
		if (AppTool.isNull(combinedTuStr)) {
			return null;
		}

		JSONObject combinedTuObj = (JSONObject) JSONObject.parse(combinedTuStr);
		return getDetailIds(combinedTuObj);
	}

	public synchronized static List<Object> getDetailIds(JSONObject combinedTuObj) {
		Object combinedItemsStr = combinedTuObj.get("items");
		if (AppTool.isNull(combinedItemsStr)) {
			return null;
		}
		JSONArray combinedItemsObj = JSONArray.parseArray(combinedItemsStr.toString());
		List<Object> detailIds = new ArrayList<>();
		for (Object obj : combinedItemsObj) {
			JSONObject json = (JSONObject) obj;
			detailIds.add(json.get("detailid"));
		}
		return detailIds;
	}

	public synchronized static void save(String target, JSONObject json) {
		AppConf.worker().hset(AppConfKeys.COMBINED_TU_INFO, target, json.toJSONString());
	}

	public synchronized static void clean(String target) {
		AppConf.worker().del(AppConfKeys.COMBINED_TU_INFO, target);
	}

	public synchronized static void deleteSub(String target) {
		AppConf.worker().delSub(AppConfKeys.COMBINED_TU_INFO, target);
	}

	public synchronized static void deleteSure(String target) {
		AppConf.worker().delSure(AppConfKeys.COMBINED_TU_INFO, target);
	}
}
