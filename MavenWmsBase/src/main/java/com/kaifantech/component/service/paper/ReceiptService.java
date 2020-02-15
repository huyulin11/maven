package com.kaifantech.component.service.paper;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.component.service.paper.base.PaperCrudService;
import com.kaifantech.entity.paper.ReceiptDetailFormMap;
import com.kaifantech.entity.paper.ReceiptMainFormMap;
import com.kaifantech.init.sys.qualifier.WmsQualifier;
import com.kaifantech.mappings.base.PaperDetailMapper;
import com.kaifantech.mappings.base.PaperMainMapper;
import com.kaifantech.mappings.receipt.ReceiptDetailMapper;
import com.kaifantech.mappings.receipt.ReceiptMainMapper;
import com.kaifantech.util.constant.wms.WmsPaperStatus;

@Service(WmsQualifier.RECEIPT_SERVICE)
public class ReceiptService extends PaperCrudService<ReceiptDetailFormMap, ReceiptMainFormMap> {
	@Override
	public AppMsg doAddItem(FormMap formMap, String paperid) throws Exception {
		return super.doAddItem(formMap, paperid);
	}

	public String getPaperid() {
		String paperid = controlInfoDao.getSid("WMS_RECEIPT_SID");
		return paperid;
	}

	@Override
	protected void initFormMap(ReceiptMainFormMap mainFormMap) {
		mainFormMap.setIfNull("company", "--");
		mainFormMap.setIfNull("warehouse", "1");
		mainFormMap.setIfNull("totalqty", "1");
		mainFormMap.setIfNull("receipttype", "1");
		mainFormMap.setIfNull("orderdate", null);
		mainFormMap.setIfNull("status", WmsPaperStatus.NEW);
	}

	@Override
	protected void initFormMap(ReceiptDetailFormMap detailFormMap) {
		detailFormMap.setIfNull("item", "1");
		detailFormMap.setIfNull("itemname", "1");
		detailFormMap.setIfNull("itemcount", "1");
		detailFormMap.setIfNull("sprice", "1");
		detailFormMap.setIfNull("inventorysts", "1");
		detailFormMap.setIfNull("expirationdate", null);
		detailFormMap.setIfNull("lot", "1");
		detailFormMap.setIfNull("remark", "1");
		detailFormMap.setIfNull("userdef3", "1");
		detailFormMap.setIfNull("userdef4", "");
		detailFormMap.setIfNull("status", WmsPaperStatus.NEW);

		Object allocItem = detailFormMap.get("allocItem");
		if (!AppTool.isNull(allocItem)) {
			String allocItemName = allocItem.toString();
			AllocItemInfoBean bean = allocInfoService.getByNameFromCache(allocItemName);
			detailFormMap.set("userdef1", allocItemName);
			detailFormMap.set("userdef2", bean.getId());
		}
	}

	@Inject
	private ReceiptMainMapper mainMapper;

	@Inject
	private ReceiptDetailMapper detailMapper;

	@Override
	public PaperMainMapper<ReceiptDetailFormMap, ReceiptMainFormMap> getMainMapper() {
		return mainMapper;
	}

	@Override
	public PaperDetailMapper<ReceiptDetailFormMap, ReceiptMainFormMap> getDetailMapper() {
		return detailMapper;
	}

	@Override
	public ReceiptMainFormMap createMainObj() {
		return new ReceiptMainFormMap();
	}

	@Override
	public ReceiptDetailFormMap createDetailObj() {
		return new ReceiptDetailFormMap();
	}

	@Override
	public ReceiptMainFormMap createMainObj(FormMap formMap) {
		return new ReceiptMainFormMap(formMap);
	}

	@Override
	public ReceiptDetailFormMap createDetailObj(FormMap formMap) {
		return new ReceiptDetailFormMap(formMap);
	}

}