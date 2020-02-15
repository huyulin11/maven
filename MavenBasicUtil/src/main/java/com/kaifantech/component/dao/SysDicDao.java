package com.kaifantech.component.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.dic.SysDicBean;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class SysDicDao extends BaseDao {
	public List<SysDicBean> getSysDicBeanList(String whereSQL) {
		return (List<SysDicBean>) jdbcTemplate().query(
				"SELECT * FROM " + AppTables.SYS_DICTIONARY_INFO + " where 1=1 and delflag=0 "
						+ (AppTool.isNullStr(whereSQL) ? "" : whereSQL),
				new BeanPropertyRowMapper<SysDicBean>(SysDicBean.class));
	}

	public List<SysDicBean> getAllSysDicBean() {
		return getSysDicBeanList(null);
	}

}
