package com.kaifantech.util.lock;

public class WarehouseLockException extends Exception {
	public WarehouseLockException(String lockedPaperid) {
		super(lockedPaperid);
	}

	private static final long serialVersionUID = 1L;
}
