package com.kaifantech.component.dao.singletask;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.singletask.SingletaskGroupBean;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class SingletaskGroupDao extends BaseDao {
	public List<Map<String, Object>> getSingletaskGroupList(String whereStr) {
		return jdbcTemplate().queryForList("SELECT * FROM " + AppTables.SINGLETASK_GROUP + " where 1=1 "
				+ (AppTool.isNullStr(whereStr) ? "" : whereStr));
	}

	public List<Map<String, Object>> getSingletaskGroupListByTask(String taskid) {
		return getSingletaskGroupList(" and taskid =" + taskid + " ");
	}

	public List<SingletaskGroupBean> getsingletaskGroupList() {
		return (List<SingletaskGroupBean>) jdbcTemplate().query(
				"SELECT a.* FROM " + AppTables.SINGLETASK_GROUP + " a  WHERE a.delflag=0  ",
				new BeanPropertyRowMapper<SingletaskGroupBean>(SingletaskGroupBean.class));
	}

}
