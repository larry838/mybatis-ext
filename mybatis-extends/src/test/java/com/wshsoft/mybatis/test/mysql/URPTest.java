package com.wshsoft.mybatis.test.mysql;

import com.wshsoft.mybatis.MybatisSessionFactoryBuilder;
import com.wshsoft.mybatis.test.mysql.entity.PhoneNumber;
import com.wshsoft.mybatis.test.mysql.entity.Role;
import com.wshsoft.mybatis.test.mysql.entity.User;
import com.wshsoft.mybatis.test.mysql.mapper.RoleMapper;
import com.wshsoft.mybatis.test.mysql.mapper.UserMapper;
import com.wshsoft.mybatis.toolkit.IdWorker;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.InputStream;

/**
 * <p>
 * 对User, Role及el, typeHandler, resultMap进行测试
 * </p>
 *
 * @author carry xie
 * @Date 2016-09-09
 */
public class URPTest {
	public static void main(String[] args) {

		// 加载配置文件
		InputStream in = UserMapperMybatisTest.class.getClassLoader().getResourceAsStream("mysql-config.xml");
		MybatisSessionFactoryBuilder mf = new MybatisSessionFactoryBuilder();
		mf.setSqlInjector(new MySqlInjector());
		mf.setMetaObjectHandler(new MyMetaObjectHandler());
		SqlSessionFactory sessionFactory = mf.build(in);
		SqlSession session = sessionFactory.openSession();

		UserMapper userMapper = session.getMapper(UserMapper.class);
		RoleMapper roleMapper = session.getMapper(RoleMapper.class);

		/**
		 * sjy 测试@TableField的el属性, 级联resultMap
		 */
		Role role = new Role();
		role.setId(IdWorker.getId());
		role.setName("admin");
		int rlt = roleMapper.insert(role);
		System.err.println("--------- insert role --------- " + rlt);

		PhoneNumber phone = new PhoneNumber("81", "0571", "82453832");

		User userA = new User();
		userA.setId(IdWorker.getId());
		userA.setName("junyu_shi");
		userA.setAge(15);
		userA.setTestType(1);
		userA.setRole(role);
		userA.setPhone(phone);
		rlt = userMapper.insert(userA);
		System.err.println("--------- insert user --------- " + rlt);

		User whereUser = userMapper.selectOne(userA);
		System.err.println("--------- select user --------- " + whereUser.toString());

		// 如果不使用el表达式, User类中就同时需要roleId用于对应User表中的字段,
		// 和Role属性用于保存resultmap的级联查询. 在插入时, 就需要写user.setRoleId(), 然后updateUser.
		role = new Role();
		role.setId(IdWorker.getId());
		role.setName("root");
		roleMapper.insert(role);
		userA.setRole(role);
		userMapper.updateById(userA);
		System.err.println("--------- upadte user's role --------- " + rlt);

		whereUser = userMapper.selectOne(userA);
		System.err.println("--------- select user --------- " + whereUser.toString());

		userMapper.deleteSelective(userA);
		System.err.println("--------- delete user --------- " + rlt);

	}

}
