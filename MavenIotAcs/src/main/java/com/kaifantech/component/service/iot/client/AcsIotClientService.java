package com.kaifantech.component.service.iot.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.component.dao.iot.client.IotClientDao;
import com.kaifantech.entity.IotClientFormMap;
import com.kaifantech.init.sys.qualifier.AcsQualifier;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.IotClientMapper;
import com.kaifantech.util.constant.taskexe.ctrl.IotDevType;

@Service(AcsQualifier.IOT_CLIENT_SERVICE)
public class AcsIotClientService implements IIotClientService {
	@Inject
	protected IotClientMapper mapper;

	@Inject
	protected IotClientDao iotClientDao;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_INFO_DAO)
	protected AgvInfoDao agvInfoDao;

	protected List<IotClientBean> list = new CopyOnWriteArrayList<>();

	protected Map<String, List<IotClientBean>> map = new HashMap<>();

	protected synchronized void doGetList() {
		list = iotClientDao.getList();
		list.stream().forEach((bean) -> {
			List<IotClientBean> items = map.get(bean.getDevtype());
			if (items == null) {
				items = new CopyOnWriteArrayList<>();
				map.put(bean.getDevtype(), items);
			}
			items.add(bean);
		});
	}

	public synchronized List<IotClientBean> getList() {
		if (AppTool.isNull(list)) {
			doGetList();
		}
		return list;
	}

	public synchronized List<IotClientBean> getList(String devType) {
		if (AppTool.isNull(devType)) {
			return null;
		}
		List<IotClientBean> list = getMap().get(devType);
		if (list == null) {
			list = new CopyOnWriteArrayList<>();
			getMap().put(devType, list);
		}
		return list;
	}

	public synchronized Map<String, List<IotClientBean>> getMap() {
		if (AppTool.isNull(map)) {
			doGetList();
		}
		return map;
	}

	public IotClientBean getBean(Integer id) {
		try {
			return getList().stream().filter((bean) -> id.equals(bean.getId())).iterator().next();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public AppBaseMapper<IotClientFormMap> getMapper() {
		return mapper;
	}

	@Override
	public List<IotClientBean> getAgvCacheList() {
		return getList(IotDevType.AGV_FANCY_IP);
	}
}