package com.abrainyun.ldc.devops.metadata.config;

import com.abrainyun.ldc.devops.metadata.annotations.UseDataSourceOne;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {
		"com.abrainyun.ldc.devops.metadata.dao.mapper" }, annotationClass = UseDataSourceOne.class, sqlSessionTemplateRef = "dsOneSqlSessionTemplate")
public class DatasourceOneMapperConfig {

}
