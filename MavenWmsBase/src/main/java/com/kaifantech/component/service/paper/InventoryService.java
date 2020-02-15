package com.kaifantech.component.service.paper;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.calculatedfun.util.FormMap;
import com.kaifantech.component.service.paper.base.PaperCrudService;
import com.kaifantech.entity.paper.InventoryDetailFormMap;
import com.kaifantech.entity.paper.InventoryMainFormMap;
import com.kaifantech.mappings.base.PaperDetailMapper;
import com.kaifantech.mappings.base.PaperMainMapper;
import com.kaifantech.mappings.inventory.InventoryDetailMapper;
import com.kaifantech.mappings.inventory.InventoryMainMapper;
import com.kaifantech.util.constant.wms.WmsPaperStatus;

@Service
public class InventoryService extends PaperCrudService<InventoryDetailFormMap, InventoryMainFormMap> {
	public String getPaperid() {
		String paperid = controlInfoDao.getSid("WMS_INVENTORY_SID");
		return paperid;
	}

	protected void initFormMap(InventoryMainFormMap formMap) {
		formMap.setIfNull("company", "--");
		formMap.setIfNull("warehouse", "1");
		formMap.setIfNull("totalqty", "1");
		formMap.setIfNull("orderdate", null);
		formMap.setIfNull("status", WmsPaperStatus.NEW);
	}

	@Override
	protected void initFormMap(InventoryDetailFormMap detailFormMap) {
		detailFormMap.setIfNull("item", "1");
		detailFormMap.setIfNull("itemname", "1");
		detailFormMap.setIfNull("itemcount", "1");
		detailFormMap.setIfNull("sprice", "1");
		detailFormMap.setIfNull("inventorysts", "1");
		detailFormMap.setIfNull("expirationdate", null);
		detailFormMap.setIfNull("sequence", "1");
		detailFormMap.setIfNull("lot", "1");
		detailFormMap.setIfNull("remark", "1");
		detailFormMap.setIfNull("userdef2", "1");
		detailFormMap.setIfNull("userdef3", "1");
		detailFormMap.setIfNull("userdef4", "");
		detailFormMap.setIfNull("status", WmsPaperStatus.NEW);

		detailFormMap.set("userdef1", detailFormMap.get("allocItem"));
	}

	@Inject
	private InventoryMainMapper mainMapper;

	@Inject
	private InventoryDetailMapper detailMapper;

	@Override
	public PaperMainMapper<InventoryDetailFormMap, InventoryMainFormMap> getMainMapper() {
		return mainMapper;
	}

	@Override
	public PaperDetailMapper<InventoryDetailFormMap, InventoryMainFormMap> getDetailMapper() {
		return detailMapper;
	}

	@Override
	public InventoryMainFormMap createMainObj() {
		return new InventoryMainFormMap();
	}

	@Override
	public InventoryDetailFormMap createDetailObj() {
		return new InventoryDetailFormMap();
	}

	@Override
	public InventoryMainFormMap createMainObj(FormMap formMap) {
		return new InventoryMainFormMap(formMap);
	}

	@Override
	public InventoryDetailFormMap createDetailObj(FormMap formMap) {
		return new InventoryDetailFormMap(formMap);
	}
}