package com.kaifantech.component.controller.de;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.BaseController;
import com.kaifantech.component.service.user.UserService;

@Controller
@RequestMapping("/www/")
public class WwwRestController extends BaseController<FormMap> {
	@Autowired
	private UserService userService;

	private static final String MODULE_NAME = "接口管理";

	@ResponseBody
	@RequestMapping("addTestUser")
	@SystemLog(module = MODULE_NAME, methods = METHOD_ADD)
	@Transactional(readOnly = false)
	public JSONObject addTestUser(String userName, String phoneNum) {
		if (AppTool.isNull(phoneNum) || AppTool.isNull(userName)) {
			return AppMsg.fail("请您输入正确格式的姓名和手机号码！").toAlbbJson();
		}
		if (phoneNum.length() != 11) {
			return AppMsg.fail("请您输入正确格式的11位手机号码！").toAlbbJson();
		}
		try {
			userService.doAddEntity(userName, phoneNum, "201");
		} catch (Exception e) {
			return AppMsg.fail("添加账号失败！请确实该手机号码是否已申请，如有疑问请与客服联系！").toAlbbJson();
		}
		return AppMsg.success("success").toAlbbJson();
	}

}
