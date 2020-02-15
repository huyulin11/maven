package com.kaifantech.component.controller.immediateTask;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.component.service.erp.system.IBaseService;
import com.kaifantech.component.controller.base.form.BaseADEController;
import com.kaifantech.entity.ImmediateTaskFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.ImmediateTaskMapper;

@Controller
@SystemLog(module = "AGV即时任务管理")
@RequestMapping("/agvImmediateTask/")
public class ImmediateTaskController extends BaseADEController<ImmediateTaskFormMap> {
	@Inject
	private ImmediateTaskMapper mapper;

	@Override
	public AppBaseMapper<ImmediateTaskFormMap> getMapper() {
		return mapper;
	}

	@Override
	public IBaseService<ImmediateTaskFormMap> getService() {
		return null;
	}

}
