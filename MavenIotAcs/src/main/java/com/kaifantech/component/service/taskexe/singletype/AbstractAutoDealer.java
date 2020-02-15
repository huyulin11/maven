package com.kaifantech.component.service.taskexe.singletype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.component.dao.ControlInfoDao;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.component.dao.taskexe.op.TaskexeOpDao;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.component.service.taskexe.info.TaskexeInfoService;
import com.kaifantech.init.sys.qualifier.UdfQualifier;

public abstract class AbstractAutoDealer {
	@Autowired
	protected TaskexeInfoService taskexeInfoService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_INFO_DAO)
	protected AgvInfoDao agvInfoDao;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	protected IIotClientService iotClientService;

	@Autowired
	protected TaskexeOpDao taskexeTaskDao;

	@Autowired
	protected ControlInfoDao controlInfoDao;

	public AppMsg newTask(AgvInfoBean targetAgv, String tasktype) {
		return AppMsg.success();
	}

	public AppMsg newTask(AgvInfoBean targetAgv) {
		return AppMsg.success();
	}

	public AppMsg watchTask(AgvInfoBean tmBean) {
		return AppMsg.success();
	}

	protected String getSid(String key) {
		String paperid = controlInfoDao.getSid(key);
		return paperid;
	}
}
