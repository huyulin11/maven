package com.kaifantech.component.service.sku;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.util.AppSet;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.cache.manager.AppConf;
import com.kaifantech.component.service.alloc.amount.IAllocAmountService;
import com.kaifantech.component.service.alloc.info.IAllocInfoService;
import com.kaifantech.component.service.alloc.status.IAllocStatusService;
import com.kaifantech.entity.AllocTxmFormMap;
import com.kaifantech.entity.paper.ReceiptDetailFormMap;
import com.kaifantech.entity.paper.ReceiptMainFormMap;
import com.kaifantech.entity.paper.ShipmentDetailFormMap;
import com.kaifantech.entity.paper.ShipmentMainFormMap;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.mappings.AllocTxmMapper;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.util.lock.WmsAllocLock;

@Service
public class AllocTxmService implements IBaseService<AllocTxmFormMap> {
	@Inject
	private AllocTxmMapper mapper;
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_ALLOC_AMOUNT_SERVICE)
	private IAllocAmountService allocOpService;
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_ALLOC_INFO_SERVICE)
	protected IAllocInfoService allocInfoService;
	@Autowired
	protected IAllocStatusService allocStatusService;

	public AppMsg doAddEntity(List<FormMap> valueList) throws Exception {
		if (AppTool.isNull(valueList)) {
			return new AppMsg(-1, "无有效明细数据");
		}
		for (FormMap tmpFormMap : valueList) {
			Object alloc = tmpFormMap.get("alloc");
			if (AppTool.isNull(alloc)) {
				return AppMsg.fail("未找到输入的货位名称！");
			}
			String locked = WmsAllocLock.getLockedObj(alloc);
			if (!AppTool.isNull(locked)) {
				return new AppMsg(-1, alloc + "被订单" + locked + "锁定！");
			}
		}
		for (FormMap tmpFormMap : valueList) {
			Object txm = tmpFormMap.get("txm"), alloc = tmpFormMap.get("alloc");
			Double num = tmpFormMap.getDouble("num");
			AllocItemInfoBean allocationInfoBean = allocInfoService.getByNameFromDB(alloc.toString());
			if (AppTool.isNull(allocationInfoBean)) {
				return AppMsg.fail("未找到目标货位执行入库任务！");
			}
			doAddTxm(txm, alloc, num);
		}
		return new AppMsg(0, "成功！");
	}

	public AppMsg doAddFromReceipt(ReceiptMainFormMap bean) throws Exception {
		List<ReceiptDetailFormMap> valueList = bean.getDetailList();
		if (AppTool.isNull(valueList)) {
			return new AppMsg(-1, "无有效明细数据");
		}

		Object alloc = AppConf.worker().get("RECEIPT_ALLOC_ITEM", bean.getPaperid());
		for (FormMap tmpFormMap : valueList) {
			Double num = tmpFormMap.getDouble("num");
			doAddTxm(tmpFormMap.get("item"), alloc, num);
		}
		return new AppMsg(0, "成功！");
	}

	public void doAddTxm(Object txm, Object alloc, Double num) throws Exception {
		AllocTxmFormMap searchFormMap = new AllocTxmFormMap();
		searchFormMap.set("txm", txm);
		searchFormMap.set("alloc", alloc);
		searchFormMap = getTxmInfo(txm, alloc);

		AllocTxmFormMap txmFormMap = new AllocTxmFormMap();
		txmFormMap.set("txm", txm);
		txmFormMap.set("alloc", alloc);
		Integer sku = txmFormMap.getInt("sku");
		txmFormMap.set("skuid", AppTool.v(sku, 1));
		txmFormMap.set("name", txm + "_" + 1);

		if (AppTool.isNull(searchFormMap)) {
			txmFormMap.set("num", AppTool.v(num, 1));
			mapper.addEntity(txmFormMap);
		} else {
			Double hasNum = searchFormMap.getDouble("num");
			txmFormMap.set("num", AppTool.v(num, 1.0) + AppTool.v(hasNum, 0.0));
			mapper.editEntity(txmFormMap);
		}
		num = txmFormMap.getDouble("num");
		AllocItemInfoBean allocItem = allocInfoService.getByNameFromDB(alloc.toString());
		allocItem.setNum(AppTool.v(allocItem.getNum(), 0.0) + AppTool.v(num, 1.0));
		allocStatusService.startStack(allocItem);
	}

	public AppMsg doCancelFromReceipt(ReceiptMainFormMap bean) throws Exception {
		List<ReceiptDetailFormMap> valueList = bean.getDetailList();
		if (AppTool.isNull(valueList)) {
			return new AppMsg(-1, "无有效明细数据");
		}

		Object alloc = AppConf.worker().get("RECEIPT_ALLOC_ITEM", bean.getPaperid());
		for (FormMap tmpFormMap : valueList) {
			AllocTxmFormMap searchFormMap = getTxmInfo(tmpFormMap.get("item"), alloc);

			Double hasNum = searchFormMap.getDouble("num");
			Double num = tmpFormMap.getDouble("num");
			Double finalNum = AppTool.v(hasNum, 0.0) - AppTool.v(num, 1.0);
			if (finalNum < 0) {
				return AppMsg.fail("错误的库存数量，请检查单据数量与库存数据是否合理！");
			}
			searchFormMap.set("num", finalNum);
			mapper.editEntity(searchFormMap);
		}
		return new AppMsg(0, "成功！");
	}

	public AppMsg doCancelFromShipment(ShipmentMainFormMap bean) throws Exception {
		List<ShipmentDetailFormMap> valueList = bean.getDetailList();
		if (AppTool.isNull(valueList)) {
			return new AppMsg(-1, "无有效明细数据");
		}

		Object alloc = AppConf.worker().get("SHIPMENT_ALLOC_ITEM", bean.getPaperid());
		for (FormMap tmpFormMap : valueList) {
			AllocTxmFormMap searchFormMap = getTxmInfo(tmpFormMap.get("item"), alloc);

			Double hasNum = searchFormMap.getDouble("num");
			Double num = tmpFormMap.getDouble("num");
			Double finalNum = AppTool.v(hasNum, 0.0) - AppTool.v(num, 1.0);
			if (finalNum < 0) {
				return AppMsg.fail("错误的库存数量，请检查单据数量与库存数据是否合理！");
			}
			searchFormMap.set("num", finalNum);
			mapper.editEntity(searchFormMap);
		}
		return new AppMsg(0, "成功！");
	}

	public AllocTxmFormMap getTxmInfo(Object txm, Object alloc) {
		AllocTxmFormMap searchFormMap = new AllocTxmFormMap();
		searchFormMap.set("txm", txm);
		searchFormMap.set("alloc", alloc);
		searchFormMap = mapper.findOne(searchFormMap);
		return searchFormMap;
	}

	public AllocTxmFormMap getByTxmAlloc(Object txm, Object alloc) {
		AllocTxmFormMap allocTxmFormMap = new AllocTxmFormMap();
		allocTxmFormMap.set("txm", txm);
		allocTxmFormMap.set("alloc", alloc);
		List<AllocTxmFormMap> list = mapper.findByNames(allocTxmFormMap);
		return AppSet.first(list);
	}

	public AllocTxmFormMap getStockByTxmAlloc(Object txm, Object alloc) {
		AllocTxmFormMap allocTxmFormMap = new AllocTxmFormMap();
		allocTxmFormMap.set("txm", txm);
		allocTxmFormMap.set("alloc", alloc);
		allocTxmFormMap.set("num", ">0");
		List<AllocTxmFormMap> list = mapper.findByNames(allocTxmFormMap);
		return AppSet.first(list);
	}

	@Override
	public AppBaseMapper<AllocTxmFormMap> getMapper() {
		return mapper;
	}
}
