package com.leju.esrestful.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.elasticsearch.client.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;

/**
 * Created by hungnguyen on 12/16/14.
 */
@Configuration
@PropertySource(value = "classpath:elasticsearch.properties")
// @EnableElasticsearchRepositories(basePackages = "com.leju.springbootes")
public class ElasticsearchConfiguration {
	@Resource
	private Environment environment;

	@Bean
	public Client esclient() throws UnknownHostException {
		int port = Integer.parseInt(environment.getProperty("elasticsearch.port"));
		Settings setting = Settings.settingsBuilder()
				.put("cluster.name", environment.getProperty("elasticsearch.clustername")).build();
		Client client = TransportClient.builder().settings(setting).build().addTransportAddress(
				new InetSocketTransportAddress(InetAddress.getByName(environment.getProperty("elasticsearch.host")),
						port));

		return client;

	}

	// @Bean
	// public ElasticsearchOperations elasticsearchTemplate() {
	// return new ElasticsearchTemplate(client());
	// }

}
