package com.kaifantech.component.controller.iot.client;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.erp.plugin.PageView;
import com.kaifantech.component.controller.base.form.BaseADEController;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.entity.IotClientFormMap;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.IotClientMapper;

@Controller
@RequestMapping("/iotinfo/socketdev/")
public class IotClientController extends BaseADEController<IotClientFormMap> {
	@Inject
	private IotClientMapper mapper;

	@Inject
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	private IIotClientService service;

	public static final String MODULE_NAME = "SOCKET设备管理";

	@ResponseBody
	@RequestMapping("findAllPage")
	public PageView findAllPage(String pageNow, String pageSize) throws Exception {
		IotClientFormMap formMap = getFormMap();
		formMap = toFormMap(formMap, 1, 10000);
		pageView.setRecords(mapper.find(formMap));
		return pageView;
	}

	@Override
	public AppBaseMapper<IotClientFormMap> getMapper() {
		return mapper;
	}

	@Override
	public IBaseService<IotClientFormMap> getService() {
		return service;
	}

}