package com.kaifantech.entity.paper;

import java.util.Comparator;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(AppTables.WMS_INVENTORY_REQUEST_DETAIL)
public class InventoryDetailFormMap extends PaperDetailFormMap implements Comparator<InventoryDetailFormMap> {
	private static final long serialVersionUID = 1L;

	public InventoryDetailFormMap() {
	}

	public InventoryDetailFormMap(FormMap formMap) {
		super(formMap);
	}

	@Override
	public int compare(InventoryDetailFormMap h1, InventoryDetailFormMap h2) {
		return h1.getSequence().compareTo(h2.getSequence());
	}
}
