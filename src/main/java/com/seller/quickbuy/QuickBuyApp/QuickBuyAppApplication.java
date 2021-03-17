package com.seller.quickbuy.QuickBuyApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class QuickBuyAppApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(QuickBuyAppApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(QuickBuyAppApplication.class, args);
	}
}
	