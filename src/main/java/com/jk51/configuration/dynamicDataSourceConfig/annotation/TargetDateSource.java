package com.jk51.configuration.dynamicDataSourceConfig.annotation;

import com.jk51.configuration.dynamicDataSourceConfig.util.DataSourceIdEnum;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述: 在方法上使用，用于指定使用的数据源
 * 作者: gaojie
 * 创建日期: 2017-12-27
 * 修改记录:
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDateSource {

    DataSourceIdEnum value();
}
