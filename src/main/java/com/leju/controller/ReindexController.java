package com.leju.controller;

import org.springframework.beans.factory.annotation.Autowired;

//import com.sishuok.entity.User;  

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leju.esrestful.service.ReindexService;

@RestController
@ComponentScan
@RequestMapping(value = "/_reindex")
public class ReindexController {
	@Autowired
	ReindexService reindex;

	@RequestMapping(value = "/byrelation",method={RequestMethod.POST})
	public boolean ReindexByRelation(@RequestParam(value = "source") String source,
			@RequestParam(value = "target") String target, @RequestBody String  relationship) {
		return reindex.ReindexByRelation(source, target, relationship);
	}
}
