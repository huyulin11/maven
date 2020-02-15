package com.kaifantech.init.sys.qualifier;

import com.kaifantech.init.sys.ServicePrefix;

public class WmsQualifier {
	private static final String DEFAULT_SERVICE_PREFIX = ServicePrefix.WMS;

	public static final String ALLOC_CHECK_SERVICE = DEFAULT_SERVICE_PREFIX + "AllocCheckService";
	public static final String ALLOC_INFO_SERVICE = DEFAULT_SERVICE_PREFIX + "AllocInfoService";
	public static final String TASKEXE_CHECK_SERVICE = DEFAULT_SERVICE_PREFIX + "TaskexeCheckService";
	public static final String RECEIPT_SERVICE = DEFAULT_SERVICE_PREFIX + "ReceiptService";
	public static final String SHIPMENT_SERVICE = DEFAULT_SERVICE_PREFIX + "ShipmentService";
	public static final String TRANSFER_SERVICE = DEFAULT_SERVICE_PREFIX + "TransferService";
	public static final String ALLOC_ITEM_SERVICE = DEFAULT_SERVICE_PREFIX + "AllocItemService";
}
