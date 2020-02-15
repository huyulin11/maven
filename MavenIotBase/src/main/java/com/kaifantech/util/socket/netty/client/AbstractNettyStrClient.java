package com.kaifantech.util.socket.netty.client;

import org.springframework.scheduling.annotation.Async;

import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.socket.client.AbstractNettyStrSocketClient;

public abstract class AbstractNettyStrClient extends AbstractNettyStrSocketClient {
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

	public AbstractNettyStrClient(IotClientBean iotClientBean ) {
		this.iotClientBean = iotClientBean;
	}

	@Async
	@Override
	public AppMsg sendCmd(String msg) {
		AppMsg appMsg = new AppMsg();
		try {
			send(msg);
			appMsg.setSuccess(true);
		} catch (Exception e) {
			appMsg.setSuccess(false);
		}
		return appMsg;
	}

	public IotClientBean getIotClientBean() {
		return iotClientBean;
	}

	public void setIotClientBean(IotClientBean iotClientBean) {
		this.iotClientBean = iotClientBean;
	}

}
