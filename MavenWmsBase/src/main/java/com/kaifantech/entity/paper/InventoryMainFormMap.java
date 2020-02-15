package com.kaifantech.entity.paper;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(AppTables.WMS_INVENTORY_REQUEST_MAIN)
public class InventoryMainFormMap extends PaperMainFormMap<InventoryDetailFormMap> {
	private static final long serialVersionUID = 1L;

	public InventoryMainFormMap() {
	}

	public InventoryMainFormMap(FormMap formMap) {
		super(formMap);
	}

	public static final String TYPE_FULL = "FULL";
	public static final String TYPE_COLUMN = "COLUMN";
	public static final String TYPE_LINE = "LINE";

	public String getInventorytype() {
		return getStr("inventorytype");
	}
}
