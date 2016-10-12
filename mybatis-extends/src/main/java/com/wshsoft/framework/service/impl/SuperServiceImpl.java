package com.wshsoft.framework.service.impl;

import com.wshsoft.mybatis.mapper.AutoMapper;

/**
 * <p>
 * 主键 Long 类型 IService 实现类（ 泛型：M 是 mapper 对象， T 是实体 ）
 * </p>
 * 
 * @author carry xie
 * @Date 2016-04-20
 */
public class SuperServiceImpl<M extends AutoMapper<T>, T> extends ServiceImpl<M, T, Long> {

}
