package com.huawei.dbconfig.gis;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(basePackages=GisDataSourceConfig.PACKAGE,sqlSessionFactoryRef="gisSqlSessionFactory")
@ConfigurationProperties(value = "classpath:application-${spring.profiles.active}.properties")
public class GisDataSourceConfig {

	static final String PACKAGE="com.huawei.dao.gis";
	static final String MAPPER_LOCATION="classpath:mapper/gis/*.xml";
	@Value("${spring.jdbc.driverClassName}")
	private String driverClass;
	@Value("${spring.jdbc.username}")
	private String username;
	@Value("${spring.jdbc.password}")
	private String password;
	@Value("${spring.jdbc.url}")
	private String jdbcUrl;

	@Bean(name="gisDataSource")
	@Primary
	public DataSource gisDataSource() {
		DruidDataSource dtSource=new DruidDataSource();
		dtSource.setDriverClassName(driverClass);
		dtSource.setUsername(username);
		dtSource.setPassword(password);
		dtSource.setUrl(jdbcUrl);
		dtSource.setPoolPreparedStatements(false);
		dtSource.setMaxActive(50);
		dtSource.setMinIdle(5);
		dtSource.setMaxWait(5*60000);
		dtSource.setTimeBetweenEvictionRunsMillis(5*60000);
		dtSource.setMinEvictableIdleTimeMillis(5*60000);
		dtSource.setValidationQuery("select 'x'");
		return dtSource;
	}
	@Bean(name="gisTransactionManager")
	@Primary
	public DataSourceTransactionManager getTransactionManager() {
		return new DataSourceTransactionManager(gisDataSource());
	}
	
	@Bean(name="gisSqlSessionFactory")
	@Primary
	public SqlSessionFactory getSqlSessionFactory(@Qualifier(value="gisDataSource") DataSource dtSource) throws Exception {
		final SqlSessionFactoryBean sqlsession=new SqlSessionFactoryBean();
		sqlsession.setDataSource(dtSource);
		sqlsession.setMapperLocations(
				new PathMatchingResourcePatternResolver()
				.getResources(GisDataSourceConfig.MAPPER_LOCATION));
		return sqlsession.getObject();
	}
	
}
