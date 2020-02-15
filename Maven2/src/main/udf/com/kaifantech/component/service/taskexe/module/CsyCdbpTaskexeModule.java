package com.kaifantech.component.service.taskexe.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.bean.msg.agv.SeerAgvMsgTaskBean;
import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.component.comm.worker.client.IConnectWorker;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.component.service.taskexe.add.ITaskexeAddService;
import com.kaifantech.component.service.taskexe.ctrl.ISeerCtrlModule;
import com.kaifantech.component.service.taskexe.dealer.ISeerTaskexeDealer;
import com.kaifantech.component.service.taskexe.info.TaskexeInfoService;
import com.kaifantech.component.service.tasksite.info.SeerTaskSiteInfoService;
import com.kaifantech.init.sys.params.AppSysParameters;
import com.kaifantech.init.sys.params.SystemLock;
import com.kaifantech.init.sys.qualifier.CsyCdbpQualifier;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.msg.agv.SeerAgvMsgGetter;

/***
 * 描述任务从用户下达到发送AGV执行前的逻辑
 ***/
@Service(CsyCdbpQualifier.TASKEXE_MODULE)
public class CsyCdbpTaskexeModule implements ITaskexeModule {

	@Autowired
	private ISeerTaskexeDealer taskexeDealer;

	@Autowired
	private TaskexeInfoService taskexeInfoService;

	@Autowired
	protected SeerTaskSiteInfoService taskSiteInfoService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_CLIENT_WORKER)
	private IConnectWorker agvClientMgr;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_TASKEXE_ADD_SERVICE)
	private ITaskexeAddService taskexeAddService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	private IIotClientService iotClientService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_CTRL_MODULE)
	private ISeerCtrlModule ctrlModule;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_INFO_DAO)
	protected AgvInfoDao agvInfoDao;

	public void startControl(IotClientBean agvBean) {
		doDeal(agvBean);
	}

	public void doDeal(IotClientBean agvBean) {
		Integer agvId = agvBean.getId();
		try {
			TaskexeBean taskexeBean = taskexeInfoService.getNextOne(agvId);
			SeerAgvMsgTaskBean agvMsg = SeerAgvMsgGetter.getStatusBean(agvId);
			if (AppTool.isNull(agvMsg)) {
				return;
			}
			AgvInfoBean agvInfo = agvInfoDao.get(agvId);
			if (taskexeBean == null || AgvTaskType.FREE.equals(agvInfo.getTaskstatus())) {
				return;
			}
			deal(taskexeBean, agvMsg);
			try {
				ctrlModule.control(agvBean, agvMsg);
			} catch (Exception e) {
				AppFileLogger.warning(agvId + "号AGV综合控制时发生错误：" + e.getMessage());
			}
		} catch (Exception e) {
			AppFileLogger.warning(agvId + "号AGV解析任务时发生错误：" + e.getMessage());
		}
	}

	private void deal(TaskexeBean taskexeBean, SeerAgvMsgTaskBean agvMsg) throws Exception {
		synchronized (SystemLock.agv(taskexeBean.getAgvId())) {
			if (AgvTaskType.match(taskexeBean.getTasktype())) {
				boolean isShutdown = taskexeBean.isShutdown();
				if (!isShutdown) {
					taskexeDealer.deal(taskexeBean, agvMsg);
				} else {
					taskexeAddService.cancel(taskexeBean, AppSysParameters.isShutdownThenToInit());
				}
				return;
			}
			AppFileLogger.warning("无法识别当前任务类型！" + taskexeBean.getTaskKey());
		}
	}
}
