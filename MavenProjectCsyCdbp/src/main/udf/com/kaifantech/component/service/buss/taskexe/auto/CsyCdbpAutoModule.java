package com.kaifantech.component.service.buss.taskexe.auto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.component.service.taskexe.auto.IAutoModule;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvSiteStatus;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;
import com.kaifantech.util.log.AppFileLogger;

@Component
@Lazy(false)
public class CsyCdbpAutoModule implements IAutoModule {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_INFO_DAO)
	private AgvInfoDao agvInfoDao;

	@Autowired
	private CsyCdbpAutoChargeDealer autoChargeDealer;
	@Autowired
	private CsyCdbpAutoTransportDealer autoTransportDealer;

	public synchronized void doWork() {
		charge();
	}

	private synchronized void charge() {
		List<AgvInfoBean> list = agvInfoDao.getChargedList();
		AppMsg msg = AppMsg.fail();
		for (AgvInfoBean agv : list) {
			if ((AgvSiteStatus.INIT.equals(agv.getSitestatus()) && AgvTaskType.GOTO_CHARGE.equals(agv.getTaskstatus()))
					|| (AgvSiteStatus.CHARGING.equals(agv.getSitestatus())
							&& AgvTaskType.BACK_CHARGE.equals(agv.getTaskstatus()))) {
				msg = autoChargeDealer.newTask(agv, agv.getTaskstatus());
			}
		}

		list = agvInfoDao.getList(AgvTaskType.TRANSPORT);
		msg = AppMsg.fail();
		for (AgvInfoBean agv : list) {
			if (AgvSiteStatus.INIT.equals(agv.getSitestatus()) && AgvTaskType.TRANSPORT.equals(agv.getTaskstatus())) {
				msg = autoTransportDealer.newTask(agv, agv.getTaskstatus());
				break;
			}
		}
		if (!AppTool.isNull(msg.getMsg())) {
			AppFileLogger.error(msg.getMsg());
		}
	}
}
