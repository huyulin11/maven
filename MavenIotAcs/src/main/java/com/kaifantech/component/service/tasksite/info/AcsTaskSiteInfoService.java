package com.kaifantech.component.service.tasksite.info;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.tasksite.TaskSiteInfoBean;
import com.kaifantech.bean.tasksite.TaskSiteLogicBean;
import com.kaifantech.component.service.tasksite.TaskSiteLogicService;
import com.kaifantech.entity.TaskSiteFormMap;
import com.kaifantech.init.sys.qualifier.AcsQualifier;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.TaskSiteInfoMapper;
import com.kaifantech.util.constant.taskexe.TaskSiteType;
import com.kaifantech.util.constant.taskexe.TurnSide;
import com.kaifantech.util.log.AppFileLogger;

@Service(AcsQualifier.TASK_SITE_INFO_SERVICE)
public class AcsTaskSiteInfoService implements ITaskSiteInfoService, IBaseService<TaskSiteFormMap> {
	public TaskSiteInfoBean getInitSite(Integer agvId) {
		try {
			return get(TaskSiteType.INIT).get(agvId);
		} catch (Exception e) {
			return null;
		}
	}

	public TaskSiteInfoBean getAllocSite(Integer allocId) {
		try {
			return get(TaskSiteType.ALLOC).get(allocId);
		} catch (Exception e) {
			return null;
		}
	}

	public Map<Integer, TaskSiteInfoBean> get(String taskSiteType) {
		initData();
		return sitesMap.get(taskSiteType);
	}

	public TaskSiteInfoBean containsRtn(List<TaskSiteInfoBean> nextSites, Integer targetsiteid) {
		for (TaskSiteInfoBean nextSite : nextSites) {
			if (targetsiteid.equals(nextSite.getId())) {
				return nextSite;
			}
		}
		return null;
	}

	public synchronized List<TaskSiteInfoBean> getCacheList(String filter) {
		List<TaskSiteInfoBean> filterList = getCacheList().stream()
				.filter(site -> "TRUE".equals(site.getJsonItem(filter))).collect(Collectors.toList());
		return filterList;
	}

	public synchronized List<TaskSiteInfoBean> getCacheList() {
		if (AppTool.isNull(cacheList)) {
			cacheList = mapper.findAll(null);
			for (TaskSiteInfoBean site : cacheList) {
				List<TaskSiteLogicBean> logics = taskSiteLogicService.getNextSites(site.getId());
				if (!AppTool.isNull(logics) && logics.size() > 0) {
					for (TaskSiteLogicBean logic : logics) {
						TaskSiteInfoBean next = cacheList.stream().filter((bean) -> {
							return bean.getId().equals(logic.getNextid());
						}).findFirst().get();
						String aspect = logic.getJsonItem("aspect");
						String direction = logic.getJsonItem("direction");
						String layer = logic.getJsonItem("layer");
						String tips = "";
						if (!AppTool.isNull(aspect)) {
							tips = "-ASPECT" + aspect;
						}
						if (!AppTool.isNull(layer)) {
							tips = tips + "-LAYER" + layer;
						}
						if (!AppTool.isNull(direction)) {
							tips = tips + "-DIRECTION" + direction;
						}
						if (logic.getSide().equals(TurnSide.LEFT)) {
							site.setRelative("LEFT" + tips, next);
						} else if (logic.getSide().equals(TurnSide.RIGHT)) {
							site.setRelative("RIGHT" + tips, next);
						} else if (logic.getSide().equals(TurnSide.MIDDLE)) {
							site.setRelative("MIDDLE" + tips, next);
						}
					}
				}
			}
		}
		return cacheList;
	}

	public TaskSiteInfoBean getBean(Integer siteid) {
		try {
			return getCacheList().stream().filter((bean) -> siteid.equals(bean.getId())).iterator().next();
		} catch (Exception e) {
			AppFileLogger.warning("未能获取到" + siteid + "号站点的具体信息！");
			return null;
		}
	}

	@Override
	public AppBaseMapper<TaskSiteFormMap> getMapper() {
		return mapper;
	}

	private synchronized void initData() {
		if (sitesMap.size() > 0) {
			return;
		}
		List<TaskSiteInfoBean> list = getCacheList();
		Iterator<TaskSiteInfoBean> filter = list.stream().filter((bean) -> {
			return true;
		}).iterator();
		while (filter.hasNext()) {
			TaskSiteInfoBean bean = filter.next();
			Map<Integer, TaskSiteInfoBean> map = sitesMap.get(bean.getSitetype());
			if (map == null) {
				map = new HashMap<>();
				sitesMap.put(bean.getSitetype(), map);
			}
			if (!AppTool.isNull(bean.getKeyId())) {
				map.put(bean.getKeyId(), bean);
			} else if (!AppTool.isNull(bean.getJsonItem("keyid"))) {
				map.put(Integer.parseInt(bean.getJsonItem("keyid")), bean);
			}
		}
	}

	@Inject
	private TaskSiteInfoMapper mapper;

	@Inject
	private TaskSiteLogicService taskSiteLogicService;

	private List<TaskSiteInfoBean> cacheList = null;

	private Map<String, Map<Integer, TaskSiteInfoBean>> sitesMap = new HashMap<>();
}