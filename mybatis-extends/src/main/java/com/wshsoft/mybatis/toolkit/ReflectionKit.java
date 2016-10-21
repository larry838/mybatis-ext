package com.wshsoft.mybatis.toolkit;


import java.util.logging.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * 反射工具类
 * </p>
 *
 * @author Caratacus
 * @Date 2016-09-22
 */
public class ReflectionKit {

	protected static final Logger logger = Logger.getLogger("ReflectionKit");

	/**
	 * <p>
	 * 反射 method 方法名，例如 getId
	 * </p>
	 *
	 * @param str
	 *            属性字符串内容
	 * @return
	 */
	public static String getMethodCapitalize(final String str) {
		return StringUtils.concatCapitalize("get", str);
	}

	/**
	 * 获取 public get方法的值
	 *
	 * @param cls
	 * @param entity
	 *            实体
	 * @param str
	 *            属性字符串内容
	 * @return Object
	 */
	public static Object getMethodValue(Class<?> cls, Object entity, String str) {
		Object obj = null;
		try {
			Method method = cls.getMethod(getMethodCapitalize(str));
			obj = method.invoke(entity);
		} catch (NoSuchMethodException e) {
			logger.warning("Warn: No such method. in " + cls + ".  Cause:" + e);
		} catch (IllegalAccessException e) {
			logger.warning("Warn: Cannot execute a private method. in " + cls + ".  Cause:" + e);
		} catch (InvocationTargetException e) {
			logger.warning("Warn: Unexpected exception on getMethodValue.  Cause:" + e);
		}
		return obj;
	}

	/**
	 * 获取 public get方法的值
	 *
	 * @param entity
	 *            实体
	 * @param str
	 *            属性字符串内容
	 * @return Object
	 */
	public static Object getMethodValue(Object entity, String str) {
		if (null == entity) {
			return null;
		}
		return getMethodValue(entity.getClass(), entity, str);
	}

	/**
	 * 调用对象的get方法检查对象所有属性是否为null
	 *
	 * @param bean
	 *            检查对象
	 * @return boolean true对象所有属性不为null,false对象所有属性为null
	 */
	public static boolean checkFieldValueNotNull(Object bean) {
		if (null == bean) {
			return false;
		}
		Class<?> cls = bean.getClass();
		TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
		if (null == tableInfo) {
			logger.warning("Warn: Could not find @TableId.");
			return false;
		}
		boolean result = false;
		List<TableFieldInfo> fieldList = tableInfo.getFieldList();
		for (TableFieldInfo tableFieldInfo : fieldList) {
			Object val = getMethodValue(cls, bean, tableFieldInfo.getProperty());
			if (null != val) {
				result = true;
				break;
			}
		}
		return result;
	}

}