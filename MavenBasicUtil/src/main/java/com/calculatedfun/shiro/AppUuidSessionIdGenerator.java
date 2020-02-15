package com.calculatedfun.shiro;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

public class AppUuidSessionIdGenerator implements SessionIdGenerator {
	private static JavaUuidSessionIdGenerator generator = new JavaUuidSessionIdGenerator();

	@Override
	public Serializable generateId(Session session) {
		Serializable uuid = generator.generateId(session);
		System.out.println(uuid);
		return uuid;
	}
}
