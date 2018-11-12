package com.config;

import com.engine.model.dao.repository.elasticsearch.CommonElasticsearchRepositoryImpl;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.shield.ShieldPlugin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by wuhao on 2016/4/18.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.engine.model.dao.repository.elasticsearch",repositoryBaseClass = CommonElasticsearchRepositoryImpl.class)
public class ElasticsearchConfig {

	private String cluster  = "yuqing";
	private String username = "aegisuser";
	private String password = "aegisshield";
	private String[] ips      = new String[]{"yqes.aegis-info.com","yqes2.aegis-info.com"};
	private int      port     = 9300;


	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ElasticsearchConfig.class);
//		CaseInfoRepository caseInfoRepository =  context.getBean(CaseInfoRepository.class);
//		System.out.println(caseInfoRepository);
	}


	@Bean
	public ElasticsearchOperations elasticsearchTemplate() {
		TransportClient.Builder builder = TransportClient.builder();
		Settings.Builder settingsBuilder = Settings.settingsBuilder().put("cluster.name", cluster);
		settingsBuilder.put("shield.user", username+":"+password);
		builder.addPlugin(ShieldPlugin.class);
		builder.settings(settingsBuilder.build());
		TransportClient client = builder.build();
		for(String ip:ips){
			try {
				client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		};
		return new ElasticsearchTemplate(client);
	}

}
