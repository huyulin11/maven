package com.kaifantech.util.socket.factory.server;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.socket.netty.server.DefauNettyByteServer;

import io.netty.channel.ChannelHandlerContext;

public class SeerAgvNettyServer extends DefauNettyByteServer {
	public SeerAgvNettyServer(IotClientBean iotClientBean) {
		super(iotClientBean);
		getHeartBeat().setInterval(1000);
	}

	public static SeerAgvNettyServer create(IotClientBean iotClientBean) {
		return new SeerAgvNettyServer(iotClientBean);
	}

	public void dealData(ChannelHandlerContext ctx, String in) {
		String cmdReceived = in;
		if (!AppTool.isNull(cmdReceived)) {
			setCmd(cmdReceived);
			String msgSend = SeerAgvNettyServerUtil.getMsgByCmd(cmdReceived, getAgvId());
			if (!AppTool.isNull(msgSend)) {
				send(ctx, msgSend);
			}
		}
	}

	public String getHeartBeatStr() {
		return SeerAgvNettyServerUtil.getMsgByCmd(null, getAgvId());
	}
}