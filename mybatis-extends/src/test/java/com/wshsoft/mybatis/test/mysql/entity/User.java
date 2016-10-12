package com.wshsoft.mybatis.test.mysql.entity;

import com.wshsoft.mybatis.annotations.FieldStrategy;
import com.wshsoft.mybatis.annotations.TableField;
import com.wshsoft.mybatis.annotations.TableId;
import com.wshsoft.mybatis.annotations.TableName;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * <p>
 * 测试用户类
 * </p>
 * 
 * @author carry xie
 * @Date 2016-09-09
 */
/* 表名 value 注解【 驼峰命名可无 】, resultMap 注解测试【 映射 xml 的 resultMap 内容 】 */
@TableName(resultMap = "userMap")
//@TableName(value = "user")
public class User implements Serializable {

	/* 表字段注解，false 表中不存在的字段，可无该注解 默认 true */
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/* 主键ID 注解，value 字段名，type 用户输入ID */
	@TableId(value = "test_id")
	private Long id;

	/* 测试忽略验证 */
	@TableField(validate = FieldStrategy.IGNORED)
	private String name;

	private Integer age;
	
	/* 测试下划线字段命名类型, 字段填充 */
	@TableField(value = "test_type", validate = FieldStrategy.FILL)
	private Integer testType;

	@TableField(el = "role.id",value = "role_id")
	private Role role;

	//或@TableField(el = "role,jdbcType=BIGINT)
	@TableField(el = "phone, typeHandler=com.wshsoft.mybatis.test.mysql.typehandler.PhoneTypeHandler")
	private PhoneNumber phone;

	private Date birthday; 

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public User() {

	}

	public User(String name) {
		this.name = name;
	}

	public User(Integer testType) {
		this.testType = testType;
	}

	public User(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public User(Long id, Integer age) {
		this.id = id;
		this.age = age;
	}

	public User(Long id, String name, Integer age, Integer testType) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.testType = testType;
	}
	
	public User(String name, Integer age, Integer testType) {
		this.name = name;
		this.age = age;
		this.testType = testType;
	}
	
	public User(Long id, String name, Integer age, Integer testType,Date birthday) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.testType = testType;
		this.birthday=birthday;
	}
	
	public User(String name, Integer age, Integer testType,Date birthday) {
		this.name = name;
		this.age = age;
		this.testType = testType;
		this.birthday=birthday;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getTestType() {
		return testType;
	}

	public void setTestType(Integer testType) {
		this.testType = testType;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public PhoneNumber getPhone() {
		return phone;
	}

	public void setPhone(PhoneNumber phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				", testType=" + testType +
				", role=" + role +
				", phone=" + phone +
				'}';
	}

	/**
	 * 测试类型
	 */
	public static void main(String args[]) throws IllegalAccessException {
		User user = new User();
		user.setName("12306");
		user.setAge(3);
		System.err.println(User.class.getName());
		Field[] fields = user.getClass().getDeclaredFields();
		for (Field field : fields) {
			System.out.println("===================================");
			System.out.println(field.getName());
			System.out.println(field.getType().toString());
			field.setAccessible(true);
			System.out.println(field.get(user));
		}
	}
}
