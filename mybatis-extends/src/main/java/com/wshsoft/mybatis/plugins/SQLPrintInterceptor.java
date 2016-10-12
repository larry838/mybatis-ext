package com.wshsoft.mybatis.plugins;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.wshsoft.mybatis.environment.Environment;
import com.wshsoft.mybatis.exceptions.MybatisExtendsException;
import com.wshsoft.mybatis.toolkit.SystemClock;

/**
 * <p>
 * 性能分析拦截器，用于输出每条 SQL 语句及其执行时间
 * </p>
 * 
 * @author carry xie
 * @Date 2016-07-07
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class SQLPrintInterceptor implements Interceptor {

	/**
	 * SQL 执行最大时长，超过自动告警，有助于发现问题。
	 */
	private long maxTime = 500;

	public Object intercept(Invocation invocation) throws Throwable {
		//生产环境不检测索引
		if (Environment.isProduct()) {
			return invocation.proceed();
		}
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameterObject = null;
		if (invocation.getArgs().length > 1) {
			parameterObject = invocation.getArgs()[1];
		}

		String statementId = mappedStatement.getId();
		BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
		Configuration configuration = mappedStatement.getConfiguration();
		String sql = getSql(boundSql, parameterObject, configuration);

		long start = SystemClock.now();
		Object result = invocation.proceed();
		long end = SystemClock.now();
		long timing = end - start;
		Log logger = LogFactory.getLog(statementId);
		if (maxTime >= 1 && timing > maxTime) {
			logger.warn("Time：" + timing + " ms==>SQL："+sql);
			throw new MybatisExtendsException(" The SQL execution time is too large, please optimize ! ");
		} else {
			logger.debug("Time：" + timing + " ms==>SQL："+sql);
			System.err.println(" Time：" + timing + " ms" + " - ID：" + statementId + "\n Execute SQL：" + sql + "\n");
		}

		return result;
	}

	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	public void setProperties(Properties prop) {
		// TODO
	}

	private String getSql(BoundSql boundSql, Object parameterObject, Configuration configuration) {
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
		if (parameterMappings != null) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else {
						MetaObject metaObject = configuration.newMetaObject(parameterObject);
						value = metaObject.getValue(propertyName);
					}
					sql = replacePlaceholder(sql, value);
				}
			}
		}
		return sql;
	}

	private String replacePlaceholder(String sql, Object propertyValue) {
		String result;
		if (propertyValue != null) {
			if (propertyValue instanceof String) {
				result = "'" + propertyValue + "'";
			} else if (propertyValue instanceof Date) {
				result = "'" + dateToString(propertyValue) + "'";
			} else {
				result = propertyValue.toString();
			}
		} else {
			result = "null";
		}

		/* 特殊处理 $ 符内容 */
		if (null != result && result.contains("$")) {
			return sql.replaceFirst("\\?", "[?]").replace("[?]", result);
		}

		/* 填充占位符 */
		return sql.replaceFirst("\\?", result);
	}

	private synchronized String dateToString(Object obj) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj);
	}

	public long getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}

}
