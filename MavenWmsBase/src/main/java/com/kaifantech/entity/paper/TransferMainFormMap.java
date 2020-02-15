package com.kaifantech.entity.paper;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(addToJson = true, value = AppTables.WMS_TRANSFER_REQUEST_MAIN)
public class TransferMainFormMap extends PaperMainFormMap<TransferDetailFormMap> {
	private static final long serialVersionUID = 1L;

	public TransferMainFormMap() {
	}

	public TransferMainFormMap(FormMap formMap) {
		super(formMap);
	}

	public String getCompany() {
		return getStr("company");
	}

	public String getWarehouse() {
		return getStr("warehouse");
	}

	public String getTransfertype() {
		return getStr("transfertype");
	}

	public String getOrderdate() {
		return getStr("orderdate");
	}
}
