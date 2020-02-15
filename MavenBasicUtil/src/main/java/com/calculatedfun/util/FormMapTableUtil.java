package com.calculatedfun.util;

import com.calculatedfun.annotation.FormMapTable;
import com.kaifantech.init.sys.dao.BaseSqlConstants;

public class FormMapTableUtil {
	public static String getTableName(FormMap formMap) {
		Object tableName = getTableName(formMap.getClass());
		if (AppTool.isNull(tableName)) {
			tableName = formMap.get(BaseSqlConstants.TABLE_KEY);
			if (AppTool.isNull(tableName)) {
				throw new NullPointerException("在" + formMap + " 没有找到数据库表对应该的注解!");
			}
		}
		return tableName.toString();
	}

	public static String getTableName(Class<?> clazz) {
		FormMapTable table = getFormMapTable(clazz);
		if (!AppTool.isNull(table)) {
			return table.value();
		} else {
			return null;
		}
	}

	public static FormMapTable getFormMapTable(FormMap formMap) throws ClassNotFoundException {
		return getFormMapTable(formMap.getClass());
	}

	public static FormMapTable getFormMapTable(Class<?> clazz) {
		boolean flag = clazz.isAnnotationPresent(FormMapTable.class);
		if (flag) {
			return (FormMapTable) clazz.getAnnotation(FormMapTable.class);
		} else {
			return null;
		}
	}

	public static FormMap toHashMap(Object parameterObject) {
		FormMap formMap = (FormMap) parameterObject;
		try {
			FormMapTable table = getFormMapTable(formMap);
			if (!AppTool.isNull(table)) {
				formMap.put(BaseSqlConstants.TABLE_KEY, table.value());
				formMap.put(BaseSqlConstants.ADD_TO_JSON, table.addToJson());
			} else {
				Object tableName = formMap.get(BaseSqlConstants.TABLE_KEY);
				if (AppTool.isNull(tableName)) {
					throw new NullPointerException("在" + formMap + " 没有找到数据库表对应该的注解!");
				}
				formMap.put(BaseSqlConstants.ADD_TO_JSON, false);
			}
			return formMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formMap;
	}
}
