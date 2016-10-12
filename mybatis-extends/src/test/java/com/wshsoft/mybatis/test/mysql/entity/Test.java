package com.wshsoft.mybatis.test.mysql.entity;

import java.io.Serializable;

import com.wshsoft.mybatis.annotations.TableField;
import com.wshsoft.mybatis.annotations.TableId;
import com.wshsoft.mybatis.annotations.TableName;

/**
 * <p>
 * 测试没有XML同样注入CRUD SQL 实体
 * </p>
 *
 * @author  carry xie
 * @Date 2016-09-25
 */
@TableName("test")
public class Test implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId
	private Long id;

	private String type;

	public Test() {

	}

	public Test(Long id, String type) {
		this.id = id;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
