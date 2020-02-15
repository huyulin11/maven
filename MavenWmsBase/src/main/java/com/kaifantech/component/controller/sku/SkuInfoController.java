package com.kaifantech.component.controller.sku;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.erp.plugin.PageView;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.form.BaseADEController;
import com.kaifantech.component.service.sku.SkuInfoService;
import com.kaifantech.entity.SkuInfoFormMap;
import com.kaifantech.entity.SkuTypeFormMap;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.SkuInfoMapper;
import com.kaifantech.mappings.SkuTypeMapper;

@Controller
@SystemLog(module = "SKU类型管理")
@RequestMapping("/sku/info/")
public class SkuInfoController extends BaseADEController<SkuInfoFormMap> {
	@Inject
	private SkuInfoMapper mapper;
	@Inject
	private SkuTypeMapper skuTypeMapper;

	@Inject
	private SkuInfoService service;

	@ResponseBody
	@RequestMapping("addEntity")
	@Transactional(readOnly = false)
	@SystemLog(methods = "新增")
	public JSONObject addEntity() {
		try {
			FormMap form = getFormMap(false);
			AppMsg msg = service.doAddEntity(new SkuInfoFormMap(form));
			if (msg.getCode() < 0) {
				return msg.toAlbbJson();
			}
		} catch (Exception e) {
			return AppMsg.fail("添加出库单异常" + e.getMessage()).toAlbbJson();
		}
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("findByName")
	public PageView findByName(Integer pageNow, Integer pageSize) throws Exception {
		SkuInfoFormMap formMap = getFormMap();
		formMap = toFormMap(formMap, pageNow, pageSize);
		formMap.set(BaseSqlConstants.COL_DELFLAG, "0");
		List<SkuInfoFormMap> data = mapper.findByName(formMap);
		return getPageView().setRecords(data);
	}

	public JSONObject defaultEditEntity(String txtGroupsSelect) throws Exception {
		SkuInfoFormMap formMap = getFormMap();
		if (!AppTool.isNull(formMap.get("type"))) {
			String type = formMap.get("type").toString();
			SkuTypeFormMap skuTypeFormMap = new SkuTypeFormMap().setRtn("name", type);
			SkuTypeFormMap types = skuTypeMapper.findOne(skuTypeFormMap);
			if (AppTool.isNull(types)) {
				skuTypeMapper.add(skuTypeFormMap);
			}
		}
		mapper.editEntity(formMap);
		return AppMsg.success().toAlbbJson();
	}

	public AppBaseMapper<SkuInfoFormMap> getMapper() {
		return mapper;
	}

	@Override
	public IBaseService<SkuInfoFormMap> getService() {
		return service;
	}
}