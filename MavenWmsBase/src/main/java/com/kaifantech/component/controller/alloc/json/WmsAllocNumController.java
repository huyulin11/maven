package com.kaifantech.component.controller.alloc.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaifantech.component.service.alloc.amount.IAllocAmountService;
import com.kaifantech.init.sys.qualifier.UdfQualifier;

@Controller
@RequestMapping("/json/wms/")
public class WmsAllocNumController {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_ALLOC_AMOUNT_SERVICE)
	private IAllocAmountService allocOpService;

	@RequestMapping("doChangeNum")
	@ResponseBody
	public String doChangeNum(Integer allocItemId, Integer skuId, Integer num) {
		return allocOpService.changeAllocNum(allocItemId, skuId, num);
	}
}
