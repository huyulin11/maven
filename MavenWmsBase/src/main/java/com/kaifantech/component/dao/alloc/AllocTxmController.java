package com.kaifantech.component.dao.alloc;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.form.BaseADEController;
import com.kaifantech.component.service.sku.AllocTxmService;
import com.kaifantech.entity.AllocTxmFormMap;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.mappings.AllocTxmMapper;
import com.kaifantech.mappings.AppBaseMapper;

@Controller
@SystemLog(module = "条码管理")
@RequestMapping("/alloc/txm/")
public class AllocTxmController extends BaseADEController<AllocTxmFormMap> {
	@Inject
	private AllocTxmMapper mapper;

	@Inject
	private AllocTxmService allocTxmService;

	@Override
	public AllocTxmFormMap getFormMap() {
		AllocTxmFormMap formMap = super.getFormMap();
		formMap.set(BaseSqlConstants.ADDED_SQL_KEY, "num>0");
		formMap.set(BaseSqlConstants.ADDED_SQL_KEY, "num>0");
		formMap.set(BaseSqlConstants.COL_DELFLAG.toLowerCase(), "0");
		return formMap;
	}

	@ResponseBody
	@RequestMapping("addEntity")
	@Transactional(readOnly = false)
	@SystemLog(methods = "新增")
	public JSONObject addEntity() {
		try {
			FormMap form = getFormMap(false);
			List<FormMap> valueList = form.getList();
			AppMsg msg = allocTxmService.doAddEntity(valueList);
			if (msg.getCode() < 0) {
				return msg.toAlbbJson();
			}
		} catch (Exception e) {
			return AppMsg.fail("添加条形码异常" + e.getMessage()).toAlbbJson();
		}
		return AppMsg.success().toAlbbJson();
	}

	public JSONObject defaultEditEntity(String txtGroupsSelect) throws Exception {
		AllocTxmFormMap formMap = getFormMap();
		mapper.editEntity(formMap);
		return AppMsg.success().toAlbbJson();
	}

	public JSONObject defaultDeleteEntity() throws Exception {
		FormMap form = getFormMap(false);
		getMapper().deleteByLogic(form);
		return AppMsg.success().toAlbbJson();
	}

	public AppBaseMapper<AllocTxmFormMap> getMapper() {
		return mapper;
	}

	@Override
	public IBaseService<AllocTxmFormMap> getService() {
		return null;
	}
}