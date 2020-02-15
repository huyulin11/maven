package com.kaifantech.component.service.paper;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.component.service.paper.base.PaperCrudService;
import com.kaifantech.entity.paper.TransferDetailFormMap;
import com.kaifantech.entity.paper.TransferMainFormMap;
import com.kaifantech.init.sys.qualifier.WmsQualifier;
import com.kaifantech.mappings.base.PaperDetailMapper;
import com.kaifantech.mappings.base.PaperMainMapper;
import com.kaifantech.mappings.transfer.TransferDetailMapper;
import com.kaifantech.mappings.transfer.TransferMainMapper;
import com.kaifantech.util.constant.wms.WmsPaperStatus;

@Service(WmsQualifier.TRANSFER_SERVICE)
public class TransferService extends PaperCrudService<TransferDetailFormMap, TransferMainFormMap> {
	public String getPaperid() {
		String paperid = controlInfoDao.getSid("WMS_TRANSFER_SID");
		return paperid;
	}

	protected void initFormMap(TransferMainFormMap main) {
		main.setIfNull("company", "--");
		main.setIfNull("warehouse", "1");
		main.setIfNull("sourcewh", "1");
		main.setIfNull("targetwh", "1");
		main.setIfNull("totalqty", "1");
		main.setIfNull("transfertype", "AGV");
		main.setIfNull("orderdate", null);
		main.setIfNull("status", WmsPaperStatus.NEW);
	}

	protected void initFormMap(TransferDetailFormMap detail) {
		detail.setIfNull("item", "1");
		detail.setIfNull("itemname", "1");
		detail.setIfNull("itemcount", "1");
		detail.setIfNull("sprice", "1");
		detail.setIfNull("inventorysts", "1");
		detail.setIfNull("expirationdate", null);
		detail.setIfNull("lot", "1");
		detail.setIfNull("remark", "1");
		detail.setIfNull("userdef3", "1");
		detail.setIfNull("userdef4", "");
		detail.setIfNull("status", WmsPaperStatus.NEW);

		String allocItemName = detail.getStr("allocItem");
		if (!AppTool.isNull(allocItemName)) {
			AllocItemInfoBean bean = allocInfoService.getByNameFromCache(allocItemName);
			detail.set("userdef1", allocItemName);
			detail.set("userdef2", bean.getId());
		}
	}

	@Inject
	private TransferMainMapper mainMapper;

	@Inject
	private TransferDetailMapper detailMapper;

	@Override
	public PaperMainMapper<TransferDetailFormMap, TransferMainFormMap> getMainMapper() {
		return mainMapper;
	}

	@Override
	public PaperDetailMapper<TransferDetailFormMap, TransferMainFormMap> getDetailMapper() {
		return detailMapper;
	}

	@Override
	public TransferMainFormMap createMainObj() {
		return new TransferMainFormMap();
	}

	@Override
	public TransferDetailFormMap createDetailObj() {
		return new TransferDetailFormMap();
	}

	@Override
	public TransferMainFormMap createMainObj(FormMap formMap) {
		return new TransferMainFormMap(formMap);
	}

	@Override
	public TransferDetailFormMap createDetailObj(FormMap formMap) {
		return new TransferDetailFormMap(formMap);
	}

}