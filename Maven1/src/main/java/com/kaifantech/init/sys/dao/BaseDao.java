package com.kaifantech.init.sys.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.calculatedfun.util.AppTool;
import com.kaifantech.component.cache.worker.AppBeanFactory;
import com.kaifantech.component.cache.worker.AppFileStaticBeanFactory;

public class BaseDao extends BaseJsonJoint {
	public int update(String sql) {
		return jdbcTemplate().update(sql);
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
		return jdbcTemplate().query(sql, rowMapper);
	}

	private static JdbcTemplate jdbcMyTemplate;

	public static JdbcTemplate jdbcMsTemplate() {
		if (AppTool.isNull(jdbcMsTemplate)) {
			jdbcMsTemplate = getJdbcTemplate("jdbcMsTemplate");
		}
		return jdbcMsTemplate;
	}

	private static JdbcTemplate jdbcMsTemplate;

	public static JdbcTemplate jdbcMyTemplate() {
		if (AppTool.isNull(jdbcMyTemplate)) {
			jdbcMyTemplate = getJdbcTemplate("jdbcMyTemplate");
		}
		return jdbcMyTemplate;
	}

	private static JdbcTemplate jdbcLaoTemplate;

	public static JdbcTemplate jdbcLaoTemplate() {
		if (AppTool.isNull(jdbcLaoTemplate)) {
			jdbcLaoTemplate = getJdbcTemplate("jdbcLaoTemplate");
		}
		return jdbcLaoTemplate;
	}

	private static JdbcTemplate jdbcBjjkAgvsTemplate;

	public static JdbcTemplate jdbcBjjkAgvsTemplate() {
		if (AppTool.isNull(jdbcBjjkAgvsTemplate)) {
			jdbcBjjkAgvsTemplate = getJdbcTemplate("jdbcBjjkAgvsTemplate");
		}
		return jdbcBjjkAgvsTemplate;
	}

	public static JdbcTemplate jdbcTemplate() {
		if (UdfDbs.DB_TYPE.equals(DbTypes.SQLSERVER))
			return jdbcMsTemplate();
		return jdbcMyTemplate();
	}

	public static JdbcTemplate getJdbcTemplate(String name) {
		try {
			return AppBeanFactory.get(name);
		} catch (Exception e) {
			return (JdbcTemplate) AppFileStaticBeanFactory.ctx().getBean(name, JdbcTemplate.class);
		}
	}

	public static void main(String[] args) {
		jdbcBjjkAgvsTemplate();
	}
}
