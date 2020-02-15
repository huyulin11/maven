package com.kaifantech.component.comm.manager;

public interface IDevRtnManager extends IRtnManager {
	default boolean isCmdSendWithCmd(int devId, String command, String... targetStatus) {
		return isCmdSend(devId, targetStatus);
	}

	boolean isCmdSend(int devId, String... targetStatus);
}
