package com.leju.controller;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;

//import com.sishuok.entity.User;  

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leju.esrestful.service.DslQueryService;

@RestController
@ComponentScan
@RequestMapping(value = "/search")
public class DslQueryController {
	@Autowired
	DslQueryService  dslquery;

	@RequestMapping(value = "/dslquery", params = {"indexname"},method={RequestMethod.POST})
	public String getrestult(@RequestParam("indexname") String indexname, @RequestBody String  querystring) throws UnknownHostException {	
		System.out.print(querystring);
		return dslquery.getResponse(indexname.split(","), querystring).toString();		
	}
}
