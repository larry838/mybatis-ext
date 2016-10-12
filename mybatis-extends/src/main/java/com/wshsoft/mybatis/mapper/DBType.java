package com.wshsoft.mybatis.mapper;

/**
 * <p>
 * mybatis-extends 数据库类型
 * </p>
 * 
 * @author carry xie
 * @Date 2016-04-15
 */
public enum DBType {
	MYSQL("mysql", "MySql数据库"), ORACLE("oracle", "Oracle数据库");

	private final String db;

	private final String desc;


	DBType( final String db, final String desc ) {
		this.db = db;
		this.desc = desc;
	}


	/**
	 * <p>
	 * 获取数据库类型（默认 MySql）
	 * </p>
	 * 
	 * @param dbType
	 *            数据库类型字符串
	 * @return
	 */
	public static DBType getDBType( String dbType ) {
		for ( DBType dt : DBType.values() ) {
			if ( dt.getDb().equals(dbType) ) {
				return dt;
			}
		}
		return MYSQL;
	}


	public String getDb() {
		return this.db;
	}


	public String getDesc() {
		return this.desc;
	}

}
