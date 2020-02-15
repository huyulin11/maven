package com.kaifantech.entity;

import java.util.Map;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(AppTables.ALLOC_TXM_INFO)
public class AllocTxmFormMap extends FormMap {
	private static final long serialVersionUID = 1L;

	public AllocTxmFormMap() {
	}

	public AllocTxmFormMap(FormMap formMap) {
		super(formMap);
	}

	public AllocTxmFormMap(Map<String, Object> formMap) {
		super(formMap);
	}
}
