package com.wshsoft.mybatis.test;

import com.wshsoft.mybatis.mapper.EntityWrapper;
import com.wshsoft.mybatis.test.mysql.entity.User;
import com.wshsoft.mybatis.toolkit.TableInfoHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 条件查询测试
 * </p>
 *
 * @author carry xie
 * @date 2016-08-19
 */
public class EntityWrapperTest {

	/*
	 * User 查询包装器
	 */
	private EntityWrapper<User> ew = new EntityWrapper<User>();

	// 初始化
	static {
		TableInfoHelper.initTableInfo(User.class);
	}

	@Test
	public void test() {
		/*
		 * 无条件测试
		 */
		Assert.assertNull(ew.getSqlSegment());
	}

	@Test
	public void test11() {
		/*
		 * 实体带where ifneed
		 */
		ew.setEntity(new User(1));
		ew.where("name={0}", "'123'").addFilterIfNeed(false, "id=12");
		String sqlSegment = ew.getSqlSegment();
		System.err.println("test11 = " + sqlSegment);
		Assert.assertEquals("AND (name='123')", sqlSegment);
	}

	@Test
	public void test12() {
		/*
		 * 实体带where orderby
		 */
		ew.setEntity(new User(1));
		ew.where("name={0}", "'123'").orderBy("id", false);
		String sqlSegment = ew.getSqlSegment();
		System.err.println("test12 = " + sqlSegment);
		Assert.assertEquals("AND (name='123')\nORDER BY id DESC", sqlSegment);
	}

	@Test
	public void test13() {
		/*
		 * 实体排序
		 */
		ew.setEntity(new User(1));
		ew.orderBy("id", false);
		String sqlSegment = ew.getSqlSegment();
		System.err.println("test13 = " + sqlSegment);
		Assert.assertEquals("ORDER BY id DESC", sqlSegment);
	}

	@Test
	public void test21() {
		/*
		 * 无实体 where ifneed orderby
		 */
		ew.where("name={0}", "'123'").addFilterIfNeed(false, "id=1").orderBy("id");
		String sqlSegment = ew.getSqlSegment();
		System.err.println("test21 = " + sqlSegment);
		Assert.assertEquals("WHERE (name='123')\nORDER BY id", sqlSegment);
	}

	@Test
	public void test22() {
		ew.where("name={0}", "'123'").orderBy("id", false);
		String sqlSegment = ew.getSqlSegment();
		System.err.println("test22 = " + sqlSegment);
		Assert.assertEquals("WHERE (name='123')\nORDER BY id DESC", sqlSegment);
	}

	@Test
	public void test23() {
		/*
		 * 无实体查询，只排序
		 */
		ew.orderBy("id", false);
		String sqlSegment = ew.getSqlSegment();
		System.err.println("test23 = " + sqlSegment);
		Assert.assertEquals("ORDER BY id DESC", sqlSegment);
	}

	@Test
	public void testNoTSQL() {
		/*
		 * 实体 filter orderby
		 */
		ew.setEntity(new User(1));
		ew.addFilter("name={0}", "'123'").orderBy("id,name");
		String sqlSegment = ew.getSqlSegment();
		System.err.println("testNoTSQL = " + sqlSegment);
		Assert.assertEquals("AND (name='123')\nORDER BY id,name", sqlSegment);
	}

	@Test
	public void testNoTSQL1() {
		/*
		 * 非 T-SQL 无实体查询
		 */
		ew.addFilter("name={0}", "'123'").addFilterIfNeed(false, "status={1}", "1");
		String sqlSegment = ew.getSqlSegment();
		System.err.println("testNoTSQL1 = " + sqlSegment);
		Assert.assertEquals("WHERE (name='123')", sqlSegment);
	}

	@Test
	public void testTSQL11() {
		/*
		 * 实体带查询使用方法 输出看结果
		 */
		ew.setEntity(new User(1));
		ew.where("name={0}", "'zhangsan'").and("id=1").orNew("status={0}", "0").or("status=1").notLike("nlike", "notvalue")
				.andNew("new=xx").like("hhh", "ddd").andNew("pwd=11").isNotNull("n1,n2").isNull("n3").groupBy("x1")
				.groupBy("x2,x3").having("x1=11").having("x3=433").orderBy("dd").orderBy("d1,d2");
		System.out.println(ew.getSqlSegment());
	}

	@Test
	public void testNull() {
		ew.orderBy(null);
		String sqlPart = ew.getSqlSegment();
		Assert.assertNull(sqlPart);
	}

	@Test
	public void testNull2() {
		ew.like(null, null).where("aa={0}", "'bb'").orderBy(null);
		String sqlPart = ew.getSqlSegment();
		Assert.assertEquals("WHERE (aa='bb')", sqlPart);
	}

	/**
	 * 测试带单引号的值是否不会再次添加单引号
	 */
	@Test
	public void testNul14() {
		ew.where("id={0}", "'11'").and("name={0}", 22);
		String sqlPart = ew.getSqlSegment();
		System.out.println("sql ==> " + sqlPart);
		Assert.assertEquals("WHERE (id='11' AND name=22)", sqlPart);
	}

	/**
	 * 测试带不带单引号的值是否会自动添加单引号
	 */
	@Test
	public void testNul15() {
		ew.where("id={0}", "11").and("name={0}", 222222222);
		String sqlPart = ew.getSqlSegment();
		System.out.println("sql ==> " + sqlPart);
		Assert.assertEquals("WHERE (id='11' AND name=222222222)", sqlPart);
	}

	/**
	 * 测试EXISTS
	 */
	@Test
	public void testNul16() {
		ew.notExists("(select * from user)");
		String sqlPart = ew.getSqlSegment();
		System.out.println("sql ==> " + sqlPart);
		Assert.assertEquals("WHERE ( NOT EXISTS ((select * from user)))", sqlPart);
	}

	/**
	 * 测试NOT IN
	 */
	@Test
	public void testNul17() {
		List<String> list = new ArrayList<String>();
		list.add("'1'");
		list.add("'2'");
		list.add("'3'");
		ew.notIn("test_type", list);
		String sqlPart = ew.getSqlSegment();
		System.out.println("sql ==> " + sqlPart);
		Assert.assertEquals("WHERE (test_type NOT IN ('1','2','3'))", sqlPart);
	}

	/**
	 * 测试IN
	 */
	@Test
	public void testNul18() {
		List<Long> list = new ArrayList<Long>();
		list.add(111111111L);
		list.add(222222222L);
		list.add(333333333L);
		ew.in("test_type", list);
		String sqlPart = ew.getSqlSegment();
		System.out.println("sql ==> " + sqlPart);
		Assert.assertEquals("WHERE (test_type IN (111111111,222222222,333333333))", sqlPart);
	}

	/**
	 * 测试BETWEEN AND
	 */
	@Test
	public void testNul19() {
		String val1 = "11";
		String val2 = "33";
		ew.between("test_type", val1, val2);
		String sqlPart = ew.getSqlSegment();
		System.out.println("sql ==> " + sqlPart);
		Assert.assertEquals("WHERE (test_type BETWEEN 11 AND 33)", sqlPart);
	}

	/**
	 * 测试Escape
	 */
	@Test
	public void testEscape() {
		String val1 = "'''";
		String val2 = "\\";
		ew.between("test_type", val1, val2);
		String sqlPart = ew.getSqlSegment();
		System.out.println("sql ==> " + sqlPart);
		Assert.assertEquals("WHERE (test_type BETWEEN '\\'' AND '\\\\')", sqlPart);
	}
}
