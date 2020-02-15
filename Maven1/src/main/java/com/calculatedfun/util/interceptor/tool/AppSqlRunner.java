package com.calculatedfun.util.interceptor.tool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.calculatedfun.erp.plugin.PageView;
import com.calculatedfun.erp.plugin.SQLServer2008Dialect;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.FormMapTableUtil;
import com.kaifantech.init.sys.dao.AppSystemDao;
import com.kaifantech.init.sys.dao.BaseDao;

public class AppSqlRunner extends BaseDao {
	private static Map<Object, Map<Object, List<Object>>> fields = new HashMap<>();

	public static Map<Object, List<Object>> getField(Object table) throws SQLException {
		Map<Object, List<Object>> field = fields.get(table);
		if (field == null) {
			field = getFieldFromDB(table);
			fields.put(table, field);
		}
		return field;
	}

	public static Map<Object, List<Object>> getField(FormMap formMap) throws SQLException {
		Object table = FormMapTableUtil.getTableName(formMap);
		Boolean isCombindTable = false;
		Map<Object, List<Object>> field = new HashMap<>();
		if (AppTool.isNull(isCombindTable) || !isCombindTable) {
			field = getField(table);
		} else {
			List<Object> tableList = formMap.getList(TABLE_KEY_LIST);
			for (Object tableName : tableList) {
				field.putAll(getField(tableName.toString()));
			}
		}
		return field;
	}

	public static Map<Object, List<Object>> getFieldFromDB(Object table) throws SQLException {
		Map<Object, List<Object>> field = new HashMap<>();
		String sql = colsInfo(table);
		List<Map<String, Object>> values = jdbcTemplate().queryForList(sql);
		for (Map<String, Object> item : values) {
			List<Object> fieldMap = field.get(FIELD);
			List<Object> columnMap = field.get(COLUMN_KEY);
			List<Object> uniMap = field.get(UNI_KEY);
			if (fieldMap == null) {
				fieldMap = new ArrayList<>();
				field.put(FIELD, fieldMap);
			}
			if (columnMap == null) {
				columnMap = new ArrayList<>();
				field.put(COLUMN_KEY, columnMap);
			}
			if (uniMap == null) {
				uniMap = new ArrayList<>();
				field.put(UNI_KEY, uniMap);
			}
			Object currentField = item.get("Field");
			fieldMap.add(currentField);
			Object column_key = item.get("Key");
			if (!AppTool.isNull(column_key) && "PRI".equals(column_key)) {
				columnMap.add(currentField);
			}
			if (!AppTool.isNull(column_key) && "UNI".equals(column_key)) {
				uniMap.add(currentField);
			}
		}
		return field;
	}

	public static void setCount(String sql, PageView pageView) throws SQLException {
		Integer count;
		String removedSql = removeOrderBys(sql);
		String countSql = COUNT_FROM + suffixStr(removedSql);
		try {
			count = AppTool.v(jdbcTemplate().queryForObject(countSql, Integer.class), 0);
		} catch (Exception e) {
			countSql = COUNT_FROM + "(" + sql + ") tmp_count";
			count = AppTool.v(jdbcTemplate().queryForObject(countSql, Integer.class), 0);
		}
		pageView.setRowCount(count);
	}

	public static boolean isSaveIntoJson(FormMap formMap, Object table) throws SQLException {
		Boolean addToJson = formMap.getBoolean(ADD_TO_JSON);
		if (!AppTool.isNull(addToJson) && addToJson) {
			return AppSystemDao.validateColumnExist(table.toString(), JSON_COL.toLowerCase());
		}
		return false;
	}

	public static String removeOrderBys(String toSql) {
		toSql = toSql.toUpperCase();
		int sun = toSql.indexOf("ORDER");
		if (sun > -1) {
			String f1 = toSql.substring(sun - 1, sun);
			String f2 = toSql.substring(sun + 5, sun + 5);
			if (f1.trim().isEmpty() && f2.trim().isEmpty()) {
				String zb = toSql.substring(sun);
				int s0 = zb.indexOf(")");
				if (s0 > -1) {
					String s1 = toSql.substring(0, sun);
					String s2 = zb.substring(s0);
					return removeOrderBys(s1 + s2);
				} else {
					toSql = toSql.substring(0, sun);
				}
			}
		}
		return toSql;
	}

	public static String suffixStr(String toSql) {
		toSql = toSql.toUpperCase();
		int sun = toSql.indexOf("FROM");
		String f1 = toSql.substring(sun - 1, sun);
		String f2 = toSql.substring(sun + 4, sun + 5);
		if (f1.trim().isEmpty() && f2.trim().isEmpty()) {
			String s1 = toSql.substring(0, sun);
			int s0 = s1.indexOf("(");
			if (s0 > -1) {
				int se1 = s1.indexOf("SELECT");
				if (s0 < se1) {
					if (se1 > -1) {
						String ss1 = s1.substring(se1 - 1, se1);
						String ss2 = s1.substring(se1 + 6, se1 + 7);
						if (ss1.trim().isEmpty() && ss2.trim().isEmpty()) {
							return suffixStr(toSql.substring(sun + 5));
						}
					}
				}
				int se2 = s1.indexOf("(SELECT");
				if (se2 > -1) {
					String ss2 = s1.substring(se2 + 7, se2 + 8);
					if (ss2.trim().isEmpty()) {
						return suffixStr(toSql.substring(sun + 5));
					}
				}
				if (se1 == -1 && se2 == -1) {
					return toSql.substring(sun + 5);
				} else {
					toSql = toSql.substring(sun + 5);
				}
			} else {
				toSql = toSql.substring(sun + 5);
			}
		}
		return toSql;
	}

	public static String dialect = null;
	public final static Logger logger = Logger.getLogger(AppSqlRunner.class);

	public static String generatePagesSql(String sql, PageView page) {
		if (page != null) {
			if ("mysql".equals(dialect)) {
				return buildPageSqlForMysql(sql, page).toString();
			} else if ("oracle".equals(dialect)) {
				return buildPageSqlForOracle(sql, page).toString();
			} else if ("SQLServer2008".equals(dialect)) {
				return buildPageSqlForSQLServer2008Dialect(sql, page).toString();
			}
		}
		return sql;
	}

	public static StringBuilder buildPageSqlForMysql(String sql, PageView page) {
		StringBuilder pageSql = new StringBuilder(100);
		String beginrow = String.valueOf((page.getPageNow() - 1) * page.getPageSize());
		pageSql.append(sql);
		pageSql.append(limitRows(beginrow, page.getPageSize()));
		return pageSql;
	}

	public static StringBuilder buildPageSqlForOracle(String sql, PageView page) {
		StringBuilder pageSql = new StringBuilder(100);
		String beginrow = String.valueOf((page.getPageNow()) * page.getPageSize());
		String endrow = String.valueOf(page.getPageNow() + 1 * page.getPageSize());

		pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
		pageSql.append(sql);
		pageSql.append(" ) temp where rownum <= ").append(endrow);
		pageSql.append(") where row_id > ").append(beginrow);
		return pageSql;
	}

	public static String buildPageSqlForSQLServer2008Dialect(String sql, PageView page) {
		SQLServer2008Dialect dialect = new SQLServer2008Dialect();
		String sqlbuild = dialect.getLimitString(sql, (page.getPageNow() - 1) * page.getPageSize(), page.getPageSize());
		return sqlbuild;
	}

	public static final String whereWhenDel(FormMap formMap) throws SQLException {
		return whereWhenDel(formMap, true);
	}

	public static final String splitVaule(Object value) throws SQLException {
		List<Object> valList = new ArrayList<>();
		String[] vals = value.toString().split(":");
		for (String val : vals) {
			valList.add(s(val));
		}
		return AppTool.join(",", valList);
	}

	public static final String whereWhenDel(FormMap formMap, boolean withWhere) throws SQLException {
		String where = whereKey(formMap, withWhere);
		if (AppTool.isNull(where)) {
			List<Object> param = new ArrayList<>();
			for (Entry<String, Object> entry : formMap.entrySet()) {
				if (isSpecialKey(entry.getKey())) {
					continue;
				}
				if (null != entry.getValue() && !"_t".equals(entry.getKey()))
					param.add(i(entry.getKey()) + " in (" + splitVaule(entry.getValue()) + ")");
			}
			where = withWhere ? where(param) : and(param);
			if (AppTool.isNull(where)) {
				return null;
			}
		}
		return where;
	}

	public static final String whereAndSelect(Object table, FormMap formMap) throws SQLException {
		Map<Object, List<Object>> mapfield = getField(formMap);
		if (AppTool.isNull(mapfield)) {
			System.err.println("未找到" + table + "表对应的系统数据！");
			return null;
		}
		String sql;
		Object selfHelpSql = formMap.get(SELF_HELP_SQL);
		if (!AppTool.isNull(selfHelpSql)) {
			sql = selfHelpSql.toString();
		} else {
			sql = selectFrom(table);
		}
		return sql + whereWhenSelect(table, formMap);
	}

	public static final String whereWhenSelect(Object table, FormMap formMap) throws SQLException {
		Map<Object, List<Object>> mapfield = getField(formMap);
		if (AppTool.isNull(mapfield)) {
			System.err.println("未找到" + table + "表对应的系统数据！");
			return "";
		}

		String sql = "";
		List<Object> field = mapfield.get(FIELD);
		List<Object> params = new ArrayList<>();

		Object where = formMap.get(WHERE);
		if (!AppTool.isNull(where)) {
			params.add(where);
		}

		for (Object k : field) {
			String whereItem = whereItemSelect(k, formMap);
			if (!AppTool.isNull(whereItem)) {
				params.add(or(whereItem));
			}
		}

		Object addedSql = formMap.get(ADDED_SQL_KEY);
		if (!AppTool.isNull(addedSql)) {
			params.add(addedSql);
		}

		if (!AppTool.isNull(params)) {
			sql += where(params);
		}

		Object by = formMap.get(ORDER_BY_KEY);

		if (AppTool.isNull(by)) {
			List<Object> orderBy = new ArrayList<>();
			if (mapfield.get("FIELD").contains((COL_SORTFLAG.toLowerCase()))) {
				orderBy.add(" SORTFLAG ");
			}
			if (mapfield.get("FIELD").contains((COL_UPDATETIME.toLowerCase()))) {
				orderBy.add(" UPDATETIME DESC ");
			}
			orderBy.add(join(",", mapfield.get(COLUMN_KEY)));
			sql += orderBy(orderBy);
		} else {
			sql += orderBy(by);
		}
		return sql;
	}

	public static final Object alias(FormMap formMap) {
		Object alias = formMap.get(ALIAS);
		if (AppTool.isNull(alias)) {
			alias = "";
		}
		return alias;
	}

	public static final String whereItemSelect(Object key, FormMap formMap) {
		Object alias = alias(formMap);
		List<Object> paramFlag = new ArrayList<>();
		if (formMap.containsKey(key)) {
			Object v = formMap.get(key);
			if (!AppTool.isNull(v)) {
				return whereItemSelect(key, v, alias);
			}
		} else {
			if (key.equals((COL_DELFLAG.toLowerCase()))) {
				paramFlag.add(item(alias, key) + " != " + s(1));
				return or(paramFlag);
			}
		}
		return "";
	}

	public static final String whereItemSelect(Object key, Object v) {
		return whereItemSelect(key, v, null);
	}

	public static final String whereItemSelect(Object key, Object v, Object alias) {
		String[] args = v.toString().split(":");
		return whereItemSelect(key, args, alias);
	}

	public static final String whereItemSelect(Object key, String[] args, Object alias) {
		List<Object> paramFlag = new ArrayList<>();
		for (String vv : args) {
			if (AppTool.isNull(vv))
				continue;
			if (vv.indexOf("%") > -1) {
				paramFlag.add(item(alias, key) + " like " + s(vv));
			} else if (vv.indexOf(">") > -1 || vv.indexOf("<") > -1 || vv.indexOf("=") > -1) {
				paramFlag.add(item(alias, key) + vv + "");
			} else {
				paramFlag.add(item(alias, key) + " = " + s(vv));
			}
		}
		if (AppTool.isNull(paramFlag))
			return null;
		return or(paramFlag);
	}

	public static final String whereKey(FormMap formMap) throws SQLException {
		return whereKey(formMap, true);
	}

	public static final String whereKey(FormMap formMap, boolean withWhere) throws SQLException {
		return whereKey(formMap, null, withWhere);
	}

	public static final String whereKey(FormMap formMap, List<Object> addedCondition) throws SQLException {
		return whereKey(formMap, addedCondition, true);
	}

	public static final String whereKey(FormMap formMap, List<Object> addedCondition, boolean withWhere)
			throws SQLException {
		Map<Object, List<Object>> mapfield = getField(formMap);
		Object alias = alias(formMap);
		String where = "";
		List<Object> whereList = new ArrayList<>();
		List<Object> colKeyUni = mapfield.get(UNI_KEY);
		List<Object> colKeyPri = mapfield.get(COLUMN_KEY);
		if (!AppTool.isNull(colKeyPri)) {
			for (Object key : colKeyPri) {
				Object v = formMap.get(key);
				if (AppTool.isNull(v)) {
					whereList.clear();
					break;
				}
				whereList.add(item(alias, key) + " = " + s(v));
			}
		}

		if (AppTool.isNull(whereList)) {
			if (!AppTool.isNull(colKeyUni)) {
				for (Object key : colKeyUni) {
					Object v = formMap.get(key);
					if (AppTool.isNull(v)) {
						whereList.clear();
						break;
					}
					whereList.add(item(alias, key) + " = " + s(v));
				}
			}
		}
		if (!AppTool.isNull(addedCondition))
			whereList.addAll(addedCondition);
		where = withWhere ? where(whereList) : and(whereList);
		return where;
	}
}