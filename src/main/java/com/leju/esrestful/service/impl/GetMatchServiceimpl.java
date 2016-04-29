package com.leju.esrestful.service.impl;

import java.net.UnknownHostException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leju.esrestful.configuration.ElasticsearchConfiguration;
import com.leju.esrestful.service.GetMatchService;

@Service
public class GetMatchServiceimpl implements GetMatchService {

	@Autowired
	private ElasticsearchConfiguration config;

	@Override
	public SearchResponse getResponse() {
		// TODO Auto-generated method stub

		SearchResponse response = new SearchResponse();
		try {
			response = config.esclient().prepareSearch("news").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.matchQuery("_content", "王石")) // Query
					.setFrom(0).setSize(6).setExplain(true).execute().actionGet();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public SearchResponse getResponse(String keyword) {
		// TODO Auto-generated method stub

		SearchResponse response = new SearchResponse();
//		 使用SearchSourceBuilder实现field功能
//		 SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		 searchSourceBuilder.query(QueryBuilders.matchQuery("_content",
//		 keyword));
//		 searchSourceBuilder.field("_content");
//		 searchSourceBuilder.field("_title");
//		 try {
//		 response =
//		 config.esclient().prepareSearch("news").addField("_id").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//		 .setExtraSource(searchSourceBuilder.toString()).execute().actionGet();
//		 } catch (UnknownHostException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }
		
		 
		 SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		 searchSourceBuilder.query(QueryBuilders.matchQuery("_content",
		 keyword));
		 searchSourceBuilder.fetchSource(new String[] { "_content", "_title" }, null);
		 try {
		 response =
		 config.esclient().prepareSearch("news").addField("_id").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		 .setExtraSource(searchSourceBuilder.toString()).execute().actionGet();
		 } catch (UnknownHostException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 
		/*
		 * try { response =
		 * config.esclient().prepareSearch("news").setSearchType(SearchType.
		 * DFS_QUERY_THEN_FETCH) .setQuery(QueryBuilders.matchQuery("_content",
		 * keyword))// Query
		 * .setFrom(0).setSize(6).setExplain(true).execute().actionGet(); }
		 * catch (UnknownHostException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		
		// 使用setFetchSource实现DSL中的_source:["a","b“]功能
//		try {
//			response = config.esclient().prepareSearch("news").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//					.setQuery(QueryBuilders.matchQuery("_content", keyword))
//					.setFetchSource(new String[] { "_content", "_title" }, null)// Query
//					.setFrom(0).setSize(6).execute().actionGet();
//		} catch (UnknownHostException e) { // TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return response;
	}
}
