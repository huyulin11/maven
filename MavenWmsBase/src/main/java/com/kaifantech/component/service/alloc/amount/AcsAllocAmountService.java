package com.kaifantech.component.service.alloc.amount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.bean.wms.sku.SkuInfoBean;
import com.kaifantech.component.dao.alloc.AllocItemDao;
import com.kaifantech.component.service.sku.SkuInfoService;
import com.kaifantech.init.sys.qualifier.AcsQualifier;
import com.kaifantech.util.constant.taskexe.alloc.AllocationStatus;

@Service(AcsQualifier.ALLOC_AMOUNT_SERVICE)
public class AcsAllocAmountService implements IAllocAmountService {
	@Autowired
	protected AllocItemDao allocDao;

	@Autowired
	protected SkuInfoService skuInfoService;

	@Override
	public String changeAllocNum(Integer allocItemId, Integer skuId, Integer num) {
		AllocItemInfoBean item = allocDao.getAllocationInfoBean(allocItemId);
		if (AppTool.isNull(item)) {
			return "未找到对应有效货位信息！";
		}
		SkuInfoBean currentSku = null;
		if (!AppTool.isNull(skuId)) {
			currentSku = skuInfoService.getSkuInfoBeanById(skuId);
		} else if (!AppTool.isNull(item.getSkuId())) {
			currentSku = skuInfoService.getSkuInfoBeanById(item.getSkuId());
		}

		Integer areaId = item.getJsonItem("areaId", Integer.class);
		Integer colId = item.getJsonItem("colId", Integer.class);
		if (AllocationStatus.SUODING.equals(item.getStatus()) || AllocationStatus.YOUHUO.equals(item.getStatus())) {
			if (num == 0) {
				if (AllocationStatus.SUODING.equals(item.getStatus())) {
					allocDao.updateNum(allocItemId, item.getStatus(), AllocationStatus.KONGWEI,
							(currentSku == null ? null : currentSku.getId()), num);
					changeSku(areaId, colId, currentSku);
					dealWhenSuoding(item);
					return "目标数量为零，且货位状态为锁定，系统强制关闭正在执行的对应货位的任务！";
				} else {
					allocDao.updateNum(allocItemId, item.getStatus(), AllocationStatus.KONGWEI,
							(currentSku == null ? null : currentSku.getId()), num);
					changeSku(areaId, colId, currentSku);
					return "更新成功！" + "目标数量为零，且货位状态为有货，系统将货位状态变更为空位！";
				}
			} else {
				allocDao.updateNum(allocItemId, (currentSku == null ? null : currentSku.getId()), num);
				changeSku(areaId, colId, currentSku);
				return "更新成功！";
			}
		}

		if (AllocationStatus.KONGWEI.equals(item.getStatus())) {
			if (num == 0) {
				return "目标数量为零，且货位状态为空位，无需更新！";
			} else {
				if (AppTool.isNull(skuId) || AppTool.isNull(currentSku)) {
					return "目标货位状态为空位，但未指定存放物品类型，无法更新！";
				} else {
					allocDao.updateNum(allocItemId, item.getStatus(), AllocationStatus.YOUHUO, currentSku.getId(), num);
					changeSku(areaId, colId, currentSku);
					return "更新成功！" + "目标数量大于零，且货位状态为空，系统将货位状态变更为有货！";
				}
			}
		}
		return null;
	}

	protected int changeSku(Integer areaId, Integer colId, SkuInfoBean currentSku) {
		int i = 0;
		if (currentSku != null) {
			List<AllocItemInfoBean> allocItemList = allocDao.getAllocsBy(areaId, colId);
			for (AllocItemInfoBean item : allocItemList) {
				i = i + allocDao.updateSku(item.getId(), currentSku.getId());
			}
		}
		return i;
	}

	public void dealWhenSuoding(AllocItemInfoBean item) {
	}
}
