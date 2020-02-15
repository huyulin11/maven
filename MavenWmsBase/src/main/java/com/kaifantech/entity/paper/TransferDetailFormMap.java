package com.kaifantech.entity.paper;

import java.util.Comparator;

import com.calculatedfun.annotation.FormMapTable;
import com.calculatedfun.util.FormMap;
import com.kaifantech.init.sys.dao.AppTables;

@FormMapTable(addToJson = true, value = AppTables.WMS_TRANSFER_REQUEST_DETAIL)
public class TransferDetailFormMap extends PaperDetailFormMap implements Comparator<TransferDetailFormMap> {
	private static final long serialVersionUID = 1L;

	public TransferDetailFormMap() {
	}

	public TransferDetailFormMap(FormMap formMap) {
		super(formMap);
	}

	public TransferDetailFormMap(FormMap formMap, boolean withId) {
		super(formMap, withId);
	}

	public String getItem() {
		return getStr("item");
	}

	public String getItemname() {
		return getStr("itemname");
	}

	public String getItemcount() {
		return getStr("itemcount");
	}

	public String getSprice() {
		return getStr("sprice");
	}

	public String getInventorysts() {
		return getStr("inventorysts");
	}

	public String getExpirationdate() {
		return getStr("expirationdate");
	}

	public String getLot() {
		return getStr("lot");
	}

	public String getRemark() {
		return getStr("remark");
	}

	public String getUserdef2() {
		return getStr("userdef2");
	}

	public String getUserdef3() {
		return getStr("userdef3");
	}

	public String getUserdef4() {
		return getStr("userdef4");
	}

	@Override
	public int compare(TransferDetailFormMap h1, TransferDetailFormMap h2) {
		return h1.getSequence().compareTo(h2.getSequence());
	}

}
