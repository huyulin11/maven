package com.kaifantech.util.iot;

public enum SeerAgvStatus {
	NONE(0), WAITING(1), RUNNING(2), SUSPENDED(3), COMPLETED(4), FAILED(5), CANCELED(6);
	private final int val;

	private SeerAgvStatus(int val) {
		this.val = val;
	}

	public int get() {
		return val;
	}

	public static String getStatusName(int val) {
		if (0 == val) {
			return "空";
		} else if (1 == val) {
			return "等待中";
		} else if (2 == val) {
			return "运行中";
		} else if (3 == val) {
			return "阻塞中";
		} else if (4 == val) {
			return "已完成";
		} else if (5 == val) {
			return "失败";
		} else if (6 == val) {
			return "已取消";
		}
		return null;
	}
}
