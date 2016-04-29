package com.leju.esrestful.service;

import java.util.Map;

import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;

public interface ElasticsearchIndexUtilitiesService {
	public Map<String, String> GetIndexSettings(String indexname) ;
	public ImmutableOpenMap<String, MappingMetaData> GetIndexMappings(String indexname);
	public boolean IndexExistes(String indexname);
	public boolean DeleteIndex(String indexname);
}
