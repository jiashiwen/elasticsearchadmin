package com.leju.esrestful.utilities;

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

import com.leju.esrestful.configuration.ElasticsearchConfiguration;

public class ElasticsearchIndexUtilties {
	@Autowired
	private ElasticsearchConfiguration config;

	public Map<String, String> GetIndexSettings(String indexname) throws UnknownHostException {
		ClusterState cs = config.esclient().admin().cluster().prepareState().setIndices(indexname).execute().actionGet()
				.getState();
		IndexMetaData imd = cs.getMetaData().index(indexname);
		return imd.getSettings().getAsMap();
	}

	public ImmutableOpenMap<String, MappingMetaData> GetIndexMappings(String indexname) {
		ImmutableOpenMap<String, MappingMetaData> mappingmetadata = null;
		try {
			GetMappingsResponse	res = config.esclient().admin().indices().getMappings(new GetMappingsRequest().indices(indexname)).get();
			
			mappingmetadata= res.mappings().get(indexname);
		} catch (UnknownHostException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mappingmetadata;
		
	}

}