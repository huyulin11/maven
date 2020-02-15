package com.calculatedfun.component.controller.sys;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
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
import com.calculatedfun.erp.util.PasswordHelper;
import com.calculatedfun.err.SystemBussException;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.Common;
import com.calculatedfun.util.MD5;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.BaseController;
import com.kaifantech.component.service.user.UserService;
import com.kaifantech.entity.ResUserFormMap;
import com.kaifantech.entity.UserFormMap;
import com.kaifantech.entity.UserGroupsFormMap;
import com.kaifantech.mappings.AppUserMapper;

@Controller
@RequestMapping("/user/")
public class UserController extends BaseController<UserFormMap> {
	@Inject
	private AppUserMapper userMapper;

	@Inject
	private ResService resService;

	@Inject
	private UserService userService;

	private static final String MODULE_NAME = "系统用户管理";

	public UserController() {
	}

	@RequestMapping("list")
	public String listUI(Model model) throws Exception {
		model.addAttribute("res", resService.findByRes());
		return "/system/user/list";
	}

	@ResponseBody
	@RequestMapping("findByPage")
	public PageView findByPage(Integer pageNow, Integer pageSize) throws Exception {
		UserFormMap userFormMap = getFormMap();
		userFormMap = toFormMap(userFormMap, pageNow, pageSize);
		pageView.setRecords(userMapper.findByPage(userFormMap));
		return pageView;
	}

	@RequestMapping("addUI")
	public String addUI(Model model) throws Exception {
		return "/system/user/add";
	}

	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module = MODULE_NAME, methods = METHOD_ADD)
	@Transactional(readOnly = false)
	public JSONObject addEntity(String txtGroupsSelect) {
		try {
			userService.doAddEntity(getFormMap(), txtGroupsSelect);
		} catch (Exception e) {
			throw new SystemBussException("添加账号异常" + e.getMessage());
		}
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_DELETE)
	public JSONObject deleteEntity() throws Exception {
		String[] ids = AppSession.getParaValues("ids");
		for (String id : ids) {
			userMapper.deleteByAttribute("userId", id, UserGroupsFormMap.class);
			userMapper.deleteByAttribute("userId", id, ResUserFormMap.class);
			userMapper.deleteByAttribute("id", id, UserFormMap.class);
		}
		return AppMsg.success().toAlbbJson();
	}

	@RequestMapping("editUI")
	public String editUI(Model model) throws Exception {
		Object operatedUserId = AppSession.getParaValue("id");
		if (Common.isNotEmpty(operatedUserId.toString())) {
			UserFormMap userinfo = userMapper.findbyFrist("id", operatedUserId.toString(), UserFormMap.class);
			model.addAttribute("user", userinfo);
			model.addAttribute("isSelf", "false");
			return "/system/user/edit";
		} else {
			return "/404/404";
		}
	}

	@RequestMapping("selfInfo")
	public String selfInfo(Model model) throws Exception {

		Object currentUserId = SecurityUtils.getSubject().getSession().getAttribute("userSessionId");

		model.addAttribute("user", userMapper.findbyFrist("id", currentUserId.toString(), UserFormMap.class));
		model.addAttribute("isSelf", "true");

		return "/system/user/edit";
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly = false)
	@SystemLog(module = MODULE_NAME, methods = METHOD_EDIT)
	public JSONObject editEntity(String txtGroupsSelect) throws Exception {
		UserFormMap userFormMap = getFormMap();

		Object operatedUserId = userFormMap.get("id");

		String isResetPwd = (String) userFormMap.get("isResetPwd");
		if (!AppTool.isNullStr(isResetPwd) && Integer.parseInt(isResetPwd) % 2 == 0) {
			userFormMap.set("password", userFormMap.get("password").toString());
			userFormMap.set("md5password", MD5.GetMD5Code(userFormMap.get("password").toString()));
			new PasswordHelper().encryptPassword(userFormMap);
		} else {
			userFormMap.remove("password");
		}

		{// if (!currentUserId.toString().equals(operatedUserId.toString())) {
			userFormMap.put("txtGroupsSelect", txtGroupsSelect);
			userMapper.deleteByAttribute("userId", operatedUserId + "", UserGroupsFormMap.class);
			if (!Common.isEmpty(txtGroupsSelect)) {
				String[] txt = txtGroupsSelect.split(",");
				for (String roleId : txt) {
					UserGroupsFormMap userGroupsFormMap = new UserGroupsFormMap();
					userGroupsFormMap.put("userId", operatedUserId);
					userGroupsFormMap.put("roleId", roleId);
					userMapper.addEntity(userGroupsFormMap);
				}
			}
		}
		userMapper.editEntity(userFormMap);

		return AppMsg.success().toAlbbJson();
	}

	/**
	 * 验证账号是否存在
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("isExist")
	@ResponseBody
	public boolean isExist(String name) {
		UserFormMap userFormMap = getFormMap();
		UserFormMap account = null;
		Object accountName = userFormMap.get("accountName");
		Object userName = userFormMap.get("userName");
		if (!AppTool.isNull(accountName)) {
			account = userMapper.findbyFrist("accountName", accountName.toString(), UserFormMap.class);
		} else if (!AppTool.isNull(userName)) {
			account = userMapper.findbyFrist("userName", userName.toString(), UserFormMap.class);
		}
		if (account == null) {
			return true;
		} else {
			return false;
		}
	}
}