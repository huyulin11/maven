package com.kaifantech.component.service.status.agv;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.bean.taskexe.AgvStatusBean;
import com.kaifantech.component.dao.acs.AgvStatusDao;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.component.service.agv.info.AgvInfoService;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.component.service.taskexe.check.ITaskexeCheckService;
import com.kaifantech.init.sys.params.AppSysParameters;
import com.kaifantech.init.sys.params.AppAutoParameters;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.log.AppFileLogger;

@Service
public class AgvStatusService {

	@Autowired
	private AgvStatusDao agvStatusDao;

	@Inject
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	private IIotClientService iotClientService;

	@Inject
	private AgvInfoService agvInfoService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_INFO_DAO)
	private AgvInfoDao agvInfoDao;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_TASKEXE_CHECK_SERVICE)
	private ITaskexeCheckService taskexeCheckService;

	public Map<String, Object> agvStatus(Integer agvId) {
		Map<String, Object> agvinfo = new HashMap<String, Object>();
		String msg = AppFileLogger.getTips(agvId);
		agvinfo.put("systemWarning", (AppTool.isNull(msg)) ? "" : msg);
		if (!agvId.equals(0)) {
			agvinfo.put("agvInfo", agvInfoDao.get(agvId));
			agvinfo.put("currentTask", taskexeCheckService.checkTaskexe(agvId));
		} else {
			agvinfo.put("isOpenPi", AppSysParameters.isOpenPi());
			agvinfo.put("isAutoTask", AppAutoParameters.isAutoTask());
			agvinfo.put("isAutoCharge", AppAutoParameters.isAutoCharge());
			agvinfo.put("isErrBack", AppAutoParameters.isErrBack());
		}
		return agvinfo;
	}

	public AppMsg addStatus(String jobId, Integer agvId, int autoflag) {
		Integer tmpAgvId = agvInfoService.checkAgvId(agvId);
		if (tmpAgvId < 0) {
			return new AppMsg(-1, "错误的agv编号");
		}
		if (AppTool.isNull(jobId)) {
			return new AppMsg(-1, "任务类型（tasktype）不能为空！");
		}
		if (tmpAgvId.equals(0)) {
			for (IotClientBean agvBean : iotClientService.getAgvCacheList()) {
				agvStatusDao.addAgvStatus(jobId, agvBean.getId(), autoflag);
			}
		}
		agvStatusDao.addAgvStatus(jobId, tmpAgvId, autoflag);
		return new AppMsg(0, "指令下达成功！");
	}

	public AppMsg addStatus(Object obj) {
		AgvStatusBean agvStatusBean = (AgvStatusBean) obj;
		return addStatus(agvStatusBean.getJobId(), agvStatusBean.getAGVId(), agvStatusBean.getAutoFlag());
	}
}
