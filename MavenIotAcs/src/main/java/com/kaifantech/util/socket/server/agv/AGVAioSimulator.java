package com.kaifantech.util.socket.server.agv;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.socket.server.general.AbstractServer;
import com.kaifantech.util.thread.ThreadTool;

public class AGVAioSimulator extends AbstractServer {
	static int BUFFER_SIZE = 1024;

	AsynchronousServerSocketChannel serverChannel;

	// private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
	private ByteBuffer writeBuffer = ByteBuffer.allocate(BUFFER_SIZE);

	// ReadHandler readHandler = new ReadHandler();
	WriteHandler writeHandler = new WriteHandler();

	// private boolean isWriting = false;

	public AGVAioSimulator(IotClientBean iotClientBean) {
		super(iotClientBean);
		try {
			this.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() throws Exception {
		this.serverChannel = AsynchronousServerSocketChannel.open()
				.bind(new InetSocketAddress(Integer.parseInt(getIotClientBean().getPort())), 100);
		this.serverChannel.accept(this, new AcceptHandler());

		ThreadTool.getThreadPool().execute(new Runnable() {
			public void run() {
				AppFileLogger.warning("运行中...");
				while (true) {
					ThreadTool.sleep(2000);
				}
			}
		});

	}

	private class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AGVAioSimulator> {
		@Override
		public void completed(final AsynchronousSocketChannel client, AGVAioSimulator attachment) {
			try {
				AppFileLogger.warning("远程地址：" + client.getRemoteAddress());
				client.setOption(StandardSocketOptions.TCP_NODELAY, true);
				client.setOption(StandardSocketOptions.SO_SNDBUF, 1024);
				client.setOption(StandardSocketOptions.SO_RCVBUF, 1024);

				if (client.isOpen()) {
					writeBuffer.flip();
					AppFileLogger.warning("client.isOpen：" + client.getRemoteAddress());
					AppFileLogger.warning(",msg:" + new String(writeBuffer.array()));
					writeBuffer.clear();
					client.write(writeBuffer, client, writeHandler);
					ThreadTool.sleep(100);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				attachment.serverChannel.accept(attachment, this);
			}
		}

		@Override
		public void failed(Throwable exc, AGVAioSimulator attachment) {
			exc.printStackTrace();
			attachment.serverChannel.accept(attachment, this);// 监听新的请求，递归调用。
		}
	}

	private class WriteHandler implements CompletionHandler<Integer, AsynchronousSocketChannel> {
		@Override
		public void completed(Integer result, AsynchronousSocketChannel client) {
			try {
				writeBuffer.flip();
				writeBuffer = ByteBuffer.wrap(getCmd().getBytes());
				writeBuffer.clear();
				client.write(writeBuffer, client, writeHandler);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void failed(Throwable exc, AsynchronousSocketChannel client) {
			exc.printStackTrace();
			AGVAioSimulator.close(client);
		}
	}

	private static void close(AsynchronousSocketChannel client) {
		try {
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}