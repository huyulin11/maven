package com.kaifantech.component.controller.user.src;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.util.AppTool;
import com.kaifantech.component.controller.base.form.BaseQueryController;
import com.kaifantech.entity.UserSrcFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.UserSrcMapper;

@Controller
@SystemLog(module = "用户源管理")
@RequestMapping("/user/src/")
public class UserSrcController extends BaseQueryController<UserSrcFormMap> {
	@Inject
	private UserSrcMapper mapper;

	public UserSrcFormMap getFormMap() {
		UserSrcFormMap form = getFormMap(false);
		Object userName = form.get("userName");
		if (!AppTool.isNull(userName)) {
			form.set("userName", userName + "%");
		}
		return form;
	}

	@Override
	public AppBaseMapper<UserSrcFormMap> getMapper() {
		return mapper;
	}
}