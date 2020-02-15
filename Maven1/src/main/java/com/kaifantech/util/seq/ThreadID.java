package com.kaifantech.util.seq;

public class ThreadID {
	private static long number = 0;

	public synchronized static long num() {
		return number++;
	}
}
