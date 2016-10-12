package com.wshsoft.mybatis.plugins;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.wshsoft.mybatis.environment.Environment;
import com.wshsoft.mybatis.exceptions.MybatisExtendsException;

/**
 * <p>
 * SQL 执行性能分析拦截器
 * </p>
 * 
 * @author carry xie
 * @Date 2016-08-16
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
	         @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class SqlExplainInterceptor implements Interceptor {
	private static final Log logger = LogFactory.getLog(SqlExplainInterceptor.class);
	
	/**
	 * 发现执行全表 delete update 语句是否停止执行
	 */
	private boolean stopProceed = false;
	/* 方言类型 */
	private String dbtype;
	
	public Object intercept(Invocation invocation) throws Throwable {
		Object result = invocation.proceed();
		// 生产环境不检测索引
		if (Environment.isProduct()) {
			return result;
		}
		// 返回结果是空,explain没有意义
		/*if (result instanceof List) {
			List<?> re = (List<?>) result;
			if (re.isEmpty()) {
				return result;
			}
		}*/
		// 不是mysql直接返回
		if (!"mysql".equalsIgnoreCase(dbtype)) {
			return result;
		}

		/**
		 * 处理 DELETE UPDATE 语句
		 */
		try {
			MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
			if (ms.getSqlCommandType() == SqlCommandType.SELECT
					|| ms.getSqlCommandType() == SqlCommandType.DELETE
					|| ms.getSqlCommandType() == SqlCommandType.UPDATE) {
				Configuration configuration = ms.getConfiguration();
				Object parameter = invocation.getArgs()[1];
				BoundSql boundSql = ms.getBoundSql(parameter);
				Executor exe = (Executor) invocation.getTarget();
				Connection connection = exe.getTransaction().getConnection();

				/**
				 * 执行 SQL 分析
				 */
				sqlExplain(configuration, ms, boundSql, connection, parameter);
			}
		} catch (Exception e) {
			logger.error("索引拦截监控出错", e);
		}
		return result;
	}

	/**
	 * <p>
	 * 判断是否执行 SQL
	 * </p>
	 * @param configuration
	 * @param mappedStatement
	 * @param boundSql
	 * @param connection
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	protected void sqlExplain( Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql,
			Connection connection, Object parameter ) throws Exception {
	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder explain = new StringBuilder("EXPLAIN ");
			explain.append(boundSql.getSql());
			String sqlExplain = explain.toString();
			StaticSqlSource sqlsource = new StaticSqlSource(configuration, sqlExplain, boundSql.getParameterMappings());
			MappedStatement.Builder builder = new MappedStatement.Builder(configuration, "explain_sql", sqlsource, SqlCommandType.SELECT);
			builder.resultMaps(mappedStatement.getResultMaps()).resultSetType(mappedStatement.getResultSetType()).statementType(mappedStatement.getStatementType());
			MappedStatement query_statement = builder.build();
			DefaultParameterHandler handler = new DefaultParameterHandler(query_statement, parameter, boundSql);
			stmt = connection.prepareStatement(sqlExplain);
			handler.setParameters(stmt);
			rs = stmt.executeQuery();
			int count = 0;
			while ( rs.next() ) {
				count++;
				String type = rs.getString("type");
				//String table = rs.getString("table");
				if ( "ALL".equals(type) ) {
					logger.error("使用了全表扫描的方式!");
				}
				if ( rs.getString("key") == null ) {
					logger.warn("没有使用索引,可能存在性能问题!");
				}
				int rows = rs.getInt("rows");
				if ( rows > 500 ) {
					logger.trace("影响行数为" + rows + ",可能存在性能问题!");
				}
				String extra = rs.getString("Extra");
				if ( extra != null && extra.contains("Using temporary") ) {
					logger.warn("使用临时表,可能存在性能问题!");
				}
				if ( extra != null && extra.contains("Using filesort") ) {
					logger.warn("使用文件排序,可能存在性能问题!");
				}
			}
			if ( count > 1 ) {
				logger.error("您的sql语句中用了子查询或者连接查询,为了保证性能和可扩展性,请不要使用子查询或者连接查询,尽量使用n+1查询或者字段冗余!");
				String tip = " Full table operation is prohibited. SQL: " + boundSql.getSql();
				if (this.isStopProceed()) {
					throw new MybatisExtendsException(tip);
				}
			}

		} catch ( Exception e ) {
			throw new MybatisExtendsException(e); 
		} finally {
			if ( rs != null ) {
				rs.close();
				rs = null;
			}
			if ( stmt != null ) {
				stmt.close();
				stmt = null;
			}
		}
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

	public boolean isStopProceed() {
		return stopProceed;
	}

	public void setStopProceed(boolean stopProceed) {
		this.stopProceed = stopProceed;
	}
	
	public String getDbtype() {
		return dbtype;
	}


	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}
	

}