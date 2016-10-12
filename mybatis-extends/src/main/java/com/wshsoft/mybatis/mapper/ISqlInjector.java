package com.wshsoft.mybatis.mapper;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;

/**
 * <p>
 * SQL 自动注入器接口
 * </p>
 * 
 * @author carry xie
 * @Date 2016-07-24
 */
public interface ISqlInjector {

	/**
	 * <p>
	 * 注入 SQL
	 * </p>
	 */
	void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass);

	/**
	 * <p>
	 * 检查SQL是否已经注入
	 * </p>
	 * <p>
	 * ps:注入基本SQL后给予标识 注入过不再注入
	 * </p>
	 */
	void inspectInject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass);

}
