package com.calculatedfun.component.controller.sys;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.calculatedfun.erp.plugin.PageView;
import com.kaifantech.component.controller.base.BaseController;
import com.kaifantech.entity.UserLoginFormMap;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.mappings.AppUserLoginMapper;

@Controller
@RequestMapping("/userlogin/")
public class UserLoginController extends BaseController<UserLoginFormMap> {
	@Inject
	private AppUserLoginMapper userLoginErpMapper;

	@RequestMapping("listUI")
	public String listUI(Model model) throws Exception {
		return "/system/userlogin/list";
	}

	@ResponseBody
	@RequestMapping("findByPage")
	public PageView findByPage(Integer pageNow, Integer pageSize) throws Exception {
		UserLoginFormMap userLoginFormMap = getFormMap();
		String order = " id desc";
		userLoginFormMap.put(BaseSqlConstants.ORDER_BY_KEY, order);
		userLoginFormMap = toFormMap(userLoginFormMap, pageNow, pageSize);
		pageView.setRecords(userLoginErpMapper.findByPage(userLoginFormMap));
		return pageView;
	}

}