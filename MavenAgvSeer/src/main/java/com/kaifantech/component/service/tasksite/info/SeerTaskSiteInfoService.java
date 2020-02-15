package com.kaifantech.component.service.tasksite.info;

import org.springframework.stereotype.Service;

import com.kaifantech.bean.tasksite.TaskSiteInfoBean;
import com.kaifantech.init.sys.qualifier.SeerQualifier;
import com.kaifantech.util.constant.taskexe.TaskSiteType;

@Service(SeerQualifier.TASK_SITE_INFO_SERVICE)
public class SeerTaskSiteInfoService extends AcsTaskSiteInfoService {
	public TaskSiteInfoBean getCkSite(Integer devId) {
		try {
			return get(TaskSiteType.CHUKU).get(devId);
		} catch (Exception e) {
			return null;
		}
	}

	public TaskSiteInfoBean getRkSite(Integer devId) {
		try {
			return get(TaskSiteType.RUKU).get(devId);
		} catch (Exception e) {
			return null;
		}
	}

	public TaskSiteInfoBean getChargeSite(Integer devId) {
		try {
			return get(TaskSiteType.CHARGE).get(devId);
		} catch (Exception e) {
			return null;
		}
	}
}