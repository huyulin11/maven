package com.calculatedfun.shiro;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import com.calculatedfun.util.AppTool;
import com.kaifantech.cache.manager.AppCache;

public class AppSessionDAO extends EnterpriseCacheSessionDAO {
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		AppCache.worker().hset("APP_SESSION", sessionId.toString(), SerializablaUtils.serialize(session));
		return session.getId();
	}

	protected Session doReadSession(Serializable sessionId) {
		String sessionStr = AppCache.worker().get(sessionId.toString());
		if (AppTool.isNull(sessionStr)) {
			return null;
		}
		return SerializablaUtils.deserialize(sessionStr);
	}

	protected void doUpdate(Session session) {
		if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
			return;
		}
		AppCache.worker().hset("APP_SESSION", session.getId().toString(), SerializablaUtils.serialize(session));
	}

	protected void doDelete(Session session) {
		AppCache.worker().del("APP_SESSION", session.getId().toString());
	}
}
