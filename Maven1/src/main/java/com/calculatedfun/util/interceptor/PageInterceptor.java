package com.calculatedfun.util.interceptor;

import java.sql.Connection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.log4j.Logger;

import com.calculatedfun.erp.plugin.PageView;
import com.calculatedfun.util.AppReflectHelper;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.interceptor.tool.AppSqlJoint;
import com.calculatedfun.util.interceptor.tool.AppSqlRunner;
import com.kaifantech.init.sys.params.AppSysParameters;
import com.kaifantech.util.log.AppFileLogger;

@SuppressWarnings("unchecked")
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PageInterceptor implements Interceptor {
	public final static Logger logger = Logger.getLogger(PageInterceptor.class);
	private static String pageSqlId = "";

	public Object intercept(Invocation ivk) throws Throwable {
		if (AppSysParameters.isCheckSql()) {
			return doInterceptAndCheckSql(ivk);
		} else {
			return doIntercept(ivk);
		}
	}

	private BaseStatementHandler getDelegate(Invocation ivk) {
		StatementHandler statementHandler = (StatementHandler) ivk.getTarget();
		return (BaseStatementHandler) AppReflectHelper.getValueByFieldName(statementHandler, "delegate");
	}

	private BoundSql getBoundSql(Invocation ivk) {
		BaseStatementHandler delegate = getDelegate(ivk);
		BoundSql boundSql = delegate.getBoundSql();
		return boundSql;
	}

	private String getSqlId(Invocation ivk) {
		BaseStatementHandler delegate = getDelegate(ivk);
		MappedStatement mappedStatement = (MappedStatement) AppReflectHelper.getValueByFieldName(delegate,
				"mappedStatement");
		return mappedStatement.getId();
	}

	public Object doInterceptAndCheckSql(Invocation ivk) throws Throwable {
		long startTime = System.currentTimeMillis();
		try {
			return doIntercept(ivk);
		} finally {
			long endTime = System.currentTimeMillis();
			String sqlId = getSqlId(ivk);
			BoundSql boundSql = getBoundSql(ivk);
			String sql = boundSql.getSql();
			sql = sql.replace("\n", "").replace("\t", "").replaceAll("\\s+", " ");
			AppFileLogger.sql("执行SQL[", sqlId, "]: \n", sql, "\n花费", (endTime - startTime), "ms");
		}
	}

	@SuppressWarnings("rawtypes")
	public Object doIntercept(Invocation ivk) throws Throwable {
		if (!(ivk.getTarget() instanceof RoutingStatementHandler)) {
			return ivk.proceed();
		}
		String sqlId = getSqlId(ivk);
		BoundSql boundSql = getBoundSql(ivk);

		Object parameterObject = boundSql.getParameterObject();
		if (parameterObject == null) {
			return ivk.proceed();
		} else {
			if (sqlId.indexOf(".AppBaseMapper.") > -1) {
				String sql = AppSqlJoint.getJoinSql(sqlId, parameterObject);
				AppReflectHelper.setValueByFieldName(boundSql, "sql", sql);
				return ivk.proceed();
			}
			PageView pageView = null;
			if (parameterObject instanceof PageView) {
				pageView = (PageView) parameterObject;
			} else if (parameterObject instanceof Map) {
				for (Entry entry : (Set<Entry>) ((Map) parameterObject).entrySet()) {
					if (entry.getValue() instanceof PageView) {
						pageView = (PageView) entry.getValue();
						break;
					}
				}
			} else {
				pageView = AppReflectHelper.getValueByFieldType(parameterObject, PageView.class);
				if (pageView == null) {
					return ivk.proceed();
				}
			}
			if (pageView == null) {
				return ivk.proceed();
			}
			String sql = boundSql.getSql();
			AppSqlRunner.setCount(sql, pageView);
			String pageSql = AppSqlRunner.generatePagesSql(sql, pageView);
			AppReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
		}

		return ivk.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties p) {
		String dialect = p.getProperty("dialect");
		if (AppTool.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialectName or dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
		AppSqlRunner.dialect = dialect;

		pageSqlId = p.getProperty("pageSqlId");
		if (AppTool.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}

}
