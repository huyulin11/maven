package com.kaifantech.util.observable;

public class ClientMsgObservable extends java.util.Observable {
	public ClientMsgObservable() {
	}

	public void msg(String msg) {
		setChanged();
		notifyObservers(msg);
	}
}