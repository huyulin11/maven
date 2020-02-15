package com.kaifantech.component.service.alloc.status;

import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

public interface IAllocStatusService {
	public AppMsg startStack(AllocItemInfoBean bean);

	public AppMsg releaseFromStack(AllocItemInfoBean bean);

	public AppMsg startWithdraw(AllocItemInfoBean bean);

	public AppMsg releaseFromWithdraw(AllocItemInfoBean bean);

	public AppMsg endStack(AllocItemInfoBean bean);

	public AppMsg endWithdraw(AllocItemInfoBean bean);

	default AppMsg start(AllocItemInfoBean bean, String tasktype) {
		if (AgvTaskType.RECEIPT.equals(tasktype))
			return startStack(bean);
		if (AgvTaskType.SHIPMENT.equals(tasktype))
			return startWithdraw(bean);
		return new AppMsg(0, "无需锁定");
	}

	default AppMsg end(AllocItemInfoBean bean, String tasktype) {
		if (AgvTaskType.RECEIPT.equals(tasktype))
			return endStack(bean);
		if (AgvTaskType.SHIPMENT.equals(tasktype))
			return endWithdraw(bean);
		return new AppMsg(0, "无需锁定");
	}
}
