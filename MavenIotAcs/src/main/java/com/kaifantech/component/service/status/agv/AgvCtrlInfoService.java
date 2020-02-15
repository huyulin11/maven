package com.kaifantech.component.service.status.agv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.component.service.taskexe.info.TaskexeInfoService;
import com.kaifantech.init.sys.UdfBusinessInfo;
import com.kaifantech.init.sys.BaseBusinessInfo.Projects;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvBussType;
import com.kaifantech.util.constant.taskexe.ctrl.AgvMoveStatus;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;
import com.kaifantech.util.log.AppFileLogger;

@Service
public class AgvCtrlInfoService {
	@Autowired
	private AgvStatusService agvStatusService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	private IIotClientService iotClientService;

	@Autowired
	private TaskexeInfoService taskInfoService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_INFO_DAO)
	private AgvInfoDao agvInfoDao;

	public boolean isSystemRunning() {
		return AgvMoveStatus.CONTINUE.equals(agvInfoDao.getMoveStatus(0));
	}

	private Map<String, Object> getAllStatus() {
		Map<String, Object> infos = new HashMap<>();
		for (AgvInfoBean agvBean : agvInfoDao.getList()) {
			infos.put(agvBean.getId().toString(), agvStatusService.agvStatus(agvBean.getId()));
		}
		Map<String, Object> info0 = agvStatusService.agvStatus(0);
		infos.put("0", info0);
		return infos;
	}

	public JSONObject agvsStatus() {
		Map<String, Object> infos = getAllStatus();
		return new JSONObject(infos);
	}

	public AppMsg checkAGV(Integer agvId) {
		if (!AgvMoveStatus.CONTINUE.equals(agvInfoDao.getMoveStatus(agvId))) {
			return new AppMsg(-1, "该AGV正在暂停中，新任务无法下达！");
		}
		return new AppMsg(0, "任务可以下达！");
	}

	public Integer getFreeAgvId(Integer outChooseAgvId, Integer chooseAgvId) {
		List<TaskexeBean> latestTaskList = taskInfoService.getNotOverList();
		Integer agvId = null;
		String msg = "";
		Boolean hasAgvId = false;
		for (IotClientBean iotClientBean : iotClientService.getAgvCacheList()) {
			Integer tmpAgvId = iotClientBean.getId();
			AgvInfoBean agvBean = agvInfoDao.get(tmpAgvId);
			if (AppTool.isNull(agvBean)) {
				continue;
			}
			if (UdfBusinessInfo.CURRENT_PROJECT.equals(Projects.TAIKAI_JY)) {
				if ((AgvBussType.TON_1).equals(iotClientBean.getJsonItem("agvbusstype"))) {
					continue;
				}
				if (AppTool.notEquals(agvBean.getCurrentsite(), 50, 51, 52)) {
					continue;
				}
			}
			if (!AppTool.isNull(outChooseAgvId) && outChooseAgvId.equals(tmpAgvId)) {
				continue;
			}
			if (latestTaskList.stream().filter((taskexe) -> {
				return !AppTool.isNull(taskexe.getAgvId()) && taskexe.getAgvId().equals(tmpAgvId);
			}).count() > 0) {
				msg += tmpAgvId + "号AGV有任务在身！";
				continue;
			}
			if (AgvInfoBean.CONNECT_FAIL.equals(agvBean.getAgvstatus())) {
				msg += tmpAgvId + "号AGV未连接！";
				continue;
			}
			if (!AgvTaskType.FREE.equals(agvBean.getTaskstatus())) {
				msg += tmpAgvId + "号AGV正在执行" + agvBean.getTaskstatus() + "任务！";
				continue;
			}
			if (!AgvMoveStatus.CONTINUE.equals(agvBean.getMovestatus())) {
				msg += tmpAgvId + "号AGV正在暂停中！";
				continue;
			}
			if (!AppTool.isNull(chooseAgvId) && !tmpAgvId.equals(chooseAgvId)) {
				if (!hasAgvId) {
					agvId = tmpAgvId;
					hasAgvId = true;
				}
				continue;
			}
			agvId = tmpAgvId;
			break;
		}
		if (!AppTool.isNull(msg)) {
			AppFileLogger.setWarningTips(0, msg);
		}
		if (AppTool.isNull(agvId)) {
			return null;
		}
		return agvId;
	}

	public Integer getFreeAgvId() {
		return getFreeAgvId(null, null);
	}

	public List<AgvInfoBean> getRtAgvInfoList() {
		List<AgvInfoBean> rtnList = new ArrayList<>();
		for (IotClientBean iotClientBean : iotClientService.getAgvCacheList()) {
			AgvInfoBean bean = agvInfoDao.get(iotClientBean.getId());
			rtnList.add(bean);
		}
		return rtnList;
	}

}
