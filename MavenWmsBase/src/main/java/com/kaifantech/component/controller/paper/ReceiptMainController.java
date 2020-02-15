package com.kaifantech.component.controller.paper;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calculatedfun.annotation.SystemLog;
import com.kaifantech.component.controller.paper.base.PaperMainController;
import com.kaifantech.entity.paper.ReceiptDetailFormMap;
import com.kaifantech.entity.paper.ReceiptMainFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.receipt.ReceiptMainMapper;

@Controller
@SystemLog(module = "入库管理")
@RequestMapping("/receipt/main/")
public class ReceiptMainController extends PaperMainController<ReceiptMainFormMap, ReceiptDetailFormMap> {
	@Inject
	protected ReceiptMainMapper receiptMainMapper;

	@Override
	public AppBaseMapper<ReceiptMainFormMap> getMapper() {
		return receiptMainMapper;
	}
}