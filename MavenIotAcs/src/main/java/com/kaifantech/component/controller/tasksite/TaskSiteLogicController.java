package com.kaifantech.component.controller.tasksite;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.erp.plugin.PageView;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.form.BaseQueryController;
import com.kaifantech.component.service.tasksite.TaskSiteLogicService;
import com.kaifantech.entity.TaskSiteLogicFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.TaskSiteLogicMapper;

/**
 * 
 * 
 * @Email: mmm333zzz520@163.com
 * @version 3.0v
 */
@Controller
@RequestMapping("/tasksitelogic/")
public class TaskSiteLogicController extends BaseQueryController<TaskSiteLogicFormMap> {
	@Inject
	private TaskSiteLogicMapper mapper;

	@Inject
	private TaskSiteLogicService service;

	private static final String MODULE_NAME = "站点管理";

	@ResponseBody
	@RequestMapping("findAllPage")
	public PageView findAllPage() throws Exception {
		return defaultFindByPage(1, 100000, false);
	}

	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module = MODULE_NAME, methods = METHOD_ADD)
	@Transactional(readOnly = false)
	public JSONObject addEntity() {
		try {
			service.doAddEntity(getFormMap());
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("新增异常，名称与编码均不能与其它数据重复，请检查！").toAlbbJson();
		}
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_DELETE)
	public JSONObject deleteEntity() throws Exception {
		String[] ids = AppSession.getParaValue("ids").split(":");
		TaskSiteLogicFormMap formMap = getFormMap();
		for (String id : ids) {
			formMap.set("id", id);
			mapper.deleteByLogic(formMap);
		}
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_EDIT)
	public JSONObject editEntity(String txtGroupsSelect) throws Exception {
		TaskSiteLogicFormMap formMap = getFormMap();
		mapper.editEntity(formMap);

		return AppMsg.success().toAlbbJson();
	}

	@Override
	public AppBaseMapper<TaskSiteLogicFormMap> getMapper() {
		return mapper;
	}
}