package com.leju.esrestful.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.leju.com.entities.StatusEntity;

import org.elasticsearch.client.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * Created by hungnguyen on 12/16/14.
 */
@Configuration
//@PropertySource(value = "classpath:asyncstatus.properties")
// @EnableElasticsearchRepositories(basePackages = "com.leju.springbootes")
public class AsyncStatusConfiguration {
//	@Resource
//	private Environment environment;

	private static final String executing = "EXECUTING";
	private static final String error = "ERROR";
	private static final String success = "SUCCESS";
	
	private static Map<String,StatusEntity> statusmap=new HashMap<String,StatusEntity>();

	public static Map<String, StatusEntity> getStatusmap() {
		return statusmap;
	}

	public static void addStatusmap(String key,StatusEntity value) {
		statusmap.put(key, value);
	}

	public static String getExecuting() {
		return executing;
	}

	public static String getError() {
		return error;
	}

	public static String getSuccess() {
		return success;
	}

//	@Bean
//	public Client esclient() throws UnknownHostException {
//		int port = Integer.parseInt(environment.getProperty("elasticsearch.port"));
//
//		Settings setting = Settings.settingsBuilder()
//				.put("cluster.name", environment.getProperty("elasticsearch.clustername")).build();
//
//		Client client = TransportClient.builder().settings(setting).build().addTransportAddress(
//				new InetSocketTransportAddress(InetAddress.getByName(environment.getProperty("elasticsearch.host")),
//						port));
//
//		return client;
//	}

	// @Bean
	// public ElasticsearchOperations elasticsearchTemplate() {
	// return new ElasticsearchTemplate(client());
	// }

}
