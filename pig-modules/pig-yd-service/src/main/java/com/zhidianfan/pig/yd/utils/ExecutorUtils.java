package com.zhidianfan.pig.yd.utils;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 * @author danda
 */
public class ExecutorUtils {
	private static Map<String, ThreadPoolExecutor> threadPoolExecutorMap = Maps.newHashMap();


	public static ThreadPoolExecutor newThreadPoolExecutor(int poolSize, int maxPoolSize) {
		return newThreadPoolExecutor(poolSize, maxPoolSize, "");
	}

	public static ThreadPoolExecutor newThreadPoolExecutor(int poolSize, int maxPoolSize, String threadName) {
		ThreadPoolExecutor threadPoolExecutor = ExecutorUtils.threadPoolExecutorMap.get(threadName);
		if (threadPoolExecutor == null) {
			ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat(threadName).build();
			threadPoolExecutor = new ThreadPoolExecutor(poolSize, maxPoolSize, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
			threadPoolExecutorMap.put(threadName, threadPoolExecutor);
		}
		return threadPoolExecutor;
	}


}
