package com.kaifantech.component.dao.acs;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;
import com.kaifantech.util.constant.taskexe.TaskexeOpFlag;
import com.kaifantech.util.constant.taskexe.ctrl.AgvCtrlType;
import com.kaifantech.util.constant.taskexe.ctrl.AgvMoveStatus;

@Service
public class AgvStatusDao extends BaseDao {
	private String selectFromStr = "SELECT tasktype,msg,agvId FROM " + AppTables.AGV_STATUS_LOG;

	public List<Map<String, Object>> getLastestAgvStatus(Integer agvId) {
		return getAgvStatus("(tasktype=" + s(AgvMoveStatus.PAUSE_USER) + " OR tasktype="
				+ s(AgvMoveStatus.PAUSE_SYS) + " OR tasktype=" + s(AgvMoveStatus.CONTINUE) + ")", agvId);
	}

	public List<Map<String, Object>> getLastestAgvStatus() {
		String sql = "" + "	SELECT agvId, tasktype, MAX(TIME) octime " + " FROM " + AppTables.AGV_STATUS_LOG
				+ " WHERE tasktype != " + s(AgvCtrlType.WARNING) + " AND tasktype != 'sysInfo' "
				+ " GROUP BY agvId, tasktype ORDER BY agvId, octime DESC ";
		return jdbcTemplate().queryForList(sql);
	}

	public List<Map<String, Object>> getLastestAgvStatusInfo(Integer agvId) {
		return getAgvStatus("(tasktype=" + s(AgvCtrlType.INFO) + ")", agvId);
	}

	public List<Map<String, Object>> getLastestAgvStatusWarning(Integer agvId) {
		return getAgvStatus("(tasktype=" + s(AgvCtrlType.WARNING) + ")", agvId);
	}

	public void deleteOUTOfDateSOPMsg() {
		jdbcTemplate().execute(
				"DELETE FROM " + AppTables.AGV_STATUS_LOG + " WHERE TO_DAYS(" + i("time") + ") < TO_DAYS(NOW()) ");
		jdbcTemplate().execute(" DELETE FROM  " + AppTables.AGV_STATUS_LOG + " WHERE tasktype!="
				+ s(AgvMoveStatus.PAUSE_USER) + " AND tasktype!=" + s(AgvMoveStatus.PAUSE_SYS) + " AND tasktype!="
				+ s(AgvMoveStatus.CONTINUE));
	}

	private void addAgvStatus(String tasktype, String msg, Integer agvId, int autoflag) {
		jdbcTemplate().execute("insert into " + AppTables.AGV_STATUS_LOG + " (" + i("uuid")
				+ ",tasktype,msg,opflag,agvId,autoflag) " + "values(uuid()," + s(tasktype) + ","
				+ s((AppTool.isNullStr(msg) ? "" : msg)) + "," + s(TaskexeOpFlag.NEW) + "," + agvId + "," + autoflag
				+ ")");
	}

	public void addAgvStatus(String tasktype, Integer agvId, int autoflag) {
		addAgvStatus(tasktype, null, agvId, autoflag);
	}

	public void addASystemError(String msg, Integer agvId) {
		addAgvStatus(AgvCtrlType.ERROR, msg, agvId, 1);
	}

	public void addASystemWarning(String msg, Integer agvId) {
		addAgvStatus(AgvCtrlType.WARNING, msg, agvId, 1);
	}

	public void addASystemInfo(String msg, Integer agvId) {
		addAgvStatus(AgvCtrlType.INFO, msg, agvId, 1);
	}

	private List<Map<String, Object>> getAgvStatus(String whereStr, Integer agvId) {
		return jdbcTemplate()
				.queryForList(selectFromStr + " where 1=1 and " + (AppTool.isNullStr(whereStr) ? "" : whereStr)
						+ (agvId == null ? "" : " AND agvId=" + agvId + " ") + " ORDER BY TIME DESC " + limitRows(1));
	}
}
