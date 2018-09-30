package com.abrainyun.ldc.devops.metadata.config;

import java.util.Arrays;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisConfig.class);
	
	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		LOGGER.info("MyBatisConfig.configurationCustomizer");
		
		
		ConfigurationCustomizer config = new ConfigurationCustomizer() {
			@Override
			public void customize(org.apache.ibatis.session.Configuration configuration) {
				LOGGER.info("MyBatisConfig.customize");
				ResultMapping resultMapping = new ResultMapping.Builder(configuration, "jsonMapped", "json",
						JsonTypeHandler.class).build();

				ResultMap jsonResultMap = new ResultMap.Builder(configuration, "JsonTypeHandler", String.class,
						Arrays.asList(resultMapping)).build();

				configuration.addResultMap(jsonResultMap);
			}
		};
		return config;
	}
}
