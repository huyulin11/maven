package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.bean.tasksite.TaskSiteLogicBean;
import com.kaifantech.entity.TaskSiteLogicFormMap;

public interface TaskSiteLogicMapper extends AppBaseMapper<TaskSiteLogicFormMap> {

	public List<TaskSiteLogicFormMap> findPage(TaskSiteLogicFormMap formMap);

	public List<TaskSiteLogicBean> findAll(TaskSiteLogicBean taskSiteBean);

	public int add(TaskSiteLogicFormMap formMap);

}
