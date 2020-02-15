package com.kaifantech.component.service.taskexe.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.component.service.taskexe.info.TaskexeInfoService;
import com.kaifantech.init.sys.params.AppSysParameters;
import com.kaifantech.init.sys.qualifier.AcsQualifier;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.TaskexeOpFlag;

@Service(AcsQualifier.TASKEXE_CHECK_SERVICE)
public class AcsTaskexeCheckService implements ITaskexeCheckService {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_INFO_DAO)
	private AgvInfoDao agvInfoDao;

	@Autowired
	private TaskexeInfoService taskInfoService;

	public AppMsg checkTaskexeInfo(Integer agvId, TaskexeBean taskexeBean) {
		if (!TaskexeOpFlag.OVER.equals(taskexeBean.getOpflag())) {
			String msgStr = taskexeBean.getAgvId().equals(agvId) ? agvId + "号AGV尚有任务未执行完成！" : "其它agv尚有任务未执行完毕";
			AppMsg msg = new AppMsg(-1, msgStr);
			msg.setObject(taskexeBean);
			return msg;
		}
		return new AppMsg(0, "可以下达新任务！");
	}

	public AppMsg checkTaskexe(Integer agvId) {
		if (!AppSysParameters.isAllowCacheTask()) {
			AgvInfoBean agvInfo = agvInfoDao.get(agvId);
			if (AppTool.isNull(agvInfo)) {
				return new AppMsg(-1, "未能获取AGV当前状态！");
			}
			TaskexeBean taskexeBean = taskInfoService.getNotOverOneF(agvId);
			if (taskexeBean != null && !TaskexeOpFlag.OVER.equals(taskexeBean.getOpflag())) {
				AppMsg msg = checkTaskexeInfo(agvId, taskexeBean);
				if (msg.getCode() < 0) {
					return msg;
				}
			}
		}
		return new AppMsg(0, "可以下达新任务！");
	}

}
