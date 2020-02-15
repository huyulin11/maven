package com.kaifantech.util.socket.base;

public class HeartBeat {
	private boolean isSend = true;
	private boolean isHex = false;
	private String str = "";
	private int interval = 2000;

	private HeartBeat() {
	}

	public String getStr() {
		return str;
	}

	public HeartBeat setStr(String str) {
		this.str = str;
		return this;
	}

	public boolean isSend() {
		return isSend;
	}

	public HeartBeat setSend(boolean isSend) {
		this.isSend = isSend;
		return this;
	}

	public boolean isHex() {
		return isHex;
	}

	public HeartBeat setHex(boolean isHex) {
		this.isHex = isHex;
		return this;
	}

	public int getInterval() {
		return interval;
	}

	public HeartBeat setInterval(int interval) {
		this.interval = interval;
		return this;
	}

	public static HeartBeat createOne() {
		return new HeartBeat().setStr("HEARTBEAT").setInterval(2000);
	}
}