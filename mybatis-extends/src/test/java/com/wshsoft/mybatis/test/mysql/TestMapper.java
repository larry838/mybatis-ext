package com.wshsoft.mybatis.test.mysql;

import org.apache.ibatis.annotations.Insert;

import com.wshsoft.mybatis.mapper.AutoMapper;
import com.wshsoft.mybatis.test.mysql.entity.Test;

/**
 * <p>
 * 继承 AutoMapper，就自动拥有CRUD方法
 * </p>
 * 
 * @author  carry xie
 * @Date 2016-09-25
 */
public interface TestMapper extends AutoMapper<Test> {

	/**
	 * 注解插入【测试】
	 */
	@Insert("insert into test(id,type) values(#{id},#{type})")
	int insertInjector(Test test);

}
