package com.kaifantech.component.service.alloc.item;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.entity.AppConfFormMap;
import com.kaifantech.mappings.AppBaseMapper;

@Service
public class AppConfService implements IBaseService<AppConfFormMap> {
	@Inject
	private AppBaseMapper<AppConfFormMap> mapper;

	public AppMsg doAddEntity(AppConfFormMap formMap) throws Exception {
		List<FormMap> list = formMap.getList();
		initFormMap(formMap);
		for (FormMap value : list) {
			formMap.set("text", value.get("allocItem"));
			value.set("json", JSONObject.toJSON(value).toString());
			mapper.addEntity(formMap);
		}
		return AppMsg.success();
	}

	public void doAddEntity(List<AppConfFormMap> formMapList) throws Exception {
		for (AppConfFormMap formMap : formMapList) {
			doAddEntity(formMap);
		}
	}

	private void initFormMap(AppConfFormMap formMap) {
		formMap.set("environment", "1");
		formMap.set("areaId", "0");
		formMap.set("colId", "0");
		formMap.set("rowId", "0");
		formMap.set("zId", "0");
		formMap.set("columnId", "0");
		formMap.set("status", "1");
		formMap.set("skuId", "0");
		formMap.set("num", "0");
	}

	@Override
	public AppBaseMapper<AppConfFormMap> getMapper() {
		return mapper;
	}
}