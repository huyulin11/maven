package com.kaifantech.entity;

import java.util.Map;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(AppTables.LAP_INFO)
public class LapInfoFormMap extends FormMap {
	private static final long serialVersionUID = 1L;

	public LapInfoFormMap() {
	}

	public LapInfoFormMap(Map<String, Object> formMap) {
		super(formMap, true);
	}
}
