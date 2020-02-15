package com.kaifantech.component.controller.paper;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.paper.base.PaperDetailController;
import com.kaifantech.component.service.paper.ShipmentService;
import com.kaifantech.entity.paper.ShipmentDetailFormMap;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.shipment.ShipmentDetailMapper;

@Controller
@RequestMapping("/shipment/detail/")
@SystemLog(module = "出库明细管理")
public class ShipmentDetailController extends PaperDetailController<ShipmentDetailFormMap> {
	@Inject
	protected ShipmentDetailMapper shipmentDetailMapper;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_SHIPMENT_SERVICE)
	protected ShipmentService shipmentService;

	@Override
	public AppBaseMapper<ShipmentDetailFormMap> getMapper() {
		return shipmentDetailMapper;
	}

	@ResponseBody
	@RequestMapping("addCombinedItem")
	@SystemLog(methods = "增加组盘明细")
	@Transactional(readOnly = false)
	public JSONObject addCombinedItem() {
		try {
			FormMap form = getFormMap(false);
			AppMsg msg = shipmentService.doAddCombinedItem(form);
			return msg.toAlbbJson();
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("增加明细异常：" + e.getMessage()).toAlbbJson();
		}
	}

	@ResponseBody
	@RequestMapping("addPickingItem")
	@SystemLog(methods = "增加组盘明细")
	@Transactional(readOnly = false)
	public JSONObject addPickingItem() {
		try {
			FormMap form = getFormMap(false);
			AppMsg msg = shipmentService.doAddPickingItem(form);
			return msg.toAlbbJson();
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("增加明细异常：" + e.getMessage()).toAlbbJson();
		}
	}
}