package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.bean.tasksite.TaskSiteDevBean;
import com.kaifantech.entity.TaskSiteDevFormMap;

public interface TaskSiteDevMapper extends AppBaseMapper<TaskSiteDevFormMap> {

	public List<TaskSiteDevFormMap> find(TaskSiteDevFormMap formMap);

	public List<TaskSiteDevBean> findAll(TaskSiteDevBean taskSiteBean);

	public int addBean(TaskSiteDevBean taskSiteBean);
}
