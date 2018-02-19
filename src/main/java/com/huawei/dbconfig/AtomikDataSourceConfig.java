package com.huawei.dbconfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.xa.DruidXADataSource;

@Configuration
public class AtomikDataSourceConfig {
	private static final String GIS_MAPPER_BASE_PACKAGE = "com.huawei.dao.gis";
    private static final String PLATFORMAT_MAPPER_BASE_PACKAGE = "com.huawei.dao.platformat";

    private static final String DATASOURCE_DRUID_PROPERTIES = "druid.properties";
    private static final String DATASOURCE_DRUID_PRIMARY_PROPERTIES = "datasource/druid-primary.properties";
    private static final String DATASOURCE_DRUID_BUSINESS_PROPERTIES = "datasource/druid-business.properties";

    private static final String CLASSPATH_MAPPER_XML = "classpath:mapper/*/*.xml";

    /**
     * druid 公共配置
     */
    private static Properties commonProperties;

    static {
        commonProperties = new Properties();
        InputStream in = AtomikDataSourceConfig.class.getClassLoader().getResourceAsStream(DATASOURCE_DRUID_PROPERTIES);
        try {
            commonProperties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties loadDruidProperties(String path) throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        properties.load(in);
        for (Map.Entry<Object, Object> entry : commonProperties.entrySet()) {
            properties.setProperty(entry.getKey().toString(), entry.getValue().toString());
        }
        return properties;
    }

    /**
     * 设置数据源
     *
     * @return
     * @throws IOException
     */
    @Primary
    @Bean
    public AtomikosDataSourceBean gisDataSource() throws IOException {
    	AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
        DruidXADataSource xaDataSource = new DruidXADataSource();
        dataSourceBean.setXaDataSourceClassName("gisDataSource");
        dataSourceBean.setXaDataSource(xaDataSource);
        return dataSourceBean;
    }


    @Bean
    public AtomikosDataSourceBean platformatDataSource() throws IOException {
    	AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
        DruidXADataSource xaDataSource = new DruidXADataSource();
        dataSourceBean.setXaDataSourceClassName("platformatDataSource");
        dataSourceBean.setXaDataSource(xaDataSource);
        return dataSourceBean;
    }

//    private AtomikosDataSourceBean getAtomikosDataSourceBean(String dataSourceProperties) throws IOException {
//        Properties properties = loadDruidProperties(dataSourceProperties);
//        AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
//        // 配置DruidXADataSource
//        DruidXADataSource xaDataSource = new DruidXADataSource();
//        xaDataSource.configFromPropety(properties);
//        // 设置置AtomikosDataSourceBean XADataSource
//        dataSourceBean.setXaDataSource(xaDataSource);
//        return dataSourceBean;
//    }

    /**
     * 设置{@link SqlSessionFactoryBean}的数据源
     * @param gisDataSource 主数据源
     * @return
     */
    @Primary
    @Bean
    public SqlSessionFactoryBean primarySqlSessionFactoryBean(@Qualifier("gisDataSource") AtomikosDataSourceBean gisDataSource) {
        return getSqlSessionFactoryBean(gisDataSource);
    }

    @Bean
    public SqlSessionFactoryBean businessSqlSessionFactoryBean(@Qualifier("platformatDataSource") AtomikosDataSourceBean platformatDataSource) {
       return getSqlSessionFactoryBean(platformatDataSource);
    }

    private SqlSessionFactoryBean getSqlSessionFactoryBean(AtomikosDataSourceBean dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(CLASSPATH_MAPPER_XML));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sqlSessionFactoryBean;
    }

    /**
     * 搜索{@link DataSourceConfig#GIS_MAPPER_BASE_PACKAGE} 包下的Mapper接口，并且将这些接口
     * 交由{@link MapperScannerConfigurer#sqlSessionFactoryBeanName} 属性设置的SqlSessionFactoryBean管理
     * @return
     */
    @Bean
    public MapperScannerConfigurer gisMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(GIS_MAPPER_BASE_PACKAGE);
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("gisSqlSessionFactoryBean");
        return mapperScannerConfigurer;
    }

    /**
     * 搜索{@link DataSourceConfig#PLATFORMAT_MAPPER_BASE_PACKAGE} 包下的Mapper接口，并且将这些接口
     * 交由{@link MapperScannerConfigurer#sqlSessionFactoryBeanName} 属性设置的SqlSessionFactoryBean管理
     * @return
     */
    @Bean
    public MapperScannerConfigurer platformatMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(PLATFORMAT_MAPPER_BASE_PACKAGE);
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("platformatSqlSessionFactoryBean");
        return mapperScannerConfigurer;
    }
}
