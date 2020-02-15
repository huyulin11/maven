package com.kaifantech.component.controller.sysdic;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.form.BaseQueryController;
import com.kaifantech.component.service.sysdic.SysDicService;
import com.kaifantech.entity.SysDicDataFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.SysDicDataMapper;

@Controller
@RequestMapping("/sys/dic/data/")
public class SysDicDataController extends BaseQueryController<SysDicDataFormMap> {
	@Inject
	private SysDicDataMapper mapper;

	@Inject
	private SysDicService service;

	private static final String MODULE_NAME = "字典数据管理";

	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module = MODULE_NAME, methods = METHOD_ADD)
	@Transactional(readOnly = false)
	public JSONObject addEntity() {
		try {
			SysDicDataFormMap sysDicDataFormMap = getFormMap(false);
			if (!AppTool.isNull(sysDicDataFormMap)) {
				service.doAddEntity(sysDicDataFormMap.get("dictype").toString(), sysDicDataFormMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("添加字典值异常，请确认对应种类字典值是否重复！").toAlbbJson();
		}
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_DELETE)
	public JSONObject deleteEntity() throws Exception {
		String[] ids = AppSession.getParaValue("ids").split(":");
		SysDicDataFormMap formMap = getFormMap();
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
		SysDicDataFormMap formMap = getFormMap();
		mapper.editEntity(formMap);
		return AppMsg.success().toAlbbJson();
	}

	@RequestMapping("findByValue")
	@ResponseBody
	public List<?> findByValue() {
		SysDicDataFormMap formMap = getFormMap();
		return mapper.findByNames(formMap);
	}

	@Override
	public AppBaseMapper<SysDicDataFormMap> getMapper() {
		return mapper;
	}

}