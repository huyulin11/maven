package com.kaifantech.component.service.alloc.check;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.singletask.SingletaskBean;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.entity.paper.PaperDetailFormMap;
import com.kaifantech.entity.paper.PaperMainFormMap;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

public interface IAllocCheckService {
	default AppMsg checkAllocName(String allocName, String type) {
		return AppMsg.success();
	}

	default <TD extends PaperDetailFormMap, TM extends PaperMainFormMap<TD>> AppMsg checkAlloc(TM obj, Integer agvId) {
		return null;
	}

	default AppMsg checkAllocBeforeAdd(Object obj, Integer agvId) {
		AppMsg msg;

		SingletaskBean singletaskBean = (SingletaskBean) obj;
		if (AgvTaskType.ZUHE_RENWU.equals(singletaskBean.getTasktype())) {
			msg = checkTaskGroup(singletaskBean);
			if (msg.getCode() < 0) {
				return msg;
			}
		}

		if (AppTool.isNull(singletaskBean)) {
			return new AppMsg(-1, "无法找到对应的任务类型！");
		}
		return checkTaskinfo(singletaskBean);
	}

	AppMsg checkTaskGroup(SingletaskBean singletaskBean);

	AppMsg checkTaskinfo(SingletaskBean singletaskBean);

	AppMsg checkAllocInfo(AllocItemInfoBean allocationInfoBean, String taskOpType);

	AppMsg checkAllocLogic(AllocItemInfoBean allocationInfoBean, String taskOpType);
}
