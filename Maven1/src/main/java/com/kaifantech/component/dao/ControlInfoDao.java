package com.kaifantech.component.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class ControlInfoDao extends BaseDao {
	public List<Map<String, Object>> getControlInfo() {
		return jdbcTemplate().queryForList("SELECT * FROM " + AppTables.CONTROL_INFO);
	}

	public Integer getMaxZId() {
		return getValueByType("MAX_Z_ID");
	}

	public Integer getAgvsPI() {
		return getValueByType("AGVS_OPEN_PI");
	}

	public int updateAgvsPI(Integer v1) {
		return updateControlInfo(v1, null, "AGVS_OPEN_PI");
	}

	public Integer getSayGoToRobaticArmStep() {
		return getValueByType("SAY_GO_TO_ROBATICARM_STEP");
	}

	public Integer getMaxPointNumInPathInfo() {
		return getValueByType("MAX_POINT_NUM_IN_PATH_INFO");
	}

	public Integer getValueByType(String type) {
		try {
			return jdbcTemplate().queryForObject("SELECT value FROM " + AppTables.CONTROL_INFO + " where type="
					+ s(type) + orderBy("type") + limitRows(0), Integer.class);
		} catch (Exception e) {
			return null;
		}
	}

	public Integer getNextValueByType(String type) {
		updateControlInfoAddOne(type);
		return getValueByType(type);
	}

	public String getPrefixByType(String type) {
		try {
			return jdbcTemplate().queryForObject("SELECT prefix FROM " + AppTables.CONTROL_INFO + " where type="
					+ s(type) + orderBy("type") + limitRows(0), String.class);
		} catch (Exception e) {
			return null;
		}
	}

	public String getSid(String key) {
		updateControlInfoAddOne(key);
		Map<String, Object> map = jdbcTemplate().queryForMap(
				"SELECT * FROM " + AppTables.CONTROL_INFO + " where type=" + s(key) + orderBy("type") + limitRows(0));
		String paperid = map.get("prefix").toString()
				+ String.format("%08d", Integer.parseInt(map.get("value").toString()));
		return paperid;
	}

	public int updateCurrentTaskexeStatusToV1FromV2(Integer v1, Integer v2) {
		return updateControlInfo(v1, v2, "CURRENT_TASKEXE_STATUS");
	}

	public int updateCurrentTaskexeStatusToInit() {
		return -1;// updateControlInfo(CangzhouTaskexeStep.INIT_OR_OVER_SGZ,
					// null, "CURRENT_TASKEXE_STATUS");
	}

	public int updateCurrentEnvironment(Integer v1) {
		return updateControlInfo(v1, null, "CURRENT_ENVIRONMENT_ID");
	}

	public int updateCurrentSku(Integer v1) {
		return updateControlInfo(v1, null, "CURRENT_SKUID");
	}

	public int updateControlInfo(Integer v1, Integer v2, String type) {
		return jdbcTemplate().update("update " + AppTables.CONTROL_INFO + " set value= " + v1 + " where type="
				+ s(type) + (AppTool.isNull(v2) ? "" : " and value=" + v2));
	}

	public int updateControlInfoAddOne(String type) {
		return jdbcTemplate()
				.update("update " + AppTables.CONTROL_INFO + " set value= value+1 where type=" + s(type));
	}

}
