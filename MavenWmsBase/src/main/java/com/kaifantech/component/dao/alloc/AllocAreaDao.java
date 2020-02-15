package com.kaifantech.component.dao.alloc;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.wms.alloc.AllocationAreaInfoBean;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;
import com.kaifantech.util.log.AppFileLogger;

@Service
public class AllocAreaDao extends BaseDao {
	private List<AllocationAreaInfoBean> getAllocationAreaInfoBeanList(String whereSQL) {
		return (List<AllocationAreaInfoBean>) jdbcTemplate().query(
				"SELECT a.* FROM " + AppTables.ALLOCATION_AREA_INFO + " a where a.delflag=0 "
						+ " and exists (select 1 from " + AppTables.ALLOC_ITEM_RELATION
						+ " i where i.delflag=0 and a.areaid=i.areaid) " + (AppTool.isNullStr(whereSQL) ? "" : whereSQL)
						+ " order by areaId ",
				new BeanPropertyRowMapper<AllocationAreaInfoBean>(AllocationAreaInfoBean.class));
	}

	public List<AllocationAreaInfoBean> getAllAllocationAreaInfoBean() {
		return getAllocationAreaInfoBeanList(null);
	}

	public List<Map<String, Object>> getAreaStorageInfo() {
		String sql = "SELECT " + i("environment") + ", areaId,	" + "GROUP_CONCAT(DISTINCT " + i("inSkuNames")
				+ ") inSkuNames," + "GROUP_CONCAT(DISTINCT " + i("name") + ") allowedSkuNames,"
				+ " GROUP_CONCAT(DISTINCT " + i("allowedSkuTypeNames") + ") allowedSkuTypeNames FROM (" + ""
				+ "SELECT   ppp." + i("environment") + ", ppp.areaId,  si." + i("name") + "," + "  CONCAT(siin."
				+ i("name") + ", '-', SUM(ppp.num)) inSkuNames,  " + "  GROUP_CONCAT(DISTINCT sti." + i("name")
				+ ") allowedSkuTypeNames " + " FROM  (SELECT t." + i("environment") + ", a.areaId, a." + i("text")
				+ ", t." + i("columnId") + ", t." + i("allowedSkuId") + ",t." + i("allowedSkuType") + ",item."
				+ i("id") + ", item." + i("num") + " " + " FROM " + AppTables.ALLOCATION_AREA_INFO
				+ " a,(SELECT  c." + i("environment") + ", c." + i("areaId") + ",  c." + i("columnId")
				+ ",  c." + i("allowedSkuId") + ",  c." + i("allowedSkuType") + "" + "   FROM   "
				+ AppTables.ALLOCATION_COLUMN_INFO + "  c) t left join  (SELECT  i." + i("areaId") + ",  i."
				+ i("columnId") + ",  i." + i("id") + ",i." + i("num") + " " + "   FROM   "
				+ AppTables.ALLOC_ITEM_INFO_WITH_RELATION
				+ "  i WHERE (num IS NOT NULL AND num > 0 AND status > 1)  AND delflag = 0) item " + "   ON t."
				+ i("areaId") + " = item.areaId AND t.columnId = item.columnId " + " WHERE a." + i("areaId")
				+ " = t.areaId  ) ppp  LEFT JOIN  " + AppTables.SKU_INFO + " si ON ppp.allowedskuid = si.id"
				+ "  LEFT JOIN " + AppTables.SKU_INFO + " siin ON ppp.id = siin.id" + "  LEFT JOIN "
				+ AppTables.SKU_TYPE + " sti ON ppp.allowedskutype = sti.id" + " GROUP BY ppp." + i("environment")
				+ ", ppp.areaId,si." + i("name") + " ,siin." + i("name") + " " + ") summ GROUP BY "
				+ i("environment") + ", areaId ";
		AppFileLogger.warning(sql);
		return jdbcTemplate().queryForList(sql);
	}

	public List<AllocationAreaInfoBean> getAllocsBean(Integer areaId) {
		return getAllocationAreaInfoBeanList(" where areaId= " + areaId);
	}

}
