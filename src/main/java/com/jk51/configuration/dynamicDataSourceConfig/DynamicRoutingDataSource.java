package com.jk51.configuration.dynamicDataSourceConfig;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述: 动态数据源
 * 作者: gaojie
 * 创建日期: 2017-12-27
 * 修改记录:
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {

        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
