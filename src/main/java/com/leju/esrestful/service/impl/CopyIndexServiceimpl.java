package com.leju.esrestful.service.impl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
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
import org.elasticsearch.index.query.WrapperQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.leju.com.entities.StatusEntity;
import com.leju.esrestful.configuration.AsyncStatusConfiguration;
import com.leju.esrestful.configuration.ElasticsearchConfiguration;
import com.leju.esrestful.service.CopyIndexService;

@Service
public class CopyIndexServiceimpl implements CopyIndexService {

	@Autowired
	ElasticsearchConfiguration esconfig;

	@Override
	public Boolean CopyIndexMetadata(String source, String target) {
		// TODO Auto-generated method stub
		Boolean isok = true;

		// 获取source settings
		Settings.Builder settingbuilder = Settings.builder();
		try {
			ClusterState cs = esconfig.esclient().admin().cluster().prepareState().setIndices(source).execute()
					.actionGet().getState();
			IndexMetaData imd = cs.getMetaData().index(source);
			Settings settings = imd.getSettings();
			for (Map.Entry<String, String> m : settings.getAsMap().entrySet()) {
				if (m.getKey().equals("index.uuid") || m.getKey().equals("index.version.created")
						|| m.getKey().equals("index.creation_date")) {
					break;
				} else {
					settingbuilder.put(m.getKey(), m.getValue());
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 创建索引
		try {
			esconfig.esclient().admin().indices().prepareCreate(target).setSettings(settingbuilder).get();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 获取source mappings并添加到target

		try {
			GetMappingsResponse res = esconfig.esclient().admin().indices()
					.getMappings(new GetMappingsRequest().indices(source)).get();
			ImmutableOpenMap<String, MappingMetaData> mapping = res.mappings().get(source);
			for (ObjectObjectCursor<String, MappingMetaData> c : mapping) {
				esconfig.esclient().admin().indices().preparePutMapping(target).setType(c.key)
						.setSource(c.value.getSourceAsMap()).get();
			}
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isok;
	}

	@Override
	public Boolean CopyIndex(String source, String target) {
		// TODO Auto-generated method stub

		// 单线程scroll，多线程bulk写入
		int cpucores = Runtime.getRuntime().availableProcessors();
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(cpucores);
		QueryBuilder qb = QueryBuilders.matchAllQuery();

		try {
			SearchResponse scrollResp = esconfig.esclient().prepareSearch(source)
					.setSearchType(SearchType.DFS_QUERY_AND_FETCH).setScroll(new TimeValue(60000)).setQuery(qb)
					.setSize(500).execute().actionGet();
			do {
				final BulkRequestBuilder bulkRequest = esconfig.esclient().prepareBulk();
				for (SearchHit hit : scrollResp.getHits().getHits()) {
					bulkRequest.add(esconfig.esclient().prepareIndex(target, hit.getType().toString(), hit.getId())
							.setSource(hit.getSourceAsString()));
				}
				fixedThreadPool.execute(new Runnable() {
					public void run() {
						bulkRequest.execute();
					}
				});
				scrollResp = esconfig.esclient().prepareSearchScroll(scrollResp.getScrollId())
						.setScroll(new TimeValue(60000)).execute().actionGet();
			} while (scrollResp.getHits().getHits().length != 0);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	@Override
	@Async
	public void CopyIndexByQueryDsl(String source, String target, String DSL) {
		// TODO Auto-generated method stub
		// 开始运行时，执行插入运行状态
		StatusEntity se = new StatusEntity();
		se.setSource(source);
		se.setTarget(target);
		se.setStatus(AsyncStatusConfiguration.getExecuting());
		AsyncStatusConfiguration.addStatusmap(se.getTarget(), se);

		// 单线程scroll，bulk写入
		int cpucores = Runtime.getRuntime().availableProcessors();
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(cpucores);
		WrapperQueryBuilder wrapper = new WrapperQueryBuilder(DSL);

		try {
			SearchResponse scrollResp = esconfig.esclient().prepareSearch(source)
					.setSearchType(SearchType.DFS_QUERY_AND_FETCH).setScroll(new TimeValue(60000)).setQuery(wrapper)
					.setSize(500).execute().actionGet();
			do {
				final BulkRequestBuilder bulkRequest = esconfig.esclient().prepareBulk();
				for (SearchHit hit : scrollResp.getHits().getHits()) {
					bulkRequest.add(esconfig.esclient().prepareIndex(target, hit.getType().toString(), hit.getId())
							.setSource(hit.getSourceAsString()));
				}
				fixedThreadPool.execute(new Runnable() {
					public void run() {
						bulkRequest.execute();
					}
				});
				scrollResp = esconfig.esclient().prepareSearchScroll(scrollResp.getScrollId())
						.setScroll(new TimeValue(60000)).execute().actionGet();
			} while (scrollResp.getHits().getHits().length != 0);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			se.setStatus(AsyncStatusConfiguration.getError());
			AsyncStatusConfiguration.addStatusmap(se.getTarget(), se);
		}

		// 运行介绍，执行插入成功状态
		se.setStatus(AsyncStatusConfiguration.getSuccess());
		AsyncStatusConfiguration.addStatusmap(se.getTarget(), se);
	}

}
