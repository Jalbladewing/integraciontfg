package com.tfgllopis.integracion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class IntegracionApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(IntegracionApplication.class, args);
	}
	
	/**
	 * Used when run as WAR
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		return builder.sources(IntegracionApplication.class);
	}
}
