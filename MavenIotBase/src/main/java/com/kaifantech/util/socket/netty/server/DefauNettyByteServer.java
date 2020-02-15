package com.kaifantech.util.socket.netty.server;

import java.net.InetSocketAddress;

import org.springframework.scheduling.annotation.Async;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.cache.manager.AppCache;
import com.kaifantech.init.sys.params.CacheKeys;
import com.kaifantech.init.sys.params.AppSysParameters;
import com.kaifantech.util.hex.AppByteUtil;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.socket.base.HeartBeat;
import com.kaifantech.util.socket.server.general.AbstractServer;
import com.kaifantech.util.thread.ThreadTool;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

public class DefauNettyByteServer extends AbstractServer {
	private EventLoopGroup group;
	private boolean isRunning = false;
	private boolean isPause = false;
	private Integer agvId;

	private HeartBeat heartBeat = HeartBeat.createOne();

	public HeartBeat getHeartBeat() {
		return heartBeat;
	}

	public EventLoopGroup getGroup() {
		return group;
	}

	public void setGroup(EventLoopGroup group) {
		this.group = group;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public DefauNettyByteServer(IotClientBean iotClientBean) {
		super(iotClientBean);
	}

	public void closeResource() {
		try {
			group.shutdownGracefully().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Async
	private synchronized void loopInit() {
		while (true) {
			ThreadTool.sleep(1500);
			if (!AppSysParameters.isConnectIotServer()) {
				continue;
			}
			if (isRunning) {
				continue;
			}
			try {
				doInit();
			} catch (Exception e) {
				isRunning = false;
			}
		}
	}

	public void send(ChannelHandlerContext ctx, String hexString) {
		byte[] bytes = AppByteUtil.hexToBytes(hexString);
		if (!(bytes == null)) {
			ctx.writeAndFlush(Unpooled.copiedBuffer(bytes));
		}
		ThreadTool.sleep(100);
	}

	public void sendMsg(ChannelHandlerContext ctx, String msg) {
		if (!AppTool.isNull(msg)) {
			byte[] req = msg.getBytes();
			ByteBuf firstMessage = Unpooled.buffer(req.length);
			ctx.writeAndFlush(firstMessage);
		}
		ThreadTool.sleep(100);
	}

	private void doInit() throws Exception {
		try {
			AppFileLogger.warning("初始化：" + getServerInfo());
			isRunning = true;
			final NettySocketServerHandler serverHandler = new NettySocketServerHandler();
			group = new NioEventLoopGroup();
			ServerBootstrap b = new ServerBootstrap();
			b.group(group).channel(NioServerSocketChannel.class)
					.localAddress(new InetSocketAddress(Integer.parseInt(getIotClientBean().getPort())))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(serverHandler);
						}
					});
			ChannelFuture f = b.bind().sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			AppFileLogger.warning(e.getMessage() + getServerInfo());
		} finally {
			group.shutdownGracefully().sync();
			isRunning = false;
		}
	}

	public void init() throws Exception {
		ThreadTool.run(() -> {
			loopInit();
		});
	}

	public void keepAlive(ChannelHandlerContext ctx) {
		ThreadTool.run(() -> {
			while (true) {
				ThreadTool.sleep(1000);
				if (!AppSysParameters.isConnectIotServer()) {
					ctx.close();
					isRunning = false;
					break;
				}
				if (!getHeartBeat().isSend()) {
					break;
				}
				try {
					ctx.writeAndFlush(Unpooled.copiedBuffer(getHeartBeat().getStr(), CharsetUtil.UTF_8));
				} catch (Exception e) {
					closeResource();
					isRunning = false;
					try {
						init();
					} catch (Exception ee) {
						ee.printStackTrace();
					}
				}
			}
		});
	}

	private int cacheSeq = 1;

	public synchronized void dealData(ChannelHandlerContext ctx, ByteBuf in) {
		String s = in.toString(CharsetUtil.UTF_8);
		AppCache.worker().hset(CacheKeys.YUFENG_SOCKET_MSG, "" + cacheSeq++, s);
		setMsg(s);
		ctx.writeAndFlush(Unpooled.copiedBuffer(getHeartBeat().getStr(), CharsetUtil.UTF_8));
	}

	@Sharable
	class NettySocketServerHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			ByteBuf in = (ByteBuf) msg;
			in.toString(CharsetUtil.UTF_8);
			dealData(ctx, in);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			cause.printStackTrace();
			ctx.close();
			isRunning = false;
		}

		/*
		 * 建立连接时，返回消息
		 */
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			super.channelActive(ctx);
			keepAlive(ctx);
		}
	}

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

	public Integer getAgvId() {
		if (AppTool.isNull(agvId))
			agvId = getIotClientBean().getId();
		return agvId;
	}

	public void setAgvId(Integer agvId) {
		this.agvId = agvId;
	}

}