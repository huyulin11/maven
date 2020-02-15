package com.kaifantech.component.service.paper;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.component.service.paper.base.PaperCrudService;
import com.kaifantech.entity.paper.ShipmentDetailFormMap;
import com.kaifantech.entity.paper.ShipmentMainFormMap;
import com.kaifantech.init.sys.qualifier.WmsQualifier;
import com.kaifantech.mappings.base.PaperDetailMapper;
import com.kaifantech.mappings.base.PaperMainMapper;
import com.kaifantech.mappings.shipment.ShipmentDetailMapper;
import com.kaifantech.mappings.shipment.ShipmentMainMapper;
import com.kaifantech.util.constant.wms.WmsPaperStatus;

@Service(WmsQualifier.SHIPMENT_SERVICE)
public class ShipmentService extends PaperCrudService<ShipmentDetailFormMap, ShipmentMainFormMap> {
	protected void initFormMap(ShipmentDetailFormMap detailFormMap) {
		detailFormMap.setIfNull("item", "1");
		detailFormMap.setIfNull("itemname", "1");
		detailFormMap.setIfNull("itemcount", "1");
		detailFormMap.setIfNull("sprice", "1");
		detailFormMap.setIfNull("lot", "1");
		detailFormMap.setIfNull("inventorysts", "1");
		detailFormMap.setIfNull("userdef3", "1");
		detailFormMap.setIfNull("userdef4", "");
		detailFormMap.setIfNull("status", WmsPaperStatus.NEW);

		if (!AppTool.isNull(detailFormMap.get("allocItem"))) {
			String allocItemName = detailFormMap.get("allocItem").toString();
			AllocItemInfoBean bean = allocInfoService.getByNameFromCache(allocItemName);
			detailFormMap.set("userdef1", allocItemName);
			detailFormMap.set("userdef2", bean.getId());
		}
	}

	public String getPaperid() {
		String paperid = controlInfoDao.getSid("WMS_SHIPMENT_SID");
		return paperid;
	}

	protected void initFormMap(ShipmentMainFormMap mainFormMap) {
		mainFormMap.setIfNull("company", "--");
		mainFormMap.setIfNull("name", "1");
		mainFormMap.setIfNull("warehouse", "1");
		mainFormMap.setIfNull("totalqty", "1");
		mainFormMap.setIfNull("tordercode", "1");
		mainFormMap.setIfNull("shipmenttype", "AGV");
		mainFormMap.setIfNull("carrier", "AGV");
		mainFormMap.setIfNull("cusstomername", "1");
		mainFormMap.setIfNull("cusstomerid", "1");
		mainFormMap.setIfNull("ordertype", "1");
		mainFormMap.setIfNull("itemstatus", "1");
		mainFormMap.setIfNull("postcode", "1");
		mainFormMap.setIfNull("phone", "1");
		mainFormMap.setIfNull("mobile", "1");
		mainFormMap.setIfNull("state", "1");
		mainFormMap.setIfNull("city", "1");
		mainFormMap.setIfNull("district", "1");
		mainFormMap.setIfNull("address", "1");
		mainFormMap.setIfNull("isfree", "1");
		mainFormMap.setIfNull("keepamount", "1");
		mainFormMap.setIfNull("area", "1");
		mainFormMap.setIfNull("shipdate", null);
		mainFormMap.setIfNull("cod", "1");
		mainFormMap.setIfNull("codvalue", "1");
		mainFormMap.setIfNull("invoice", "1");
		mainFormMap.setIfNull("invoiceamount", "1");
		mainFormMap.setIfNull("invoicetype", "1");
		mainFormMap.setIfNull("invoicename", "1");
		mainFormMap.setIfNull("invoicecontent", "1");
		mainFormMap.setIfNull("shopname", "1");
		mainFormMap.setIfNull("remark", "1");
		mainFormMap.setIfNull("status", WmsPaperStatus.NEW);
	}

	@Inject
	private ShipmentMainMapper mainMapper;

	@Inject
	private ShipmentDetailMapper detailMapper;

	@Override
	public PaperMainMapper<ShipmentDetailFormMap, ShipmentMainFormMap> getMainMapper() {
		return mainMapper;
	}

	@Override
	public PaperDetailMapper<ShipmentDetailFormMap, ShipmentMainFormMap> getDetailMapper() {
		return detailMapper;
	}

	@Override
	public ShipmentMainFormMap createMainObj() {
		return new ShipmentMainFormMap();
	}

	@Override
	public ShipmentDetailFormMap createDetailObj() {
		return new ShipmentDetailFormMap();
	}

	@Override
	public ShipmentMainFormMap createMainObj(FormMap formMap) {
		return new ShipmentMainFormMap(formMap);
	}

	@Override
	public ShipmentDetailFormMap createDetailObj(FormMap formMap) {
		return new ShipmentDetailFormMap(formMap);
	}

	public AppMsg doAddCombinedItem(FormMap formMap) throws Exception {
		return null;
	}

	public AppMsg doAddPickingItem(FormMap formMap) throws Exception {
		return null;
	}

	public AppMsg startPcs(String tu) throws Exception {
		return null;
	}

	public AppMsg overPcs(String tu) throws Exception {
		return null;
	}

	public AppMsg overAll(String tu) throws Exception {
		return null;
	}
}