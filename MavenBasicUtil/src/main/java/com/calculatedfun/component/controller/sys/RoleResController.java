package com.calculatedfun.component.controller.sys;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.component.service.sys.ResService;
import com.calculatedfun.erp.plugin.PageView;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.Common;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.BaseController;
import com.kaifantech.entity.ResRoleFormMap;
import com.kaifantech.mappings.AppResRoleMapper;

@Controller
@RequestMapping("/roleres/")
public class RoleResController extends BaseController<ResRoleFormMap> {
	@Inject
	private AppResRoleMapper resroleMapper;

	@Inject
	private ResService resService;

	private static final String MODULE_NAME = "系统资源角色关系管理";

	@RequestMapping("list")
	public String listUI(Model model) throws Exception {
		model.addAttribute("res", resService.findByRes());
		return "/system/roleres/list";
	}

	@ResponseBody
	@RequestMapping("findByPage")
	public PageView findByPage(Integer pageNow, Integer pageSize) throws Exception {
		ResRoleFormMap ResRoleFormMap = getFormMap();
		ResRoleFormMap = toFormMap(ResRoleFormMap, pageNow, pageSize);
		pageView.setRecords(resroleMapper.findByPage(ResRoleFormMap));
		return pageView;
	}

	@RequestMapping("addUI")
	public String addUI(Model model) throws Exception {
		return "/system/roleres/add";
	}

	@ResponseBody
	@RequestMapping("addEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_ADD)
	public JSONObject addEntity() throws Exception {
		ResRoleFormMap ResRoleFormMap = getFormMap();
		resroleMapper.addEntity(ResRoleFormMap);
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_DELETE)
	public JSONObject deleteEntity() throws Exception {
		String[] ids = AppSession.getParaValues("ids");
		for (String id : ids) {
			resroleMapper.deleteByAttribute("id", id, ResRoleFormMap.class);
		}
		return AppMsg.success().toAlbbJson();
	}

	@RequestMapping("editUI")
	public String editUI(Model model) throws Exception {
		String id = AppSession.getParaValue("id");
		if (Common.isNotEmpty(id)) {
			model.addAttribute("roleres", resroleMapper.findbyFrist("id", id, ResRoleFormMap.class));
		}
		return "/system/roleres/edit";
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_EDIT)
	public JSONObject editEntity() throws Exception {
		ResRoleFormMap ResRoleFormMap = getFormMap();
		resroleMapper.editEntity(ResRoleFormMap);
		return AppMsg.success().toAlbbJson();
	}

}