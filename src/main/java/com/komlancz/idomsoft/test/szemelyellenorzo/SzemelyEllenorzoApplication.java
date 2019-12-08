package com.komlancz.idomsoft.test.szemelyellenorzo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.komlancz.idomsoft.test.szemelyellenorzo")
public class SzemelyEllenorzoApplication extends SpringBootServletInitializer {

	@Override protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(SzemelyEllenorzoApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SzemelyEllenorzoApplication.class, args);
	}

}
