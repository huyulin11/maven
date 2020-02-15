package com.kaifantech.component.comm.cmd.sender.agv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.comm.worker.client.IConnectWorker;
import com.kaifantech.component.log.AgvStatusDBLogger;
import com.kaifantech.init.sys.qualifier.CsyCdbpQualifier;
import com.kaifantech.util.log.AppFileLogger;

@Service
public class SeerAgvConfSender implements IAgvCmdSender {
	@Autowired
	private AgvStatusDBLogger dbLogger;

	@Autowired
	@Qualifier(CsyCdbpQualifier.AGV_CLIENT_CONF_WORKER)
	private IConnectWorker agvClientMgr;

	public synchronized AppMsg send(Integer agvId, String... cmds) {
		String finalCmd = String.join("", cmds);
		try {
			AppMsg appMsg = agvClientMgr.get(agvId).sendCmd(finalCmd);
			if (appMsg.isSuccess()) {
				sysout(agvId, finalCmd);
				appMsg.setCode(0);
			}
			return appMsg;
		} catch (Exception e) {
			String msg = "发送指令（" + finalCmd + "）失败：" + e.getMessage();
			AppFileLogger.warning(msg);
			return new AppMsg(-1, msg);
		}
	}

	private void sysout(Integer agvId, String cmd) {
		String msg = "向" + agvId + "号AGV下达命令: " + cmd;
		dbLogger.warning(msg, agvId, AgvStatusDBLogger.MSG_LEVEL_WARNING);
	}

}
