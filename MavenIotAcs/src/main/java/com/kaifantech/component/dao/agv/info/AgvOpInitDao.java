package com.kaifantech.component.dao.agv.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kaifantech.util.constant.taskexe.ctrl.AgvSiteStatus;
import com.kaifantech.component.service.taskexe.ctrl.ICtrlModule;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

@Service
public class AgvOpInitDao extends AgvInfoDao {

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_CTRL_MODULE)
	private ICtrlModule ctrlModule;

	public void commandToInit(Integer agvId) {
		update(agvId, " json=''", AND + " sitestatus=" + str(AgvSiteStatus.INIT),
				AND + " taskstatus=" + str(AgvTaskType.FREE));
		update(agvId, " taskstatus=" + str(AgvTaskType.GOTO_INIT), " taskexesid=''",
				AND + " sitestatus=" + str(AgvSiteStatus.INIT), AND + " taskstatus=" + str(AgvTaskType.FREE));
	}

	public void command(Integer agvId, String to) {
		update(agvId, " taskstatus=" + str(AgvTaskType.GOTO_INIT), " taskexesid=''",
				"`JSON` = " + jsonItemUpdate("TO", to), AND + " sitestatus=" + str(AgvSiteStatus.INIT),
				AND + " taskstatus=" + str(AgvTaskType.FREE));
	}

	public void goWorkToInit(Integer agvId, String taskexesid) {
		update(agvId, " sitestatus=" + str(AgvSiteStatus.MOVING), " taskexesid=" + str(taskexesid),
				AND + " sitestatus=" + str(AgvSiteStatus.INIT), AND + " taskstatus=" + str(AgvTaskType.GOTO_INIT));
		ctrlModule.startup(agvId);
	}

	public void workOverToInit(Integer agvId) {
		update(agvId, " sitestatus=" + str(AgvSiteStatus.INIT), " taskstatus=" + str(AgvTaskType.FREE),
				" taskexesid=''", AND + " sitestatus=" + str(AgvSiteStatus.MOVING),
				AND + " taskstatus=" + str(AgvTaskType.GOTO_INIT));
	}
}
