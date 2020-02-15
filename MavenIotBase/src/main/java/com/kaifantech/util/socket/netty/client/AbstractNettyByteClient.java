package com.kaifantech.util.socket.netty.client;

import org.springframework.scheduling.annotation.Async;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.socket.client.AbstractNettyByteSocketClient;

import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public abstract class AbstractNettyByteClient extends AbstractNettyByteSocketClient {
	private IotClientBean iotClientBean;
	private Integer devId;
	private String latestMsg = "";
	private String sendedCmd = "";
	private Integer agvId;

	public String getLatestMsg() {
		return latestMsg;
	}

	public void setLatestMsg(String latestMsg) {
		this.latestMsg = latestMsg;
	}

	public String getMsg() {
		return getLatestMsg();
	}

	public String getSendedCmd() {
		return sendedCmd;
	}

	public void setSendedCmd(String sendedCmd) {
		this.sendedCmd = sendedCmd;
	}

	public Integer getAgvId() {
		if (AppTool.isNull(agvId))
			agvId = getIotClientBean().getId();
		return agvId;
	}

	public void setAgvId(Integer agvId) {
		this.agvId = agvId;
	}

	public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}

	public String loginfo(String content) {
		return "AGV编号：" + getAgvId() + "，" + getDevId() + "号设备:" + content;
	}

	public AbstractNettyByteClient(IotClientBean iotClientBean) {
		this.iotClientBean = iotClientBean;
	}

	@Async
	@Override
	public AppMsg sendCmd(String msg) {
		AppMsg appMsg = new AppMsg();
		try {
			send(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
			appMsg.setSuccess(true);
		} catch (Exception e) {
			appMsg.setSuccess(false);
		}
		return appMsg;
	}

	@Async
	@Override
	public boolean sendCmd(byte[] msg) {
		try {
			send(Unpooled.copiedBuffer(msg));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public IotClientBean getIotClientBean() {
		return iotClientBean;
	}

	public void setIotClientBean(IotClientBean iotClientBean) {
		this.iotClientBean = iotClientBean;
	}

}
