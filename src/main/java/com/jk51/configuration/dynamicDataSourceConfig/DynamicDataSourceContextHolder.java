package com.jk51.configuration.dynamicDataSourceConfig;

import com.jk51.configuration.dynamicDataSourceConfig.util.DataSourceIdEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述: 动态数据源上下文切换
 * 作者: gaojie
 * 创建日期: 2017-12-27
 * 修改记录:
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<DataSourceIdEnum> contextHoler = new ThreadLocal(){

        @Override
        protected DataSourceIdEnum initialValue(){
            return DataSourceIdEnum.MASTER;
        }
    };

    public static List<Object> dataSourceIds = new ArrayList<>();


    public static void setDataSourceType(DataSourceIdEnum dataSourceType){

        contextHoler.set(dataSourceType);
    }

    public static DataSourceIdEnum getDataSourceType(){
        return contextHoler.get();
    }

    public static void clearDataSourceType(){
        contextHoler.remove();
    }


    public static boolean containsDataSource(String dataSourceId){
        return dataSourceId.contains(dataSourceId);
    }
}
