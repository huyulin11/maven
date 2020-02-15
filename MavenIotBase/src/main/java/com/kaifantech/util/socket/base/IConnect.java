package com.kaifantech.util.socket.base;

import com.calculatedfun.util.msg.AppMsg;

public interface IConnect {
	default void init() throws Exception {
	}

	default boolean sendCmd(byte[] msg) {
		return false;
	}

	default AppMsg sendCmd(String msg) {
		return null;
	}

	default void closeResource() {
	}

	default String getMsg() {
		return null;
	}

	default void setCmd(String cmd) {
	}

	default String getCmd() {
		return null;
	}
}