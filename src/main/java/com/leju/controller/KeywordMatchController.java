package com.leju.controller;

import org.springframework.beans.factory.annotation.Autowired;

//import com.sishuok.entity.User;  

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leju.esrestful.service.GetMatchService;

@RestController
@ComponentScan
@RequestMapping(value = "/search")
public class KeywordMatchController {
	@Autowired
	GetMatchService service;

	@RequestMapping(value = "/match")
	public String getName(@RequestParam("keyword") String keyword,
			@RequestParam(value = "force", required = false) boolean force) {

		if (force == true) {
			return String.valueOf(force);
		}
		return service.getResponse(keyword).toString();
	}
}
