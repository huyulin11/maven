package com.kaifantech.component.dao.acs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class AgvMsgDao extends BaseDao {
	private Map<Integer, String> latestMsgMap = new HashMap<Integer, String>();

	public void deleteOUTOfDateMsgFromAGV() {
		jdbcTemplate().execute(
				"DELETE FROM " + AppTables.CONNECT_MSG_AGV + " WHERE TO_DAYS(" + i("time") + ") < TO_DAYS(NOW()) ");
	}

	public Map<String, Object> getLatestMsgFromDB(Integer agvid) {
		return jdbcTemplate().queryForMap(" SELECT msg," + i("time") + "," + i("agvId") + " FROM "
				+ AppTables.CONNECT_MSG_AGV + " WHERE flag='Receive' AND agvid=" + agvid + " ORDER BY " + i("time")
				+ " DESC  " + limitRows(1));
	}

	public String getLatestMsg(Integer agvid) {
		Object msg = latestMsgMap.get(agvid);
		return AppTool.isNull(msg) ? "" : msg.toString();
	}

	public void addAMsg(String msg, String flag, Integer agvid) {
		jdbcTemplate().execute("insert into " + AppTables.CONNECT_MSG_AGV + "  (" + i("uuid") + ",agvid,msg,flag) "
				+ "values(uuid()," + agvid + "," + s(msg) + "," + s(flag) + ")");
	}

	public void addAReceiveMsg(String msg, Integer agvId) {
		latestMsgMap.put(agvId, msg);
		addAMsg(msg, "Receive", agvId);
	}

}
