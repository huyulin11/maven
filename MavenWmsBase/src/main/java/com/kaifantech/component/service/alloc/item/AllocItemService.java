package com.kaifantech.component.service.alloc.item;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.entity.AllocItemFormMap;
import com.kaifantech.init.sys.qualifier.WmsQualifier;
import com.kaifantech.mappings.AllocItemMapper;
import com.kaifantech.mappings.AppBaseMapper;

@Service(WmsQualifier.ALLOC_ITEM_SERVICE)
public class AllocItemService implements IBaseService<AllocItemFormMap> {
	@Inject
	protected AllocItemMapper mapper;

	public AppMsg doAddEntity(AllocItemFormMap formMap) throws Exception {
		List<FormMap> list = formMap.getList();
		initFormMap(formMap);
		for (FormMap value : list) {
			value.set("text", value.get("allocItem"));
			AllocItemFormMap obj = new AllocItemFormMap(value);
			mapper.addEntity(obj);
		}
		return AppMsg.success();
	}

	public void doAddEntity(List<AllocItemFormMap> formMapList) throws Exception {
		for (AllocItemFormMap formMap : formMapList) {
			doAddEntity(formMap);
		}
	}

	protected void initFormMap(AllocItemFormMap formMap) {
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
	public AppBaseMapper<AllocItemFormMap> getMapper() {
		return mapper;
	}
}