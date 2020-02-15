package com.kaifantech.component.controller.ctrl.agv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.kaifantech.component.service.status.agv.AgvCtrlInfoService;
import com.kaifantech.component.service.status.agv.AgvStatusService;

@Controller
@RequestMapping("/")
public class AgvStatusController {

	@Autowired
	private AgvStatusService agvStatusService;

	@Autowired
	private AgvCtrlInfoService agvsCtrlInfoService;

	@RequestMapping("agvinfo")
	@ResponseBody
	public JSONObject agvinfo(Integer agvId) {
		return new JSONObject(agvStatusService.agvStatus(agvId));
	}

	@RequestMapping("agvsinfo")
	@ResponseBody
	public JSONObject agvsinfo() {
		return agvsCtrlInfoService.agvsStatus();
	}

}
