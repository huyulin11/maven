package com.kaifantech.component.dao.agv.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kaifantech.util.constant.taskexe.ctrl.AgvSiteStatus;
import com.kaifantech.component.service.taskexe.ctrl.ICtrlModule;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

@Service
public class AgvOpSuspendDao extends AgvInfoDao {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_CTRL_MODULE)
	private ICtrlModule ctrlModule;

	public void suspendToCharge(Integer agvId, String tasktype) {
		update(agvId, " sitestatus='" + AgvSiteStatus.INIT + "'",
				" taskstatus='" + AgvTaskType.GOTO_CHARGE + "'", " taskexesid=''", AND + " (sitestatus='"
						+ AgvSiteStatus.alloc(tasktype) + "' " + "or sitestatus='" + AgvSiteStatus.MOVING + "'" + ")",
				AND + " taskstatus='" + tasktype + "'");
		ctrlModule.pause(agvId);
	}
}
