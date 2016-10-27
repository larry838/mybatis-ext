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
	 * 根据mapperClass注入SQL
	 * 
	 * @param configuration
	 * @param builderAssistant
	 * @param mapperClass
	 */
	void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass);

	/**
	 * 检查SQL是否注入(已经注入过不再注入)
	 * 
	 * @param configuration
	 * @param builderAssistant
	 * @param mapperClass
	 */
	void inspectInject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass);
}
