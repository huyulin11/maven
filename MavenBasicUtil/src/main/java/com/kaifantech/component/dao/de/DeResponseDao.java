package com.kaifantech.component.dao.de;

import org.junit.runner.RunWith;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.calculatedfun.util.AppTool;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-shiro.xml", "classpath:spring-application.xml" })
@Service
public class DeResponseDao extends BaseDao {
	public int updateExstatus(String sid, Integer exstatus) {
		return jdbcTemplate().update(
				"update " + AppTables.O_ALL_RESPONSE + " set " + " exstatus= " + exstatus + " where sid= " + sid);
	}

	public int updateErrinfo(String sid, String errinfo) {
		return jdbcTemplate().update(
				"update " + AppTables.O_ALL_RESPONSE + " set " + " errinfo= " + s(errinfo) + " where sid= " + sid);
	}

	public int updateExtimes(String sid) {
		return jdbcTemplate()
				.update("update " + AppTables.O_ALL_RESPONSE + " set " + " extimes=extimes+1 " + " where sid= " + sid);
	}

	public int add(String request, Object response) {
		if (AppTool.isNull(request)) {
			return 0;
		}
		return jdbcTemplate().update("insert into " + AppTables.O_ALL_RESPONSE + " (request,response) " + "values("
				+ s(request) + "," + s(response) + ")");
	}

}
