package com.kaifantech.component.dao.iot.relation;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.kaifantech.bean.iot.relation.IotRelationBean;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class IotRelationDao extends BaseDao {
	public List<IotRelationBean> getIotRelationBeanList() {
		return (List<IotRelationBean>) jdbcTemplate().query(
				"SELECT a.* FROM " + AppTables.IOT_RELATION_INFO + " a  WHERE a.delflag=0  ",
				new BeanPropertyRowMapper<IotRelationBean>(IotRelationBean.class));
	}

	public IotRelationBean getIotRelationBean(Integer iotRelationId) {
		List<IotRelationBean> fl = (List<IotRelationBean>) jdbcTemplate().query(
				"SELECT a.* FROM " + AppTables.IOT_RELATION_INFO + " a  WHERE a.delflag=0 AND id= " + iotRelationId,
				new BeanPropertyRowMapper<IotRelationBean>(IotRelationBean.class));
		return (fl == null || fl.size() > 1) ? null : fl.get(0);
	}

}
