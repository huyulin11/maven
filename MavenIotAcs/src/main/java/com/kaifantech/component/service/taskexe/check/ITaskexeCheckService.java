package com.kaifantech.component.service.taskexe.check;

import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.taskexe.TaskexeBean;

public interface ITaskexeCheckService {

	public AppMsg checkTaskexe(Integer agvId);

	default AppMsg checkBeforeAdd(TaskexeBean obj, Integer agvId) {
		return AppMsg.success();
	}
}
