package com.kaifantech.component.controller.paper;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calculatedfun.annotation.SystemLog;
import com.kaifantech.component.controller.paper.base.PaperMainController;
import com.kaifantech.entity.paper.InventoryDetailFormMap;
import com.kaifantech.entity.paper.InventoryMainFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.inventory.InventoryMainMapper;

@Controller
@RequestMapping("/inventory/main/")
@SystemLog(module = "盘点管理")
public class InventoryMainController extends PaperMainController<InventoryMainFormMap, InventoryDetailFormMap> {
	@Inject
	protected InventoryMainMapper inventoryMainMapper;

	@Override
	public AppBaseMapper<InventoryMainFormMap> getMapper() {
		return inventoryMainMapper;
	}
}