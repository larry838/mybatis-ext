package com.wshsoft.mybatis.plugins.pagination.dialects;

import com.wshsoft.mybatis.plugins.pagination.IDialect;

/**
 * <p>
 * HSQL 数据库分页语句组装实现
 * </p>
 * 
 * @author carry xie
 * @Date 2016-01-23
 */
public class HSQLDialect implements IDialect {

	public String buildPaginationSql(String originalSql, int offset, int limit) {
		StringBuilder sql = new StringBuilder(originalSql);
		sql.append(" limit ").append(offset).append(",").append(limit);
		return sql.toString();
	}

}
