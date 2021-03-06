package com.leju;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@ComponentScan
public class SearchrestApplication {

	@RequestMapping("/hello")
	@ResponseBody
	String home() {
		return "Hello World!";
	}
	private static final Logger logger = LoggerFactory.getLogger(SpringApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SearchrestApplication.class, args);
	}
}
