package com.kaifantech.component.service.taskexe.watch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kaifantech.bean.msg.agv.SeerAgvMsgTaskBean;
import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.bean.taskexe.TaskexeDetailBean;
import com.kaifantech.component.dao.taskexe.op.TaskexeOpDao;
import com.kaifantech.component.service.taskexe.add.ITaskexeAddService;
import com.kaifantech.component.service.tasksite.info.AcsTaskSiteInfoService;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.mappings.taskexe.TaskexeDetailMapper;

@Service
public class SeerTaskexeWatchDealer {
	@Autowired
	protected TaskexeOpDao taskexeTaskDao;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_TASKEXE_ADD_SERVICE)
	private ITaskexeAddService taskexeAddService;

	@Autowired
	protected TaskexeDetailMapper taskexeDetailMapper;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_TASK_SITE_INFO_SERVICE)
	protected AcsTaskSiteInfoService taskSiteInfoService;

	public void watchSingleSite(TaskexeBean taskexeBean, SeerAgvMsgTaskBean agvMsg, List<TaskexeDetailBean> detailList)
			throws Exception {
	}
}
