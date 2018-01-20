package com.jk51.configuration.dynamicDataSourceConfig.advice;

import com.jk51.configuration.dynamicDataSourceConfig.DynamicDataSourceContextHolder;
import com.jk51.configuration.dynamicDataSourceConfig.annotation.TargetDateSource;
import com.jk51.configuration.dynamicDataSourceConfig.util.DataSourceIdEnum;
import org.apache.ibatis.binding.MapperMethod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2017-12-27
 * 修改记录:
 */

@Component
@Aspect
@Order(-1)
public class DynamicDataSourceAspect {

    //private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);


    @Before("@annotation(an)" )
    public void changeDataSource(JoinPoint point,TargetDateSource an) throws Throwable{

        DataSourceIdEnum dsId = an.value();

        if(!DynamicDataSourceContextHolder.containsDataSource(dsId.toString())){

            System.err.println("数据源["+dsId+"]不存在，使用默认数据源["+point.getSignature().getName()+"]");
        }else  {

            System.out.println("使用数据源["+dsId+"]"+point.getSignature().getName());
            DynamicDataSourceContextHolder.setDataSourceType(dsId);
        }

    }

    @After("@annotation(an)" )
    public void restoreDataSource(JoinPoint point,TargetDateSource an){


        DataSourceIdEnum dsId = an.value();

        System.out.println("RevertDataSource : {} > {}"+dsId+point.getSignature());
        DynamicDataSourceContextHolder.clearDataSourceType();
    }


/*    private DataSourceIdEnum getDataSourceId(JoinPoint point){

        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();

        //获取访问的方法名
        String methodName = point.getSignature().getName();

        //得到方法的参数类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();

        DataSourceIdEnum dsId = DataSourceIdEnum.MASTER;
        try{

            Method method = className.getMethod(methodName,argClass);
            if(method.isAnnotationPresent(TargetDateSource.class)){

                TargetDateSource targetDateSource = method.getAnnotation(TargetDateSource.class);
                dsId = targetDateSource.value();
            }
        }catch (Exception e){

            logger.error("获取TargetDateSource注解失败");
        }

        return dsId;
    }*/
}
