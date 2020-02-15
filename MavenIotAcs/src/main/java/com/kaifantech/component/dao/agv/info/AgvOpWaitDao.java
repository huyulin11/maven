package com.kaifantech.component.dao.agv.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kaifantech.component.service.taskexe.ctrl.ICtrlModule;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvSiteStatus;

@Service
public class AgvOpWaitDao extends AgvInfoDao {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_CTRL_MODULE)
	private ICtrlModule ctrlModule;

	public void reach(Integer agvId) {
		update(agvId, " sitestatus=" + str(AgvSiteStatus.WAIT), AND + " sitestatus=" + str(AgvSiteStatus.MOVING));
	}

	public void over(Integer agvId) {
		update(agvId, " sitestatus=" + str(AgvSiteStatus.MOVING), AND + " sitestatus=" + str(AgvSiteStatus.WAIT));
		ctrlModule.startup(agvId);
	}

}
