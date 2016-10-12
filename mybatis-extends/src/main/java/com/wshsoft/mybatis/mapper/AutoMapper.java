package com.wshsoft.mybatis.mapper;

/**
 * <p>
 * Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
 * </p>
 * <p>
 * java.lang.Long 类型 ID 主键
 * </p>
 * @author carry xie
 * @Date 2016-01-23
 */
public interface AutoMapper<T> extends BaseMapper<T, Long> {
	
}
