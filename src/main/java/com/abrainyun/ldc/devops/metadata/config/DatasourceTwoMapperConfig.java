package com.abrainyun.ldc.devops.metadata.config;

import org.springframework.context.annotation.Configuration;

import com.abrainyun.ldc.devops.metadata.annotations.UseDataSourceTwo;

import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = {
		"com.abrainyun.ldc.devops.metadata.dao.mapper" }, annotationClass = UseDataSourceTwo.class, sqlSessionTemplateRef = "dsTwoSqlSessionTemplate")
public class DatasourceTwoMapperConfig {

}
