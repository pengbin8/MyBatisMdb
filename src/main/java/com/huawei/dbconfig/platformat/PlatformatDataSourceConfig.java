package com.huawei.dbconfig.platformat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

@Configuration
@MapperScan(basePackages=PlatformatDataSourceConfig.PACKAGE,sqlSessionTemplateRef = "platformatSqlSessionTemplate")
@ConfigurationProperties(value = "classpath:application-${spring.profiles.active}.properties")
public class PlatformatDataSourceConfig {
	static final String PACKAGE="com.huawei.dao.platformat";
	static final String MAPPER_LOCATION="classpath:mapper/platformat/*.xml";
	
	@Value("${platformat.jdbc.driverClassName}")
	private String driverClass;
	@Value("${platformat.jdbc.username}")
	private String username;
	@Value("${platformat.jdbc.password}")
	private String password;
	@Value("${platformat.jdbc.url}")
	private String jdbcUrl;

	@Bean(name="platformatDataSource")
	public DataSource platformatDataSource() throws SQLException {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(jdbcUrl);
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(password);
		mysqlXaDataSource.setUser(username);
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("platformatDataSource");

		xaDataSource.setMinPoolSize(5);
		xaDataSource.setMaxPoolSize(20);
		xaDataSource.setMaxLifetime(5*36000);
		xaDataSource.setBorrowConnectionTimeout(5*3000);
		xaDataSource.setLoginTimeout(5*6000);
		xaDataSource.setMaintenanceInterval(6*60000);
		xaDataSource.setMaxIdleTime(20*36000);
		xaDataSource.setTestQuery("select 'x'");
		return xaDataSource;
	}
	
	@Bean(name = "platformatSqlSessionFactory")
	public SqlSessionFactory platformatSqlSessionFactory(@Qualifier("platformatDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(
				new PathMatchingResourcePatternResolver()
				.getResources(PlatformatDataSourceConfig.MAPPER_LOCATION));
		return bean.getObject();
	}

	@Bean(name = "platformatSqlSessionTemplate")
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("platformatSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
