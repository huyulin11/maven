package com.kaifantech.component.service.alloc.info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.component.dao.alloc.AllocItemDao;
import com.kaifantech.init.sys.qualifier.WmsQualifier;
import com.kaifantech.util.thread.ThreadTool;

@Service(WmsQualifier.ALLOC_INFO_SERVICE)
public class AllocInfoService implements IAllocInfoService {
	@Autowired
	protected AllocItemDao allocDao;

	private List<AllocItemInfoBean> cacheList;

	private boolean isCacheDataOk = false;

	@Override
	public synchronized List<AllocItemInfoBean> getCacheList() {
		if (AppTool.isNull(cacheList)) {
			cacheList = allocDao.getAllocs();
		}
		return cacheList;
	}

	@Override
	public AllocItemInfoBean getById(String id) {
		return allocDao.getAllocationInfoBean(id);
	}

	@Override
	public AllocItemInfoBean getByNameFromDB(String allocItemName) {
		return allocDao.getAllocationInfoBean(allocItemName);
	}

	@Override
	public AllocItemInfoBean getByNameFromCache(String allocName) {
		if (!isCacheDataOk) {
			ThreadTool.run(() -> {
				getCacheList();
				isCacheDataOk = true;
			});
			return getByNameFromDB(allocName);
		}
		try {
			return getCacheList().stream().filter((bean) -> (allocName.equals(bean.getText()))).iterator().next();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public AllocItemInfoBean getEmptyOne(Object warehouse, int seq) {
		return allocDao.getEmptyOne(warehouse, seq);
	}

	@Override
	public AllocItemInfoBean getByTaskid(String taskid) {
		return null;
	}

	public List<AllocItemInfoBean> getAllocsToShow(Integer areaId) {
		return allocDao.getAllocsToShow(areaId);
	}

	public List<AllocItemInfoBean> getAllocs() {
		return allocDao.getAllocs();
	}
}
