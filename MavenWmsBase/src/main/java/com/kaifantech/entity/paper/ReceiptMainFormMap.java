package com.kaifantech.entity.paper;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(AppTables.WMS_RECEIPT_REQUEST_MAIN)
public class ReceiptMainFormMap extends PaperMainFormMap<ReceiptDetailFormMap> {
	private static final long serialVersionUID = 1L;

	public ReceiptMainFormMap() {
	}

	public ReceiptMainFormMap(FormMap formMap) {
		super(formMap);
	}

	public String getCompany() {
		return getStr("company");
	}

	public String getWarehouse() {
		return getStr("warehouse");
	}

	public String getReceipttype() {
		return getStr("receipttype");
	}

	public String getOrderdate() {
		return getStr("orderdate");
	}
}
