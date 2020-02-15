package com.kaifantech.util.socket.client;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import com.calculatedfun.util.DateFactory;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.thread.ThreadTool;

public class AioSocketClient extends AbstractANioSocketClient {

	private AsynchronousSocketChannel sc = null;

	final ByteBuffer buffer = ByteBuffer.allocate(1024);

	public AioSocketClient(IotClientBean iotClientBean) {
		super(iotClientBean);
	}

	private CompletionHandler<Integer, ByteBuffer> readHandler = new CompletionHandler<Integer, ByteBuffer>() {
		@Override
		public void completed(Integer result, ByteBuffer attachment) {
			try {
				if (result < 0) {
					AppFileLogger.warning(getClientInfo() + "------" + "服务端断开连接，重新尝试连接");
					closeAndInit();
				} else if (result == 0) {
					AppFileLogger.warning(getClientInfo() + "------" + "空数据--");
				} else {
					buffer.flip();
					String msg = new String(buffer.array());
					setLatestMsg(msg);
					AppFileLogger.warning(getClientInfo() + "------" + "接受数据：" + getMsg());
					buffer.clear();
					sc.read(buffer, null, readHandler);
				}
			} catch (Exception e) {
				closeAndInit();
			}
		}

		@Override
		public void failed(Throwable exc, ByteBuffer attachment) {
			exc.printStackTrace();
			AppFileLogger.warning(getClientInfo() + "------" + "读取失败" + DateFactory.getCurrTime());
			closeAndInit();
		}
	};

	private CompletionHandler<Void, ? super Object> handler = new CompletionHandler<Void, Object>() {
		@Override
		public void completed(Void result, Object attachment) {
			try {
				sc.read(buffer, null, readHandler);
			} catch (Exception e) {
				closeAndInit();
			}
		}

		@Override
		public void failed(Throwable exc, Object attachment) {
			exc.printStackTrace();
			AppFileLogger.warning(getClientInfo() + "------" + "连接失败" + DateFactory.getCurrTime());
			closeAndInit();
		}
	};

	public synchronized void init() {
		try {
			if (sc == null || !sc.isOpen()) {
				if (sc != null && sc.isOpen()) {
					closeResource();
				}
				sc = AsynchronousSocketChannel.open();
				sc.connect(getServerAddress(), null, handler);
			}
			ThreadTool.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			setLatestMsg("");
			AppFileLogger.warning(getClientInfo() + "------" + "连接初始化失败，稍后重新尝试连接!");
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
		setLatestMsg("");
		closeResource(sc);
	}

	public static void closeResource(AsynchronousSocketChannel targetSc) {
		if (targetSc != null) {
			try {
				targetSc.shutdownInput();
			} catch (Exception e) {
			}
			try {
				targetSc.shutdownOutput();
			} catch (Exception e) {
			}
			try {
				targetSc.close();
			} catch (Exception e) {
			}
			targetSc = null;
		}
	}

	private void closeAndInit() {
		closeResource();
		init();
	}
}