package com.kaifantech.component.comm.manager;

import java.util.Arrays;

import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.thread.ThreadTool;

public interface IRtnManager {
	AppMsg doSend(int devId, String command);

	public boolean isCmdSendWithCmd(int devId, String command, String... targetStatus);

	default AppMsg sendNeedRtn(int devId, String command, String... targetStatus) {
		return sendNeedRtn(devId, command, targetStatus, 3, 5);
	}

	default AppMsg sendNeedRtn(int devId, String command, String[] targetStatus, int repeatTimes, int intervalSeconds) {
		int times = 0;
		AppMsg appMsg = AppMsg.fail();
		while (true) {
			if (repeatTimes > 0 && times >= repeatTimes) {
				return AppMsg.fail();
			}
			appMsg = doSend(devId, command);
			times++;
			if (!appMsg.isSuccess()) {
				return appMsg;
			}
			ThreadTool.sleep(500);
			if (isCmdSendWithCmd(devId, command, targetStatus)) {
				appMsg.setSuccess(true);
				break;
			} else {
				if (times >= 3) {
					AppFileLogger.error("发送指令" + "（" + command + "）" + "到" + devId + "号设备出现问题" + "，重试次数：" + times
							+ "（目标状态：" + Arrays.toString(targetStatus) + "）");
				}
				ThreadTool.sleep(intervalSeconds * 1000);
			}
		}
		return appMsg;
	}

}
