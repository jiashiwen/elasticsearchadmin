package com.leju.controller;

//import com.sishuok.entity.User;  

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan

public class TestValueController {
	@RequestMapping(value="/test/{id}",method={RequestMethod.POST,RequestMethod.GET})
    public String getBookById(@PathVariable String id){
     String getid=id;
     return getid;
    }
}
