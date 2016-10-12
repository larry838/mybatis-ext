package com.wshsoft.mybatis.test.base.generator;

import com.wshsoft.mybatis.generator.ConfigGenerator;

/**
 * ConfigGeneratorTest
 * @author carry xie
 * @Date 2016-01-06
 */
public class BaseConfigGenerator {

	protected static ConfigGenerator getConfigGenerator() {
		ConfigGenerator cg = new ConfigGenerator();
		cg.setEntityPackage("com.wshsoft.entity");// entity 实体包路径
		cg.setMapperPackage("com.wshsoft.mapper");// mapper 映射文件路径
		cg.setServicePackage("com.wshsoft.service");// service 层路径
		cg.setXmlPackage("com.wshsoft.mapper.xml");// xml层路径（可以不写）
		cg.setServiceImplPackage("com.wshsoft.service.impl");// serviceimpl层路径（可以不写）

		/* 此处可以配置，自定义 service 及 serviceImpl 子类路径 */
		//cg.setSuperService("com.xxx.service.IBaseService");
		//cg.setSuperServiceImpl("com.xxx.service.impl.BaseServiceImpl");

		/* 此处设置 String 类型数据库ID，默认Long类型 */
		// cg.setConfigIdType(ConfigIdType.STRING);

		/*
		 * 生成文件保存位置
		 */
		cg.setSaveDir("D:/mybatis-extends/");

		/*
		 * 【实体】是否生成字段常量（默认 false）<br>
		 * -----------------------------------<br>
		 * public static final String ID = "test_id";
		 */
		cg.setColumnConstant(false);

		/*
		 * 【实体】是否为构建者模型（默认 false）<br>
		 * -----------------------------------<br>
		 * 	public User setName(String name) {
		 * 		this.name = name;
		 * 		return this;
		 * 	}
		 */
		cg.setBuliderModel(false);

		/*
		 * <p>
		 * true 表示数据库设置全局下划线命名规则，默认 false
		 * -------------------------------------------------------<br>
		 *【 开启该设置实体可无 @TableId(value = "test_id") 字段映射，启动配置对应也要开启 true 设置 】<br>
		 * </p>
		 */
		cg.setDbColumnUnderline(false);

		/*
		 * 表是否包括前缀 <p> 例如 mp_user 生成实体类 false 为 MpUser , true 为 User </p>
		 */
		cg.setDbPrefix(false);

		/*
		 * 默认值为true , 是否覆盖当前路径下已有文件
		 */
		cg.setFileOverride(true);

		/*
		 * 自定义类名，需要包含 %s 格式化会填充实体beanName
		 */
//		 cg.setMapperName("Test%sDao");
//		 cg.setMapperXMLName("Test%sMapper");
//		 cg.setServiceName("Test%sSer");
//		 cg.setServiceImplName("%sSerImpl");
		
		/**
		 * 自定义 BaseEntity
		 */
//		ConfigBaseEntity cbe = new ConfigBaseEntity();
//		cbe.setPackageName("com.wshsoft.test");
//		cbe.setClassName("BaseEntity");
//		cbe.setColumns(new String[]{"test_id", "age", "createTime"});
//		cg.setConfigBaseEntity(cbe);
		return cg;
	}

}
