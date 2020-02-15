package com.calculatedfun.component.controller.sys;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.component.service.sys.ResService;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.Common;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.form.BaseQueryController;
import com.kaifantech.entity.RoleFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.AppRoleMapper;

@Controller
@RequestMapping("/role/")
public class RoleController extends BaseQueryController<RoleFormMap> {
	@Inject
	private AppRoleMapper roleMapper;

	@Inject
	private ResService resService;

	private static final String MODULE_NAME = "系统角色管理";

	@RequestMapping("list")
	public String listUI(Model model) throws Exception {
		model.addAttribute("res", resService.findByRes());
		return "/system/role/list";
	}

	@RequestMapping("addUI")
	public String addUI(Model model) throws Exception {
		return "/system/role/add";
	}

	@ResponseBody
	@RequestMapping("addEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_ADD)
	public JSONObject addEntity() throws Exception {
		RoleFormMap roleFormMap = getFormMap();
		roleMapper.addEntity(roleFormMap);
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_DELETE)
	public JSONObject deleteEntity() throws Exception {
		String[] ids = AppSession.getParaValues("ids");
		for (String id : ids) {
			roleMapper.deleteByAttribute("id", id, RoleFormMap.class);
		}
		return AppMsg.success().toAlbbJson();
	}

	@RequestMapping("editUI")
	public String editUI(Model model) throws Exception {
		String id = AppSession.getParaValue("id");
		if (Common.isNotEmpty(id)) {
			model.addAttribute("role", roleMapper.findbyFrist("id", id, RoleFormMap.class));
		}
		return "/system/role/edit";
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_EDIT)
	public JSONObject editEntity() throws Exception {
		RoleFormMap roleFormMap = getFormMap();
		roleMapper.editEntity(roleFormMap);
		return AppMsg.success().toAlbbJson();
	}

	@RequestMapping("selRole")
	public String seletRole(Model model) throws Exception {
		RoleFormMap roleFormMap = getFormMap();
		Object userId = roleFormMap.get("userId");
		List<RoleFormMap> list = new LinkedList<RoleFormMap>();
		if (null != userId) {
			list = roleMapper.seletUserRole(roleFormMap);
			String ugid = "";
			for (RoleFormMap ml : list) {
				ugid += ml.get("id") + ",";
			}
			ugid = AppTool.trimComma(ugid);
			model.addAttribute("txtRoleSelect", ugid);
		}
		model.addAttribute("userRole", list);
		List<RoleFormMap> roles = roleMapper.seletAllUserRole();
		roles.removeAll(list);
		model.addAttribute("role", roles);
		return "/system/user/roleSelect";
	}

	@Override
	public AppBaseMapper<RoleFormMap> getMapper() {
		return roleMapper;
	}

}