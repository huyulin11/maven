package com.kaifantech.util.socket.server.general;

import com.kaifantech.bean.iot.client.IotClientBean;

public abstract class AbstractServer extends Thread implements ISocketServer {
	public int jStart = 0;
	public int j = jStart;
	boolean started = false;
	private IotClientBean iotClientBean;
	private String cmd = "";
	private String msgReceived = "";

	public AbstractServer(IotClientBean iotClientBean) {
		this.setIotClientBean(iotClientBean);
	}

	public void init() throws Exception {
	}

	public String getCmd() {
		return cmd;
	}

	public void closeResource() {

	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public IotClientBean getIotClientBean() {
		return iotClientBean;
	}

	public void setIotClientBean(IotClientBean iotClientBean) {
		this.iotClientBean = iotClientBean;
	}

	public String getMsg() {
		return msgReceived;
	}

	public void setMsg(String msg) {
		this.msgReceived = msg;
	}

}