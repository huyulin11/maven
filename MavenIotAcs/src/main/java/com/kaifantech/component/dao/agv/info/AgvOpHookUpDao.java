package com.kaifantech.component.dao.agv.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kaifantech.component.service.taskexe.ctrl.ICtrlModule;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvSiteStatus;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

@Service
public class AgvOpHookUpDao extends AgvInfoDao {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_CTRL_MODULE)
	private ICtrlModule ctrlModule;

	public void reach(Integer agvId) {
		update(agvId, " sitestatus='" + AgvSiteStatus.HOOK_UP + "'", AND + " sitestatus='" + AgvSiteStatus.MOVING + "'",
				AND + "(" + " taskstatus='" + AgvTaskType.GOTO_INIT + "'" + " OR " + " taskstatus='"
						+ AgvTaskType.DELIVER + "'" + ")");
	}

	public void over(Integer agvId) {
		update(agvId, " sitestatus='" + AgvSiteStatus.MOVING + "'", AND + " sitestatus='" + AgvSiteStatus.HOOK_UP + "'",
				AND + "(" + " taskstatus='" + AgvTaskType.GOTO_INIT + "'" + " OR " + " taskstatus='"
						+ AgvTaskType.DELIVER + "'" + ")");
		ctrlModule.startup(agvId);
	}

}
