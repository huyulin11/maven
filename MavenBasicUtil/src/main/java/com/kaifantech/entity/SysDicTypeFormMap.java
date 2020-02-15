package com.kaifantech.entity;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.bean.base.IBaseJsonBean;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(value = AppTables.SYS_DICTIONARY_TYPE_INFO, addToJson = true)
public class SysDicTypeFormMap extends FormMap implements IBaseJsonBean {
	private static final long serialVersionUID = 1L;

	@Override
	public String getJson() {
		return getStr("json");
	}

	@Override
	public void setJson(String json) {
		put("json", json);
	}
}
