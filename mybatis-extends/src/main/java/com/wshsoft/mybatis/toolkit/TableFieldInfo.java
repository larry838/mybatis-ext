package com.wshsoft.mybatis.toolkit;

import com.wshsoft.mybatis.MybatisConfiguration;
import com.wshsoft.mybatis.annotations.FieldStrategy;

/**
 * <p>
 * 数据库表字段反射信息
 * </p>
 *
 * @author carry xie 
 * @Date 2016-09-09
 */
public class TableFieldInfo {

	/**
	 * <p>
	 * 是否有存在字段名与属性名关联
	 * </p>
	 * true , false
	 */
	private boolean related;

	/**
	 * 字段名
	 */
	private String column;

	/**
	 * 属性名
	 */
	private String property;

	/**
	 * 属性表达式#{property}, 可以指定jdbcType, typeHandler等
	 */
	private String el;

	/**
	 * 字段策略【 默认，自判断 null 】
	 */
	private FieldStrategy fieldStrategy = FieldStrategy.NOT_NULL;

	public TableFieldInfo(boolean related, String column, String property, String el, FieldStrategy fieldStrategy) {
		this.related = related;
		this.setColumn(column);
		this.property = property;
		this.el = el;
		/*
		 * 优先使用单个字段注解，否则使用全局配置
		 */
		if (fieldStrategy != FieldStrategy.NOT_NULL) {
			this.fieldStrategy = fieldStrategy;
		} else {
			this.fieldStrategy = MybatisConfiguration.FIELD_STRATEGY;
		}
	}

	public TableFieldInfo(boolean related, String column, String property) {
		this.related = related;
		this.setColumn(column);
		this.property = property;
		this.el = property;
		this.fieldStrategy = MybatisConfiguration.FIELD_STRATEGY;
	}

	public TableFieldInfo(String column) {
		this.related = false;
		this.setColumn(column);
		this.property = column;
		this.el = column;
		this.fieldStrategy = MybatisConfiguration.FIELD_STRATEGY;
	}

	public boolean isRelated() {
		return related;
	}

	public void setRelated(boolean related) {
		this.related = related;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = SqlReservedWords.convert(column);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getEl() {
		return el;
	}

	public void setEl(String el) {
		this.el = el;
	}

	public FieldStrategy getFieldStrategy() {
		return fieldStrategy;
	}

	public void setFieldStrategy(FieldStrategy fieldStrategy) {
		this.fieldStrategy = fieldStrategy;
	}

}
