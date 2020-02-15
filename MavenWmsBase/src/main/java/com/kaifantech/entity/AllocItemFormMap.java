package com.kaifantech.entity;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(AppTables.ALLOCATION_ITEM_INFO)
public class AllocItemFormMap extends FormMap {
	public AllocItemFormMap() {
	}

	public AllocItemFormMap(FormMap value) {
		super(value);
	}

	private static final long serialVersionUID = 1L;
}
