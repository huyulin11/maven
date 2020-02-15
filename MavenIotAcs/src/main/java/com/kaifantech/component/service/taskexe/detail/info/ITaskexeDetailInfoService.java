
package com.kaifantech.component.service.taskexe.detail.info;

import java.util.List;

import com.kaifantech.bean.taskexe.TaskexeDetailBean;
import com.kaifantech.bean.taskexe.TaskexeBean;

public interface ITaskexeDetailInfoService {
	public List<TaskexeDetailBean> getList(TaskexeBean taskexeBean) throws Exception;

	public List<TaskexeDetailBean> find(TaskexeBean taskexeBean);

	public List<TaskexeDetailBean> logic(List<TaskexeDetailBean> list);

	public void deleteUntilYesterday();
}
