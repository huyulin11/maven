package com.kaifantech.component.controller.alloc.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.kaifantech.bean.wms.alloc.AllocationAreaInfoBean;
import com.kaifantech.bean.wms.sku.SkuInfoBean;
import com.kaifantech.bean.wms.sku.SkuTypeBean;
import com.kaifantech.component.dao.lap.LapDao;
import com.kaifantech.component.service.alloc.area.AllocAreaService;
import com.kaifantech.component.service.alloc.column.IAllocColumnService;
import com.kaifantech.component.service.alloc.info.IAllocInfoService;
import com.kaifantech.component.service.sku.SkuInfoService;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.log.AppFileLogger;

@Controller
@RequestMapping("/json/wms/")
public class JsonWmsController {

	@Autowired
	private IAllocColumnService allocColumnService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_ALLOC_INFO_SERVICE)
	private IAllocInfoService allocInfoService;

	@Autowired
	private SkuInfoService skuInfoService;

	@Autowired
	private LapDao lapDao;

	@Autowired
	private AllocAreaService allocAreaService;

	@RequestMapping("getAreaList")
	@ResponseBody
	public Object getAreaList() {
		try {
			List<AllocationAreaInfoBean> object = allocAreaService.getAllocationAreaInfoBeanListForShow();
			AppFileLogger.warning(object);
			return JSONArray.toJSON(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("getAllocsBy")
	@ResponseBody
	public Object getAllocsBy(Integer areaId) {
		return JSONArray.toJSON(allocInfoService.getAllocsToShow(areaId));
	}

	@RequestMapping("getAllocs")
	@ResponseBody
	public Object getAllocs() {
		return JSONArray.toJSON(allocInfoService.getAllocs());
	}

	@RequestMapping("getAllocColumn")
	@ResponseBody
	public Object getAllocColumn(Integer areaId) {
		return JSONArray.toJSON(allocColumnService.getListShowBy(areaId));
	}

	@RequestMapping("getSkuInfo")
	@ResponseBody
	public Object getSkuInfo() {
		try {
			List<SkuInfoBean> skuInfoList = skuInfoService.getSkuInfoBeanList();
			List<SkuTypeBean> skuTypeList = skuInfoService.getSkuTypeBeanList();
			Map<String, Object> map = new HashMap<>();
			map.put("skuInfo", skuInfoList);
			map.put("skuType", skuTypeList);
			return JSONArray.toJSON(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("changeSku")
	@ResponseBody
	public int changeSku(Integer lapId, Integer skuId) {
		return lapDao.updateLap(lapId, skuId);
	}

}
