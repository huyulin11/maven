package com.kaifantech.component.controller.paper;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.kaifantech.component.controller.paper.base.PaperMainController;
import com.kaifantech.component.service.paper.ShipmentService;
import com.kaifantech.entity.paper.ShipmentDetailFormMap;
import com.kaifantech.entity.paper.ShipmentMainFormMap;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.shipment.ShipmentMainMapper;

@Controller
@RequestMapping("/shipment/main/")
@SystemLog(module = "出库管理")
public class ShipmentMainController extends PaperMainController<ShipmentMainFormMap, ShipmentDetailFormMap> {
	@Inject
	protected ShipmentMainMapper shipmentMainMapper;

	@Inject
	@Qualifier(UdfQualifier.DEFAULT_SHIPMENT_SERVICE)
	protected ShipmentService shipmentService;

	@Override
	public AppBaseMapper<ShipmentMainFormMap> getMapper() {
		return shipmentMainMapper;
	}

	@ResponseBody
	@RequestMapping("startPcs")
	@SystemLog(methods = "开始运输")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject startPcs(String tu) throws Exception {
		return shipmentService.startPcs(tu).toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("overPcs")
	@SystemLog(methods = "开始运输")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject overPcs(String tu) throws Exception {
		return shipmentService.overPcs(tu).toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("overAll")
	@SystemLog(methods = "结束运输")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject overAll(String tu) throws Exception {
		return shipmentService.overAll(tu).toAlbbJson();
	}
}