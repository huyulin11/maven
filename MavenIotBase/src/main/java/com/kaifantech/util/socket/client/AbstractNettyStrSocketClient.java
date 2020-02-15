package com.kaifantech.util.socket.client;

import java.net.InetSocketAddress;

import org.springframework.scheduling.annotation.Async;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.DateFactory;
import com.kaifantech.init.sys.params.AppSysParameters;
import com.kaifantech.util.hex.AppByteUtil;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.socket.base.HeartBeat;
import com.kaifantech.util.socket.netty.client.InitClientStrHandlerInitializer;
import com.kaifantech.util.thread.ThreadTool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

@Sharable
public abstract class AbstractNettyStrSocketClient extends SimpleChannelInboundHandler<String>
		implements ISocketClient {
	private EventLoopGroup group = null;

	private void doInit() throws Exception {
		try {
			group = new NioEventLoopGroup();
			setRunning(true);
			Thread.currentThread().setName("NETTY" + getClientInfo());
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.remoteAddress(new InetSocketAddress(getIotClientBean().getIp(),
							Integer.parseInt(getIotClientBean().getPort())))
					.option(ChannelOption.SO_KEEPALIVE, true).handler(new InitClientStrHandlerInitializer(this));
			ChannelFuture f = b.connect().sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			setRunning(false);
			log("连接" + DateFactory.getCurrTime() + "-连接失败，稍后重试！" + e.getMessage() + getClientInfo());
		} finally {
			closeResource();
		}
	}

	@Async
	private void loopInit() {
		log("初始化：" + getClientInfo());
		while (true) {
			ThreadTool.sleep(1500);
			if (!isConnected()) {
				log("isConnected" + ":no;isRunning:" + isRunning() + ";" + getClientInfo());
			}
			if (!AppSysParameters.isConnectIotClient()) {
				continue;
			}
			if (isRunning()) {
				continue;
			}
			try {
				doInit();
			} catch (Exception e) {
				setRunning(false);
			}
			if (!isConnected()) {
				log("isConnected" + ":watch;isRunning:" + isRunning() + ";" + getClientInfo());
			}
		}
	}

	@Async
	public void keepAlive() {
		while (true) {
			ThreadTool.sleep(heartBeat.getInterval());
			if (!heartBeat.isSend() || AppTool.isNull(heartBeat.getStr())) {
				continue;
			}
			if (!AppSysParameters.isConnectIotClient()) {
				continue;
			}
			if (heartBeat.isHex()) {
				sendCmd(AppByteUtil.hexToBytes(heartBeat.getStr()));
			} else {
				sendCmd(heartBeat.getStr());
			}
		}
	}

	private HeartBeat heartBeat = HeartBeat.createOne();

	public HeartBeat getHeartBeat() {
		return heartBeat;
	}

	@Async
	public synchronized void init() {
		ThreadTool.run(() -> {
			loopInit();
		});

		ThreadTool.run(() -> {
			keepAlive();
		});
	}

	@Override
	public void closeResource() {
		setRunning(false);
		try {
			if (!AppTool.isNull(group)) {
				group.shutdownGracefully().sync();
				group = null;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean isRunning = false;
	private boolean isConnected = false;

	public boolean isRunning() {
		return isRunning;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	private ChannelHandlerContext ctx;

	public void log(String content) {
		AppFileLogger.connectInfo(loginfo(content));
	}

	public abstract String loginfo(String content);

	public abstract void dealData(ChannelHandlerContext ctx, String in);

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		isConnected = true;
		if (!AppSysParameters.isConnectIotClient()) {
			isRunning = false;
			ctx.close();
		}
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String in) {
		try {
			dealData(ctx, in);
			if (!AppSysParameters.isConnectIotClient()) {
				isRunning = false;
				ctx.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log("socketDevMsg:" + getIotClientBean().getIp() + "-" + getIotClientBean().getPort() + ":解析错误");
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		isRunning = false;
		isConnected = false;
		log("连接异常，稍后重新连接");
		ctx.close();
		cause.printStackTrace();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		isRunning = false;
		isConnected = false;
		log("连接异常，稍后重新连接");
		super.channelInactive(ctx);
		ctx.close();
	}

	public void send(String msg) {
		if (isRunning) {
			ctx.writeAndFlush(msg);
		}
	}

}