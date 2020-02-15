package com.kaifantech.component.controller.paper;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calculatedfun.annotation.SystemLog;
import com.kaifantech.component.controller.paper.base.PaperMainController;
import com.kaifantech.entity.paper.TransferDetailFormMap;
import com.kaifantech.entity.paper.TransferMainFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.transfer.TransferMainMapper;

@Controller
@RequestMapping("/transfer/main/")
@SystemLog(module = "周转管理")
public class TransferMainController extends PaperMainController<TransferMainFormMap, TransferDetailFormMap> {
	@Inject
	protected TransferMainMapper transferMainMapper;

	@Override
	public AppBaseMapper<TransferMainFormMap> getMapper() {
		return transferMainMapper;
	}
}