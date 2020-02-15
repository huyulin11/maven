package com.kaifantech.component.service.rest;

import org.springframework.stereotype.Component;

import com.kaifantech.init.sys.SystemURL;

@Component
public class WmsRestService extends AbstractRestService {
	public static WmsRestService one = new WmsRestService();

	public String status(String jsonStr) {
		return load(SystemURL.MSG_TO_WMS, jsonStr, 5);
	}

	public String log(String jsonStr) {
		return load(SystemURL.LOG_TO_WMS, jsonStr, 1);
	}

	public String receiptErr(String jsonStr) {
		return load(SystemURL.RECEIPT_ERR_TO_WMS, jsonStr, 5);
	}

	public String inventoryResult(String jsonStr) {
		return load(SystemURL.INVENTORY_RESULT_TO_WMS, jsonStr, 1);
	}

	public String agvAbnormal(String jsonStr) {
		return load(SystemURL.AGV_ABNORMAL_TO_WMS, jsonStr, 1);
	}
}
