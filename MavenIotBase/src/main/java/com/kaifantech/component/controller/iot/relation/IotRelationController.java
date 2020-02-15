package com.kaifantech.component.controller.iot.relation;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.form.BaseQueryController;
import com.kaifantech.component.service.iot.relation.IotRelationService;
import com.kaifantech.entity.IotRelationFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.IotRelationMapper;

/**
 * 
 * 
 * @Email: mmm333zzz520@163.com
 * @version 3.0v
 */
@Controller
@RequestMapping("/iotinfo/relation/")
public class IotRelationController extends BaseQueryController<IotRelationFormMap> {
	@Inject
	private IotRelationMapper mapper;

	@Inject
	private IotRelationService service;

	private static final String MODULE_NAME = "设备关系管理";

	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module = MODULE_NAME, methods = METHOD_ADD)
	@Transactional(readOnly = false)
	public JSONObject addEntity() {
		try {
			service.doAddEntity(getFormMap());
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("新增异常，请仔细检查数据、网络连接情况及系统登录情况，请检查！").toAlbbJson();
		}
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_DELETE)
	public JSONObject deleteEntity() throws Exception {
		String[] ids = AppSession.getParaValue("ids").split(":");
		IotRelationFormMap formMap = getFormMap();
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
		IotRelationFormMap formMap = getFormMap();
		mapper.editEntity(formMap);

		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("saveAll")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = "批量保存")
	public JSONObject saveAll(String txtGroupsSelect) throws Exception {
		IotRelationFormMap formMap = getFormMap();
		service.saveAll(formMap);

		return AppMsg.success().toAlbbJson();
	}

	@Override
	public AppBaseMapper<IotRelationFormMap> getMapper() {
		return mapper;
	}
}