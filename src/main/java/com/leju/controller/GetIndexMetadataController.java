package com.leju.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leju.esrestful.service.ElasticsearchIndexUtilitiesService;

@RestController
@ComponentScan
@RequestMapping(value = "/indexmetadata")
public class GetIndexMetadataController {
	@Autowired
	ElasticsearchIndexUtilitiesService indexutilities;

	@RequestMapping(value = "/getsettings", params = { "sourcename" })
	public String getsetting(@RequestParam("sourcename") String sourcename) {
		return indexutilities.GetIndexSettings(sourcename).toString();
	}

	@RequestMapping(value = "/getmappings", params = { "sourcename" })
	public String getmapping(@RequestParam("sourcename") String sourcename) {

		Map<String, Object> mappings = new HashMap<String, Object>();
		ObjectMapper objectmapper = new ObjectMapper();
		String result = "";
		ImmutableOpenMap<String, MappingMetaData> mappingmetadata = indexutilities.GetIndexMappings(sourcename);

		for (ObjectObjectCursor<String, MappingMetaData> c : mappingmetadata) {
			try {
				mappings.put(c.key, c.value.getSourceAsMap());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			result = objectmapper.writeValueAsString(mappings);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
}
