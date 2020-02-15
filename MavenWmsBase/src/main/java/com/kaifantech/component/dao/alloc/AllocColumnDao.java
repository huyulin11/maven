package com.kaifantech.component.dao.alloc;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.wms.alloc.AllocColumnInfoBean;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class AllocColumnDao extends BaseDao {
	private List<AllocColumnInfoBean> getAllocationColumnInfoBeanList(String whereSQL) {
		return (List<AllocColumnInfoBean>) jdbcTemplate().query(
				"SELECT * FROM " + AppTables.ALLOCATION_COLUMN_INFO
						+ " where delflag=0 and areaId in (SELECT areaId FROM " + AppTables.ALLOCATION_AREA_INFO
						+ " WHERE delflag=0) " + (AppTool.isNullStr(whereSQL) ? "" : whereSQL),
				new BeanPropertyRowMapper<AllocColumnInfoBean>(AllocColumnInfoBean.class));
	}

	private Integer getCount(String whereSQL) {
		return jdbcTemplate().queryForObject("SELECT count(*) FROM " + AppTables.ALLOCATION_COLUMN_INFO
				+ " where delflag=0 and areaId in (SELECT areaId FROM " + AppTables.ALLOCATION_AREA_INFO
				+ " WHERE delflag=0) " + (AppTool.isNullStr(whereSQL) ? "" : whereSQL), Integer.class);
	}

	public List<AllocColumnInfoBean> getAllAllocationColumnInfoBean() {
		return getAllocationColumnInfoBeanList(null);
	}

	public Integer getAllAllocationColumnInfoBeanCount(Integer areaId, Integer colId) {
		return getCount(" and areaId= " + areaId + " and colId= " + colId);
	}

	public AllocColumnInfoBean getAllocationColumnInfoBeanBy(Integer areaId, Integer colId) {
		List<AllocColumnInfoBean> columnBeanList = getAllocationColumnInfoBeanList(
				" and areaId= " + areaId + " and colId= " + colId);
		return (columnBeanList == null || columnBeanList.size() == 0) ? null : columnBeanList.get(0);
	}

	public List<AllocColumnInfoBean> getAllocsBean(Integer colId) {
		return getAllocationColumnInfoBeanList(" where colId= " + colId);
	}

	public int insert(Integer areaId, Integer colId, Integer environment, Integer allowedSkuId) {
		return jdbcTemplate().update("insert into  " + AppTables.ALLOCATION_COLUMN_INFO + " (  " + i("areaId")
				+ ",  " + i("colId") + ", " + i("environment") + ", " + i("allowedSkuId") + ") VALUES  ("
				+ s(areaId) + "," + s(colId) + "," + s(environment) + "," + allowedSkuId + ")");
	}
}
