package com.wshsoft.mybatis.test.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.session.RowBounds;

import com.wshsoft.mybatis.mapper.AutoMapper;
import com.wshsoft.mybatis.test.mysql.entity.User;

/**
 * <p>
 * 继承 AutoMapper，就自动拥有CRUD方法
 * </p>
 * 
 * @author carry xie
 * @Date 2016-01-23
 */
public interface UserMapper extends AutoMapper<User> {

	/**
	 * 用户列表，分页显示
	 * 
	 * @param pagination
	 *            传递参数包含该属性，即自动分页
	 * @return
	 */
	List<User> selectListRow(RowBounds pagination);
	
	/**
	 * 注解插入【测试】
	 */
	@Insert("INSERT INTO user(test_id,name,age) VALUES(#{id},#{name},#{age})")  
	int insertInjector(User user);

	/**
	 * 自定义注入方法
	 */
	int deleteCustomAll();
	/**
	 * 注解 删除
	 * @return 
	 */
	@Delete("DELETE FROM user")
	void deleteAll();

}
