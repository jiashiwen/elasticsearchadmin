package com.leju.controller;

import org.springframework.beans.factory.annotation.Autowired;

//import com.sishuok.entity.User;  

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.leju.esrestful.configuration.AsyncStatusConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leju.com.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@ComponentScan

public class GetAsycStatusController {
	@Autowired
	private AsyncStatusConfiguration as;

	private ObjectMapper mapper = new ObjectMapper();
	@RequestMapping("/getasyncstatus")
	@ResponseBody
	String test() {
		
		
		try {
			return mapper.writeValueAsString(as.getStatusmap());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

}
