package com.kaifantech.component.service.iot.relation;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.calculatedfun.util.FormMap;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.bean.iot.relation.IotRelationBean;
import com.kaifantech.component.dao.iot.relation.IotRelationDao;
import com.kaifantech.entity.IotRelationFormMap;
import com.kaifantech.mappings.IotRelationMapper;

/**
 * 
 * 
 * @Email: mmm333zzz520@163.com
 * @version 3.0v
 */
@Service
public class IotRelationService {
	@Inject
	private IotRelationMapper mapper;

	@Inject
	private IotRelationDao dao;

	private List<IotRelationBean> iotRelationList = null;

	public void saveAll(FormMap formMap) throws Exception {
		List<FormMap> list = formMap.getList();
		for (FormMap item : list) {
			mapper.editEntity(item);
		}

	}

	public void doAddEntity(IotRelationFormMap formMap) throws Exception {
		mapper.add(formMap);
	}

	public List<IotRelationBean> getIotRelationList() {
		if (iotRelationList == null || iotRelationList.size() == 0) {
			iotRelationList = dao.getIotRelationBeanList();
		}
		return iotRelationList;
	}

	public IotRelationBean getIotRelationBean(Integer id) {
		try {
			return getIotRelationList().stream().filter((bean) -> id.equals(bean.getId())).iterator().next();
		} catch (Exception e) {
			return null;
		}
	}

	public IotRelationBean getIotRelationBean(Integer devid, String devtype) {
		try {
			return getIotRelationList().stream()
					.filter((bean) -> devid.equals(bean.getDevid()) && devtype.equals(bean.getDevtype())).iterator()
					.next();
		} catch (Exception e) {
			return null;
		}
	}

	public IotRelationBean getIotRelationBean(IotClientBean socketDev) {
		try {
			return getIotRelationList().stream().filter((bean) -> socketDev.getId().equals(bean.getDevid())
					&& socketDev.getDevtype().equals(bean.getDevtype())).iterator().next();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}