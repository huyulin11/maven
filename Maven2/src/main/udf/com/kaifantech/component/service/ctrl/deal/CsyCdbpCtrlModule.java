package com.kaifantech.component.service.ctrl.deal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.bean.msg.agv.SeerAgvMsgTaskBean;
import com.kaifantech.cache.manager.AppCache;
import com.kaifantech.component.comm.manager.agv.IAgvManager;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.component.service.taskexe.ctrl.ISeerCtrlModule;
import com.kaifantech.init.sys.params.SeerCacheKeys;
import com.kaifantech.init.sys.qualifier.CsyCdbpQualifier;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvMoveStatus;

@Service(CsyCdbpQualifier.CTRL_MODULE)
public class CsyCdbpCtrlModule implements ISeerCtrlModule {
	@Autowired
	private IAgvManager agvManager;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_INFO_DAO)
	private AgvInfoDao agvInfoDao;

	private boolean isTest = false;

	public void control(IotClientBean agvBean, SeerAgvMsgTaskBean agvMsg) {
		if (!AgvMoveStatus.CONTINUE.equals(agvInfoDao.getMoveStatus(agvBean.getId()))) {
			if (isTest) {
				AppCache.worker().hset(SeerCacheKeys.pauseStat(), agvBean.getId(), 1);
				pause(agvBean.getId());
			}
		} else {
			if (isTest) {
				AppCache.worker().hset(SeerCacheKeys.pauseStat(), agvBean.getId(), 0);
				startup(agvBean.getId());
			}
		}
	}

	public void pause(Integer agvId) {
		agvManager.pause(agvId);
	}

	public void startup(Integer agvId) {
		agvManager.startup(agvId);
	}
}
