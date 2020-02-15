package com.kaifantech.util.msg.agv;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ExecutorService;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.component.cache.worker.AppBeanFactory;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.component.service.taskexe.module.ITaskexeModule;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.thread.ThreadTool;

public abstract class AcsAgvMsgCatcher implements java.util.Observer {
	protected static long number = 0;
	protected IotClientBean iotClientBean;
	protected static AgvInfoDao agvInfoDao = AppBeanFactory.get(UdfQualifier.DEFAULT_AGV_INFO_DAO);
	protected static ITaskexeModule taskexeModule = AppBeanFactory.get(UdfQualifier.DEFAULT_TASKEXE_MODULE);
	protected static ExecutorService pool = ThreadTool.getThreadPool(12);

	public AcsAgvMsgCatcher(IotClientBean iotClientBean) {
		this.iotClientBean = iotClientBean;
	}

	public IotClientBean getIotClientBean() {
		return iotClientBean;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (AppTool.isNull(arg)) {
			return;
		}
		updateAgvInfo(getIotClientBean().getId(), arg.toString());
	}

	protected static Map<Integer, Boolean> isRunning = new HashMap<>();

	public abstract void updateAgvInfo(Integer agvId, String msg);
}
