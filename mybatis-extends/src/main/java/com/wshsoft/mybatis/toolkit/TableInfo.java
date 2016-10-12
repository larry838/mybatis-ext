package com.wshsoft.mybatis.toolkit;

import java.util.List;

import com.wshsoft.mybatis.annotations.IdType;

/**
 * <p>
 * 数据库表反射信息
 * </p>
 * 
 * @author carry xie
 * @Date 2016-01-23
 */
public class TableInfo {

	/**
	 * 表主键ID 类型
	 */
	private IdType idType;

	/**
	 * 表名称
	 */
	private String tableName;

	/**
	 * 表映射结果集
	 */
	private String resultMap;

	/**
	 * <p>
	 * 主键是否有存在字段名与属性名关联
	 * </p>
	 * true , false
	 */
	private boolean keyRelated = false;

	/**
	 * 表主键ID 属性名
	 */
	private String keyProperty;

	/**
	 * 表主键ID 字段名
	 */
	private String keyColumn;

	/**
	 * 表字段信息列表
	 */
	private List<TableFieldInfo> fieldList;

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getResultMap() {
		return resultMap;
	}

	public void setResultMap(String resultMap) {
		this.resultMap = resultMap;
	}

	public boolean isKeyRelated() {
		return keyRelated;
	}

	public void setKeyRelated(boolean keyRelated) {
		this.keyRelated = keyRelated;
	}

	public String getKeyProperty() {
		return keyProperty;
	}

	public void setKeyProperty(String keyProperty) {
		this.keyProperty = keyProperty;
	}

	public String getKeyColumn() {
		return keyColumn;
	}

	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}

	public List<TableFieldInfo> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<TableFieldInfo> fieldList) {
		this.fieldList = fieldList;
	}

}
