package com.kaifantech.component.controller.paper;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.paper.base.PaperDetailController;
import com.kaifantech.component.service.paper.ShipmentService;
import com.kaifantech.entity.paper.ShipmentDetailFormMap;
import com.kaifantech.entity.paper.TransferDetailFormMap;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.transfer.TransferDetailMapper;
import com.kaifantech.util.constant.wms.WmsPaperStatus;

@Controller
@SystemLog(module = "周转明细管理")
@RequestMapping("/transfer/detail/")
public class TransferDetailController extends PaperDetailController<TransferDetailFormMap> {
	@Inject
	protected TransferDetailMapper transferDetailMapper;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_SHIPMENT_SERVICE)
	protected ShipmentService shipmentService;

	@Override
	public AppBaseMapper<TransferDetailFormMap> getMapper() {
		return transferDetailMapper;
	}

	@ResponseBody
	@RequestMapping("deleteBySub")
	@SystemLog(methods = "请求撤销")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject deleteBySub(String detailid) throws Exception {
		if (AppTool.isNull(detailid)) {
			return AppMsg.fail("请求的参数detailid为空！").toAlbbJson();
		}
		FormMap detail = wmsPaperService.getService(this).findDetailByDetailid(detailid);
		if (AppTool.isNull(detail)) {
			return AppMsg.fail("未找到请求ID对应的对象！").toAlbbJson();
		}
		TransferDetailFormMap obj = new TransferDetailFormMap(detail, true);
		if (WmsPaperStatus.NEW.equals(obj.getStatus())) {
			getMapper().deleteByLogic(obj);
		} else {
			ShipmentDetailFormMap shiDetail = shipmentService.findDetailByRelationid(detailid);
			if (AppTool.isNull(shiDetail)) {
				return AppMsg.fail("未找到请求ID对应的出库明细对象！").toAlbbJson();
			}
			shipmentService.deleteBySub(shiDetail);
			getMapper().deleteBySub(obj);
		}
		return AppMsg.success("保存成功").toAlbbJson();
	}
}