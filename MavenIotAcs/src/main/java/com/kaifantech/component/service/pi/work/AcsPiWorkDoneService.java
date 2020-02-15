package com.kaifantech.component.service.pi.work;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvMoveStatus;

@Service
public class AcsPiWorkDoneService implements IPiWorkDoneService {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_INFO_DAO)
	private AgvInfoDao agvInfoDao;

	private List<Integer> toStop = new ArrayList<Integer>();
	private List<Integer> toWarning = new ArrayList<Integer>();
	private List<Integer> toContinue = new ArrayList<Integer>();

	public void doneControl() {
		for (Integer agvId : toStop) {
			if (AgvMoveStatus.CONTINUE.equals(agvInfoDao.getMoveStatus(agvId))) {
				agvInfoDao.pauseAuto(agvId);
			}
		}

		for (Integer agvId : toContinue) {
			if (!toWarning.contains(agvId) && !toStop.contains(agvId)) {
				if (AgvMoveStatus.PAUSE_SYS.equals(agvInfoDao.getMoveStatus(agvId))) {
					agvInfoDao.startup(agvId);
				}
			}
		}

		if (toStop.size() == 0 && toWarning.size() == 0 && toContinue.size() == 0) {
			for (AgvInfoBean agvBean : agvInfoDao.getList()) {
				if (AgvMoveStatus.PAUSE_SYS.equals(agvInfoDao.getMoveStatus(agvBean.getId()))) {
					agvInfoDao.startup(agvBean.getId());
				}
			}
		}

		toStop.clear();
		toWarning.clear();
		toContinue.clear();
	}

	public void addToStop(Integer forkliFtId) {
		if (!toStop.contains(forkliFtId)) {
			toStop.add(forkliFtId);
		}
	}

	public void addToContinue(Integer forkliFtId) {
		if (!toContinue.contains(forkliFtId)) {
			toContinue.add(forkliFtId);
		}
	}
}
