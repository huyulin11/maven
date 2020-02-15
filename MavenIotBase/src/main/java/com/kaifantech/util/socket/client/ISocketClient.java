package com.kaifantech.util.socket.client;

import com.kaifantech.util.socket.base.ISocket;

public interface ISocketClient extends ISocket {
	default String getClientInfo() {
		return "客户端:" + getSocketInfo();
	}

	default void setDevId(Integer devId) {
	}

	public String getLatestMsg();

	public void setLatestMsg(String latestMsg);
}