package com.kaifantech.component.service.iot.client;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.scheduling.annotation.Scheduled;

import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.entity.IotClientFormMap;

public interface IIotClientService extends IBaseService<IotClientFormMap> {
	public List<IotClientBean> getList();

	public List<IotClientBean> getList(String devType);

	default List<IotClientBean> getList(String devType, String bussType) {
		List<IotClientBean> list = getList(devType);
		List<IotClientBean> bussList = new CopyOnWriteArrayList<>();
		for (IotClientBean iotClientBean : list) {
			if (bussType.equals(iotClientBean.getJsonItem("agvbusstype"))) {
				bussList.add(iotClientBean);
			}
		}
		return bussList;
	}

	public Map<String, List<IotClientBean>> getMap();

	public IotClientBean getBean(Integer id);

	default String getBussType(Integer id) {
		IotClientBean iotClientBean = getBean(id);
		if (!AppTool.isNull(iotClientBean)) {
			return iotClientBean.getJsonItem("agvbusstype");
		}
		return null;
	}

	@Scheduled(cron = "0 0 0/1 * * ?")
	default void shuffleAgvList() {
		Collections.shuffle(getAgvCacheList());
	}

	public List<IotClientBean> getAgvCacheList();
}