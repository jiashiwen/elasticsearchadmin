package com.leju.utilities.JsonUtilities;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class JsonUpdater {

	private HashMap<String, Object> jsonMap;

	public JsonUpdater(Map<String, Object> jsonMap) {
		this.jsonMap = (HashMap<String, Object>) jsonMap;
	}

	public boolean UpdateByPath(Collection<String> propertiesOnThePath, Object newValue) {
		LinkedList<String> path = new LinkedList<String>(propertiesOnThePath);
		String lastProperty = path.removeLast();

		HashMap<String, Object> objectMap = jsonMap;
		while (!path.isEmpty()) {
			String property = path.poll();
			if (!objectMap.containsKey(property)) {
				return false;
			}

			objectMap = (HashMap<String, Object>) objectMap.get(property);
		}

		if (!objectMap.containsKey(lastProperty)) {
			return false;
		}

		objectMap.put(lastProperty, newValue);

		return false;
	}
	
	public  boolean RemoveByPath(Collection<String> propertiesOnThePath) {
		LinkedList<String> path = new LinkedList<String>(propertiesOnThePath);
		String lastProperty = path.removeLast();
		HashMap<String, Object> objectMap = this.jsonMap;
	
		for(int i=0;i<path.size();i++){
			String property = path.get(i);
			if (!objectMap.containsKey(property)) {
				return false;
			}
			objectMap = (HashMap<String, Object>) objectMap.get(property);
		}
		
		if (!objectMap.containsKey(lastProperty)) {
			return false;
		}
		objectMap.remove(lastProperty);	
		if(objectMap.size()==0 && path.size()>0){
			this.RemoveByPath(path);
		}
		return true;
	}
}
