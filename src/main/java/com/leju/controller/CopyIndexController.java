package com.leju.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leju.esrestful.service.CopyIndexService;
import com.leju.esrestful.service.ElasticsearchIndexUtilitiesService;

@RestController
@ComponentScan

public class CopyIndexController {
	@Autowired
	CopyIndexService copyindex;

	@Autowired
	ElasticsearchIndexUtilitiesService esutilt;

	@RequestMapping(value = "/copyindex")
	public String CopyIndex(@RequestParam(value = "source") String source,
			@RequestParam(value = "target") String target, @RequestParam(value = "type") String type,
			@RequestParam(value = "force", required = false) boolean force) {
		String result = "copy " + source + "  to " + target + " success!";

		if (force && esutilt.IndexExistes(target)) {
			esutilt.DeleteIndex(target);
		}

		if (type.equals("metadata")) {
			if (copyindex.CopyIndexMetadata(source, target)) {
				return result;
			} else {
				result = "copy " + source + "  to " + target + " False!";
			}
		} else if (type.endsWith("data")) {
			if (copyindex.CopyIndex(source, target)) {
				return result;
			} else {
				result = "copy " + source + "  to " + target + " False!";
			}
		}
		return result;
	}
}
