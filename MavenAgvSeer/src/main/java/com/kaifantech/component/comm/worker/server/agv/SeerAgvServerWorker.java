package com.kaifantech.component.comm.worker.server.agv;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.component.comm.worker.server.IServerWorker;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.init.sys.qualifier.SeerQualifier;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.socket.base.IConnect;
import com.kaifantech.util.socket.factory.server.SeerAgvNettyServer;

@Service(SeerQualifier.AGV_SERVER_WORKER)
public class SeerAgvServerWorker implements IServerWorker {
	private Map<Integer, IConnect> map = new HashMap<>();

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	private IIotClientService iotClientService;

	public synchronized void init() {
		if (map == null || map.size() == 0) {
			for (IotClientBean agv : iotClientService.getAgvCacheList()) {
				if ("127.0.0.1".equals(agv.getIp())) {
					SeerAgvNettyServer server = SeerAgvNettyServer.create(agv);
					server.setAgvId(agv.getId());
					map.put(agv.getId(), server);
					try {
						server.init();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public synchronized Map<Integer, IConnect> getMap() {
		return map;
	}
}
