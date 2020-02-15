package com.kaifantech.component.dao.agv.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.google.common.collect.ImmutableMap;
import com.kaifantech.bean.info.agv.AGVBeanWithLocation;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.component.service.taskexe.ctrl.ICtrlModule;
import com.kaifantech.init.sys.dao.AppSystemDao;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.ctrl.AgvMoveStatus;
import com.kaifantech.util.constant.taskexe.ctrl.AgvSiteStatus;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;
import com.kaifantech.util.thread.ThreadTool;

@Service(UdfQualifier.DEFAULT_AGV_INFO_DAO)
public class AgvInfoDao extends AppSystemDao {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_CTRL_MODULE)
	private ICtrlModule ctrlDealModule;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	private IIotClientService iotClientService;

	private static final String KEY_TABLE = AppTables.AGV_INFO;
	private static final String UPDATE_SQL = UPDATE + KEY_TABLE;
	private static final String SELECT_SQL = "SELECT a.* FROM " + KEY_TABLE + " a  WHERE a.delflag=0 ";

	static {
		boolean isColExist = false;

		isColExist = validateColumnExist(KEY_TABLE, "json");
		if (!isColExist) {
			addColumn(KEY_TABLE, "json", " VARCHAR(500) NULL DEFAULT NULL AFTER `taskexesid`");
		}

		isColExist = validateColumnExist(KEY_TABLE, "updatetime");
		if (!isColExist) {
			addColumn(KEY_TABLE, "updatetime",
					" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP() AFTER `id`");
		}
	}

	public void pauseByUser(Integer agvId) {
		update(agvId, " movestatus=" + str(AgvMoveStatus.PAUSE_USER));
		ctrlDealModule.pause(agvId);
	}

	public void updateRemark(Integer agvId, String remark) {
		update(agvId, "`JSON` = " + jsonItemUpdate("REMARK", remark));
	}

	public void pauseAuto(Integer agvId) {
		update(agvId, " movestatus=" + str(AgvMoveStatus.PAUSE_SYS));
		ctrlDealModule.pause(agvId);
	}

	public void pauseSelf(Integer agvId) {
		update(agvId, " movestatus=" + str(AgvMoveStatus.PAUSE_SELF));
		ctrlDealModule.pause(agvId);
	}

	public void pauseOutErr(Integer agvId) {
		update(agvId, " movestatus=" + str(AgvMoveStatus.PAUSE_OUT_ERR),
				AND + " movestatus!=" + str(AgvMoveStatus.PAUSE_OUT_ERR));
		ThreadTool.run(() -> {
			ctrlDealModule.pause(agvId);
		});
	}

	public void pauseRepathErr(Integer agvId) {
		update(agvId, " movestatus=" + str(AgvMoveStatus.PAUSE_REPATH_ERR),
				AND + " movestatus!=" + str(AgvMoveStatus.PAUSE_REPATH_ERR));
		ThreadTool.run(() -> {
			ctrlDealModule.pause(agvId);
		});
	}

	public void pauseCacheErr(Integer agvId) {
		update(agvId, " movestatus=" + str(AgvMoveStatus.PAUSE_CACHE_ERR),
				AND + " movestatus=" + str(AgvMoveStatus.CONTINUE));
		ThreadTool.run(() -> {
			ctrlDealModule.pause(agvId);
		});
	}

	public void workOverForce(Integer agvId) {
		update(agvId, " sitestatus=" + str(AgvSiteStatus.INIT), " taskstatus=" + str(AgvTaskType.FREE),
				" taskexesid=''");
	}

	public void startup(Integer agvId) {
		update(agvId, " movestatus=" + str(AgvMoveStatus.CONTINUE));
	}

	public void startupFromCacheErr(Integer agvId) {
		update(agvId, " movestatus=" + str(AgvMoveStatus.CONTINUE),
				AND + " movestatus=" + str(AgvMoveStatus.PAUSE_CACHE_ERR));
	}

	public void startupFromOutErr(Integer agvId) {
		update(agvId, " movestatus=" + str(AgvMoveStatus.CONTINUE),
				AND + " movestatus=" + str(AgvMoveStatus.PAUSE_OUT_ERR));
	}

	public void startupFromSelf(Integer agvId) {
		update(agvId, " movestatus=" + str(AgvMoveStatus.CONTINUE),
				AND + " movestatus=" + str(AgvMoveStatus.PAUSE_SELF));
	}

	public void startupFromRepathErr(Integer agvId) {
		update(agvId, " movestatus=" + str(AgvMoveStatus.CONTINUE),
				AND + " movestatus=" + str(AgvMoveStatus.PAUSE_REPATH_ERR));
	}

	public List<AgvInfoBean> getList() {
		return (List<AgvInfoBean>) query("SELECT a.* FROM " + KEY_TABLE + " a  WHERE a.delflag=0  ",
				new BeanPropertyRowMapper<AgvInfoBean>(AgvInfoBean.class));
	}

	public List<AgvInfoBean> getChargedList() {
		String sql = SELECT_SQL + " and ( a.taskstatus=" + str(AgvTaskType.GOTO_CHARGE) + " or a.taskstatus="
				+ str(AgvTaskType.BACK_CHARGE) + " " + ")" + " ORDER BY field(a.taskstatus,"
				+ str(AgvTaskType.BACK_CHARGE) + "," + str(AgvTaskType.GOTO_CHARGE) + ")";
		return (List<AgvInfoBean>) query(sql, new BeanPropertyRowMapper<AgvInfoBean>(AgvInfoBean.class));
	}

	private Map<Object, Object> busstypeMap = new HashMap<>();

	private Object getBusstypeAgvs(Object busstype) {
		Object busstypeAgvs = busstypeMap.get(busstype);
		if (AppTool.isNull(busstypeAgvs)) {
			busstypeAgvs = "";
			List<Integer> agvIds = new ArrayList<>();
			for (IotClientBean agv : iotClientService.getAgvCacheList()) {
				if (busstype.equals(agv.getJsonItem("agvbusstype"))) {
					agvIds.add(agv.getId());
				}
			}
			busstypeAgvs = StringUtils.join(agvIds, ',');
			busstypeMap.put(busstype, busstypeAgvs);
		}
		return busstypeAgvs;
	}

	public String agvIdStr(Object agvId) {
		String agvIdStr = AppTool.isNull(agvId) ? "" : " and a.id in (" + agvId + ")";
		return agvIdStr;
	}

	public String busstypeStr(Object busstype) {
		String busstypeStr = AppTool.isNull(busstype) ? "" : " and a.id in (" + getBusstypeAgvs(busstype) + ")";
		return busstypeStr;
	}

	public String taskstatusStr(Object taskstatus) {
		String taskstatusStr = AppTool.isNull(taskstatus) ? "" : AND + " ( a.taskstatus=" + str(taskstatus) + ")";
		return taskstatusStr;
	}

	public String sitestatusStr(Object sitestatus) {
		String sitestatusStr = AppTool.isNull(sitestatus) ? "" : (AND + " sitestatus" + "=" + str(sitestatus));
		return sitestatusStr;
	}

	public String sitestatusNotStr(Object sitestatus) {
		String sitestatusStr = AppTool.isNull(sitestatus) ? "" : (AND + " sitestatus" + "!=" + str(sitestatus));
		return sitestatusStr;
	}

	public String sitestatusStr(Object sitestatus, Boolean flag) {
		String sitestatusStr = AppTool.isNull(sitestatus) ? ""
				: (AND + " sitestatus" + (AppTool.isNull(flag) || flag ? "=" : "!=") + str(sitestatus));
		return sitestatusStr;
	}

	public List<AgvInfoBean> getBy(Map<Object, Object> conditions) {
		String sql = SELECT_SQL;
		for (Entry<Object, Object> condition : conditions.entrySet()) {
			if (AppTool.isNull(condition.getKey())) {
				continue;
			}
			String key = condition.getKey().toString().toUpperCase();
			if (key.equals("BUSSTYPE")) {
				sql += " " + busstypeStr(condition.getValue());
			} else if (key.equals("TASKSTATUS")) {
				sql += " " + taskstatusStr(condition.getValue());
			} else if (key.equals("SITESTATUS")) {
				sql += " " + sitestatusStr(condition.getValue());
			} else if (key.equals("SITESTATUSNOT")) {
				sql += " " + sitestatusNotStr(condition.getValue());
			}
		}
		return (List<AgvInfoBean>) query(sql, new BeanPropertyRowMapper<AgvInfoBean>(AgvInfoBean.class));
	}

	public List<AgvInfoBean> getList(String taskstatus) {
		return getBy(ImmutableMap.of("TASKSTATUS", taskstatus));
	}

	public List<AgvInfoBean> getList(String taskstatus, String busstype) {
		Map<Object, Object> condition = ImmutableMap.of("BUSSTYPE", busstype, "TASKSTATUS", taskstatus);
		return getBy(condition);
	}

	public List<AgvInfoBean> getList(String taskstatus, String busstype, String sitestatus, boolean flag) {
		Map<Object, Object> condition = ImmutableMap.of("BUSSTYPE", busstype, "TASKSTATUS", taskstatus,
				((AppTool.isNull(flag) || flag) ? "SITESTATUS" : "SITESTATUSNOT"), sitestatus);
		return getBy(condition);
	}

	private AgvInfoBean getBean(Integer agvId) {
		String sql = "SELECT a.* FROM " + KEY_TABLE + " a  WHERE a.delflag=0 AND id= " + agvId;
		List<AgvInfoBean> fl = (List<AgvInfoBean>) query(sql,
				new BeanPropertyRowMapper<AgvInfoBean>(AgvInfoBean.class));
		return (fl == null || fl.size() > 1 || fl.size() == 0) ? null : fl.get(0);
	}

	public AgvInfoBean get(Integer agvId) {
		AgvInfoBean bean = getBean(agvId);
		if (bean == null) {
			newOne(agvId);
			bean = getBean(agvId);
		}
		return bean;
	}

	public AgvInfoBean get(IotClientBean iotClientBean) {
		return get(iotClientBean.getId());
	}

	public String getMoveStatus(Integer agvId) {
		AgvInfoBean bean = get(agvId);
		return bean.getMovestatus();
	}

	public Integer getChargeId(Integer agvId) {
		AgvInfoBean bean = get(agvId);
		return bean.getChargeid();
	}

	public String getSiteStatus(Integer agvId) {
		AgvInfoBean bean = get(agvId);
		return bean.getSitestatus();
	}

	public String getTaskStatus(Integer agvId) {
		AgvInfoBean bean = get(agvId);
		return bean.getTaskstatus();
	}

	public AGVBeanWithLocation getAGVBeanWithLocation(Integer agvId) {
		List<AGVBeanWithLocation> fl = (List<AGVBeanWithLocation>) query(
				"SELECT a.* FROM " + KEY_TABLE + " a  WHERE a.delflag=0 AND id= " + agvId,
				new BeanPropertyRowMapper<AGVBeanWithLocation>(AGVBeanWithLocation.class));
		return (fl == null || fl.size() > 1) ? null : fl.get(0);
	}

	public synchronized int newOne(Integer agvId) {
		return update("REPLACE into  " + KEY_TABLE + " (id) VALUE (  " + agvId + ")");
	}

	protected synchronized int update(Integer agvId, String... addedSqls) {
		List<String> sets = new ArrayList<>();
		List<String> conditions = new ArrayList<>();
		conditions.add(" where id=" + agvId + " ");
		if (!AppTool.isNull(addedSqls)) {
			for (String addedSql : addedSqls) {
				if (addedSql.startsWith(AND)) {
					conditions.add(addedSql);
				} else {
					sets.add(addedSql + " ");
				}
			}
		}
		if (sets.size() == 0) {
			return -1;
		}
		String sql = UPDATE_SQL + SET + StringUtils.join(sets.toArray(), ",")
				+ StringUtils.join(conditions.toArray(), " ");
		return update(sql);
	}

	public int update(Integer agvId, List<String> sqlList) {
		String sql = UPDATE_SQL + SET + StringUtils.join(sqlList.toArray(), ",") + " where id= " + agvId;
		return update(sql);
	}

	public int update(Integer agvId, Map<String, String> fields) {
		String sql = UPDATE_SQL + set(fields) + " where id= " + agvId;
		return update(sql);
	}

}
