package com.wshsoft.mybatis.test.oracle;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.wshsoft.mybatis.MybatisSessionFactoryBuilder;
import com.wshsoft.mybatis.mapper.EntityWrapper;
import com.wshsoft.mybatis.plugins.pagination.Page;
import com.wshsoft.mybatis.test.oracle.entity.TestUser;

/**
 * <p>
 * 测试类
 * </p>
 * 
 * @author carry xie
 * @Date 2016-04-25
 */
public class TestUserMapperTest {


	/**
	 * 
	 * RUN 测试（ 更多查看 MySql 测试类 ）
	 * 
	 */
	public static void main( String[] args ) {

		//加载配置文件
		InputStream in = TestUserMapperTest.class.getClassLoader().getResourceAsStream("oracle-config.xml");

		/*
		 * 此处采用 MybatisSessionFactoryBuilder 构建
		 * SqlSessionFactory，目的是引入AutoMapper功能
		 */
		MybatisSessionFactoryBuilder mf = new MybatisSessionFactoryBuilder();

		/** 设置数据库类型为 oracle */
		mf.setDbType("oracle");

		/*
		 * 1、数据库字段驼峰命名不需要任何设置
		 * 2、当前演示是驼峰下划线混合命名
		 * 3、如下开启，表示数据库字段使用下划线命名，该设置是全局的。
		 *	 开启该设置实体可无 @TableId(value = "test_id") 字段映射
		 */
		//mf.setDbColumnUnderline(true);

		SqlSessionFactory sessionFactory = mf.build(in);
		SqlSession session = sessionFactory.openSession();
		TestUserMapper testUserMapper = session.getMapper(TestUserMapper.class);
		System.err.println(" debug run 查询执行 test_user 表数据变化！ ");
		session.delete("deleteAll");

		/**
		 * 插入
		 */
		int rlt = testUserMapper.insert(new TestUser("10", "abc", 18, 1));
		System.err.println("\n--------------insert-------" + rlt);
		sleep();
		
		/**
		 * 批量插入
		 */
		List<TestUser> ul = new ArrayList<TestUser>();
		ul.add(new TestUser("11", "1a", 11, 1));
		ul.add(new TestUser("12", "2a", 12, 2));
		ul.add(new TestUser("a", 1, 1));
		ul.add(new TestUser("b", 2, 2));
		ul.add(new TestUser("c", 3, 1));
		rlt = testUserMapper.insertBatch(ul);
		System.err.println("\n--------------insertBatch-------" + rlt);
		sleep();
		
		
		/**
		 * 批量更新
		 */
		List<TestUser> ul1 = new ArrayList<TestUser>();
		ul1.add(new TestUser("10", "update-0a", 11, 1));
		ul1.add(new TestUser("11", "update-1a", 11, 1));
		ul1.add(new TestUser("12", "update-2a", 12, 2));
		rlt = testUserMapper.updateBatchById(ul1);
		System.err.println("\n--------------updateBatchById-------" + rlt);
		sleep();
		
		System.err.println("\n------------------list 分页查询 ----查询 testType = 1 的所有数据--id--DESC--排序--------");
		Page<TestUser> page = new Page<TestUser>(1, 2);
		EntityWrapper<TestUser> ew = new EntityWrapper<TestUser>(new TestUser(1), "TEST_ID DESC");
		List<TestUser> paginList = testUserMapper.selectPage(page, ew);
		page.setRecords(paginList);
		for ( int i = 0 ; i < page.getRecords().size() ; i++ ) {
			print(page.getRecords().get(i));
		}
		System.err.println(" 翻页：" + page.toString());
		
		/* 删除测试数据  */
		rlt = session.delete("deleteAll");
		System.err.println("清空测试数据！ rlt=" + rlt);

		/**
		 * 提交
		 */
		session.commit();
	}


	/*
	 * 打印测试信息
	 */
	private static void print( TestUser user ) {
		sleep();
		if ( user != null ) {
			System.out.println("\n user: id="
					+ user.getTestId() + ", name=" + user.getName() + ", age=" + user.getAge() + ", testType="
					+ user.getTestType());
		} else {
			System.out.println("\n user is null.");
		}
	}


	/*
	 * 慢点打印 
	 */
	private static void sleep() {
		try {
			Thread.sleep(1000);
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		}
	}
}
