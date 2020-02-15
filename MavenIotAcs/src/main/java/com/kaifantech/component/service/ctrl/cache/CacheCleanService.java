package com.kaifantech.component.service.ctrl.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.dao.acs.AgvMsgDao;
import com.kaifantech.component.dao.acs.AgvStatusDao;

@Service
public class CacheCleanService {

	@Autowired
	private AgvStatusDao agvStatusDao;

	@Autowired
	private AgvMsgDao msgDao;

	public AppMsg deleteOUTOfDateData() {
		try {
			msgDao.deleteOUTOfDateMsgFromAGV();
			agvStatusDao.deleteOUTOfDateSOPMsg();
		} catch (Exception e) {
			return new AppMsg(-1, "");
		}
		return AppMsg.success();
	}

}
