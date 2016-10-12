package com.wshsoft.mybatis.environment;

/**
 *  探测运行环境
 * @author carry xie
 * @Date 2016-01-23
 */
public class Environment{

	public static String LOCAL = "LOCAL";

	public static String DEVELOP = "DEVELOP";

	public static String TEST = "TEST";

	public static String PRODUCT = "PRODUCT";

    public static boolean isProduct=false;//生产环境

	public static boolean isProduct() {
		return isProduct;
	}

	public static void setProduct(boolean isProduct) {
		Environment.isProduct = isProduct;
	}
    

}