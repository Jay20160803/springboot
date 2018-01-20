package com.jk51.configuration.dynamicDataSourceConfig;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;

import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.jk51.configuration.dynamicDataSourceConfig.util.DataSourceIdEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述: 数据源配置类，在该类中生成多个数据源实例并将其注入到 ApplicationContext 中
 * 作者: gaojie
 * 创建日期: 2017-12-27
 * 修改记录:
 */

@Configuration
public class DataSourceConfigurer implements EnvironmentAware{


    private Logger logger = LoggerFactory.getLogger(DataSourceConfigurer.class);
    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.dataSource")
    public DataSoureSettings masterDataSourceSettings(){
        return new DataSoureSettings();
    }

    @Bean
    @ConfigurationProperties(prefix = "custom.dataSource")
    public DataSoureSettings customDataSourceSettings(){
        return new DataSoureSettings();
    }


    @Bean
    @Primary
    public DruidDataSource master(){

        DataSoureSettings settings = masterDataSourceSettings();

        if(StringUtils.isEmpty(settings.getUrl())){
            logger.error("dataSource url 不能为空");
            throw new ApplicationContextException("dataSource url 不能为空");
        }

        return createDruidDataSource(settings);

    }

    @Bean
    public DruidDataSource customer(){

        DataSoureSettings settings = customDataSourceSettings();

        if(StringUtils.isEmpty(settings.getUrl())){
            logger.error("dataSource url 不能为空");
            throw new ApplicationContextException("dataSource url 不能为空");
        }

        return createDruidDataSource(settings);
    }

    @Bean
    public DataSource dynamicDataSource(){

        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();

        Map<Object,Object> dataSourceMap = new HashMap();
        dataSourceMap.put(DataSourceIdEnum.MASTER,master());
        dataSourceMap.put(DataSourceIdEnum.CUSTOMER,customer());

        dynamicRoutingDataSource.setDefaultTargetDataSource(master());
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        DynamicDataSourceContextHolder.dataSourceIds.addAll(dataSourceMap.keySet());

        return dynamicRoutingDataSource;
    }


    @Bean public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    @Bean
    public WallFilter getWallFilter(){
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(getWallConfig());
        return wallFilter;
    }

    @Bean
    public WallConfig getWallConfig(){
        WallConfig wallConfig = new WallConfig();
        wallConfig.setMultiStatementAllow(true);
        wallConfig.setCommentAllow(true);
        wallConfig.setMultiStatementAllow(true);
        return wallConfig;
    }


    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(dynamicDataSource());//指定数据源(这个必须有，否则报错)
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));//指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")));//指定xml文件位置
        fb.setTypeHandlersPackage("com.jk51.typeHandler");
        //向sqlSessionFactory中添加拦截器
        // 先注释 日志太多
//        fb.setPlugins(new Interceptor[]{db});
        return fb.getObject();
    }

    private DruidDataSource createDruidDataSource(DataSoureSettings settings){

        List<Filter> filteList = new ArrayList();
        filteList.add(getWallFilter());

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(settings.getDriverClassName());
        druidDataSource.setUrl(settings.getUrl());
        druidDataSource.setUsername(settings.getUsername());
        druidDataSource.setPassword(settings.getPassword());
        druidDataSource.setInitialSize(Integer.parseInt(settings.getInitialSize()));
        druidDataSource.setMinIdle(Integer.parseInt(settings.getMinIdle()));
        druidDataSource.setMaxActive(Integer.parseInt(settings.getMaxActive()));
        druidDataSource.setMaxWait(Integer.parseInt(settings.getMaxWait()));
        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(settings.getTimeBetweenEvictionRunsMillis()));
        druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(settings.getMinEvictableIdleTimeMillis()));
        druidDataSource.setValidationQuery(settings.getValidationQuery());
        druidDataSource.setTestWhileIdle(Boolean.parseBoolean(settings.getTestWhileIdle()));
        druidDataSource.setTestOnBorrow(Boolean.parseBoolean(settings.getTestOnBorrow()));
        druidDataSource.setTestOnReturn(Boolean.parseBoolean(settings.getTestOnReturn()));
        druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(settings.getPoolPreparedStatements()));
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(settings.getMaxPoolPreparedStatementPerConnectionSize()));
        druidDataSource.setProxyFilters(filteList);

        return druidDataSource;
    }


}
