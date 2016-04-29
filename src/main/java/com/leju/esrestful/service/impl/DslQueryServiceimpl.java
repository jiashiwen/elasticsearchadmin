package com.leju.esrestful.service.impl;

import java.net.UnknownHostException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.WrapperQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leju.esrestful.configuration.ElasticsearchConfiguration;
import com.leju.esrestful.service.DslQueryService;

@Service
public  class DslQueryServiceimpl implements DslQueryService {

	@Autowired
	private ElasticsearchConfiguration config;

	@Override
	public SearchResponse getResponse(String[] index,String dsl) {
		SearchResponse response = new SearchResponse();
		WrapperQueryBuilder wrapper = new WrapperQueryBuilder(dsl);
		try {
			response = config.esclient().prepareSearch().setIndices(index).setQuery(wrapper).execute().actionGet();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}


}
