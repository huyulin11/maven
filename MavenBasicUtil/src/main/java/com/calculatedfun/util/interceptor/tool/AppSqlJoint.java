package com.calculatedfun.util.interceptor.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.erp.plugin.PageView;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.DateFactory;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.FormMapTableUtil;
import com.kaifantech.init.sys.dao.BaseSqlConstants;

public class AppSqlJoint extends AppSqlRunner {
	@SuppressWarnings("unchecked")
	public static final String getJoinSql(String sqlId, Object parameterObject) throws Exception {
		String sqlFuncId = sqlId.substring(sqlId.lastIndexOf(".") + 1).toUpperCase();
		FormMap formMap = null;
		List<Object> lists = null;
		if (parameterObject instanceof FormMap) {
			formMap = FormMapTableUtil.toHashMap(parameterObject);
			return getJoinSqlSimple(sqlFuncId, formMap);
		} else if (parameterObject instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) parameterObject;
			if (map.containsKey(BaseSqlConstants.LIST)) {
				lists = (List<Object>) map.get(BaseSqlConstants.LIST);
				return getJoinSqlBatch(sqlFuncId, lists);
			} else {
				Class<?> fm = (Class<?>) map.get("param3");
				Object o = fm.newInstance();
				formMap = FormMapTableUtil.toHashMap(o);
				formMap.put("key", map.get("param1"));
				formMap.put("value", map.get("param2"));
				return getJoinSqlSimple(sqlFuncId, formMap);
			}
		} else {
			throw new NullPointerException("调用公共方法，传入参数有错误！具体请看参数说明！");
		}
	}

	public static final String getJoinSqlSimple(String sqlId, FormMap formMap) throws Exception {
		if (null == formMap) {
			return null;
		}

		String sql = null;
		Object table = formMap.get(TABLE_KEY);
		Object where;
		Object value = formMap.get("value");
		Object key = formMap.get("key");
		Map<Object, List<Object>> mapfield = getField(table);
		List<Object> fieldList = mapfield.get(FIELD);
		List<Object> priKeyList = mapfield.get(COLUMN_KEY);

		switch (sqlId) {
		case FINDBYWHERE: {
			sql = selectFrom(table);
			where = formMap.get(WHERE);
			if (!AppTool.isNull(where)) {
				sql += " " + where.toString();
			}
			return sql;
		}
		case FINDBYPAGE: {
			sql = whereAndSelect(table, formMap);
			Object paging = formMap.get("paging");
			if (AppTool.isNull(paging)) {
				throw new Exception("调用findByPage接口,必须传入PageView对象! formMap.set(\"paging\", pageView);");
			}
			PageView pageView = (PageView) paging;
			setCount(sql, pageView);
			sql = generatePagesSql(sql, pageView);
			return sql;
		}
		case FINDBYNAMES: {
			sql = whereAndSelect(table, formMap);
			return sql;
		}
		case FINDONE: {
			where = whereKey(formMap);
			if (AppTool.isNull(where)) {
				throw new Exception(sqlId + " 调用公共方法异常!,传入参数错误！");
			}
			sql = selectFrom(table) + where;
			return sql;
		}
		case FINDBYATTRIBUTE: {
			sql = selectFrom(table);
			if (null != value && value.toString().indexOf("%") > -1) {
				sql += where(i(key) + " LIKE " + s(value));
			} else {
				sql += where(i(key) + " IN (" + s(value) + ")");
			}
			return sql;
		}
		case FINDBYFRIST: {
			sql = selectFrom(table);
			if (null != value && !"".equals(value.toString())) {
				sql += where(i(key) + " = " + s(value));
			} else {
				throw new Exception(sqlId + " 调用公共方法异常!,传入参数错误！");
			}
			return sql;
		}
		case ADDENTITY: {
			List<Object> fieldString = new ArrayList<>();
			List<Object> fieldValues = new ArrayList<>();
			for (Object col : fieldList) {
				Object v = formMap.get(col);

				if (JSON_COL.toLowerCase().equals(col)) {
					if (isSaveIntoJson(formMap, table)) {
						JSONObject json = new JSONObject();
						if (!AppTool.isNull(v)) {
							json = JSONObject.parseObject(v.toString());
						}
						String jsonEx = json.toJSONString();
						fieldString.add(i(col));
						fieldValues.add(s(jsonEx));
					}
				} else if (OPERATOR_COL.toLowerCase().equals(col)) {
					JSONObject json = new JSONObject();
					if (!AppTool.isNull(v)) {
						json = JSONObject.parseObject(v.toString());
					}
					Object operator = AppSession.getUserName();
					json.put("CRETATE" + "_OPERATOR", (AppTool.isNull(operator) ? "" : operator));
					json.put("CRETATE" + "_TIME", DateFactory.getCurrTime("yyyy-MM-dd HH:mm:ss"));
					String jsonEx = json.toJSONString();
					fieldString.add(i(col));
					fieldValues.add(s(jsonEx));
				} else if (!AppTool.isNull(v)) {
					fieldString.add(i(col));
					fieldValues.add(s(v));
				}
			}
			sql = joinSql("insert into ", table, " (", AppTool.join(",", fieldString), ")  values (",
					AppTool.join(",", fieldValues), ")");
			return sql;
		}
		case EDITENTITY: {
			List<String> updateList = new ArrayList<>();
			for (Object col : fieldList) {
				Object v = formMap.get(col);
				if (!AppTool.isNull(v)) {
					if (AppTool.isNull(priKeyList) || !priKeyList.contains(col))
						updateList.add(i(col) + "=" + s(v));
				} else if (COL_UPDATETIME.toLowerCase().equals(col)) {
					updateList.add(i(col) + "=" + s(DateFactory.getCurrTime("yyyy-MM-dd HH:mm:ss")));
				}
			}
			where = whereKey(formMap);
			if (AppTool.isNull(where)) {
				return null;
			}
			if (isSaveIntoJson(formMap, table)) {
				removeSpecialKey(formMap);
				updateList.add(i("json") + "=" + jsonItemUpdate(formMap));
			}
			if (AppTool.isNull(updateList)) {
				return null;
			}
			sql = "update " + table + set(updateList) + " " + where;
			return sql;
		}
		case UPDATESTATUS: {
			Object fromStatus = formMap.get(FROM_STATUS);
			Object toStatus = formMap.get(TARGET_STATUS);

			Map<String, Object> sets = new HashMap<>();
			sets.put("status", toStatus);
			if (fieldList.contains(COL_UPDATETIME.toLowerCase()))
				sets.put(COL_UPDATETIME, DateFactory.getCurrTime("yyyy-MM-dd HH:mm:ss"));
			sql = "update " + table + set(sets);
			List<Object> addeds = Arrays.asList(!AppTool.isNull(fromStatus) ? (" status= " + s(fromStatus)) : "");
			where = whereKey(formMap, addeds);
			if (AppTool.isNull(where)) {
				return null;
			}
			// Object operator = AppSession.getUserName();
			// if (field.contains(OPERATOR_COL.toLowerCase())) {
			// Map<String, Object> updateInfo =
			// ImmutableMap.of("UPDATE_OPERATOR" + "_" + toStatus,
			// (AppTool.isNull(operator) ? "" : operator), "UPDATE_TIME" + "_" +
			// toStatus,
			// String jsonEx = jsonItemUpdate(updateInfo);
			// sql += ", json=" + jsonEx;
			// }
			return sql + where;
		}
		case DELETEBYATTRIBUTE: {
			sql = joinSql("delete from ", table);
			if (null != value) {
				sql += where(i(key) + " IN (" + splitVaule(value) + ")");
			}
			return sql;
		}
		case TRUNCATE: {
			sql = joinSql("truncate table ", table);
			return sql;
		}
		case DELETEBYNAMES:
		case DELETEBYSUB:
		case DELETEBYSURE:
		case DELETEBYLOGIC: {
			sql = "";
			where = whereWhenDel(formMap);
			if (AppTool.isNull(where)) {
				return null;
			}
			if (DELETEBYNAMES.equals(sqlId)) {
				sql = delete(table.toString());
			} else if (DELETEBYSUB.equals(sqlId)) {
				sql = deleteSub(table.toString());
			} else if (DELETEBYSURE.equals(sqlId)) {
				sql = deleteLogic(table.toString());
				where += " AND delflag = 2 ";
			} else if (DELETEBYLOGIC.equals(sqlId)) {
				sql = deleteLogic(table.toString());
			}
			sql += where;
			return sql;
		}
		}
		throw new Exception("异常公共方法调用!" + sqlId);
	}

	@SuppressWarnings("unchecked")
	public static final String getJoinSqlBatch(String sqlId, List<Object> formMaps) throws Exception {
		if (AppTool.isNull(formMaps)) {
			return null;
		}

		String table = FormMapTableUtil.getTableName(formMaps.get(0).getClass());
		String sql = "";
		Object where;
		Map<Object, List<Object>> mapfield = getField(table);
		List<Object> fieldList = mapfield.get(FIELD);

		switch (sqlId) {
		case DELETEBYLOGIC: {
			List<Object> orList = new ArrayList<>();
			for (Object obj : formMaps) {
				FormMap formMap = (FormMap) obj;
				String whereOne = whereWhenDel(formMap, false);
				if (AppTool.isNull(whereOne)) {
					continue;
				}
				orList.add(whereOne);
			}
			if (DELETEBYNAMES.equals(sqlId)) {
				sql = delete(table.toString());
			} else {
				sql = deleteLogic(table.toString());
			}
			where = where(or(orList));
			sql += where;
			return sql;
		}
		case BATCHINSERT:
		case BATCHSAVE: {
			List<Object> allValues = new ArrayList<>();
			List<Object> fields = new ArrayList<>();

			for (int i = 0; i < formMaps.size(); i++) {
				List<Object> oneValues = new ArrayList<>();
				Object object = formMaps.get(i);
				Map<String, Object> froMmap = (Map<String, Object>) object;
				for (Object col : fieldList) {
					Object v = froMmap.get(col);
					if (!AppTool.isNull(v)) {
						if (i == 0)
							fields.add(col);
						oneValues.add(s(v));
					} else if (col.equals(COL_DELFLAG.toLowerCase())) {
						if (i == 0)
							fields.add(col);
						oneValues.add(s(0));
					}
				}
				allValues.add(joinSql("(", AppTool.join(",", oneValues), ")"));
			}
			sql = (BATCHINSERT.equals(sqlId) ? insert(table) : replace(table)) + " (" + AppTool.join(",", fields)
					+ ")  values " + AppTool.join(",", allValues);
			return sql;
		}
		}
		throw new Exception("异常批量公共方法调用!" + sqlId);
	}
}
