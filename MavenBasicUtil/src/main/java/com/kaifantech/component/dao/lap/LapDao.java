package com.kaifantech.component.dao.lap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class LapDao extends BaseDao {
	public List<Map<String, Object>> getLapList() {
		return jdbcTemplate().queryForList("SELECT a.* FROM " + AppTables.LAP_INFO + " a  WHERE a.delflag=0  ");
	}

	public int updateLap(Integer lapId, boolean flag) {
		return jdbcTemplate()
				.update("update " + AppTables.LAP_INFO + " set inUesd= " + (flag ? 1 : 0) + " where id=" + lapId);
	}

	public int updateLap(Integer lapId, Integer skuId) {
		return jdbcTemplate().update("update " + AppTables.LAP_INFO + " set skuId= " + skuId + " where id=" + lapId);
	}

	public int setLapInUesd(Integer lapId) {
		return updateLap(lapId, true);
	}

	public int setLapNotInUesd(Integer lapId) {
		return updateLap(lapId, false);
	}

}
