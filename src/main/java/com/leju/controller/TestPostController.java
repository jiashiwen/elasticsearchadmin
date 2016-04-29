package com.leju.controller;

//import com.sishuok.entity.User;  

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan

public class TestPostController {
	@RequestMapping(value="/testpost",method={RequestMethod.POST})
    public String getBookById(@RequestBody String body){
  
     return body+"ok!!!!!!!!!!!!!!!!!";
    }
}
