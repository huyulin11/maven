package com.kaifantech.util.socket.factory.client;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.hex.AppByteUtil;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.msg.agv.SeerAgvMsgGetter;
import com.kaifantech.util.socket.netty.client.AbstractNettyByteClient;
import com.kaifantech.util.thread.ThreadTool;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class SeerAgvNettyClient extends AbstractNettyByteClient {
	public static final String MSG_PREFIX = "cmd=position;";
	public static final String MSG_SUFFIX = "?";
	public static final String MSG_SUFFIX2 = "�";

	public SeerAgvNettyClient(IotClientBean iotClientBean) {
		super(iotClientBean);
		getHeartBeat().setSend(false);
	}

	public void dealData(ChannelHandlerContext ctx, ByteBuf in) {
		String agvMsgStr = AppByteUtil.getHexStrFrom(in);
		if (AppTool.isNull(agvMsgStr)) {
			return;
		}
		dealMsg(agvMsgStr);
	}

	public void dealException(ChannelHandlerContext ctx) {
		AppFileLogger.setErrorTips(getAgvId(), "连接中断，将", getAgvId(), "号AGV的连接状态置为未连接！");
		SeerAgvMsgGetter.setConnected(getAgvId(), false);
	}

	public synchronized AppMsg sendCmd(String innerCmd) {
		if (!isConnected()) {
			return AppMsg.fail();
		}
		ThreadTool.sleep(400);
		setSendedCmd(innerCmd);
		boolean isSuccess = super.sendCmd(AppByteUtil.hexToBytes(innerCmd));
		AppMsg appMsg = new AppMsg();
		appMsg.setSuccess(isSuccess);
		appMsg.setMsg(innerCmd);
		return appMsg;
	}

	public String dealMsg(String msg) {
		if (!AppTool.isNullStr(msg)) {
			SeerAgvMsgGetter.setMsg(getAgvId(), msg);
		}
		SeerAgvMsgGetter.setConnected(getAgvId(), true);
		return msg;
	}
}
