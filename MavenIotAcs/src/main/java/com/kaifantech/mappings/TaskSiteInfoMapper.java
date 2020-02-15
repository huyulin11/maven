package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.bean.tasksite.TaskSiteInfoBean;
import com.kaifantech.entity.TaskSiteFormMap;

public interface TaskSiteInfoMapper extends AppBaseMapper<TaskSiteFormMap> {

	public List<TaskSiteFormMap> find(TaskSiteFormMap formMap);

	public List<TaskSiteFormMap> findFuzzy(TaskSiteFormMap formMap);

	public List<TaskSiteInfoBean> findAll(TaskSiteInfoBean taskSiteBean);

	public int addBean(TaskSiteInfoBean taskSiteBean);
}
