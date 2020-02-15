package com.kaifantech.component.timer.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kaifantech.component.comm.worker.client.IConnectWorker;
import com.kaifantech.init.sys.UdfBusinessInfo;
import com.kaifantech.init.sys.BaseBusinessInfo;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.seq.ThreadID;
import com.kaifantech.util.thread.ThreadTool;

@Component
@Lazy(false)
public class SeerIotConnectAsServerTimer {
	private static boolean isRunning = false;
	private static String timerType = "IOT_SERVER设备连接器";

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_SERVER_WORKER)
	private IConnectWorker agvServerWorker;

	@Scheduled(cron = "0/5 * * * * ?")
	public void resolute() {
		if (UdfBusinessInfo.CURRENT_CLIENT.equals(BaseBusinessInfo.Clients.YUFENG)) {
			return;
		}
		if (!isRunning) {
			Thread.currentThread().setName(timerType + ThreadID.num());
			isRunning = true;
			agvSimulate();
		}
		isRunning = false;
	}

	private void agvSimulate() {
		ThreadTool.run(() -> {
			agvServerWorker.startConnect();
		});
	}

}
