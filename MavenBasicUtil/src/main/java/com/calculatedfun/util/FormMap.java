package com.calculatedfun.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kaifantech.init.sys.dao.BaseSqlConstants;

public class FormMap extends HashMap<String, Object> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static <T extends FormMap> List<FormMap> transfer(List<T> list) {
		List<FormMap> valueList = new ArrayList<>();
		for (T obj : list) {
			valueList.add(obj);
		}
		return valueList;
	}

	@SuppressWarnings("unchecked")
	public <C extends FormMap> C setRtn(String key, Object value) {
		set(key, value);
		return (C) this;
	}

	public FormMap() {
	}

	public String getDelflag() {
		return getStr("delflag");
	}

	public <C extends FormMap> FormMap(FormMap formMap) {
		Map<String, Object> map = formMap;
		initFormMap(map);
	}

	public FormMap(Map<String, Object> map) {
		initFormMap(map);
	}

	public FormMap(Map<String, Object> map, boolean withId) {
		initFormMap(map, withId);
	}

	public void initFormMap(Map<String, Object> map) {
		initFormMap(map, false);
	}

	public void initFormMap(Map<String, Object> map, boolean withId) {
		if (!AppTool.isNull(map)) {
			for (java.util.Map.Entry<String, Object> entry : map.entrySet()) {
				if (withId || !"id".equals(entry.getKey().toString().toLowerCase())) {
					this.put(entry.getKey(), entry.getValue());
				}
			}
		}
	}

	public void set(String key, Object value) {
		this.put(key, value);
	}

	public void setIfNull(String key, Object value) {
		Object tempVal = this.get(key);
		if (AppTool.isNull(tempVal)) {
			this.put(key, value);
		}
	}

	public void setIfNotNull(String key, Object value) {
		if (!AppTool.isNull(value)) {
			this.put(key, value);
		}
	}

	public String getStr(String attr) {
		Object obj = get(attr);
		if (AppTool.isNull(obj)) {
			return null;
		}
		return obj.toString();
	}

	public Integer getInt(String attr) {
		Object obj = get(attr);
		if (AppTool.isNull(obj)) {
			return null;
		}
		return Integer.parseInt(obj.toString());
	}

	public Long getLong(String attr) {
		return (Long) this.get(attr);
	}

	public java.math.BigInteger getBigInteger(String attr) {
		return (java.math.BigInteger) this.get(attr);
	}

	public java.util.Date getDate(String attr) {
		return (java.util.Date) this.get(attr);
	}

	public java.sql.Time getTime(String attr) {
		return (java.sql.Time) this.get(attr);
	}

	public java.sql.Timestamp getTimestamp(String attr) {
		return (java.sql.Timestamp) this.get(attr);
	}

	public Double getDouble(String attr) {
		Object obj = get(attr);
		if (AppTool.isNull(obj)) {
			return null;
		}
		return Double.parseDouble(obj.toString());
	}

	public Float getFloat(String attr) {
		return (Float) this.get(attr);
	}

	public Boolean getBoolean(String attr) {
		return (Boolean) this.get(attr);
	}

	public java.math.BigDecimal getBigDecimal(String attr) {
		return (java.math.BigDecimal) this.get(attr);
	}

	public byte[] getBytes(String attr) {
		return (byte[]) this.get(attr);
	}

	public Number getNumber(String attr) {
		return (Number) this.get(attr);
	}

	public String[] getAttrNames() {
		Set<String> attrNameSet = this.keySet();
		return attrNameSet.toArray(new String[attrNameSet.size()]);
	}

	public Object[] getAttrValues() {
		Collection<Object> attrValueCollection = values();
		return attrValueCollection.toArray(new Object[attrValueCollection.size()]);
	}

	public String setCombindTable(String table1, String table2, String connectCol) {
		List<String> tableList = Arrays.asList(table1, table2);
		set(BaseSqlConstants.TABLE_KEY_LIST, tableList);
		String table = "select * from " + table1 + " m" + "," + table2 + " d" + " WHERE m." + connectCol + "=d."
				+ connectCol;
		return table;
	}

	@SuppressWarnings("unchecked")
	public Map<String, FormMap> getMap() {
		return (Map<String, FormMap>) this.get(BaseSqlConstants.LIST);
	}

	public List<FormMap> getList() {
		Map<String, FormMap> map = getMap();
		if (AppTool.isNull(map)) {
			return null;
		}
		Collection<FormMap> valueCollection = map.values();
		List<FormMap> valueList = new ArrayList<>(valueCollection);
		return valueList;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getList(String key) {
		List<Object> list = (List<Object>) get(key);
		return list;
	}
}
