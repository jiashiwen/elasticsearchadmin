package com.leju.esrestful.service.impl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leju.esrestful.configuration.ElasticsearchConfiguration;
import com.leju.esrestful.service.ReindexService;
import com.leju.utilities.JsonUtilities.JsonUpdater;
import com.leju.utilities.JsonUtilities.ProcessJson;

@Service
public class ReindexServiceimpl implements ReindexService {
	@Autowired
	private ElasticsearchConfiguration config;

	@Autowired
	ElasticsearchIndexUtilitiesServiceimpl esutilities;

	private ProcessJson processjson = new ProcessJson();
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public String Reindex(String SourceName, String TargetName) {
		// TODO Auto-generated method stub
		String mapping = null;
		ClusterState clusterstate;
		try {
			clusterstate = config.esclient().admin().cluster().prepareState().setIndices(SourceName).execute()
					.actionGet().getState();
			IndexMetaData imd = clusterstate.getMetaData().index(SourceName);
			mapping = imd.mapping("news").getSourceAsMap().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping = "aaaa";
	}

	@Override
	public boolean ReindexByRelation(String source, String target, String relationship) {
		// TODO Auto-generated method stub
		if (!esutilities.IndexExistes(target)) {
			return false;
		}

		Map<String, Object> relationmap = processjson.JsonToMap(relationship);
		String sourcestring = "";
		
		// 单线程scroll，多线程bulk写入
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
		QueryBuilder qb = QueryBuilders.matchAllQuery();

		try {
			SearchResponse scrollResp = config.esclient().prepareSearch(source)
					.setSearchType(SearchType.DFS_QUERY_AND_FETCH).setScroll(new TimeValue(60000)).setQuery(qb)
					.setSize(500).execute().actionGet();

			do {
				final BulkRequestBuilder bulkRequest = config.esclient().prepareBulk();
			
				for (SearchHit hit : scrollResp.getHits().getHits()) {
					// 如果对应关系中包含type名称则执行如下步骤
					if (relationmap.containsKey(hit.type())) {
						// 如果对应关系中包含type名称则执行如下步骤
						Map<String, Object> sourcemap = hit.getSource();
						this.ChangeMapByRelation(sourcemap,
								(Map<String, ArrayList<String>>) relationmap.get(hit.type()));
						try {
							// 将souremap转换为json string
							sourcestring = mapper.writeValueAsString(sourcemap);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						bulkRequest.add(config.esclient().prepareIndex(target, hit.getType().toString(), hit.getId())
								.setSource(sourcestring));
						sourcestring = "";
					} else {
						bulkRequest.add(config.esclient().prepareIndex(target, hit.getType().toString(), hit.getId())
								.setSource(hit.getSourceAsString()));
					}
				}
				fixedThreadPool.execute(new Runnable() {
					public void run() {
						bulkRequest.execute();
					}
				});
				scrollResp = config.esclient().prepareSearchScroll(scrollResp.getScrollId())
						.setScroll(new TimeValue(60000)).execute().actionGet();
			} while (scrollResp.getHits().getHits().length != 0);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * 用于通过对应关系处理sourcemap，例如：relation为{_origin.media=[_origin.p_id,
	 * _origin.media, _origin.c_id]}，表示目标的_origin.media字段由源的_origin.p_id,
	 * _origin.media和_origin.c_id构成
	 */
	private Map<String, Object> ChangeMapByRelation(Map<String, Object> source,
			Map<String, ArrayList<String>> relation) {
		JsonUpdater jsonupdater = new JsonUpdater(source);
		String targetstring = "";
		for (String key : relation.keySet()) {

			// 获取目标field需要的源fields列表
			ArrayList<String> fields = relation.get(key);

			List<String> targetfield = processjson.SpliteStringByDotAsList(key);

			// 判断对应关系中数组是否为空，如果为空，删除该目标字段
			if (fields.size() == 0) {
				jsonupdater.RemoveByPath(targetfield);
			} else {
				//如果不为空，装配目标field
				for (int i = 0; i < fields.size(); i++) {
					List<String> splitedlist = processjson.SpliteStringByDotAsList(fields.get(i));
					String tempstring = processjson.GetMapValuebyPath(splitedlist, source).toString();
					if (!tempstring.equals("")) {
						targetstring = targetstring + tempstring + "\r\n";
					}
				}
				// 替换相关目标字段
				jsonupdater.UpdateByPath(targetfield, targetstring);
				targetstring = "";
			}
		}
		return source;
	}
}
