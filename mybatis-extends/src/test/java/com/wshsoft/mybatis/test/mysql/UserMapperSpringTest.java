package com.wshsoft.mybatis.test.mysql;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wshsoft.mybatis.mapper.EntityWrapper;
import com.wshsoft.mybatis.plugins.pagination.Page;
import com.wshsoft.mybatis.test.mysql.entity.User;
import com.wshsoft.mybatis.test.mysql.mapper.UserMapper;

/**
 * 
 * @author carry xie
 * @Date 2014-5-16
 */ 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring.xml" })

public class UserMapperSpringTest {
	 
	@Autowired
	private UserMapper userMapper;
	@Before
	public void before() {
	     userMapper.insertInjector(new User(1L, "1", 1, 1));
	}
	
	/*
	@Test
	public void testselectById(){
		User user=userMapper.selectById(1L);
		System.out.println(user.toString());
	}
	

	
	@Test
	public void testQueryAll(){
		userMapper.selectList(null);
	}
	
	@Test
	public void testSelectList(){
        List<User> ul2 = userMapper.selectList(new EntityWrapper<User>(null, "age,name"));
        for (User anUl2 : ul2) {
            print(anUl2);
        }
	}
	*/
	   
		@Test
		   public void testquery(){
			System.err.println("\n------------------list 分页查询 ----查询所有数据--id--DESC--排序--------");
			Page<User> page = new Page<User>(1, 2);
			EntityWrapper<User> ew = new EntityWrapper<User>(new User(1));
			ew.setSqlSelect("age,name");
			ew.where("name like {0}", "'%dateBatch%'").and("age={0}", 3).orderBy("test_id", false);
			List<User> paginList = userMapper.selectPage(page, ew);
			page.setRecords(paginList);
			for ( int i = 0 ; i < page.getRecords().size() ; i++ ) {
				print(page.getRecords().get(i));
			}
			System.err.println(" 翻页：" + page.toString());
			
	    
		   }
	@After
	public  void after() {
		 userMapper.deleteAll();
	}	
    /*
     * 打印测试信息
     */
    private static void print(User user) {
        if (user != null) {
            System.out.println("\n user: id=" + user.getId() + ", name=" + user.getName() + ", age=" + user.getAge()
                    + ", testType=" + user.getTestType());
        } else {
            System.out.println("\n user is null.");
        }
    }
}
