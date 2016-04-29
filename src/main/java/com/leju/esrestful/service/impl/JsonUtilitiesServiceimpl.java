package com.leju.esrestful.service.impl;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leju.esrestful.service.JsonUtilitiesService;

public class JsonUtilitiesServiceimpl implements JsonUtilitiesService {

	// json数组转object数组
	@Override
	public Object[] JsonToArray(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Object[] array = {};
		try {
			array = mapper.readValue(json, Object[].class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	// 用"."分割的字符串转数组
	@Override
	public String[] SpliteStringByDot(String string) {
		return string.split("\\.");
	}

	// 通过路径查找Json内容
	@Override
	public String FindJsonPathContent(String[] path, String jsonstring) {
		ObjectMapper mapper = new ObjectMapper();
		String pathcontent = "";
		try {
			JsonNode tempnode = mapper.readTree(jsonstring);
			for (int i = 0; i < path.length; i++) {
				tempnode = tempnode.path(path[i]);
			}
			pathcontent = tempnode.asText();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pathcontent;
	}

	
}
