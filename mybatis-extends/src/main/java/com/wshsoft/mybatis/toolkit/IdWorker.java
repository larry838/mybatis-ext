package com.wshsoft.mybatis.toolkit;

/**
 * <p>
 * 高效GUID产生算法(sequence),基于Snowflake实现64位自增ID算法。 <br>
 * 优化开源项目 http://git.oschina.net/yu120/sequence
 * </p>
 *
 * @author carry xie
 * @Date 2016-08-01
 */
public class IdWorker {

	/**
	 * 主机和进程的机器码
	 */
	private static Sequence worker = new Sequence();

	public static long getId() {
		return worker.nextId();
	}

}