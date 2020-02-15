package com.kaifantech.component.service.de;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.component.service.paper.InventoryService;
import com.kaifantech.component.service.paper.ReceiptService;
import com.kaifantech.component.service.paper.ShipmentService;
import com.kaifantech.entity.paper.InventoryMainFormMap;
import com.kaifantech.entity.paper.ReceiptMainFormMap;
import com.kaifantech.entity.paper.ShipmentMainFormMap;
import com.kaifantech.init.sys.qualifier.UdfQualifier;

@Service
public class WmsRestfulService {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_RECEIPT_SERVICE)
	protected ReceiptService receiptMainService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_SHIPMENT_SERVICE)
	protected ShipmentService shipmentMainService;

	@Autowired
	protected InventoryService inventoryMainService;

	public Object getPapersStatus(String paperid) {
		ReceiptMainFormMap rtnR = receiptMainService.findByPaperid(paperid, false);
		if (!AppTool.isNull(rtnR)) {
			return rtnR;
		}
		ShipmentMainFormMap rtnS = shipmentMainService.findByPaperid(paperid, false);
		if (!AppTool.isNull(rtnS)) {
			return rtnS;
		}
		InventoryMainFormMap rtnI = inventoryMainService.findByPaperid(paperid, false);
		if (!AppTool.isNull(rtnI)) {
			return rtnI;
		}
		return null;
	}
}
