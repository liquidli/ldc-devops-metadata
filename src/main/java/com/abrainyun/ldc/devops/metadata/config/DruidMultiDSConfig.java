package com.abrainyun.ldc.devops.metadata.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DruidMultiDSConfig {

	@Primary
	@Bean
	@ConfigurationProperties("spring.datasource.druid.one")
	public DataSource dataSourceOne() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.druid.two")
	public DataSource dataSourceTwo() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "dsOneSessionFactory")
	@Primary
	public SqlSessionFactory dsOneSessionFactory(@Qualifier("dataSourceOne") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
//		bean.setMapperLocations(
//				new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "dsOneTransactionManager")
	@Primary
	public DataSourceTransactionManager dsOneTransactionManager(@Qualifier("dataSourceOne") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "dsOneSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate dsOneSqlSessionTemplate(
			@Qualifier("dsOneSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean(name = "dsTwoSessionFactory")
	public SqlSessionFactory dsTwoSessionFactory(@Qualifier("dataSourceTwo") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
//		bean.setMapperLocations(
//				new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "dsTwoTransactionManager")
	public DataSourceTransactionManager dsTwoTransactionManager(@Qualifier("dataSourceTwo") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "dsTwoSqlSessionTemplate")
	public SqlSessionTemplate dsTwoSqlSessionTemplate(
			@Qualifier("dsTwoSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
