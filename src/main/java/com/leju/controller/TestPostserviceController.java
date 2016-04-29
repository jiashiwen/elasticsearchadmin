package com.leju.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.leju.esrestful.service.*;

@RestController
@ComponentScan

public class TestPostserviceController {

	@Autowired
	GetMatchService service;

	@RequestMapping(value = "/testpostservice")
	public String getTag() {
		return service.getResponse().toString();
	}

}
