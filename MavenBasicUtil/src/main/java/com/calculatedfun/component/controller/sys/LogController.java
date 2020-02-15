package com.calculatedfun.component.controller.sys;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.calculatedfun.erp.plugin.PageView;
import com.kaifantech.component.controller.base.BaseController;
import com.kaifantech.entity.LogFormMap;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.mappings.AppLogMapper;

@Controller
@RequestMapping("/log/")
public class LogController extends BaseController<LogFormMap> {
	@Inject
	private AppLogMapper logMapper;

	@RequestMapping("list")
	public String listUI(Model model) throws Exception {
		return "/system/log/list";
	}

	@ResponseBody
	@RequestMapping("findByPage")
	public PageView findByPage(Integer pageNow, Integer pageSize) throws Exception {
		LogFormMap logFormMap = getFormMap(false);
		logFormMap.put(BaseSqlConstants.ORDER_BY_KEY, "opertime desc");
		logFormMap = toFormMap(logFormMap, pageNow, pageSize);
		pageView.setRecords(logMapper.findByPage(logFormMap));
		return pageView;
	}
}