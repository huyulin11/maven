package com.kaifantech.component.service.tasksite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.bean.tasksite.TaskSiteDevBean;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.mappings.TaskSiteDevMapper;
import com.kaifantech.util.constant.taskexe.ctrl.IotDevType;
import com.kaifantech.util.log.AppFileLogger;

@Service
public class TaskSiteDevService {
	public List<TaskSiteDevBean> getAutodoorSite() {
		if (AppTool.isNull(autodoorList)) {
			getFullList();
		}
		return autodoorList;
	}

	public List<TaskSiteDevBean> getAutodoorSite(Integer devid) {
		List<TaskSiteDevBean> list = new ArrayList<>();
		for (TaskSiteDevBean taskSiteDevBean : getAutodoorSite()) {
			if (taskSiteDevBean.getDevid().equals(devid)) {
				list.add(taskSiteDevBean);
			}
		}
		return list;
	}

	public List<TaskSiteDevBean> getLiftSite() {
		if (AppTool.isNull(liftList)) {
			getFullList();
		}
		return liftList;
	}

	public List<TaskSiteDevBean> getLightSite() {
		if (AppTool.isNull(lightList)) {
			getFullList();
		}
		return lightList;
	}

	public Integer getAutodoorIdFromSite(Integer siteId) {
		try {
			for (TaskSiteDevBean key : getAutodoorSite()) {
				if (siteId.equals(key.getSiteid())) {
					return key.getDevid();
				}
			}
		} catch (Exception e) {
			AppFileLogger.warning("未能获取到" + siteId + "号站点对应的设备具体信息！");
		}
		return null;
	}

	public Integer getLiftIdFromSite(Integer siteId) {
		try {
			for (TaskSiteDevBean key : getLiftSite()) {
				if (siteId.equals(key.getSiteid())) {
					return key.getDevid();
				}
			}
		} catch (Exception e) {
			AppFileLogger.warning("未能获取到" + siteId + "号站点对应的设备具体信息！");
		}
		return null;
	}

	public Integer getLightIdFromSite(Integer siteId) {
		try {
			for (TaskSiteDevBean key : getLightSite()) {
				if (siteId.equals(key.getSiteid())) {
					return key.getDevid();
				}
			}
		} catch (Exception e) {
			AppFileLogger.warning("未能获取到" + siteId + "号站点对应的设备具体信息！");
		}
		return null;
	}

	public synchronized List<?> getFullList() {
		if (AppTool.isNull(fullList)) {
			fullList = mapper.findAll(null);
			fullList.stream().forEach((bean) -> {
				IotClientBean iot = iotClientService.getBean(bean.getDevid());
				if (!AppTool.isNull(iot)) {
					if (iot.getDevtype().equals(IotDevType.ZIGBEE_AUTODOOR)) {
						autodoorList.add(bean);
					}
					if (iot.getDevtype().equals(IotDevType.ZIGBEE_LIFT)) {
						liftList.add(bean);
					}
					if (iot.getDevtype().equals(IotDevType.ZIGBEE_LIGHT)) {
						lightList.add(bean);
					}
				}
			});
		}
		return fullList;
	}

	@Inject
	private TaskSiteDevMapper mapper;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	private IIotClientService iotClientService;

	private List<TaskSiteDevBean> fullList = new ArrayList<>();

	private List<TaskSiteDevBean> autodoorList = new ArrayList<>();

	private List<TaskSiteDevBean> liftList = new ArrayList<>();

	private List<TaskSiteDevBean> lightList = new ArrayList<>();
}