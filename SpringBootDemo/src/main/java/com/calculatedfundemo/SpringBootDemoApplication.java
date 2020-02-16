package com.calculatedfundemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @ServletComponentScan
// @ComponentScan(basePackages = {
// "com.calculatedfundemo"//"com.calculatedfun.component.*",
// "com.calculatedfun.logAop",
// })"com.kaifantech.component.*" })
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}
}
