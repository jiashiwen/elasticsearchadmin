package com.leju.esrestful.service;

import org.elasticsearch.action.search.SearchResponse;

/**
 * Created by hungnguyen on 12/28/14.
 */


public interface SetAsyncStatusService {
	public void SetAsyncStatus(String source,String target);
	
}