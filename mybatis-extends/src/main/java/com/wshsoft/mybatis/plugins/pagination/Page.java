package com.wshsoft.mybatis.plugins.pagination;

import java.util.Collections;
import java.util.List;

import com.wshsoft.mybatis.toolkit.StringUtils;

/**
 * <p>
 * 实现分页辅助类
 * </p>
 *
 * @author carry xie
 * @Date 2016-03-01
 */
public class Page<T> extends Pagination {

	private static final long serialVersionUID = 1L;

	/**
	 * 查询数据列表
	 */
	private List<T> records = Collections.emptyList();

	/**
	 * <p>
	 * SQL 排序 ORDER BY 字段，例如： id DESC（根据id倒序查询）
	 * </p>
	 * <p>
	 * DESC 表示按倒序排序(即：从大到小排序)<br>
	 * ASC 表示按正序排序(即：从小到大排序)
	 * </p>
	 */
	private String orderByField;

	/**
	 * 是否为升序 ASC（ 默认： true ）
	 */
	private boolean isAsc = true;

	public Page() {
		/* 注意，传入翻页参数 */
	}

	public Page(int current, int size) {
		super(current, size);
	}

	public Page(int current, int size, String orderByField) {
		super(current, size);
		this.setOrderByField(orderByField);
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public String getOrderByField() {
		return orderByField;
	}

	public void setOrderByField(String orderByField) {
		if (StringUtils.isNotEmpty(orderByField)) {
			this.orderByField = orderByField;
		}
	}

	public boolean isAsc() {
		return isAsc;
	}

	public void setAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	@Override
	public String toString() {
		StringBuffer pg = new StringBuffer();
		pg.append(" Page:{ [").append(super.toString()).append("], ");
		if (records != null) {
			pg.append("records-size:").append(records.size());
		} else {
			pg.append("records is null");
		}
		return pg.append(" }").toString();
	}

}
