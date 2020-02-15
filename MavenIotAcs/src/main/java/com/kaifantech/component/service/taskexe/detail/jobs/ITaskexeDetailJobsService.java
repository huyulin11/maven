
package com.kaifantech.component.service.taskexe.detail.jobs;

import java.util.List;

import com.kaifantech.bean.taskexe.TaskexeDetailBean;
import com.kaifantech.bean.taskexe.TaskexeBean;

public interface ITaskexeDetailJobsService {
	public List<TaskexeDetailBean> getList(TaskexeBean taskexeBean) throws Exception;
}
