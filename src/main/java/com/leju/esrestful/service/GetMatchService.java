package com.leju.esrestful.service;

import java.net.UnknownHostException;

import org.elasticsearch.action.search.SearchResponse;

/**
 * Created by hungnguyen on 12/28/14.
 */

public interface GetMatchService {
	public SearchResponse getResponse() throws UnknownHostException;
	public SearchResponse getResponse(String keyword) throws UnknownHostException;
}