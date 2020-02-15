package com.kaifantech.component.dao.iot.client;

import java.nio.ByteBuffer;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class IotMsgDao extends BaseDao {
	public void deleteOUTOfDateMsgFromAGV() {
		jdbcTemplate().execute("DELETE FROM " + AppTables.CONNECT_ROBOTICARM_MSG + " WHERE TO_DAYS(" + i("time")
				+ ") < TO_DAYS(NOW()) ");
	}

	public Map<String, Object> getLatestMsg(Integer lapId) {
		return jdbcTemplate().queryForMap(" SELECT msg," + i("time") + "," + i("lapId") + " FROM "
				+ AppTables.CONNECT_ROBOTICARM_MSG + "  WHERE flag='Receive' AND lapId=" + lapId + " ORDER BY "
				+ i("time") + " DESC  " + limitRows(1));
	}

	public void addAMsg(String msg, String flag, Integer lapId) {
		jdbcTemplate().execute("insert into " + AppTables.CONNECT_ROBOTICARM_MSG + "  (" + i("uuid")
				+ ",lapId,msg,flag) " + "values(uuid()," + lapId + "," + s(msg) + "," + s(flag) + ")");
	}

	public void addAReceiveMsg(String msg) {
		addAMsg(msg, "Receive", 1);
	}

	public void addAReceiveMsg(String msg, Integer lapId) {
		addAMsg(msg, "Receive", lapId);
	}

	public void addAReceiveMsg(ByteBuffer msg, Integer lapId) {
		addAMsg(msg.toString(), "Receive", lapId);
	}

}
