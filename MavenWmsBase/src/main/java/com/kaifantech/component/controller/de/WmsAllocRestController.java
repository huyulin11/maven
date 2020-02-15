package com.kaifantech.component.controller.de;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.kaifantech.component.service.alloc.info.IAllocInfoService;
import com.kaifantech.init.sys.qualifier.UdfQualifier;

@Controller
@RequestMapping("/de/acs/wms/")
public class WmsAllocRestController {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_ALLOC_INFO_SERVICE)
	private IAllocInfoService allocInfoService;

	@RequestMapping("getAllocs")
	@ResponseBody
	public Object getAllocs() {
		return JSONArray.toJSON(allocInfoService.getAllocs());
	}
}
