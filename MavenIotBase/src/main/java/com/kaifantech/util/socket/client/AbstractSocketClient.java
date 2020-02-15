package com.kaifantech.util.socket.client;

import java.util.List;

import com.kaifantech.bean.iot.client.IotClientBean;

public abstract class AbstractSocketClient implements ISocketClient {
	private Integer devId;
	private String latestMsg = "";
	private String sendedCmd = "";
	private IotClientBean iotClientBean;

	public AbstractSocketClient(IotClientBean iotClientBean) {
	}

	public List<Integer> getLatestMsgList() {
		return null;
	}

	@Override
	public IotClientBean getIotClientBean() {
		return iotClientBean;
	}

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

	public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}

}