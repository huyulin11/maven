package com.kaifantech.component.controller.singletask.crud;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.err.SystemBussException;
import com.kaifantech.component.controller.base.form.BaseQueryController;
import com.kaifantech.component.service.singletask.crud.SingletaskMgrService;
import com.kaifantech.component.service.singletask.info.SingleTaskInfoService;
import com.kaifantech.entity.SingletaskFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.SingletaskMapper;

/**
 * 
 * 
 * @Email: mmm333zzz520@163.com
 * @version 3.0v
 */
@Controller
@RequestMapping("/agvtasks/")
public class SingletaskController extends BaseQueryController<SingletaskFormMap> {
	@Inject
	private SingletaskMapper mapper;

	@Autowired
	private SingleTaskInfoService singleTaskInfoService;

	@Inject
	private SingletaskMgrService service;

	private static final String MODULE_NAME = "任务管理";

	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module = MODULE_NAME, methods = METHOD_ADD)
	@Transactional(readOnly = false)
	public String addEntity() {
		try {
			service.doAddEntity(getFormMap());
		} catch (Exception e) {
			throw new SystemBussException("添加账单异常");
		}
		return "success";
	}

	@RequestMapping("getSingletask")
	@ResponseBody
	public Object getSingletask(String taskid) {
		return JSONArray.toJSON(singleTaskInfoService.get(taskid));
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_DELETE)
	public String deleteEntity() throws Exception {
		String[] ids = AppSession.getParaValue("ids").split(",");
		SingletaskFormMap formMap = getFormMap();
		for (String id : ids) {
			formMap.set("id", id);
			mapper.deleteByLogic(formMap);
		}
		return "success";
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_EDIT)
	public String editEntity(String txtGroupsSelect) throws Exception {
		SingletaskFormMap formMap = getFormMap();
		mapper.editEntity(formMap);

		return "success";
	}

	@Override
	public AppBaseMapper<SingletaskFormMap> getMapper() {
		return mapper;
	}
}