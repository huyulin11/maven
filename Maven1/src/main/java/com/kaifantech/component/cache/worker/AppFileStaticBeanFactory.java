package com.kaifantech.component.cache.worker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.calculatedfun.util.AppTool;

public class AppFileStaticBeanFactory {
	private static ApplicationContext applicationContext;

	public static ApplicationContext ctx() {
		if (AppTool.isNull(applicationContext)) {
			applicationContext = new FileSystemXmlApplicationContext("classpath:spring-application.xml");
		}
		return applicationContext;
	}
}
