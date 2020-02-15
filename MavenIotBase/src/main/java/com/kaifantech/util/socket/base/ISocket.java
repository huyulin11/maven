package com.kaifantech.util.socket.base;

import java.nio.charset.Charset;
import java.util.Deque;
import java.util.UUID;

import com.kaifantech.bean.iot.client.IotClientBean;

public interface ISocket extends IConnect {

	static Charset CHARSET = Charset.forName("UTF-8");

	default Deque<String> getErrMsgDeque() {
		return null;
	}

	static String str() {
		return UUID.randomUUID().toString().replace("-", "") + '\n';
	}

	IotClientBean getIotClientBean();

	default String getSocketInfo() {
		return "IP:PORT-" + getIotClientBean().getIp() + ":" + getIotClientBean().getPort() + "("
				+ this.getClass().getSimpleName() + ")";
	}

}