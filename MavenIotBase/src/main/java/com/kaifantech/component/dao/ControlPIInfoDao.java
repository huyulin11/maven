package com.kaifantech.component.dao;

import org.springframework.stereotype.Service;

import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class ControlPIInfoDao extends BaseDao {
	public Integer getValueBy(String type, String key) {
		return jdbcTemplate().queryForObject("SELECT value FROM " + AppTables.CONTROL_PI_INFO + " where " + " `type`='"
				+ type + "'" + " and " + " `key`='" + key + "'" + " limit 0,1", Integer.class);
	}
}
