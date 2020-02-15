package com.kaifantech.component.controller.de;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.taskexe.AgvStatusBean;
import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.component.controller.base.BaseController;
import com.kaifantech.component.dao.de.DeRequestDao;
import com.kaifantech.component.service.de.AcsRestfulService;
import com.kaifantech.component.service.status.agv.AgvStatusService;
import com.kaifantech.component.service.taskexe.add.ITaskexeAddService;
import com.kaifantech.init.sys.qualifier.UdfQualifier;

@Controller
@RequestMapping("/de/acs/")
public class AcsRestController extends BaseController<FormMap> {

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_TASKEXE_ADD_SERVICE)
	private ITaskexeAddService taskexeService;

	@Autowired
	private AgvStatusService agvStatusService;

	@Autowired
	private AcsRestfulService acsRestfulService;

	@Inject
	private DeRequestDao dao;

	@RequestMapping("getAgvStatus")
	@ResponseBody
	public Object getAgvStatus(HttpServletRequest request, HttpServletResponse response) {
		dao.add(AppSession.getIp(request), "getAgvStatus", "空");
		Object obj = acsRestfulService.getAgvStatusTest();
		String jsonStr = JSONArray.toJSONString(obj);
		jsonStr = jsonStr.replaceAll("\"", "'");
		return jsonStr;
	}

	@RequestMapping("addTask")
	@ResponseBody
	public JSONObject addTask(String tasktype, Integer agvId) {
		AppMsg msg = taskexeService.addTask(new TaskexeBean(tasktype, agvId, 0));
		return msg.toAlbbJson();
	}

	@RequestMapping("addCtrlTask")
	@ResponseBody
	public JSONObject addCtrlTask(String tasktype, Integer agvId) {
		AppMsg msg = agvStatusService.addStatus(new AgvStatusBean(tasktype, agvId, 0));
		return msg.toAlbbJson();
	}
}
