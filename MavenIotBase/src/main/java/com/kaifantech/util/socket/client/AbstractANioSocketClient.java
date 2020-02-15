package com.kaifantech.util.socket.client;

import java.net.InetSocketAddress;

import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.thread.ThreadTool;

public class AbstractANioSocketClient extends AbstractSocketClient {
	public AbstractANioSocketClient(IotClientBean iotClientBean) {
		super(iotClientBean);
		serverAddress = new InetSocketAddress(iotClientBean.getIp(), Integer.parseInt(iotClientBean.getPort()));
		ThreadTool.getThreadPool().execute(new Runnable() {
			public void run() {
				try {
					init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private InetSocketAddress serverAddress = null;

	public InetSocketAddress getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(InetSocketAddress serverAddress) {
		this.serverAddress = serverAddress;
	}
}