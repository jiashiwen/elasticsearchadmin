package com.leju.utilities.JsonUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProcessJson {

	private ObjectMapper mapper = new ObjectMapper();

	// json数组转object数组
	public Object[] JsonToArray(String json) {

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
	public String[] SpliteStringByDot(String string) {
		return string.split("\\.");
	}

	// 用"."分割的字符串转list
	public List<String> SpliteStringByDotAsList(String string) {
		List<String> splitedlist = new ArrayList<String>();
		Collections.addAll(splitedlist, string.split("\\."));
		return splitedlist;
	}

	//将json字符串转换为Map
	public Map<String, Object>  JsonToMap(String jsonstring){
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		try {
			jsonmap=mapper.readValue(jsonstring, HashMap.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonmap;
		
	}
	
	// 通过路径查找Json内容
	public String FindJsonPathContent(String[] path, String jsonstring) {

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

	// 通过路径查找多层级map对应的值
	public Object GetMapValuebyPath(Collection<String> propertiesOnThePath, Map<String, Object> sourcemap) {
		LinkedList<String> path = new LinkedList<String>(propertiesOnThePath);
		String lastProperty = path.removeLast();

		HashMap<String, Object> objectMap = (HashMap<String, Object>) sourcemap;

		Object result = "";
		while (!path.isEmpty()) {
			String property = path.poll();
			if (!objectMap.containsKey(property)) {
				return result;
			}
			objectMap = (HashMap<String, Object>) objectMap.get(property);
		}

		if (!objectMap.containsKey(lastProperty)) {
			return "";
		}

		result = objectMap.get(lastProperty).toString();

		return result;
	}

}
