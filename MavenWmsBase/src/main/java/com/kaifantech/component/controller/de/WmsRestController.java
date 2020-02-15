package com.kaifantech.component.controller.de;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppSet;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.MsgFromWMS;
import com.kaifantech.component.dao.de.DeRequestDao;
import com.kaifantech.component.service.de.WmsRestfulService;

@Controller
@RequestMapping("/de/acs/wms/")
public class WmsRestController {

	private MsgFromWMS BASIC_MSG = new MsgFromWMS("true", "200", "接口形式初始规范，后续基于最近的接口规范文档做调整！");

	@Inject
	private DeRequestDao dao;

	@Autowired
	private WmsRestfulService wmsRestfulService;

	@RequestMapping("getPapersStatus")
	@ResponseBody
	public Object getPapersStatus(String paperid, HttpServletRequest request, HttpServletResponse response) {
		dao.add(AppSession.getIp(request), "getPapersStatus", "空");
		Map<String, Object> map = AppSet.bean2Map(wmsRestfulService.getPapersStatus(paperid));
		if (!AppTool.isNull(map)) {
			map.remove("receipttype");
			map.remove("company");
			map.put("wicket", map.remove("name"));
		}
		String jsonStr = JSONArray.toJSONString(map);
		jsonStr = jsonStr.replaceAll("\"", "'");
		return jsonStr;
	}

	@RequestMapping("addWarehouse")
	@ResponseBody
	public JSONObject addWarehouse(@RequestBody JSONObject requestJson, HttpServletRequest request,
			HttpServletResponse response) {
		int i = dao.add(AppSession.getIp(request), "addWarehouse", requestJson);
		return BASIC_MSG.newMsg("档案库信息同步接口，参数为json对象或json队列，重复者覆盖更新。" + i).toAlbbJson();
	}

	@RequestMapping("addArchivalFrame")
	@ResponseBody
	public JSONObject addArchivalFrame(@RequestBody JSONObject requestJson, HttpServletRequest request,
			HttpServletResponse response) {
		int i = dao.add(AppSession.getIp(request), "addArchivalFrame", requestJson);
		return BASIC_MSG.newMsg("档案架信息同步接口，参数为json对象或json队列，重复者覆盖更新。" + i).toAlbbJson();
	}

	@RequestMapping("addCacheCable")
	@ResponseBody
	public JSONObject addCacheCable(@RequestBody JSONObject requestJson, HttpServletRequest request,
			HttpServletResponse response) {
		int i = dao.add(AppSession.getIp(request), "addCacheCable", requestJson);
		return BASIC_MSG.newMsg("缓存柜信息同步接口，参数为json对象或json队列，重复者覆盖更新。" + i).toAlbbJson();
	}

	@RequestMapping("addAllocItem")
	@ResponseBody
	public JSONObject addAllocItem(@RequestBody JSONObject requestJson, HttpServletRequest request,
			HttpServletResponse response) {
		int i = dao.add(AppSession.getIp(request), "addAllocItem", requestJson);
		return BASIC_MSG.newMsg("货位信息同步接口，参数为json对象或json队列，重复者覆盖更新。" + i).toAlbbJson();
	}

	@RequestMapping("addReceipt")
	@ResponseBody
	public JSONObject addReceipt(@RequestBody JSONObject requestJson, HttpServletRequest request,
			HttpServletResponse response) {
		int i = dao.add(AppSession.getIp(request), "addReceipt", requestJson);
		return BASIC_MSG.newMsg("入库单信息同步，参数为单个json对象，入库单号不可重复。" + i).toAlbbJson();
	}

	@RequestMapping("addShipment")
	@ResponseBody
	public JSONObject addShipment(@RequestBody JSONObject requestJson, HttpServletRequest request,
			HttpServletResponse response) {
		int i = dao.add(AppSession.getIp(request), "addShipment", requestJson);
		return BASIC_MSG.newMsg("出库单信息同步，参数为单个json对象，入库单号不可重复。" + i).toAlbbJson();
	}

}
