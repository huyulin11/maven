package com.kaifantech.component.dao.taskexe.info;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.init.sys.dao.AppSystemDao;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.util.constant.taskexe.TaskexeOpFlag;

@Service
public class TaskexeInfoDao extends AppSystemDao {
	private static final String KEY_TABLE = AppTables.TASKEXE_S2C_TASK;
	private static final String SELECT_STR = selectFrom(KEY_TABLE);
	private static final String DEFAULT_ORDER_BY = "FIELD(tasktype ,'GOTO_CHARGE','BACK_CHARGE') DESC,FIELD(opflag,'SEND','NEW','OVER'),0+JSON_VALUE("
			+ i("json") + ",'$.seq') desc, " + i("time") + "";

	public List<TaskexeBean> getList(String whereSql, String orderBySql, Integer limitRows) {
		String sql = join(SELECT_STR, whereSql, orderByIgnoreIdtf(orderBySql), limitRows(limitRows));
		return query(sql, new BeanPropertyRowMapper<TaskexeBean>(TaskexeBean.class));
	}

	/** 这里只处理需要发送到AGV的任务 */
	public List<TaskexeBean> getNotOverList() {
		String sql = where(notin("opflag", TaskexeOpFlag.OVER, TaskexeOpFlag.SUSPEND));
		return getList(sql, DEFAULT_ORDER_BY, 10);
	}

	public List<TaskexeBean> getNotOverList(String taskid) {
		String sql = where(in("opflag", TaskexeOpFlag.NEW, TaskexeOpFlag.SEND),
				"json_value(" + i("json") + ",'$.taskid')=" + s(taskid));
		return getList(sql, DEFAULT_ORDER_BY, 10);
	}

	public List<TaskexeBean> getOverList(String taskexesid) {
		String sql = where(" opflag=" + s(TaskexeOpFlag.OVER), "taskexesid=" + s(taskexesid));
		return getList(sql, DEFAULT_ORDER_BY, 10);
	}

	public List<TaskexeBean> getOverListUntilYesterday() {
		String sql = where("opflag=" + s(TaskexeOpFlag.OVER),
				"date_add(" + i("overtime") + ",INTERVAL 6 hour) < NOW() ");
		return getList(sql, "  " + i("overtime") + " ", 50);
	}

	public List<TaskexeBean> getAllList(String taskexesid) {
		String sql = where("taskexesid=" + s(taskexesid));
		return getList(sql, "  tasksequence", null);
	}

	public List<TaskexeBean> getNotOverListByF(Integer agvId) {
		String sql = where(in("opflag", TaskexeOpFlag.NEW, TaskexeOpFlag.SEND), "agvId=" + agvId);
		return getList(sql, DEFAULT_ORDER_BY, 10);
	}

	public List<TaskexeBean> getNotOverListByF(List<Integer> agvs) {
		String sql = where(in("agvId", agvs), in("opflag", TaskexeOpFlag.NEW, TaskexeOpFlag.SEND));
		return getList(sql, DEFAULT_ORDER_BY, 10);
	}

	/** 这里只处理需要发送到AGV的任务 */
	public List<TaskexeBean> getToSendList(Integer agvId) {
		String sql = where("agvId=" + agvId, notin("opflag", TaskexeOpFlag.OVER, TaskexeOpFlag.SUSPEND));
		return getList(sql, DEFAULT_ORDER_BY, 1);
	}

	public List<TaskexeBean> getNewList() {
		String sql = where("opflag=" + s(TaskexeOpFlag.NEW));
		return getList(sql, DEFAULT_ORDER_BY, 10);
	}

	public List<TaskexeBean> getSuspendList() {
		String sql = where("opflag=" + s(TaskexeOpFlag.SUSPEND));
		return getList(sql, DEFAULT_ORDER_BY, 10);
	}

	public TaskexeBean selectByUuid(String uuid) {
		String sql = where("uuid=" + s(uuid));
		return getList(sql, null, 1).get(0);
	}

	public int delete(TaskexeBean taskexe) {
		String sql = "DELETE FROM " + KEY_TABLE
				+ where("taskexesid=" + s(taskexe.getTaskexesid()), "tasksequence = " + taskexe.getTasksequence());
		return update(sql);
	}

}
