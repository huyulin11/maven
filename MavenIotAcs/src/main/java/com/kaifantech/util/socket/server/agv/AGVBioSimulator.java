package com.kaifantech.util.socket.server.agv;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.socket.server.general.AbstractServer;
import com.kaifantech.util.thread.ThreadTool;

public class AGVBioSimulator extends AbstractServer {
	ServerSocket ss = null;
	public TransDealer transDealer;
	boolean started = false;
	Socket s;

	public AGVBioSimulator(IotClientBean iotClientBean) {
		super(iotClientBean);
	}

	@Override
	public void start() {
		try {
			ss = new ServerSocket(Integer.parseInt(getIotClientBean().getPort()));
			started = true;
			AppFileLogger.warning("端口已开启,占用" + getIotClientBean().getPort() + "端口号....");
		} catch (BindException e) {
			AppFileLogger.warning("端口使用中....");
			AppFileLogger.warning("请关掉相关程序并重新运行服务器！");
		} catch (IOException e) {
			e.printStackTrace();
		}

		ThreadTool.getThreadPool().execute(new Runnable() {
			public void run() {
				startListen();
			}
		});

	}

	private void startListen() {
		try {
			while (started) {
				if (s == null || !s.isConnected() || s.isClosed()) {
					s = ss.accept();
					transDealer = new TransDealer(s);
					AppFileLogger.warning("a client connected!");

					ThreadTool.getThreadPool().execute(transDealer);
					ThreadTool.sleep(1000);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
				AppFileLogger.warning();
				AppFileLogger.warning("重新开始监听！");
				startListen();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getCmd() {
		return transDealer.getStrReadFromC();
	}

	public void setMsgTobeSend(String strToSend) {
		if (AppTool.isNull(transDealer)) {
			return;
		}
		transDealer.setStrToSend(strToSend);
	}
}