package com.wshsoft.mybatis.test.ioc;

/**
 * <p>
 * 宝马汽车
 * </p>
 * 
 * @author xiejian
 * @Date 2016-07-06
 */
public class Bmw implements ICar {

	public boolean start() {
		System.err.println(" 宝马X6 点火启动... ");
		return false;
	}


	public void driver() {
		System.err.println(" 走你！ 宝马X6 ... ");
	}

}
