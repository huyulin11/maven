package com.kaifantech.component.service.tasksite.info;

import java.util.List;
import java.util.Map;

import com.kaifantech.bean.tasksite.TaskSiteInfoBean;

public interface ITaskSiteInfoService {
	public Map<Integer, TaskSiteInfoBean> get(String taskSiteType);

	public TaskSiteInfoBean containsRtn(List<TaskSiteInfoBean> nextSites, Integer targetsiteid);

	public List<TaskSiteInfoBean> getCacheList();

	public TaskSiteInfoBean getBean(Integer siteid);
}