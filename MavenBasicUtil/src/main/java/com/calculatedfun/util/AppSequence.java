package com.calculatedfun.util;

public class AppSequence {
	private int seq = 1;
	private boolean autoIncrement = true;

	public AppSequence() {
	}

	public AppSequence(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public int getNextSeq() {
		if (autoIncrement)
			return seq++;
		else
			return seq;
	}
}