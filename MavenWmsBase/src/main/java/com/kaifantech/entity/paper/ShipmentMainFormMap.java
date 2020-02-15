package com.kaifantech.entity.paper;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(addToJson = true, value = AppTables.WMS_SHIPMENT_REQUEST_MAIN)
public class ShipmentMainFormMap extends PaperMainFormMap<ShipmentDetailFormMap> {
	private static final long serialVersionUID = 1L;

	public ShipmentMainFormMap() {
	}

	public ShipmentMainFormMap(FormMap formMap) {
		super(formMap);
	}
}
