package com.kaifantech.component.service.taskexe.add;

import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.taskexe.TaskexeBean;

/***
 * 描述任务从用户下达到发送AGV执行前的逻辑
 ***/
public interface ITaskexeAddService {
	public AppMsg addTask(Object obj);

	default void cancel(TaskexeBean taskexeBean, boolean flag) throws Exception {
	}

	default void goonFromErr(TaskexeBean taskexeBean) throws Exception {
	}

	default void rePath(TaskexeBean taskexeBean) throws Exception {
	}
}
