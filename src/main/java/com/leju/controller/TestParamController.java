package com.leju.controller;


//import com.sishuok.entity.User;  

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan

public class TestParamController {

	@RequestMapping(value="/testparam",params="myParam")
	public String getName(@RequestParam("myParam") String a) {
		return a;
	}
}
