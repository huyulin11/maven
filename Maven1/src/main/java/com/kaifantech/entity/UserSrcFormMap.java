package com.kaifantech.entity;

import java.util.Map;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(AppTables.USER_SRC)
public class UserSrcFormMap extends FormMap {
	private static final long serialVersionUID = 1L;

	public UserSrcFormMap() {
	}

	public UserSrcFormMap(Map<String, Object> formMap) {
		super(formMap, true);
	}
}
