package com.kaifantech.component.comm.worker.client;

import java.util.Map;

import com.kaifantech.init.sys.params.AppSysParameters;
import com.kaifantech.util.socket.base.IConnect;

public interface IConnectWorker {
	default IConnect get(Integer keyId) {
		return getMap().get(keyId);
	}

	Map<Integer, IConnect> getMap();

	void init();

	default void startConnect() {
		if (!AppSysParameters.isConnectIotClient()) {
			return;
		}
		init();
	}
}
