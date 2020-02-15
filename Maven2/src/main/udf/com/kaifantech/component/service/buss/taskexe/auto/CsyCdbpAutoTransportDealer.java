package com.kaifantech.component.service.buss.taskexe.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.component.dao.agv.info.AgvOpTransportDao;
import com.kaifantech.component.service.taskexe.singletype.AbstractAutoDealer;
import com.kaifantech.util.constant.taskexe.ctrl.AgvSiteStatus;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

@Component
public class CsyCdbpAutoTransportDealer extends AbstractAutoDealer {
	@Autowired
	protected AgvOpTransportDao agvOpTransportDao;

	@Transactional(propagation = Propagation.REQUIRED)
	public synchronized AppMsg newTask(AgvInfoBean targetAgv, String tasktype) {
		if (AppTool.and(!(AgvSiteStatus.INIT.equals(targetAgv.getSitestatus())
				&& AgvTaskType.TRANSPORT.equals(targetAgv.getTaskstatus())))) {
			return new AppMsg(-1, "下达运输任务到" + targetAgv.getId() + "号AGV出现异常：" + "任务参数有问题：" + tasktype);
		}
		try {
			String taskexesid = getSid();
			if (AgvTaskType.TRANSPORT.equals(tasktype)) {
				taskexeTaskDao.addATaskTo(taskexesid, targetAgv.getId(), 0, AgvTaskType.TRANSPORT, 2,
						targetAgv.getJsonItem("TO"));
				agvOpTransportDao.goWork(targetAgv.getId(), taskexesid);
				return new AppMsg(0, "下达运输任务！");
			}
			return new AppMsg(0, targetAgv.getId() + "号AGV" + "充电任务新增成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new AppMsg(-1, "出现异常：" + e.getMessage());
		}
	}

	public String getSid() {
		return getSid("AGV_TRANSPORT_SID");
	}
}
