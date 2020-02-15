package com.kaifantech.component.service.taskexe.info;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppSet;
import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.component.dao.taskexe.info.TaskexeInfoDao;

@Service
public class TaskexeInfoService {
	@Autowired
	private TaskexeInfoDao taskexeTaskDao;

	public TaskexeBean getNextOne(Integer agvId) {
		List<TaskexeBean> taskexeBeanList = taskexeTaskDao.getToSendList(agvId);
		return AppSet.first(taskexeBeanList);
	}

	public TaskexeBean getNotOverOne(String taskid) {
		if (AppTool.isNull(taskid)) {
			return null;
		}
		List<TaskexeBean> taskexeBeanList = taskexeTaskDao.getNotOverList(taskid);
		return AppSet.first(taskexeBeanList);
	}

	public TaskexeBean getOne(String taskexesid) {
		if (AppTool.isNull(taskexesid)) {
			return null;
		}
		List<TaskexeBean> taskexeBeanList = taskexeTaskDao.getAllList(taskexesid);
		return AppSet.first(taskexeBeanList);
	}

	public TaskexeBean getNotOverOneF(Integer agvId) {
		List<TaskexeBean> taskexeBeanList = taskexeTaskDao.getNotOverListByF(agvId);
		return AppSet.first(taskexeBeanList);
	}

	public List<TaskexeBean> getNotOverOneFBean(List<AgvInfoBean> agvs) {
		List<Integer> agvIds = new ArrayList<>();
		for (AgvInfoBean bean : agvs) {
			agvIds.add(bean.getId());
		}
		return taskexeTaskDao.getNotOverListByF(agvIds);
	}

	public List<TaskexeBean> getNotOverList() {
		return taskexeTaskDao.getNotOverList();
	}

	public boolean isWorking(Integer agvId) {
		List<TaskexeBean> notOverTaskList = getNotOverList();
		if (notOverTaskList.stream().filter((taskexe) -> {
			return agvId.equals(taskexe.getAgvId());
		}).count() > 0) {
			return true;
		}
		return false;
	}

	public boolean isWorking(Integer agvId, String tasktype) {
		List<TaskexeBean> notOverTaskList = getNotOverList();
		if (notOverTaskList.stream().filter((taskexe) -> {
			return agvId.equals(taskexe.getAgvId()) && tasktype.equals(taskexe.getTasktype());
		}).count() > 0) {
			return true;
		}
		return false;
	}

	public List<TaskexeBean> getAllList(String taskexesid) {
		return taskexeTaskDao.getAllList(taskexesid);
	}

	public TaskexeBean getLatestOne(Integer agvId) {
		return AppSet.first(taskexeTaskDao.getList(" where agvId= " + agvId, " ORDER BY TIME DESC ", 1));
	}

	public List<TaskexeBean> getNewList() {
		List<TaskexeBean> taskexeBeanList = taskexeTaskDao.getNewList();
		if (AppTool.isNull(taskexeBeanList)) {
			return null;
		}
		return taskexeBeanList;
	}

	public List<TaskexeBean> getSuspendList() {
		List<TaskexeBean> taskexeBeanList = taskexeTaskDao.getSuspendList();
		if (AppTool.isNull(taskexeBeanList)) {
			return null;
		}
		return taskexeBeanList;
	}
}
