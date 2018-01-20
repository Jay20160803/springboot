package com.jk51.module.quartz.util;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述: 异常工具类
 * 作者: wangzhengfei
 * 创建日期: 2017-02-22
 * 修改记录:
 */
public class ExceptionUtils {


    /**
     * 获取原始异常消息
     * @param t Throwable实例
     * @return 异常消息
     */
    public static String getCauseExpMsg(Throwable t){
        if(t != t.getCause() && t.getCause() != null){
            getCauseExpMsg(t.getCause());
        }
        return t.getMessage();
    }
}
