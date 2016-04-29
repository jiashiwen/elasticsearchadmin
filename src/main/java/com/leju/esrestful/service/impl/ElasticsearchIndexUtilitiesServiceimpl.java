package com.leju.esrestful.service.impl;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leju.esrestful.configuration.ElasticsearchConfiguration;
import com.leju.esrestful.service.ElasticsearchIndexUtilitiesService;

@Service
public class ElasticsearchIndexUtilitiesServiceimpl implements ElasticsearchIndexUtilitiesService {
	@Autowired
	private ElasticsearchConfiguration config;
	private ImmutableOpenMap<String, MappingMetaData> mappingmetadata;
    private Map<String, String> settings;
    
	// 获取索引setting
	@Override
	public Map<String, String> GetIndexSettings(String indexname) {
	
		try {
			ClusterState cs = config.esclient().admin().cluster().prepareState().setIndices(indexname).execute()
					.actionGet().getState();
			IndexMetaData imd = cs.getMetaData().index(indexname);
			settings = imd.getSettings().getAsMap();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return settings;
	}

	// 获取索引mapping
	@Override
	public ImmutableOpenMap<String, MappingMetaData> GetIndexMappings(String indexname) {
		try {
			GetMappingsResponse res = config.esclient().admin().indices()
					.getMappings(new GetMappingsRequest().indices(indexname)).get();
			mappingmetadata = res.mappings().get(indexname);
		} catch (UnknownHostException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mappingmetadata;
	}

	//判断索引是否存在
	@Override
	public boolean IndexExistes(String indexname) {
		boolean exists = false;
		try {
			exists= config.esclient().admin().indices().prepareExists(indexname).execute().get().isExists();
		} catch (UnknownHostException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exists;	
	}

	@Override
	public boolean DeleteIndex(String indexname) {
		boolean isAcknowledged = false;
		try {
			isAcknowledged=config.esclient().admin().indices().prepareDelete(indexname).execute().get().isAcknowledged();
		} catch (UnknownHostException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return isAcknowledged;
	}
	
}