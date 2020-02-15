package com.kaifantech.entity.paper;

import java.util.Comparator;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(AppTables.WMS_SHIPMENT_REQUEST_DETAIL)
public class ShipmentDetailFormMap extends PaperDetailFormMap implements Comparator<ShipmentDetailFormMap> {
	private static final long serialVersionUID = 1L;
	private ShipmentMainFormMap parent;

	public ShipmentDetailFormMap() {
	}

	public ShipmentDetailFormMap(FormMap formMap) {
		super(formMap);
	}

	@Override
	public int compare(ShipmentDetailFormMap o1, ShipmentDetailFormMap o2) {
		return 0;
	}

	public ShipmentMainFormMap getParent() {
		return parent;
	}

	public void setParent(ShipmentMainFormMap parent) {
		this.parent = parent;
	}

	public String getRelationid() {
		return getStr("relationid");
	}
}
