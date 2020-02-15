package com.kaifantech.component.service.sku;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.wms.sku.SkuInfoBean;
import com.kaifantech.bean.wms.sku.SkuTypeBean;
import com.kaifantech.component.dao.sku.SkuInfoDao;
import com.kaifantech.component.dao.sku.SkuTypeDao;
import com.kaifantech.entity.SkuInfoFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.SkuInfoMapper;

@Service
public class SkuInfoService implements IBaseService<SkuInfoFormMap> {

	@Autowired
	protected SkuInfoDao skuInfoDao;

	@Autowired
	protected SkuTypeDao skuTypeDao;

	private List<SkuInfoBean> skuInfoBeanList;
	private List<SkuTypeBean> skuTypeBeanList;

	@Inject
	private SkuInfoMapper mapper;

	public AppMsg doAddEntity(SkuInfoFormMap formMap) throws Exception {
		List<FormMap> valueList = formMap.getList();
		if (AppTool.isNull(valueList)) {
			return new AppMsg(-1, "无有效明细数据");
		}

		for (FormMap tmpFormMap : valueList) {
			SkuInfoFormMap detailFormMap = new SkuInfoFormMap(tmpFormMap);
			mapper.add(detailFormMap);
		}
		return new AppMsg(0, "新增成功！");
	}

	public List<SkuInfoBean> getSkuInfoBeanList() {
		skuInfoBeanList = skuInfoDao.getAllList();
		return skuInfoBeanList;
	}

	public List<SkuTypeBean> getSkuTypeBeanList() {
		skuTypeBeanList = skuTypeDao.getAllList();
		return skuTypeBeanList;
	}

	public SkuInfoBean getSkuInfoBeanById(Integer skuId) {
		try {
			return getSkuInfoBeanList().stream().filter((bean) -> (skuId.equals(bean.getId()))).iterator().next();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public SkuInfoBean getSkuInfoBeanByType(Integer skuTypeId) {
		return getSkuInfoBeanList().stream().filter((bean) -> (skuTypeId.equals(bean.getType()))).iterator().next();
	}

	@Override
	public AppBaseMapper<SkuInfoFormMap> getMapper() {
		return null;
	}

}
