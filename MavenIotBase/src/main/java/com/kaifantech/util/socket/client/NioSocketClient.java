package com.kaifantech.util.socket.client;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.thread.ThreadTool;

public class NioSocketClient extends AbstractANioSocketClient {
	private static Selector selector = null;
	private SocketChannel sc = null;
	private boolean isRunning = false;

	public NioSocketClient(IotClientBean iotClientBean) {
		super(iotClientBean);
	}

	public synchronized void init() {
		try {
			if (selector == null) {
				selector = Selector.open();
			}
			if (sc == null || !sc.isConnected() || !sc.isOpen()) {
				if (sc != null && sc.isOpen()) {
					closeResource();
				}
				sc = SocketChannel.open(getServerAddress());
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);
			}
			if (!isRunning) {
				ThreadTool.getThreadPool().execute(new ClientThread());
			}
			int sllepTimes = 5000;
			ThreadTool.sleep(sllepTimes);
			init();

		} catch (Exception e) {
			setLatestMsg("");
			AppFileLogger.warning("连接初始化失败，稍后重新尝试连接!" + getClientInfo());
			ThreadTool.sleep(2000);
			init();
		}
	}

	public AppMsg sendCmd(String msg) {
		try {
			sc.write(CHARSET.encode(msg));
			return AppMsg.success();
		} catch (Exception e) {
			closeResource();
			return AppMsg.fail();
		}
	}

	public void sendMsgAsy(String msg) {
		ThreadTool.getThreadPool().execute(new Runnable() {
			public void run() {
				sendCmd(msg);
			}
		});
	}

	@Override
	public void closeResource() {
		try {

			setLatestMsg("");
			if (sc != null) {
				sc.close();
				sc = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		isRunning = false;
	}

	private int contentNullTimes = 0;

	private class ClientThread extends Thread {
		@Override
		public void run() {
			try {
				isRunning = true;
				while (true) {
					String content = "";
					ByteBuffer bff = ByteBuffer.allocate(1024);
					if (AppTool.isNull(bff)) {
						continue;
					}
					if (AppTool.isNull(sc)) {
						continue;
					}
					while (sc.read(bff) > 0) {
						bff.flip();
						content += CHARSET.decode(bff);
						AppFileLogger.warning(content);
						bff.clear();
						contentNullTimes = 0;
					}
					if (AppTool.isNull(content)) {
						contentNullTimes++;
						if (contentNullTimes > 10) {
							contentNullTimes = 0;
						}
					} else {
						setLatestMsg(content);
					}
					ThreadTool.sleep(500);
				}

			} catch (Exception io) {
				closeResource();
				io.printStackTrace();
			}
			isRunning = false;
		}
	}
}