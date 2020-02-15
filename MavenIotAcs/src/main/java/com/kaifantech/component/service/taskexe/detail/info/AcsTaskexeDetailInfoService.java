
package com.kaifantech.component.service.taskexe.detail.info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.bean.taskexe.TaskexeDetailBean;
import com.kaifantech.component.dao.taskexe.info.TaskexeInfoDao;
import com.kaifantech.component.service.tasksite.info.AcsTaskSiteInfoService;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.mappings.taskexe.TaskexeDetailJobsMapper;
import com.kaifantech.mappings.taskexe.TaskexeDetailMapper;
import com.kaifantech.mappings.taskexe.TaskexeDetailWorksMapper;
import com.kaifantech.util.log.AppFileLogger;

public abstract class AcsTaskexeDetailInfoService implements ITaskexeDetailInfoService {
	@Autowired
	protected TaskexeInfoDao taskexeTaskDao;

	@Autowired
	protected TaskexeDetailMapper taskexeDetailMapper;

	@Autowired
	protected TaskexeDetailWorksMapper taskexeDetailWorksMapper;

	@Autowired
	protected TaskexeDetailJobsMapper taskexeDetailJobsMapper;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_TASK_SITE_INFO_SERVICE)
	protected AcsTaskSiteInfoService taskSiteInfoService;

	public List<TaskexeDetailBean> find(TaskexeBean taskexeBean) {
		return taskexeDetailMapper
				.find(new TaskexeDetailBean(taskexeBean.getTaskexesid(), taskexeBean.getTasksequence()));
	}

	public void deleteUntilYesterday() {
		List<TaskexeBean> taskexeList = taskexeTaskDao.getOverListUntilYesterday();
		if (!AppTool.isNull(taskexeList)) {
			for (TaskexeBean taskexe : taskexeList) {
				taskexeDetailMapper.delete(taskexe);
				taskexeDetailWorksMapper.delete(taskexe);
				taskexeDetailJobsMapper.delete(taskexe);
				int i = taskexeTaskDao.delete(taskexe);
				if (i > 0) {
					AppFileLogger.warning("删除任务数据：" + taskexe.getTaskexesid() + "-" + taskexe.getTasksequence());
				}
			}
		}
	}

	public List<TaskexeDetailBean> logic(List<TaskexeDetailBean> list) {
		if (AppTool.isNull(list)) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			TaskexeDetailBean current = list.get(i);
			if (i > 0) {
				TaskexeDetailBean past = list.get(i - 1);
				current.setPast(past);
			}
			if (i < list.size() - 1) {
				TaskexeDetailBean next = list.get(i + 1);
				current.setNext(next);
			}
		}
		return list;
	}
}
