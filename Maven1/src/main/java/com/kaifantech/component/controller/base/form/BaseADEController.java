package com.kaifantech.component.controller.base.form;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.IQueryController;
import com.kaifantech.component.controller.base.IUpdateController;

public abstract class BaseADEController<TT extends FormMap> extends BaseQueryController<TT>
		implements IQueryController<TT>, IUpdateController<TT> {
	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(methods = METHOD_ADD)
	public JSONObject addEntity() {
		try {
			return defaultAddEntity();
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("新增失败，请检查数据是否与已有数据重复").toAlbbJson();
		}
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	@SystemLog(methods = METHOD_DELETE)
	public JSONObject deleteEntity() {
		try {
			return defaultDeleteEntity();
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("删除失败").toAlbbJson();
		}
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly = false)
	@SystemLog(methods = METHOD_EDIT)
	public JSONObject editEntity() {
		try {
			return defaultEditEntity();
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("修改失败").toAlbbJson();
		}
	}
}