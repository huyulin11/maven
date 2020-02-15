package com.kaifantech.component.dao.singletask;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.singletask.SingletaskBean;
import com.kaifantech.bean.tasksite.TaskSiteInfoBean;
import com.kaifantech.component.cache.worker.AppBeanFactory;
import com.kaifantech.component.service.tasksite.info.SeerTaskSiteInfoService;
import com.kaifantech.init.sys.dao.AppSystemDao;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

@Service
public class SingletaskInfoDao extends AppSystemDao {
	private static final String KEY_TABLE = AppTables.SINGLETASK_INFO;

	static {
		SeerTaskSiteInfoService taskSiteInfoService = (SeerTaskSiteInfoService) AppBeanFactory
				.get(SeerTaskSiteInfoService.class);
		boolean jsonColExist = validateColumnExist(KEY_TABLE, "json");
		if (!jsonColExist) {
			addColumn(KEY_TABLE, "json", " VARCHAR(500) NULL DEFAULT NULL ");
			List<SingletaskBean> beans = (List<SingletaskBean>) jdbcTemplate().query(
					"SELECT a.* FROM " + AppTables.SINGLETASK_INFO + " a " + ", " + AppTables.AGV_INFO + " agv "
							+ " WHERE a.delflag=0  " + " and a.agvId=agv.id and agv.delflag=0 ",
					new BeanPropertyRowMapper<SingletaskBean>(SingletaskBean.class));
			for (SingletaskBean bean : beans) {
				List<TaskSiteInfoBean> siteList = new ArrayList<>();
				if (AgvTaskType.RECEIPT.equals(bean.getTasktype())) {
					siteList.add(taskSiteInfoService.getInitSite(bean.getAgvid()));
					siteList.add(taskSiteInfoService.getRkSite(bean.getLapid()));
					siteList.add(taskSiteInfoService.getAllocSite(bean.getAllocid()));
					siteList.add(taskSiteInfoService.getInitSite(bean.getAgvid()));
				} else if (AgvTaskType.SHIPMENT.equals(bean.getTasktype())) {
					siteList.add(taskSiteInfoService.getInitSite(bean.getAgvid()));
					siteList.add(taskSiteInfoService.getAllocSite(bean.getAllocid()));
					siteList.add(taskSiteInfoService.getCkSite(bean.getLapid()));
					siteList.add(taskSiteInfoService.getInitSite(bean.getAgvid()));
				} else if (AgvTaskType.GOTO_CHARGE.equals(bean.getTasktype())) {
					siteList.add(taskSiteInfoService.getInitSite(bean.getAgvid()));
					siteList.add(taskSiteInfoService.getChargeSite(bean.getLapid()));
				} else if (AgvTaskType.BACK_CHARGE.equals(bean.getTasktype())) {
					siteList.add(taskSiteInfoService.getChargeSite(bean.getLapid()));
					siteList.add(taskSiteInfoService.getInitSite(bean.getAgvid()));
				}
				List<Integer> siteIdList = new ArrayList<>();
				for (TaskSiteInfoBean taskSiteInfoBean : siteList) {
					if (AppTool.isNull(taskSiteInfoBean))
						continue;
					siteIdList.add(taskSiteInfoBean.getId());
				}
				String sites = StringUtils.join(siteIdList, ",");
				String sql = "update " + KEY_TABLE
						+ " t SET `json`=JSON_OBJECT('agvid',agvid,'lapid',lapid,'sites',JSON_ARRAY(" + sites + "))"
						+ where("id=" + bean.getId());
				jdbcTemplate().update(sql);
			}
			System.exit(0);
		}
	}

	public List<SingletaskBean> getList() {
		return (List<SingletaskBean>) jdbcTemplate().query(
				"SELECT a.* FROM " + AppTables.SINGLETASK_INFO + " a " + ", " + AppTables.AGV_INFO + " agv "
						+ " WHERE a.delflag=0  " + " and a.agvId=agv.id and agv.delflag=0 ",
				new BeanPropertyRowMapper<SingletaskBean>(SingletaskBean.class));
	}

	public SingletaskBean getSingletaskBy(Integer allocId, Integer agvId, Integer lapId) {
		List<SingletaskBean> sl = jdbcTemplate().query(
				"SELECT a.* FROM " + AppTables.SINGLETASK_INFO + " a  WHERE a.delflag=0 and allocId=" + allocId
						+ " and agvId=" + agvId + " and lapId=" + lapId + " ",
				new BeanPropertyRowMapper<SingletaskBean>(SingletaskBean.class));
		return sl == null || sl.size() >= 2 || sl.size() == 0 ? null : sl.get(0);
	}

	public SingletaskBean getSingletaskBy(Integer allocId, Integer agvId, Integer lapId, Integer taskType) {
		List<SingletaskBean> sl = jdbcTemplate().query(
				"SELECT a.* FROM " + AppTables.SINGLETASK_INFO + " a  WHERE a.delflag=0 and allocId=" + allocId
						+ " and agvId=" + agvId + " and lapId=" + lapId + " and taskType= " + taskType,
				new BeanPropertyRowMapper<SingletaskBean>(SingletaskBean.class));
		return sl == null || sl.size() >= 2 || sl.size() == 0 ? null : sl.get(0);
	}

	public List<SingletaskBean> getSingletaskForCurrentUser(Integer agvId) {
		return (List<SingletaskBean>) jdbcTemplate().query(
				"SELECT a.* FROM " + AppTables.SINGLETASK_INFO + " a," + AppTables.SINGLETASK_ROLE + " e " + ", "
						+ AppTables.ALLOC_ITEM_INFO_WITH_RELATION + " aii" + ", " + AppTables.ALLOCATION_COLUMN_INFO
						+ " aci" + ", " + AppTables.ALLOCATION_AREA_INFO + " aai " + "WHERE 1=1 "
						+ "and a.`id`=e.taskid AND a.delflag=0  AND e.`delflag`=0 "
						+ (AppTool.isNull(agvId) ? "" : ("AND e.agvId =" + agvId)) + " AND e.`roleid` in  "
						+ AppSession.getUserRoles()
						+ "  AND a.`allocid` = aii.`id` AND aii.`colId` = aci.`colId` AND aii.`areaId` = aci.`areaId` AND aii.`areaId` = aai.`areaId`"
						+ " AND aii.`delflag` = 0  AND aci.`delflag` = 0  AND aai.`delflag` = 0"
						+ " order by a.orderby,a.taskText ",
				new BeanPropertyRowMapper<SingletaskBean>(SingletaskBean.class));
	}

	public List<SingletaskBean> getSingletaskBeanListByGroup(String parentTaskid) {
		return (List<SingletaskBean>) jdbcTemplate().query("SELECT a.* FROM " + AppTables.SINGLETASK_INFO
				+ " a  WHERE a.delflag=0 AND id in " + "(select taskid from `" + AppTables.SINGLETASK_GROUP
				+ " where parentTaskid=" + parentTaskid + ")",
				new BeanPropertyRowMapper<SingletaskBean>(SingletaskBean.class));
	}

	public int insert(String taskName, String taskText, Integer allocid, Integer taskType, Integer agvId, Integer lapId,
			Integer environment) {
		return jdbcTemplate().update("insert into  " + AppTables.SINGLETASK_INFO
				+ " (`environment`, `taskName`,  `taskText`,  `allocid`,  `taskType`,  `agvId`,  `lapId`)"
				+ " VALUES  (" + "'" + environment + "'," + "'" + taskName + "'," + "'" + taskText + "'," + "'"
				+ allocid + "'," + "'" + taskType + "'," + "'" + agvId + "'," + "'" + lapId + "')");
	}

	public int update(String taskName, String taskText, String taskid) {
		return jdbcTemplate().update("update  " + AppTables.SINGLETASK_INFO + " set " + " `taskName`='" + taskName
				+ "'," + " `taskText`='" + taskText + "'" + " where " + " `id`=" + " '" + taskid + "'");
	}

}
