package com.kaifantech.component.dao.agv.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kaifantech.util.constant.taskexe.ctrl.AgvSiteStatus;
import com.kaifantech.component.service.taskexe.ctrl.ICtrlModule;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

@Service
public class AgvOpWmsDao extends AgvInfoDao {

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_CTRL_MODULE)
	private ICtrlModule ctrlModule;

	public void command(Integer agvId, String tasktype) {
		update(agvId, " taskstatus='" + tasktype + "'", AND + " sitestatus='" + AgvSiteStatus.INIT + "'",
				AND + " taskstatus='" + AgvTaskType.FREE + "'");
	}

	public void goWork(Integer agvId, String tasktype, String taskexesid) {
		update(agvId, " sitestatus='" + AgvSiteStatus.MOVING + "'", " taskexesid='" + taskexesid + "'",
				AND + " sitestatus='" + AgvSiteStatus.INIT + "'", AND + " taskstatus='" + tasktype + "'");
		ctrlModule.startup(agvId);
	}

	public void reachAlloc(Integer agvId, String tasktype) {
		update(agvId, " sitestatus='" + AgvSiteStatus.alloc(tasktype) + "'",
				AND + " sitestatus='" + AgvSiteStatus.MOVING + "'", AND + " taskstatus='" + tasktype + "'");
	}

	public void workDoneAlloc(Integer agvId, String tasktype) {
		update(agvId, " sitestatus='" + AgvSiteStatus.MOVING + "'",
				AND + " sitestatus='" + AgvSiteStatus.alloc(tasktype) + "'", AND + " taskstatus='" + tasktype + "'");
		ctrlModule.startup(agvId);
	}

	public void reach(Integer agvId, String tasktype) {
		update(agvId, " sitestatus='" + AgvSiteStatus.window(tasktype) + "'",
				AND + " sitestatus='" + AgvSiteStatus.MOVING + "'", AND + " taskstatus='" + tasktype + "'");
	}

	public void workDoneWindow(Integer agvId, String tasktype) {
		update(agvId, " sitestatus='" + AgvSiteStatus.MOVING + "'",
				AND + " sitestatus='" + AgvSiteStatus.window(tasktype) + "'", AND + " taskstatus='" + tasktype + "'");
		ctrlModule.startup(agvId);
	}

	public void workOver(Integer agvId, String tasktype) {
		update(agvId, " sitestatus='" + AgvSiteStatus.INIT + "'", " taskstatus='" + AgvTaskType.FREE + "'",
				" taskexesid=''", AND + " sitestatus='" + AgvSiteStatus.MOVING + "'",
				AND + " taskstatus='" + tasktype + "'");
	}
}
