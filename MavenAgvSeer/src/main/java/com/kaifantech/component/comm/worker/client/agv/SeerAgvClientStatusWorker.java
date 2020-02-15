package com.kaifantech.component.comm.worker.client.agv;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.component.comm.worker.client.IConnectWorker;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.init.sys.qualifier.SeerQualifier;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.IotDevType;
import com.kaifantech.util.msg.agv.SeerAgvMsgGetter;
import com.kaifantech.util.socket.base.IConnect;
import com.kaifantech.util.socket.factory.client.SeerClientFactory;

@Service(SeerQualifier.AGV_CLIENT_STATUS_WORKER)
public class SeerAgvClientStatusWorker implements IConnectWorker {
	private Map<Integer, IConnect> map = new HashMap<Integer, IConnect>();

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	private IIotClientService iotClientService;

	@Autowired
	@Qualifier(SeerQualifier.AGV_CLIENT_CTRL_WORKER)
	private IConnectWorker agvCtrlWorker;
	@Autowired
	@Qualifier(SeerQualifier.AGV_CLIENT_NAVI_WORKER)
	private IConnectWorker agvNaviWorker;
	@Autowired
	@Qualifier(SeerQualifier.AGV_CLIENT_CONF_WORKER)
	private IConnectWorker agvConfWorker;
	@Autowired
	@Qualifier(SeerQualifier.AGV_CLIENT_OTHER_WORKER)
	private IConnectWorker agvOtherWorker;

	public void init() {
		if (AppTool.isNull(map)) {
			for (IotClientBean agvBean : iotClientService.getList(IotDevType.AGV_SEER_STATUS)) {
				IConnect agvClient = SeerClientFactory.create(agvBean);
				map.put(agvBean.getId(), agvClient);
				java.util.Observer catcher = new SeerAgvStatusCatcher(agvBean);
				SeerAgvMsgGetter.setCatcher(agvBean.getId(), catcher);
				try {
					agvClient.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public synchronized Map<Integer, IConnect> getMap() {
		init();
		return map;
	}

	public void startConnect() {
		getMap();
		agvCtrlWorker.startConnect();
		agvNaviWorker.startConnect();
		agvConfWorker.startConnect();
		agvOtherWorker.startConnect();
	}
}
