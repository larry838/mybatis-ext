package com.wshsoft.mybatis.test.mysql.entity;

import com.wshsoft.mybatis.annotations.TableField;
import com.wshsoft.mybatis.annotations.TableId;
import com.wshsoft.mybatis.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 测试角色类
 * </p>
 *
 * @author carry xie
 * @Date 2016-09-09
 */
@TableName(resultMap = "RoleMap")
public class Role implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId
	private Long id;

	/** 角色 */
	private String name;

	/** 排序 */
	private Integer sort;

	/** 描述 */
	private String description;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role{" + "id=" + id + ", name='" + name + '\'' + ", sort=" + sort 
				+ ", description='" + description + '\'' + '}';
	}
}
