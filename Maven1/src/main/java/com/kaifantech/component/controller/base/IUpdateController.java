package com.kaifantech.component.controller.base;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;

public interface IUpdateController<TT extends FormMap> extends IBaseService<TT>, IBaseAppCompnent<TT> {
	default JSONObject defaultAddEntity() throws Exception {
		getService().doAddEntity(getFormMap());
		return AppMsg.success().toAlbbJson();
	}

	default JSONObject defaultDeleteEntity() throws Exception {
		String[] ids = AppSession.getParaValue("ids").split(":");
		TT formMap = getFormMap();
		for (String id : ids) {
			formMap.set("id", id);
			getMapper().deleteByLogic(formMap);
		}
		return AppMsg.success().toAlbbJson();
	}

	default JSONObject defaultEditEntity() throws Exception {
		TT formMap = getFormMap();
		getMapper().editEntity(formMap);
		return AppMsg.success().toAlbbJson();
	}

	IBaseService<TT> getService();
}