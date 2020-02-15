package com.kaifantech.component.dao.taskexe.op;

import org.springframework.stereotype.Service;

import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.init.sys.dao.BaseDao;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.util.constant.taskexe.TaskexeOpFlag;

@Service
public class TaskexeOpDao extends BaseDao {
	public void overASendTask(String uuid) {
		jdbcTemplate().execute("update " + AppTables.TASKEXE_S2C_TASK + " set opflag = " + s(TaskexeOpFlag.OVER)
				+ " where " + " uuid= " + s(uuid) + " and opflag= " + s(TaskexeOpFlag.SEND));
	}

	public void suspendASendTask(String uuid) {
		jdbcTemplate().execute("update " + AppTables.TASKEXE_S2C_TASK + " set opflag = " + s(TaskexeOpFlag.SUSPEND)
				+ " where " + " uuid= " + s(uuid) + " and opflag= " + s(TaskexeOpFlag.SEND));
	}

	public void overATask(String uuid) {
		jdbcTemplate().execute("update " + AppTables.TASKEXE_S2C_TASK + " set opflag = " + s(TaskexeOpFlag.OVER)
				+ " where " + " uuid= " + s(uuid));
	}

	public void overANormalTask(String uuid) {
		jdbcTemplate().execute("update " + AppTables.TASKEXE_S2C_TASK + " set opflag = " + s(TaskexeOpFlag.OVER)
				+ " where " + " uuid= " + s(uuid) + " and opflag= " + s(TaskexeOpFlag.NEW));
	}

	public void overASendTask(TaskexeBean taskexeBean) {
		overASendTask(taskexeBean.getUuid());
	}

	public void suspendASendTask(TaskexeBean taskexeBean) {
		suspendASendTask(taskexeBean.getUuid());
	}

	public void overANormalTask(TaskexeBean taskexeBean) {
		overANormalTask(taskexeBean.getUuid());
	}

	public void overATask(TaskexeBean taskexeBean) {
		overATask(taskexeBean.getUuid());
	}

	public void sendATask(TaskexeBean taskexeBean) {
		sendATask(taskexeBean.getUuid());
	}

	public void assignT2Agv(TaskexeBean taskexeBean, Integer agvId) {
		assignT2Agv(taskexeBean.getTaskexesid(), taskexeBean.getTasksequence(), agvId);
	}

	public void assignT2Agv(String taskexesid, Integer tasksequence, Integer agvId) {
		jdbcTemplate().execute("update " + AppTables.TASKEXE_S2C_TASK + " set agvId = " + agvId + " where taskexesid="
				+ s(taskexesid) + " AND tasksequence=" + tasksequence);
	}

	public void sendATask(String uuid) {
		jdbcTemplate().execute("update " + AppTables.TASKEXE_S2C_TASK + " set opflag = " + s(TaskexeOpFlag.SEND)
				+ " where uuid=" + s(uuid));
	}

	public void addATask(String taskexesid, Integer agvId, Integer lapId, Integer autoflag) {
		jdbcTemplate().execute("insert into " + AppTables.TASKEXE_S2C_TASK + " (" + i("uuid")
				+ ",taskexesid,opflag,agvId,lapId,autoflag) " + "values(uuid()," + s(taskexesid) + ","
				+ s(TaskexeOpFlag.NEW) + "," + agvId + "," + lapId + "," + autoflag + ")");
	}

	public void addATask(TaskexeBean taskexeBean) {
		addATask(taskexeBean.getTaskexesid(), taskexeBean.getAgvId(), taskexeBean.getLapId(), taskexeBean.getTasktype(),
				taskexeBean.getJson(), taskexeBean.getAutoFlag());
	}

	public void addATask(String taskexesid, Integer agvId, Integer lapId, String tasktype, String json,
			Integer autoflag) {
		jdbcTemplate().execute("insert into " + AppTables.TASKEXE_S2C_TASK + " (" + i("uuid")
				+ ",taskexesid,opflag,agvId,lapId,tasktype,json,autoflag) " + "values(uuid()," + s(taskexesid) + ","
				+ s(TaskexeOpFlag.NEW) + "," + agvId + "," + lapId + "," + s(tasktype) + "," + s(json) + ","
				+ autoflag + ")");
	}

	public void addATask(String taskexesid, Integer agvId, Integer lapId, String tasktype, Integer autoflag) {
		jdbcTemplate().execute("insert into " + AppTables.TASKEXE_S2C_TASK + " (" + i("uuid")
				+ ",taskexesid,opflag,agvId,lapId,tasktype,autoflag) " + "values(uuid()," + s(taskexesid) + ","
				+ s(TaskexeOpFlag.NEW) + "," + agvId + "," + lapId + "," + s(tasktype) + "," + autoflag + ")");
	}

	public void addATaskTo(String taskexesid, Integer agvId, Integer lapId, String tasktype, Integer autoflag,
			String to) {
		jdbcTemplate().execute("insert into " + AppTables.TASKEXE_S2C_TASK + " (" + i("uuid")
				+ ",taskexesid,opflag,agvId,lapId,tasktype,autoflag," + i("json") + ") " + "values(uuid(),"
				+ s(taskexesid) + "," + s(TaskexeOpFlag.NEW) + "," + agvId + "," + lapId + "," + s(tasktype) + ","
				+ autoflag + "," + jsonItemInsert("TO", to) + ")");
	}

	public void addATask(String taskexesid, Integer lapId, String tasktype, String addedinfo, String addeddesc,
			Integer tasksequence, Integer autoflag) {
		jdbcTemplate().execute("insert into " + AppTables.TASKEXE_S2C_TASK + " (" + i("uuid")
				+ ",taskexesid,opflag,lapId,tasktype,addedinfo,addeddesc,tasksequence,autoflag) " + "values(uuid(),"
				+ s(taskexesid) + "," + s(TaskexeOpFlag.NEW) + "," + lapId + "," + s(tasktype) + ","
				+ s(addedinfo) + "," + s(addeddesc) + "," + tasksequence + "," + autoflag + ")");
	}
}
