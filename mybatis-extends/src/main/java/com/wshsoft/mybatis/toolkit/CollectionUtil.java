package com.wshsoft.mybatis.toolkit;

import java.util.Collection;

/**
 * <p>
 * Collection工具类
 * </p>
 *
 * @author carry xie
 * @Date 2016-09-19
 */
public class CollectionUtil {

	/**
	 * <p>
	 * 校验集合是否为空
	 * </p>
	 *
	 * @param coll
	 * @return boolean
	 */
	public static boolean isEmpty(Collection<?> coll) {
		return (coll == null || coll.isEmpty());
	}

	/**
	 * <p>
	 * 校验集合是否不为空
	 * </p>
	 *
	 * @param coll
	 * @return boolean
	 */
	public static boolean isNotEmpty(Collection<?> coll) {
		return !isEmpty(coll);
	}

}
