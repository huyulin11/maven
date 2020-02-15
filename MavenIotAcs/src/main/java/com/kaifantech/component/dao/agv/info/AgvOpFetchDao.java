package com.kaifantech.component.dao.agv.info;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kaifantech.component.service.taskexe.ctrl.ICtrlModule;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvSiteStatus;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

@Service
public class AgvOpFetchDao extends AgvInfoDao {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_CTRL_MODULE)
	private ICtrlModule ctrlModule;

	public void command(Integer agvId, String to) {
		update(agvId, " json=''", AND + " sitestatus=" + str(AgvSiteStatus.INIT),
				AND + " taskstatus=" + str(AgvTaskType.FREE));
		update(agvId, " taskstatus=" + str(AgvTaskType.FETCH), " taskexesid=''", "`JSON` = " + jsonItemUpdate("TO", to),
				AND + " sitestatus=" + str(AgvSiteStatus.INIT), AND + " taskstatus=" + str(AgvTaskType.FREE));
	}

	public void command(Integer agvId, Integer middleStopSite, String to) {
		Map<String, Object> params = new HashMap<>();
		params.put("MIDDLE", middleStopSite);
		params.put("TO", to);
		update(agvId, " taskstatus=" + str(AgvTaskType.FETCH), " taskexesid=''", "`JSON` = " + jsonItemUpdate(params),
				AND + " sitestatus=" + str(AgvSiteStatus.INIT), AND + " taskstatus=" + str(AgvTaskType.FREE));
	}

	public void goWork(Integer agvId, String taskexesid) {
		update(agvId, " sitestatus=" + str(AgvSiteStatus.MOVING), " taskexesid=" + str(taskexesid),
				AND + " sitestatus=" + str(AgvSiteStatus.INIT), AND + " taskstatus=" + str(AgvTaskType.FETCH));
		ctrlModule.startup(agvId);
	}

	public void workOver(Integer agvId) {
		update(agvId, " sitestatus=" + str(AgvSiteStatus.INIT), " taskstatus=" + str(AgvTaskType.FREE),
				" taskexesid=''", AND + " sitestatus=" + str(AgvSiteStatus.MOVING),
				AND + " taskstatus=" + str(AgvTaskType.FETCH));
	}
}
