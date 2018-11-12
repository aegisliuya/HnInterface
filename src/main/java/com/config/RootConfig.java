package com.config;

import com.config.BaseJpaConfig;
import com.config.CacheConfig;
import com.config.RabbitMQConfig;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by 41401 on 2016/4/16.
 */
@Configuration
@EnableAsync(proxyTargetClass = true)
@Import({
		CacheConfig.class
		,/*ElasticsearchConfig.class,*/BaseJpaConfig.class, /*MonitorJpaConfig.class,*/
		RabbitMQConfig.class
})
@ComponentScan(basePackages = "com.controller")
@EnableScheduling
@EnableCaching
public class RootConfig {


	@Autowired
	private CacheManager cacheManager;


	@Bean(name="cacheManager")
	@Qualifier("cacheManager")
	public EhCacheCacheManager ehCacheCacheManager(){
		EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
		ehCacheCacheManager.setCacheManager(cacheManager);
 		return ehCacheCacheManager;
	}

}
