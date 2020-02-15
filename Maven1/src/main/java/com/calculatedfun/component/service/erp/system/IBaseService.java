package com.calculatedfun.component.service.erp.system;

import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.IBaseGetter;

public interface IBaseService<TT extends FormMap> extends IBaseGetter<TT> {
	default AppMsg doAddEntity(TT formMap) throws Exception {
		getMapper().addEntity(formMap);
		return AppMsg.success();
	}
}