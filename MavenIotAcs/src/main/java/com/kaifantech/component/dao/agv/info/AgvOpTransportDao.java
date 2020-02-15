package com.kaifantech.component.dao.agv.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kaifantech.component.service.taskexe.ctrl.ICtrlModule;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvSiteStatus;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

@Service
public class AgvOpTransportDao extends AgvInfoDao {

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_CTRL_MODULE)
	private ICtrlModule ctrlModule;

	public void command(Integer agvId, String to) {
		update(agvId, " taskstatus=" + str(AgvTaskType.TRANSPORT), " taskexesid=''", "`JSON` = " + jsonItemUpdate("TO", to),
				AND + " sitestatus=" + str(AgvSiteStatus.INIT), AND + " taskstatus=" + str(AgvTaskType.FREE));
	}

	public void goWork(Integer agvId, String taskexesid) {
		update(agvId, " sitestatus=" + str(AgvSiteStatus.MOVING), " taskexesid=" + str(taskexesid),
				AND + " sitestatus=" + str(AgvSiteStatus.INIT), AND + " taskstatus=" + str(AgvTaskType.TRANSPORT));
		ctrlModule.startup(agvId);
	}

	public void workOver(Integer agvId) {
		update(agvId, " sitestatus=" + str(AgvSiteStatus.INIT), " taskstatus=" + str(AgvTaskType.FREE),
				" taskexesid=''", AND + " sitestatus=" + str(AgvSiteStatus.MOVING),
				AND + " taskstatus=" + str(AgvTaskType.TRANSPORT));
	}

}
