package com.kaifantech.component.service.taskexe.deal;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.bean.msg.agv.SeerAgvMsgTaskBean;
import com.kaifantech.bean.singletask.SingletaskBean;
import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.cache.manager.AppCache;
import com.kaifantech.component.comm.manager.agv.SeerAgvManager;
import com.kaifantech.component.dao.agv.info.AgvOpChargeDao;
import com.kaifantech.component.dao.agv.info.AgvOpTransportDao;
import com.kaifantech.component.dao.agv.info.AgvOpWmsDao;
import com.kaifantech.component.dao.taskexe.op.TaskexeOpDao;
import com.kaifantech.component.service.alloc.info.IAllocInfoService;
import com.kaifantech.component.service.alloc.status.IAllocStatusService;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.component.service.singletask.info.SingleTaskInfoService;
import com.kaifantech.component.service.taskexe.dealer.ISeerTaskexeDealer;
import com.kaifantech.component.service.taskexe.detail.info.ITaskexeDetailInfoService;
import com.kaifantech.component.service.taskexe.info.TaskexeInfoService;
import com.kaifantech.component.service.taskexe.watch.SeerTaskexeWatchDealer;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.TaskexeOpFlag;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.msg.agv.SeerAgvMsgGetter;
import com.kaifantech.util.thread.ThreadTool;

@Service
public class CsyCdbpTaskexeDealer implements ISeerTaskexeDealer {
	@Autowired
	protected ITaskexeDetailInfoService taskexeDetailService;

	@Autowired
	protected TaskexeOpDao taskexeTaskDao;

	@Autowired
	protected SeerTaskexeWatchDealer taskexeWatchDealer;

	@Autowired
	protected SeerAgvManager agvManager;

	@Autowired
	protected SingleTaskInfoService singleTaskInfoService;

	@Autowired
	protected IAllocStatusService allocService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_ALLOC_INFO_SERVICE)
	protected IAllocInfoService allocInfoService;

	@Autowired
	protected AgvOpChargeDao agvOpChargeDao;

	@Autowired
	protected AgvOpWmsDao agvOpWmsDao;

	@Autowired
	protected AgvOpTransportDao agvOpTransportDao;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	protected IIotClientService iotClientService;

	@Autowired
	protected TaskexeInfoService taskexeInfoService;

	protected static ExecutorService pool = ThreadTool.getThreadPool(2);

	public synchronized void deal(TaskexeBean taskexeBean, SeerAgvMsgTaskBean agvMsg) throws Exception {
		Integer agvId = taskexeBean.getAgvId();
		if (TaskexeOpFlag.NEW.equals(taskexeBean.getOpflag())) {
			AgvInfoBean agvBean = agvOpWmsDao.get(agvId);
			if (AppTool.equals(agvBean.getTaskstatus(), AgvTaskType.GOTO_CHARGE, AgvTaskType.BACK_CHARGE)
					&& AppTool.equals(taskexeBean.getTasktype(), AgvTaskType.SHIPMENT, AgvTaskType.RECEIPT)) {
				AppFileLogger.setWarningTips(agvId, "目标AGV正在充电，任务无法下达！", taskexeBean.getTaskKey());
				return;
			}
			startWork(taskexeBean);
			return;
		}
		if (TaskexeOpFlag.SEND.equals(taskexeBean.getOpflag())) {
			watchWork(taskexeBean);
			return;
		}
	}

	protected void startWork(TaskexeBean taskexeBean) {
		ThreadTool.sleep(1000);
		Integer agvId = taskexeBean.getAgvId();

		if (AppTool.equals(taskexeBean.getTasktype(), AgvTaskType.TRANSPORT)) {
			agvOpTransportDao.command(agvId, taskexeBean.getTasktype());
			agvOpTransportDao.goWork(agvId, taskexeBean.getTaskexesid());
			AppMsg msg = agvManager.doTask(agvId, taskexeBean.getJsonItem("TO"));
			if (!msg.isSuccess()) {
				return;
			}
			taskexeTaskDao.sendATask(taskexeBean);
		} else {
			SingletaskBean singletaskBean = singleTaskInfoService.get(taskexeBean.getJsonItem("taskid"));
			AppFileLogger.setWarningTips(agvId, "开始任务！", taskexeBean.getTaskKey(), ",任务名：",
					singletaskBean.getTasktext());
			AppMsg msg = agvManager.doTask(agvId, singletaskBean.getTaskname());
			if (!msg.isSuccess()) {
				return;
			}
			if (AppTool.equals(taskexeBean.getTasktype(), AgvTaskType.RECEIPT, AgvTaskType.SHIPMENT)) {
				agvOpWmsDao.command(agvId, taskexeBean.getTasktype());
				agvOpWmsDao.goWork(agvId, taskexeBean.getTasktype(), taskexeBean.getTaskexesid());
			}
			taskexeTaskDao.sendATask(taskexeBean);
			AppFileLogger.setWarningTips(agvId, agvId, "号AGV执行的", taskexeBean.getTaskexesid(), "-",
					taskexeBean.getTasksequence(), ",任务名：", singletaskBean.getTasktext(), "发送成功，更新任务状态为SEND！");
			agvOpChargeDao.updateRemark(agvId, "" + singletaskBean.getTasktext() + "任务已发送");
		}
	}

	protected void watchWork(TaskexeBean taskexeBean) {
		Integer agvId = taskexeBean.getAgvId();
		SeerAgvMsgTaskBean agvMsg = SeerAgvMsgGetter.getStatusBean(agvId);
		if (!AppTool.isNull(agvMsg) && agvMsg.isTaskfinished()) {
			if (AppTool.equals(taskexeBean.getTasktype(), AgvTaskType.RECEIPT, AgvTaskType.SHIPMENT)) {
				AllocItemInfoBean allocItem = allocInfoService.getByTaskid(taskexeBean.getJsonItem("taskid"));
				AppMsg msg = allocService.end(allocItem, taskexeBean.getTasktype());
				if (!msg.isSuccess()) {
					return;
				}
				agvOpWmsDao.workOver(agvId, taskexeBean.getTasktype());
			} else if (AppTool.equals(taskexeBean.getTasktype(), AgvTaskType.GOTO_CHARGE, AgvTaskType.BACK_CHARGE)) {
				if (AgvTaskType.GOTO_CHARGE.equals(taskexeBean.getTasktype())) {
					agvOpChargeDao.workOverGotoCharge(agvId);
				} else if (AgvTaskType.BACK_CHARGE.equals(taskexeBean.getTasktype())) {
					agvOpChargeDao.workOverBackCharge(agvId);
				}
			}
			SingletaskBean singletaskBean = singleTaskInfoService.get(taskexeBean.getJsonItem("taskid"));
			taskexeTaskDao.overASendTask(taskexeBean);
			AppCache.worker().hset("AREA_CURRENT", agvId, "O");
			AppCache.worker().hset("AREA_NEXT", agvId, "O");
			AppCache.worker().hset("AREA_NEXT_ALLOC", agvId, "O");
			AppFileLogger.setWarningTips(agvId, agvId, "号AGV执行的", taskexeBean.getTaskexesid(), "-",
					taskexeBean.getTasksequence(), ",任务名：", singletaskBean.getTasktext(),
					"任务所有明细任务均已执行完毕，更新任务状态为OVER！");
			agvOpChargeDao.updateRemark(agvId, "" + singletaskBean.getTasktext() + "任务已结束");
		}
	}
}
