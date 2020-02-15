package com.kaifantech.component.dao.acs;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class LapAgvDao extends BaseDao {
	public List<Map<String, Object>> getLapAGVList() {
		return jdbcTemplate().queryForList(
				"SELECT a.* FROM " + AppTables.LAP_FORKLIFT_INFO + " a  WHERE a.delflag=0 order by lapId,sortBy ");
	}
}
