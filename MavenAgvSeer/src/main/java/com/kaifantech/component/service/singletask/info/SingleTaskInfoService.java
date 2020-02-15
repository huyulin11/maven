package com.kaifantech.component.service.singletask.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.singletask.SingletaskBean;
import com.kaifantech.component.dao.singletask.SingletaskInfoDao;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

@Service
public class SingleTaskInfoService {

	@Autowired
	private SingletaskInfoDao singletaskDao;

	private Map<Object, List<SingletaskBean>> cacheMap = new HashMap<Object, List<SingletaskBean>>();

	private List<SingletaskBean> cacheList = new ArrayList<SingletaskBean>();

	public void init() {
		this.cacheMap.clear();
		this.cacheList.clear();
	}

	public synchronized List<SingletaskBean> getList() {
		if (AppTool.isNull(cacheList) || cacheList.size() <= 0) {
			cacheList = singletaskDao.getList();
		}
		return cacheList;
	}

	public List<SingletaskBean> getListForCurrentUser(Integer agvId) {
		return singletaskDao.getSingletaskForCurrentUser(agvId);
	}

	public List<SingletaskBean> getListForCurrentUser2() {
		List<SingletaskBean> list = cacheMap.get(AppSession.getUserId());
		if (AppTool.isNull(list)) {
			list = singletaskDao.getSingletaskForCurrentUser(null);
			cacheMap.put(AppSession.getUserId(), list);
		}
		return list;
	}

	public SingletaskBean get(String taskid) {
		try {
			return getList().stream().filter((bean) -> taskid.equals(bean.getId())).iterator().next();
		} catch (Exception e) {
			return null;
		}
	}

	public SingletaskBean getByTaskName(String taskName) {
		if (AppTool.isNull(taskName)) {
			return null;
		}
		try {
			return getList().stream().filter((bean) -> taskName.equals(bean.getTaskname())).iterator().next();
		} catch (Exception e) {
			return null;
		}
	}

	public List<SingletaskBean> getByAllocId(Integer allocId) {
		try {
			List<SingletaskBean> sl = new ArrayList<SingletaskBean>();
			for (SingletaskBean bean : getList()) {
				if (!AppTool.isNull(bean.getAllocid()) && bean.getAllocid().equals(allocId)
						&& !AppTool.isNull(bean.getTasktype()) && (AgvTaskType.RECEIPT.equals(bean.getTasktype())
								|| AgvTaskType.SHIPMENT.equals(bean.getTasktype()))) {
					sl.add(bean);
				}
			}
			return sl;
		} catch (Exception e) {
			return null;
		}
	}

	public SingletaskBean getBy(Integer allocId, Integer agvId, Integer lapId) {
		try {
			List<SingletaskBean> sl = new ArrayList<SingletaskBean>();
			for (SingletaskBean bean : getList()) {
				if (!AppTool.isNull(bean.getAllocid()) && bean.getAllocid().equals(allocId)
						&& !AppTool.isNull(bean.getTasktype()) && AgvTaskType.RECEIPT.equals(bean.getTasktype())
						&& lapId.equals(bean.getLapid()) && (agvId.equals(bean.getAgvid()))) {
					sl.add(bean);
				}
			}
			return sl == null || sl.size() >= 2 || sl.size() == 0 ? null : sl.get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public List<SingletaskBean> getBy(Integer allocId) {
		try {
			List<SingletaskBean> sl = new ArrayList<SingletaskBean>();
			getList().stream().filter((bean) -> (!AppTool.isNull(bean.getAllocid()) && bean.getAllocid().equals(allocId)
					&& !AppTool.isNull(bean.getTasktype()))).forEach(sl::add);
			return sl;
		} catch (Exception e) {
			return null;
		}
	}

	public long getCountBy(Integer allocId) {
		try {
			return getList().stream()
					.filter((bean) -> (!AppTool.isNull(bean.getAllocid()) && bean.getAllocid().equals(allocId)
							&& !AppTool.isNull(bean.getTasktype()) && AgvTaskType.RECEIPT.equals(bean.getTasktype())))
					.count();
		} catch (Exception e) {
			return 0;
		}
	}

}
