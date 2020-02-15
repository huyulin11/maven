package com.kaifantech.entity;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(AppTables.SKU_INFO)
public class SkuInfoFormMap extends FormMap {
	private static final long serialVersionUID = 1L;

	public SkuInfoFormMap() {
	}

	public SkuInfoFormMap(FormMap formMap) {
		super(formMap);
	}
}
