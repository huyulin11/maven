package com.kaifantech.component.service.taskexe.oper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.singletask.SingletaskBean;
import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.component.dao.agv.info.AgvOpInitDao;
import com.kaifantech.component.dao.agv.info.AgvOpWmsDao;
import com.kaifantech.component.dao.taskexe.op.TaskexeOpDao;
import com.kaifantech.component.service.agv.info.AgvInfoService;
import com.kaifantech.component.service.alloc.info.IAllocInfoService;
import com.kaifantech.component.service.alloc.status.IAllocStatusService;
import com.kaifantech.component.service.paper.base.PaperService;
import com.kaifantech.component.service.singletask.info.SingleTaskInfoService;
import com.kaifantech.component.service.taskexe.add.ITaskexeAddService;
import com.kaifantech.component.service.taskexe.check.ITaskexeCheckService;
import com.kaifantech.component.service.taskexe.status.ITaskexeStatusService;
import com.kaifantech.init.sys.params.AppSysParameters;
import com.kaifantech.init.sys.qualifier.CsyCdbpQualifier;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.TaskexeOpFlag;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.thread.ThreadTool;

/***
 * 描述任务从用户下达到发送AGV执行前的逻辑
 ***/
@Service(CsyCdbpQualifier.TASKEXE_ADD_SERVICE)
public class CsyCdbpTaskexeAddService implements ITaskexeAddService {
	@Autowired
	private AgvInfoService agvInfoService;
	@Autowired
	protected AgvOpInitDao agvOpInitDao;
	@Autowired
	private TaskexeOpDao taskexeTaskDao;
	@Autowired
	private ITaskexeStatusService taskexeStatusService;
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_TASKEXE_CHECK_SERVICE)
	private ITaskexeCheckService taskexeCheckService;
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_ALLOC_INFO_SERVICE)
	private IAllocInfoService allocInfoService;
	@Autowired
	private IAllocStatusService allocService;
	@Autowired
	private SingleTaskInfoService singleTaskInfoService;
	@Autowired
	private PaperService paperService;
	@Autowired
	private AgvOpWmsDao agvOpWmsDao;

	@Override
	public AppMsg addTask(Object obj) {
		TaskexeBean taskexeBean = (TaskexeBean) obj;
		return addTask(taskexeBean);
	}

	private AppMsg addTask(TaskexeBean taskexeBean) {
		Integer tmpAgvId = agvInfoService.checkAgvId(taskexeBean.getAgvId());
		if (tmpAgvId < 0) {
			return new AppMsg(-1, "错误的agv编号");
		}
		AppMsg msg = taskexeCheckService.checkBeforeAdd(taskexeBean, tmpAgvId);
		if (msg.getCode() >= 0) {
			String taskid = taskexeBean.getJsonItem("taskid");
			SingletaskBean singletaskBean = singleTaskInfoService.get(taskid);
			String taskexesid = null;
			if (AppTool.equals(singletaskBean.getTasktype(), AgvTaskType.RECEIPT, AgvTaskType.SHIPMENT)) {
				taskexesid = paperService.getService(singletaskBean.getTasktype()).getPaperid();
			} else {
				System.err.println("无法处理的任务类型");
				return AppMsg.fail();
			}
			taskexeBean.setTaskexesid(taskexesid);
			taskexeBean.setTasktype(singletaskBean.getTasktype());
			AllocItemInfoBean allocItem = allocInfoService.getByTaskid(taskid);
			allocItem.setSkuId(taskexeBean.getSkuId());
			allocService.start(allocItem, singletaskBean.getTasktype());
			taskexeStatusService.changeStatusWhenNew(taskid);
			taskexeTaskDao.addATask(taskexeBean);
			agvOpWmsDao.command(taskexeBean.getAgvId(), taskexeBean.getTasktype());
			msg.setMsg("任务完成缓存！如AGV正在充电任务将挂起，直到AGV完成充电！");
		}
		return msg;
	}

	public synchronized void cancel(TaskexeBean taskexeBean, boolean flag) throws Exception {
		if (TaskexeOpFlag.OVER.equals(taskexeBean.getOpflag())) {
			return;
		}
		Integer agvId = taskexeBean.getAgvId();
		AppSysParameters.setTaskstop(agvId, false);
		taskexeBean.setShutdown();
		ThreadTool.sleep(1000);
		agvOpInitDao.pauseOutErr(agvId);
		agvOpInitDao.workOverForce(agvId);
		taskexeTaskDao.overATask(taskexeBean);
		agvOpInitDao.startupFromOutErr(agvId);
		AppFileLogger.setWarningTips(taskexeBean.getAgvId(), agvId + "号AGV任务" + taskexeBean.getTaskexesid() + "被中止！");
	}
}