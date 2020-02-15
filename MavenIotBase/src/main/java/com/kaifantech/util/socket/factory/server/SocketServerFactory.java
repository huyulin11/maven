package com.kaifantech.util.socket.factory.server;

import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.socket.netty.server.DefauNettyByteServer;

public class SocketServerFactory {
	public static DefauNettyByteServer create(IotClientBean iotClientBean) {
		return new DefauNettyByteServer(iotClientBean);
	}
}
