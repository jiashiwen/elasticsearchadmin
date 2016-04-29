package com.leju.controller;

//import com.sishuok.entity.User;  

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan

public class TestController {
	@RequestMapping("/test")
	@ResponseBody	
	String test() {
		return "Hello test!";
	}
	


}
