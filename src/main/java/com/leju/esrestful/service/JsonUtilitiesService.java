package com.leju.esrestful.service;

import org.elasticsearch.action.search.SearchResponse;

/**
 * Created by hungnguyen on 12/28/14.
 */

public interface JsonUtilitiesService {

	Object[] JsonToArray(String json);
	String[] SpliteStringByDot(String string);
	String FindJsonPathContent(String[] path, String jsonstring);

}