package com.kaifantech.component.controller.paper;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calculatedfun.annotation.SystemLog;
import com.kaifantech.component.controller.paper.base.PaperDetailController;
import com.kaifantech.entity.paper.ReceiptDetailFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.receipt.ReceiptDetailMapper;

@Controller
@SystemLog(module = "入库明细管理")
@RequestMapping("/receipt/detail/")
public class ReceiptDetailController extends PaperDetailController<ReceiptDetailFormMap> {
	@Inject
	protected ReceiptDetailMapper receiptDetailMapper;

	@Override
	public AppBaseMapper<ReceiptDetailFormMap> getMapper() {
		return receiptDetailMapper;
	}
}