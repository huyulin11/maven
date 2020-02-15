package com.kaifantech.util.socket.factory.client;

import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.constant.taskexe.ctrl.IotDevType;
import com.kaifantech.util.socket.base.IConnect;
import com.kaifantech.util.socket.netty.client.AbstractNettyByteClient;

public class SeerClientFactory {
	private static IConnect create(IotClientBean iotClientBean, String devtype) {
		switch (devtype) {
		case IotDevType.AGV_SEER_STATUS: {
			SeerAgvNettyClient agvNettyClient = new SeerAgvNettyClient(iotClientBean);
			agvNettyClient.setAgvId(iotClientBean.getId());
			return agvNettyClient;
		}
		case IotDevType.AGV_SEER_CTRL: {
			SeerAgvNettyClient agvNettyClient = new SeerAgvNettyClient(iotClientBean);
			agvNettyClient.setAgvId(iotClientBean.getId());
			return agvNettyClient;
		}
		case IotDevType.AGV_SEER_NAVI: {
			SeerAgvNettyClient agvNettyClient = new SeerAgvNettyClient(iotClientBean);
			agvNettyClient.setAgvId(iotClientBean.getId());
			return agvNettyClient;
		}
		case IotDevType.AGV_SEER_CONF: {
			SeerAgvNettyClient agvNettyClient = new SeerAgvNettyClient(iotClientBean);
			agvNettyClient.setAgvId(iotClientBean.getId());
			return agvNettyClient;
		}
		case IotDevType.AGV_SEER_OTHER: {
			SeerAgvNettyClient agvNettyClient = new SeerAgvNettyClient(iotClientBean);
			agvNettyClient.setAgvId(iotClientBean.getId());
			return agvNettyClient;
		}
		default:
			return null;
		}
	}

	public static IConnect create(IotClientBean bean) {
		IConnect client = create(bean, bean.getDevtype());
		((AbstractNettyByteClient) client).setDevId(bean.getId());
		return client;
	}
}