package com.kaifantech.component.controller.paper;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calculatedfun.annotation.SystemLog;
import com.kaifantech.component.controller.paper.base.PaperDetailController;
import com.kaifantech.entity.paper.InventoryDetailFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.inventory.InventoryDetailMapper;

@Controller
@RequestMapping("/inventory/detail/")
@SystemLog(module = "盘点明细管理")
public class InventoryDetailController extends PaperDetailController<InventoryDetailFormMap> {
	@Inject
	protected InventoryDetailMapper inventoryDetailMapper;

	public AppBaseMapper<InventoryDetailFormMap> getMapper() {
		return inventoryDetailMapper;
	}
}