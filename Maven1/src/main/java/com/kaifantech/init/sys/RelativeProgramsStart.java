package com.kaifantech.init.sys;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.kaifantech.cache.manager.AppConf;
import com.kaifantech.util.os.OSinfo;
import com.kaifantech.util.thread.ThreadTool;

public class RelativeProgramsStart {
	public static void open() {
		ThreadTool.run(() -> {
			try {
				String initNginxPort = AppConf.worker().getOrInit("NGINX_PORT", "" + 180);
				if (!checkIpPort("127.0.0.1", Integer.parseInt(initNginxPort))) {
					if (OSinfo.isWindows()) {
						runNginx();
					}
				}
			} catch (IOException e) {
			}
		});
	}

	public static void runRedis() throws IOException {
		Runtime.getRuntime().exec(SystemInitiator.REDIS_BAT);
	}

	public static void runNginx() throws IOException {
		Runtime.getRuntime().exec(SystemInitiator.NGINX_BAT);
	}

	public static boolean checkIpPort(String host, int port) {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(host, port));
		} catch (IOException e) {
			return false;
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}