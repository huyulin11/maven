package com.kaifantech.component.comm.worker.server;

import com.kaifantech.component.comm.worker.client.IConnectWorker;
import com.kaifantech.init.sys.params.AppSysParameters;
import com.kaifantech.util.socket.base.IConnect;

public interface IServerWorker extends IConnectWorker {
	default void startConnect() {
		if (!AppSysParameters.isConnectIotServer()) {
			return;
		}
		init();
		if (!AppSysParameters.isConnectIotServer()) {
			for (IConnect t : getMap().values()) {
				t.closeResource();
			}
			getMap().clear();
		}
	}
}
