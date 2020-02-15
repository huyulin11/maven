package com.kaifantech.component.comm.worker.client.agv;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.bean.iot.relation.IotRelationBean;
import com.kaifantech.component.comm.worker.client.IConnectWorker;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.component.service.iot.relation.IotRelationService;
import com.kaifantech.init.sys.qualifier.SeerQualifier;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.IotDevType;
import com.kaifantech.util.socket.base.IConnect;
import com.kaifantech.util.socket.factory.client.SeerClientFactory;
import com.kaifantech.util.socket.netty.client.AbstractNettyByteClient;
import com.kaifantech.util.thread.ThreadTool;

@Service(SeerQualifier.AGV_CLIENT_OTHER_WORKER)
public class SeerAgvClientOtherWorker implements IConnectWorker {
	private Map<Integer, IConnect> clientMap = new HashMap<Integer, IConnect>();

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	private IIotClientService iotClientService;

	@Autowired
	private IotRelationService iotRelationService;

	private boolean isCheckingConnect = false;

	public synchronized void init() {
		if (clientMap != null && clientMap.size() > 0) {
			if (!isCheckingConnect) {
				ThreadTool.run(() -> {
					while (true) {
						isCheckingConnect = true;
						ThreadTool.sleep(5000);
					}
				});
			}
			return;
		}

		for (IotClientBean bean : iotClientService.getList()) {
			if (!bean.getDevtype().equals(IotDevType.AGV_SEER_OTHER)) {
				continue;
			}
			try {
				IConnect client;
				client = SeerClientFactory.create(bean);
				IotRelationBean relation = iotRelationService.getIotRelationBean(bean);
				if (AppTool.isNull(relation)) {
					continue;
				}
				clientMap.put(relation.getAgvid(), client);
				((AbstractNettyByteClient) client).setAgvId(relation.getAgvid());
				client.init();
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public Map<Integer, IConnect> getMap() {
		init();
		return clientMap;
	}
}
