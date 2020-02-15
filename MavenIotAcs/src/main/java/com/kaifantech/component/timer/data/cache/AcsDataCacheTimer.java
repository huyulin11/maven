package com.kaifantech.component.timer.data.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.AppFile;
import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.cache.manager.AppConf;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.component.service.status.agv.AgvCtrlInfoService;
import com.kaifantech.component.service.status.agv.AgvStatusService;
import com.kaifantech.component.service.taskexe.module.ITaskexeModule;
import com.kaifantech.component.timer.pi.AcsPiCtrlTimer;
import com.kaifantech.component.timer.taskexe.auto.AcsAutoTimer;
import com.kaifantech.init.sys.UdfBusinessInfo;
import com.kaifantech.init.sys.BaseBusinessInfo;
import com.kaifantech.init.sys.params.AppAutoParameters;
import com.kaifantech.init.sys.params.AppConfParameters;
import com.kaifantech.init.sys.params.AppSysParameters;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.thread.ThreadTool;

public class AcsDataCacheTimer extends AbstractDataCacheTimer {
	protected static String timerType = "数据缓存器";
	protected final Logger logger = Logger.getLogger(AcsDataCacheTimer.class);

	@Autowired
	protected AgvStatusService agvStatusService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	protected IIotClientService iotClientService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_TASKEXE_MODULE)
	private ITaskexeModule taskexeModule;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_INFO_DAO)
	protected AgvInfoDao agvInfoDao;

	@Autowired
	protected AgvCtrlInfoService agvsCtrlInfoService;

	@Autowired
	protected AcsPiCtrlTimer piCtrlTimer;

	@Autowired
	protected AcsAutoTimer taskexeAutoTimer;

	public AcsDataCacheTimer() {
		logger.info(timerType + "开始启动！");
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		super.onApplicationEvent(contextRefreshedEvent);
		try {
			List<IotClientBean> list = iotClientService.getList();
			String info = JSONArray.toJSON(list).toString();
			AppFile.createFile(UdfBusinessInfo.getProjJsonsPath() + "iot/", "iots.json", info);
			ThreadTool.run(() -> {
				for (IotClientBean client : iotClientService.getAgvCacheList()) {
					// taskexeModule.loopControl(client);
					agvInfoDao.get(client.getId());
					List<String> sqlList = new ArrayList<>();
					sqlList.add("agvstatus= '" + AgvInfoBean.CONNECT_FAIL + "'");
					agvInfoDao.update(client.getId(), sqlList);
				}
			});

			ThreadTool.run(() -> {
				piCtrlTimer.resolute();
			});

			ThreadTool.run(() -> {
				taskexeAutoTimer.resolute();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "0/2 * * * * ?")
	public void sysInfo() throws Exception {
		agvInfo2JsonAll();
		agvInfo2JsonSingle();
		sysInfo2Json();
	}

	protected void agvInfo2JsonAll() throws Exception {
		for (IotClientBean agvBean : iotClientService.getAgvCacheList()) {
			Map<String, Object> map = agvStatusService.agvStatus(agvBean.getId());
			if (AppTool.isNull(map)) {
				map = new HashMap<>();
			}
			String agvInfo = JSONArray.toJSON(map).toString();
			AppFile.createFile(UdfBusinessInfo.getProjJsonsPath() + "agv/", "agvInfo" + agvBean.getId() + ".json",
					agvInfo);
		}
		JSONObject agvs = agvsCtrlInfoService.agvsStatus();
		String info;
		if (AppTool.isNull(agvs)) {
			agvs = new JSONObject();
		}
		info = agvs.toString();
		AppFile.createFile(UdfBusinessInfo.getProjJsonsPath() + "agv/", "agvInfo0.json", info);
	}

	protected void agvInfo2JsonSingle() throws Exception {
		List<AgvInfoBean> list = agvsCtrlInfoService.getRtAgvInfoList();
		if (!AppTool.isNull(list)) {
			String info = JSONArray.toJSON(list).toString();
			AppFile.createFile(UdfBusinessInfo.getProjJsonsPath() + "agv/", "agvList.json", info);
		}
	}

	static {
		AppConfParameters.setAgvGeneralSearchInteval();
	}

	@Scheduled(cron = "0/1 * * * * ?")
	public void readAcsParams() {
		if (UdfBusinessInfo.CURRENT_CLIENT.equals(BaseBusinessInfo.Clients.YUFENG)) {
			return;
		}
		AppAutoParameters.setAutoTask();
		AppSysParameters.setOpenPi();
		AppAutoParameters.setAutoCharge();
		AppAutoParameters.setErrBack();
	}

	@Scheduled(cron = "* 0/1 * * * ?")
	public void taskQuantity() throws IOException {
		List<Map<String, Object>> list = AppConf.worker().getList(UdfBusinessInfo.CURRENT_CLIENT + "_TASK_QUANTITY");
		String info = JSONArray.toJSON(list).toString();
		AppFile.createFile(UdfBusinessInfo.getProjJsonsPath() + "", "taskQuantity.json", info);
	}

	public void devsInfos() {
	}
}
