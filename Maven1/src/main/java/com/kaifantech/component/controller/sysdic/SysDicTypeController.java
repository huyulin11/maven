package com.kaifantech.component.controller.sysdic;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.err.SystemBussException;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.form.BaseQueryController;
import com.kaifantech.component.service.sysdic.SysDicTypeService;
import com.kaifantech.entity.SysDicTypeFormMap;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.SysDicTypeMapper;

@Controller
@RequestMapping("/sys/dic/type/")
public class SysDicTypeController extends BaseQueryController<SysDicTypeFormMap> {
	@Inject
	private SysDicTypeMapper mapper;

	@Inject
	private SysDicTypeService service;

	private static final String MODULE_NAME = "字典类型管理";

	public SysDicTypeFormMap getFormMap() {
		SysDicTypeFormMap form = getFormMap(false);
		form.set(BaseSqlConstants.ORDER_BY_KEY, "dictype");
		form.set(BaseSqlConstants.COL_DELFLAG, "0");
		return form;
	}

	@ResponseBody
	@RequestMapping("addEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_ADD)
	public JSONObject addEntity() {
		try {
			SysDicTypeFormMap sysDicTypeFormMap = getFormMap(false);
			if (AppTool.isNull(sysDicTypeFormMap)) {
				return AppMsg.fail("提交保存的数据为空！").toAlbbJson();
			}
			List<FormMap> items = sysDicTypeFormMap.getList();
			if (!AppTool.isNull(items)) {
				sysDicTypeFormMap.put("items", JSONArray.toJSON(items));
			}
			sysDicTypeFormMap.remove(BaseSqlConstants.LIST);
			sysDicTypeFormMap.setJson(JSONObject.toJSONString(sysDicTypeFormMap));
			service.doAddEntity(sysDicTypeFormMap);
		} catch (Exception e) {
			throw new SystemBussException("添加字典类型异常");
		}
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_DELETE)
	public JSONObject deleteEntity() throws Exception {
		String[] ids = AppSession.getParaValue("ids").split(":");
		SysDicTypeFormMap formMap = getFormMap();
		for (String id : ids) {
			formMap.set("dictype", id);
			mapper.deleteByLogic(formMap);
		}
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly = false)
	public JSONObject editEntity(String txtGroupsSelect) throws Exception {
		SysDicTypeFormMap formMap = getFormMap();
		mapper.editEntity(formMap);
		return AppMsg.success().toAlbbJson();
	}

	@Override
	public AppBaseMapper<SysDicTypeFormMap> getMapper() {
		return mapper;
	}

}