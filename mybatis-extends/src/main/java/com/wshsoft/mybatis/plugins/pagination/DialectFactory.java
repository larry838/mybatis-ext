package com.wshsoft.mybatis.plugins.pagination;

import com.wshsoft.mybatis.plugins.pagination.dialects.HSQLDialect;
import com.wshsoft.mybatis.plugins.pagination.dialects.MySqlDialect;
import com.wshsoft.mybatis.plugins.pagination.dialects.OracleDialect;
import com.wshsoft.mybatis.plugins.pagination.dialects.PostgreDialect;
import com.wshsoft.mybatis.plugins.pagination.dialects.SQLServerDialect;
import com.wshsoft.mybatis.plugins.pagination.dialects.SQLiteDialect;

/**
 * <p>
 * 分页方言工厂类
 * </p>
 * 
 * @author carry xie
 * @Date 2016-01-23
 */
public class DialectFactory {

	/**
	 * <p>
	 * 根据数据库类型选择不同分页方言
	 * </p>
	 * 
	 * @param dbtype
	 *            数据库类型
	 * @return
	 * @throws Exception
	 */
	public static IDialect getDialectByDbtype( String dbtype ) throws Exception {
		if ( "mysql".equalsIgnoreCase(dbtype) ) {
			return new MySqlDialect();
		} else if ( "oracle".equalsIgnoreCase(dbtype) ) {
			return new OracleDialect();
		} else if ( "hsql".equalsIgnoreCase(dbtype) ) {
			return new HSQLDialect();
		} else if ( "sqlite".equalsIgnoreCase(dbtype) ) {
			return new SQLiteDialect();
		} else if ( "postgre".equalsIgnoreCase(dbtype) ) {
			return new PostgreDialect();
		} else if ( "sqlserver".equalsIgnoreCase(dbtype) ) {
			return new SQLServerDialect();
		} else {
			return null;
		}
	}

}
