package com.kaifantech.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTool {
	private static ExecutorService threadPool = Executors.newCachedThreadPool();// Executors.newFixedThreadPool(128);

	public static void sleep(int mt) {
		try {
			Thread.sleep(mt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void sleepOneSecond() {
		sleep(1000);
	}

	public static ExecutorService getThreadPool() {
		return threadPool;
	}

	public static ExecutorService getThreadPool(int num) {
		return Executors.newFixedThreadPool(num);
	}

	public static void run(Runnable runnable) {
		run(runnable, false);
	}

	public static void run(Runnable runnable, boolean alone) {
		Thread thread = new Thread(runnable);
		if (alone) {
			thread.start();
		} else {
			threadPool.execute(thread);
		}
	}

	public static void setName(String name) {
		Thread.currentThread().setName(name);
	}
}
