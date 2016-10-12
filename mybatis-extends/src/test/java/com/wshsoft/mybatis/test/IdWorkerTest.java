package com.wshsoft.mybatis.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.wshsoft.mybatis.toolkit.IdWorker;

/**
 * <p>
 * IdWorker 并发测试
 * </p>
 * 
 * @author carry xie
 * @date 2016-08-01
 */
public class IdWorkerTest { 

	/**
	 * 测试
	 */
	public static void main(String[] args) {
		int count = 1000;
		ExecutorService executorService = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
			executorService.execute(new IdWorkerTest().new Task());
		}
		executorService.shutdown();
		while (!executorService.isTerminated()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public class Task implements Runnable {

		public void run() {
			try {
				long id = IdWorker.getId();
				System.err.println(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
