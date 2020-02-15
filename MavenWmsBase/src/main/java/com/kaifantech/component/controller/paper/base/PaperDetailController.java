package com.kaifantech.component.controller.paper.base;

import javax.inject.Inject;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.form.BaseQueryController;
import com.kaifantech.component.service.paper.base.PaperService;
import com.kaifantech.entity.paper.PaperDetailFormMap;

public abstract class PaperDetailController<TD extends PaperDetailFormMap> extends BaseQueryController<TD> {
	@Inject
	protected PaperService wmsPaperService;

	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(methods = METHOD_ADD)
	@Transactional(readOnly = false)
	public JSONObject addEntity() {
		try {
			FormMap form = getFormMap(false);
			AppMsg msg = wmsPaperService.getService(this).doAddEntity(form);
			return msg.toAlbbJson();
		} catch (DuplicateKeyException e) {
			return AppMsg.fail("主键重复").toAlbbJson();
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("新增单据异常").toAlbbJson();
		}
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@SystemLog(methods = METHOD_EDIT)
	@Transactional(readOnly = false)
	public JSONObject editEntity(String paperid) {
		try {
			if (AppTool.isNull(paperid)) {
				return AppMsg.fail("无有效的订单编号！").toAlbbJson();
			}
			FormMap form = getFormMap(false);
			AppMsg msg = wmsPaperService.getService(this).doEditEntity(form, paperid);
			return msg.toAlbbJson();
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("修改单据异常：" + e.getMessage()).toAlbbJson();
		}
	}

	@ResponseBody
	@RequestMapping("addItem")
	@SystemLog(methods = "增加明细")
	@Transactional(readOnly = false)
	public JSONObject addItem(String paperid) {
		try {
			if (AppTool.isNull(paperid)) {
				return AppMsg.fail("无有效的订单编号！").toAlbbJson();
			}
			FormMap form = getFormMap(false);
			AppMsg msg = wmsPaperService.getService(this).doAddItem(form, paperid);
			return msg.toAlbbJson();
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("增加明细异常：" + e.getMessage()).toAlbbJson();
		}
	}
}