package com.kaifantech.component.comm.worker.client.agv;

import java.util.ArrayList;
import java.util.List;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.bean.msg.agv.SeerAgvMsgTaskBean;
import com.kaifantech.cache.manager.AppConf;
import com.kaifantech.util.hex.AppByteUtil;
import com.kaifantech.util.iot.SeerAgvStatus;
import com.kaifantech.util.msg.agv.AcsAgvMsgCatcher;
import com.kaifantech.util.msg.agv.SeerAgvMsgGetter;

public class SeerAgvStatusCatcher extends AcsAgvMsgCatcher {
	public SeerAgvStatusCatcher(IotClientBean iotClientBean) {
		super(iotClientBean);
	}

	@Override
	public void updateAgvInfo(Integer agvId, String msg) {
		List<String> sqlList = agvsSqlList(agvId, msg);
		agvInfoDao.update(agvId, sqlList);
		Boolean running = isRunning.get(agvId);
		if (AppTool.isNull(running) || !running) {
			isRunning.put(agvId, true);
			AppConf.worker().hset("SHOOT_DATA_" + agvId, number++ % 65536, msg);
			pool.execute(() -> {
				taskexeModule.startControl(iotClientBean);
				isRunning.put(agvId, false);
			});
		}
	}

	public List<String> agvsSqlList(Integer agvId, String msg) {
		AgvInfoBean agvBean = agvInfoDao.get(agvId);
		List<String> sqlList = new ArrayList<>();
		SeerAgvMsgTaskBean agvMsgBean = SeerAgvMsgGetter.getStatusBean(agvBean.getId());
		if (AppTool.isNull(agvMsgBean) || !agvMsgBean.isValid() || !SeerAgvMsgGetter.isConnected(agvBean.getId())) {
			sqlList.add("agvstatus= '" + AgvInfoBean.CONNECT_FAIL + "'");
		} else {
			Integer currentSite = agvMsgBean.currentSite();
			if (!AppTool.isNull(currentSite)) {
				sqlList.add("currentsite= '" + currentSite + "'");
			}
			Integer agvstatus = agvMsgBean.getTask_status();
			if (!AppTool.isNull(agvstatus)) {
				sqlList.add("agvstatus= '" + SeerAgvStatus.getStatusName(agvstatus) + "'");
			}
			double batteryInt = agvMsgBean.getCurrentBattery();
			String battery = "" + batteryInt;
			if (!AppTool.isNull(battery)) {
				sqlList.add("battery= '" + battery + "'");
			}
			String speed = "" + AppByteUtil.hexToInt(agvMsgBean.getCurrentSpeed());
			if (!AppTool.isNull(speed)) {
				sqlList.add("speed= '" + speed + "'");
			}
		}
		return sqlList;
	}
}
