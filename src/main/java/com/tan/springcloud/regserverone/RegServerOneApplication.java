package com.tan.springcloud.regserverone;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
//http://blog.csdn.net/lc0817/article/details/54375802
@SpringBootApplication
public class RegServerOneApplication {

	public static void main(String[] args) {
		//SpringApplication.run(RegServerOneApplication.class, args);
		
		 new SpringApplicationBuilder(RegServerOneApplication.class)
         .web(true).run(args);
	}
}
