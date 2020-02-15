package com.kaifantech.component.controller.ctrl.agv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.kaifantech.component.dao.ControlInfoDao;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.component.service.lap.LapAgvInfoService;
import com.kaifantech.component.service.status.agv.AgvCtrlInfoService;
import com.kaifantech.init.sys.qualifier.UdfQualifier;

@Controller
@RequestMapping("/")
public class AgvsCtrlController {

	@Autowired
	private AgvCtrlInfoService agvsCtrlInfoService;

	@Autowired
	private LapAgvInfoService lapInfoService;

	@Autowired
	private ControlInfoDao controlInfoDao;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	private IIotClientService iotClientService;

	@RequestMapping("initAgvInfo")
	@ResponseBody
	public Object initAgvInfo(String task) {
		return "初始化结束";
	}

	@RequestMapping("getControlInfo")
	@ResponseBody
	public Object getControlInfo() {
		return JSONArray.toJSON(controlInfoDao.getControlInfo());
	}

	@RequestMapping("getAGVList")
	@ResponseBody
	public Object getAGVList() {
		return JSONArray.toJSON(iotClientService.getAgvCacheList());
	}

	@RequestMapping("getAGVListSop")
	@ResponseBody
	public Object getAGVListSop() {
		return JSONArray.toJSON(agvsCtrlInfoService.getRtAgvInfoList());
	}

	@RequestMapping("getLapList")
	@ResponseBody
	public Object getLapList() {
		return JSONArray.toJSON(lapInfoService.getAllLapList());
	}
}
