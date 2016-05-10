package com.leju.controller;

import org.springframework.beans.factory.annotation.Autowired;

//import com.sishuok.entity.User;  

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.leju.esrestful.configuration.AsyncStatusConfiguration;
import com.leju.esrestful.service.SetAsyncStatusService;
import com.leju.com.entities.*;

@RestController
@ComponentScan

public class SetAsycStatusController {
	@Autowired
	private AsyncStatusConfiguration as;
	
	@Autowired
	SetAsyncStatusService setstatus;

	@RequestMapping("/setasyncstatus")

	String test(@RequestParam(value = "source", required = true) String source,
			@RequestParam(value = "target", required = true) String target) {

		setstatus.SetAsyncStatus(source, target);
		return target;

	}

}
