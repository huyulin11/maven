package com.kaifantech.component.service.taskexe.module;

import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.seq.ThreadID;
import com.kaifantech.util.thread.ThreadTool;

public interface ITaskexeModule {
	public void startControl(IotClientBean agvBean);

	default void loopControl(IotClientBean agvBean) {
		ThreadTool.run(() -> {
			Thread.currentThread().setName("任务处理定时器(AGV:" + agvBean.getId() + ")" + "衍生进程" + ThreadID.num());
			while (true) {
				ThreadTool.sleepOneSecond();
				startControl(agvBean);
			}
		});
	}
}
