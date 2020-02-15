package com.kaifantech.component.service.paper.base;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kaifantech.component.controller.paper.InventoryDetailController;
import com.kaifantech.component.controller.paper.InventoryMainController;
import com.kaifantech.component.controller.paper.ReceiptDetailController;
import com.kaifantech.component.controller.paper.ReceiptMainController;
import com.kaifantech.component.controller.paper.ShipmentDetailController;
import com.kaifantech.component.controller.paper.ShipmentMainController;
import com.kaifantech.component.controller.paper.TransferDetailController;
import com.kaifantech.component.controller.paper.TransferMainController;
import com.kaifantech.component.controller.paper.base.PaperDetailController;
import com.kaifantech.component.controller.paper.base.PaperMainController;
import com.kaifantech.component.service.paper.InventoryService;
import com.kaifantech.component.service.paper.ReceiptService;
import com.kaifantech.component.service.paper.ShipmentService;
import com.kaifantech.component.service.paper.TransferService;
import com.kaifantech.entity.paper.InventoryDetailFormMap;
import com.kaifantech.entity.paper.InventoryMainFormMap;
import com.kaifantech.entity.paper.PaperDetailFormMap;
import com.kaifantech.entity.paper.PaperMainFormMap;
import com.kaifantech.entity.paper.ReceiptDetailFormMap;
import com.kaifantech.entity.paper.ReceiptMainFormMap;
import com.kaifantech.entity.paper.ShipmentDetailFormMap;
import com.kaifantech.entity.paper.ShipmentMainFormMap;
import com.kaifantech.entity.paper.TransferDetailFormMap;
import com.kaifantech.entity.paper.TransferMainFormMap;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.mappings.base.PaperDetailMapper;
import com.kaifantech.mappings.base.PaperMainMapper;
import com.kaifantech.mappings.inventory.InventoryDetailMapper;
import com.kaifantech.mappings.inventory.InventoryMainMapper;
import com.kaifantech.mappings.receipt.ReceiptDetailMapper;
import com.kaifantech.mappings.receipt.ReceiptMainMapper;
import com.kaifantech.mappings.shipment.ShipmentDetailMapper;
import com.kaifantech.mappings.shipment.ShipmentMainMapper;
import com.kaifantech.mappings.transfer.TransferDetailMapper;
import com.kaifantech.mappings.transfer.TransferMainMapper;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

@Service
public class PaperService {
	public String getTasktype(Object obj) {
		if (obj instanceof PaperMainFormMap) {
			if (obj instanceof ReceiptMainFormMap) {
				return AgvTaskType.RECEIPT;
			} else if (obj instanceof ShipmentMainFormMap) {
				return AgvTaskType.SHIPMENT;
			} else if (obj instanceof InventoryMainFormMap) {
				return AgvTaskType.INVENTORY;
			} else if (obj instanceof TransferMainFormMap) {
				return AgvTaskType.TRANSFER;
			}
			return null;
		}
		return null;
	}

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_RECEIPT_SERVICE)
	protected ReceiptService receiptService;
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_SHIPMENT_SERVICE)
	protected ShipmentService shipmentService;
	@Autowired
	protected InventoryService inventoryService;
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_TRANSFER_SERVICE)
	protected TransferService transferService;

	public PaperCrudService<?, ?> getService(Object obj) {
		if (obj instanceof String) {
			if (AgvTaskType.RECEIPT.equals(obj.toString())) {
				return receiptService;
			} else if (AgvTaskType.SHIPMENT.equals(obj.toString())) {
				return shipmentService;
			} else if (AgvTaskType.INVENTORY.equals(obj.toString())) {
				return inventoryService;
			} else if (AgvTaskType.TRANSFER.equals(obj.toString())) {
				return transferService;
			}
			return null;
		} else if (obj instanceof PaperMainFormMap) {
			if (obj instanceof ReceiptMainFormMap) {
				return receiptService;
			} else if (obj instanceof ShipmentMainFormMap) {
				return shipmentService;
			} else if (obj instanceof InventoryMainFormMap) {
				return inventoryService;
			} else if (obj instanceof TransferMainFormMap) {
				return transferService;
			}
			return null;
		} else if (obj instanceof PaperMainController) {
			if (obj instanceof ReceiptMainController) {
				return receiptService;
			} else if (obj instanceof ShipmentMainController) {
				return shipmentService;
			} else if (obj instanceof InventoryMainController) {
				return inventoryService;
			} else if (obj instanceof TransferMainController) {
				return transferService;
			}
			return null;
		} else if (obj instanceof PaperDetailController) {
			if (obj instanceof ReceiptDetailController) {
				return receiptService;
			} else if (obj instanceof ShipmentDetailController) {
				return shipmentService;
			} else if (obj instanceof InventoryDetailController) {
				return inventoryService;
			} else if (obj instanceof TransferDetailController) {
				return transferService;
			}
			return null;
		} else if (obj instanceof PaperDetailFormMap) {
			if (obj instanceof ReceiptDetailFormMap) {
				return receiptService;
			} else if (obj instanceof ShipmentDetailFormMap) {
				return shipmentService;
			} else if (obj instanceof InventoryDetailFormMap) {
				return inventoryService;
			} else if (obj instanceof TransferDetailFormMap) {
				return transferService;
			}
			return null;
		}
		return null;
	}

	@Inject
	protected ReceiptMainMapper receiptMainMapper;
	@Inject
	protected ShipmentMainMapper shipmentMainMapper;
	@Inject
	protected InventoryMainMapper inventoryMainMapper;
	@Inject
	protected TransferMainMapper transferMainMapper;

	public PaperMainMapper<?, ?> getMainMapper(Object obj) {
		if (obj instanceof String) {
			if (AgvTaskType.RECEIPT.equals(obj.toString())) {
				return receiptMainMapper;
			} else if (AgvTaskType.SHIPMENT.equals(obj.toString())) {
				return shipmentMainMapper;
			} else if (AgvTaskType.INVENTORY.equals(obj.toString())) {
				return inventoryMainMapper;
			} else if (AgvTaskType.TRANSFER.equals(obj.toString())) {
				return transferMainMapper;
			}
			return null;
		} else if (obj instanceof PaperDetailFormMap) {
			if (obj instanceof ReceiptDetailFormMap) {
				return receiptMainMapper;
			} else if (obj instanceof ShipmentDetailFormMap) {
				return shipmentMainMapper;
			} else if (obj instanceof InventoryDetailFormMap) {
				return inventoryMainMapper;
			} else if (obj instanceof TransferDetailFormMap) {
				return transferMainMapper;
			}
			return null;
		} else if (obj instanceof PaperMainFormMap) {
			if (obj instanceof ReceiptMainFormMap) {
				return receiptMainMapper;
			} else if (obj instanceof ShipmentMainFormMap) {
				return shipmentMainMapper;
			} else if (obj instanceof InventoryMainFormMap) {
				return inventoryMainMapper;
			} else if (obj instanceof TransferMainFormMap) {
				return transferMainMapper;
			}
			return null;
		} else if (obj instanceof PaperMainController) {
			if (obj instanceof ReceiptMainController) {
				return receiptMainMapper;
			} else if (obj instanceof ShipmentMainController) {
				return shipmentMainMapper;
			} else if (obj instanceof InventoryMainController) {
				return inventoryMainMapper;
			} else if (obj instanceof TransferMainController) {
				return transferMainMapper;
			}
			return null;
		} else if (obj instanceof PaperDetailController) {
			if (obj instanceof ReceiptDetailController) {
				return receiptMainMapper;
			} else if (obj instanceof ShipmentDetailController) {
				return shipmentMainMapper;
			} else if (obj instanceof InventoryDetailController) {
				return inventoryMainMapper;
			} else if (obj instanceof TransferDetailController) {
				return transferMainMapper;
			}
			return null;
		}

		return null;
	}

	@Inject
	protected ReceiptDetailMapper receiptDetailMapper;
	@Inject
	protected ShipmentDetailMapper shipmentDetailMapper;
	@Inject
	protected InventoryDetailMapper inventoryDetailMapper;
	@Inject
	protected TransferDetailMapper transferDetailMapper;

	public PaperDetailMapper<?, ?> getDetailMapper(Object obj) {
		if (obj instanceof String) {
			if (AgvTaskType.RECEIPT.equals(obj.toString())) {
				return receiptDetailMapper;
			} else if (AgvTaskType.SHIPMENT.equals(obj.toString())) {
				return shipmentDetailMapper;
			} else if (AgvTaskType.INVENTORY.equals(obj.toString())) {
				return inventoryDetailMapper;
			} else if (AgvTaskType.TRANSFER.equals(obj.toString())) {
				return transferDetailMapper;
			}
			return null;
		} else if (obj instanceof PaperDetailFormMap) {
			if (obj instanceof ReceiptDetailFormMap) {
				return receiptDetailMapper;
			} else if (obj instanceof ShipmentDetailFormMap) {
				return shipmentDetailMapper;
			} else if (obj instanceof InventoryDetailFormMap) {
				return inventoryDetailMapper;
			} else if (obj instanceof TransferDetailFormMap) {
				return transferDetailMapper;
			}
			return null;
		} else if (obj instanceof PaperMainFormMap) {
			if (obj instanceof ReceiptMainFormMap) {
				return receiptDetailMapper;
			} else if (obj instanceof ShipmentMainFormMap) {
				return shipmentDetailMapper;
			} else if (obj instanceof InventoryMainFormMap) {
				return inventoryDetailMapper;
			} else if (obj instanceof InventoryMainFormMap) {
				return inventoryDetailMapper;
			}
			return null;
		} else if (obj instanceof PaperMainController) {
			if (obj instanceof ReceiptMainController) {
				return receiptDetailMapper;
			} else if (obj instanceof ShipmentMainController) {
				return shipmentDetailMapper;
			} else if (obj instanceof InventoryMainController) {
				return inventoryDetailMapper;
			} else if (obj instanceof TransferMainController) {
				return transferDetailMapper;
			}
			return null;
		} else if (obj instanceof PaperDetailController) {
			if (obj instanceof ReceiptDetailController) {
				return receiptDetailMapper;
			} else if (obj instanceof ShipmentDetailController) {
				return shipmentDetailMapper;
			} else if (obj instanceof InventoryDetailController) {
				return inventoryDetailMapper;
			} else if (obj instanceof TransferDetailController) {
				return transferDetailMapper;
			}
			return null;
		}
		return null;
	}
}