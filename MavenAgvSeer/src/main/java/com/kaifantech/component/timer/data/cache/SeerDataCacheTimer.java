package com.kaifantech.component.timer.data.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.component.comm.manager.agv.SeerAgvManager;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.component.service.taskexe.module.ITaskexeModule;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.seq.ThreadID;
import com.kaifantech.util.thread.ThreadTool;

@Component
@Lazy(false)
public class SeerDataCacheTimer extends AcsDataCacheTimer {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	private IIotClientService iotClientService;
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_TASKEXE_MODULE)
	private ITaskexeModule taskexeModule;
	@Autowired
	private SeerAgvManager agvManager;

	public SeerDataCacheTimer() {
		super();
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		super.onApplicationEvent(contextRefreshedEvent);
		try {
			ThreadTool.run(() -> {
				keepSearch();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void keepSearch() {
		for (IotClientBean client : iotClientService.getAgvCacheList()) {
			ThreadTool.run(() -> {
				ThreadTool.setName("查询指令定时发送器(AGV:" + client.getId() + ")" + "衍生进程" + ThreadID.num());
				while (true) {
					try {
						agvManager.generalSearch(client.getId());
						ThreadTool.sleep(2000);
					} catch (Exception e) {
					}
				}
			});
		}
	}
}
