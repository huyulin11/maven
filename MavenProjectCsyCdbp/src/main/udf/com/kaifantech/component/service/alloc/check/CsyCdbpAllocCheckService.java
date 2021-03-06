package com.kaifantech.component.service.alloc.check;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.singletask.SingletaskBean;
import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.component.service.singletask.group.SingletaskGroupService;
import com.kaifantech.component.service.singletask.info.SingleTaskInfoService;
import com.kaifantech.component.service.taskexe.check.ITaskexeCheckService;
import com.kaifantech.component.service.taskexe.info.TaskexeInfoService;
import com.kaifantech.init.sys.qualifier.CsyCdbpQualifier;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

@Service(CsyCdbpQualifier.ALLOC_CHECK_SERVICE)
public class CsyCdbpAllocCheckService extends WmsAllocCheckService {
	@Autowired
	private SingletaskGroupService singletaskGroupService;

	@Autowired
	private SingleTaskInfoService singleTaskInfoService;

	@Autowired
	private TaskexeInfoService taskInfoService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_TASKEXE_CHECK_SERVICE)
	private ITaskexeCheckService taskexeCheckService;

	public AppMsg checkLatestTaskexe(Integer agvId) {
		return taskexeCheckService.checkTaskexe(agvId);
	}

	public AppMsg checkTaskGroup(SingletaskBean singletaskBean) {
		List<TaskexeBean> latestTaskList = taskInfoService.getNotOverList();
		if (latestTaskList != null && latestTaskList.size() > 0) {
			for (TaskexeBean taskexeBean : latestTaskList) {
				SingletaskBean tmpSingletaskBean = singleTaskInfoService.get(taskexeBean.getJsonItem("taskid"));
				if (AgvTaskType.ZUHE_RENWU.equals(singletaskBean.getTasktype())
						&& !singletaskGroupService.inTheSameGroupWith(tmpSingletaskBean, singletaskBean)) {
					return new AppMsg(-1, "正在执行的子任务与已执行的子任务不属于同一组！");
				} else if (tmpSingletaskBean.getId().equals(singletaskBean.getId())) {
					return new AppMsg(-1, "正在执行的子任务已经处于执行状态！");
				}
			}
		}
		return new AppMsg(0, "任务可以下达！");
	}

	public AppMsg checkAllocBeforeAdd(Object obj, Integer agvId) {
		AppMsg msg = super.checkAllocBeforeAdd(obj, agvId);
		if (msg.getCode() < 0) {
			return msg;
		}

		msg = checkLatestTaskexe(agvId);
		if (msg.getCode() < 0) {
			return msg;
		}

		SingletaskBean singletaskBean = (SingletaskBean) obj;
		AllocItemInfoBean allocationInfoBean = allocService.getByTaskid(singletaskBean.getId());
		msg = checkAllocInfo(allocationInfoBean, singletaskBean.getTasktype());
		return msg;
	}

}
