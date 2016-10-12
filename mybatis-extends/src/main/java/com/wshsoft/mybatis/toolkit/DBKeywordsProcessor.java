package com.wshsoft.mybatis.toolkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * <p>
 * 数据库关键字替换
 * </p>
 * 
 * @author carry xie
 * @Date 2016-08-25
 */
public class DBKeywordsProcessor {
	protected static final Logger logger = Logger.getLogger("DBKeywordsProcessor");
	private static Set<String> KEYWORDS = null;

	static {
		BufferedReader br = null;
		try {
			InputStream in = DBKeywordsProcessor.class.getClassLoader().getResourceAsStream("database_keywords.dic");
			br = new BufferedReader(new InputStreamReader(in));
			if (null == KEYWORDS) {
				KEYWORDS = new HashSet<String>();
			}
			String keyword = null;
			while ((keyword = br.readLine()) != null) {
				KEYWORDS.add(keyword);
			}
		} catch (Exception e) {
			logger.warning("If you want to support the keyword query, must have database_keywords.dic. " + e.getMessage());
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					logger.severe(e.getMessage());
				}
			}
		}
	}

	/**
	 * <p>
	 * 数据库关键词转义
	 * </p>
	 * 
	 * @param keyword
	 *            数据库关键词
	 * @return
	 */
	public static String convert(String keyword) {
		if (null != KEYWORDS && KEYWORDS.contains(keyword)) {
			return String.format("`%s`", keyword);
		}
		return keyword;
	}

}
