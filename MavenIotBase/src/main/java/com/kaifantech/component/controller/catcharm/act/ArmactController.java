package com.kaifantech.component.controller.catcharm.act;

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
import com.kaifantech.component.service.iot.armact.ArmactService;
import com.kaifantech.entity.ArmactFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.ArmactMapper;

@Controller
@RequestMapping("/armact/")
public class ArmactController extends BaseQueryController<ArmactFormMap> {
	@Inject
	private ArmactMapper mapper;

	@Inject
	private ArmactService service;

	private static final String MODULE_NAME = "AGV上机械手动作管理";

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
		ArmactFormMap formMap = getFormMap();
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
		ArmactFormMap formMap = getFormMap();
		mapper.editEntity(formMap);
		return AppMsg.success().toAlbbJson();
	}

	@Override
	public AppBaseMapper<ArmactFormMap> getMapper() {
		return mapper;
	}
}